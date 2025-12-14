package edu.thepower.accesodatos.tercerapractica.ejercicio2.opcional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Clase de utilidades para migrar configuraciones entre
 * archivos .properties y una tabla de base de datos.
 *
 * Tabla utilizada: configuracion (clave VARCHAR PK, valor VARCHAR)
 */
public class MigradorConfiguracionBD {

    /**
     * Migra todas las propiedades de archivo a base de datos.
     * Si ya existe alguna clave en la tabla, se actualiza su valor.
     *
     * @param archivo ruta del archivo Properties
     * @param conn    conexión JDBC
     * @return número de propiedades migradas
     * @throws IOException  si hay error al leer archivo
     * @throws SQLException si hay error de BD
     */
    public static int migrarPropertiesABD(String archivo, Connection conn)
            throws IOException, SQLException {

        // Me aseguro de que la tabla existe
        crearTablaConfiguracionSiNoExiste(conn);

        Properties props = new Properties();
        File fichero = new File(archivo);

        if (!fichero.exists()) {
            throw new IOException("El archivo de properties no existe: " + archivo);
        }

        // Cargo el archivo properties
        try (FileInputStream fis = new FileInputStream(fichero)) {
            props.load(fis);
        }

        System.out.println("Migrando propiedades a BD...");

        String sqlInsert = """
                INSERT INTO configuracion (clave, valor)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE valor = VALUES(valor)
                """;

        int contador = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            for (String clave : props.stringPropertyNames()) {
                String valor = props.getProperty(clave);

                System.out.println(" " + clave + " = " + valor);

                pstmt.setString(1, clave);
                pstmt.setString(2, valor);
                pstmt.executeUpdate();
                contador++;
            }
        }

        System.out.println("Propiedades migradas a BD: " + contador);
        return contador;
    }

    /**
     * Exporta configuración de base de datos a archivo Properties.
     *
     * @param conn    conexión JDBC
     * @param archivo ruta del archivo destino
     * @return número de propiedades exportadas
     * @throws SQLException si hay error de BD
     * @throws IOException  si hay error al escribir
     */
    public static int exportarBDaProperties(Connection conn, String archivo)
            throws SQLException, IOException {

        crearTablaConfiguracionSiNoExiste(conn);

        Properties props = new Properties();

        String sqlSelect = "SELECT clave, valor FROM configuracion";

        int contador = 0;

        System.out.println("Exportando configuración de BD a archivo...");

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {

            while (rs.next()) {
                String clave = rs.getString("clave");
                String valor = rs.getString("valor");

                props.setProperty(clave, valor);
                contador++;
            }
        }

        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            props.store(fos, "Configuración exportada desde la base de datos");
        }

        System.out.println("Propiedades exportadas a archivo: " + contador);
        return contador;
    }

    /**
     * Sincroniza: actualiza BD con valores de Properties que hayan cambiado.
     * La "verdad" se toma del archivo .properties.
     *
     * @param archivo ruta del archivo Properties
     * @param conn    conexión JDBC
     * @return número de propiedades actualizadas o insertadas
     * @throws IOException  si hay error al leer
     * @throws SQLException si hay error de BD
     */
    public static int sincronizarPropiedades(String archivo, Connection conn)
            throws IOException, SQLException {

        crearTablaConfiguracionSiNoExiste(conn);

        // 1. Cargo las propiedades del archivo
        Properties props = new Properties();
        File fichero = new File(archivo);

        if (!fichero.exists()) {
            throw new IOException("El archivo de properties no existe: " + archivo);
        }

        try (FileInputStream fis = new FileInputStream(fichero)) {
            props.load(fis);
        }

        // 2. Cargo la configuración actual de la BD en un mapa
        Map<String, String> configBD = new HashMap<>();
        String sqlSelect = "SELECT clave, valor FROM configuracion";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {

            while (rs.next()) {
                configBD.put(rs.getString("clave"), rs.getString("valor"));
            }
        }

        int cambios = 0;

        // 3. Para cada clave del archivo, comparo y actualizo/creo si hace falta
        String sqlInsert = "INSERT INTO configuracion (clave, valor) VALUES (?, ?)";
        String sqlUpdate = "UPDATE configuracion SET valor = ? WHERE clave = ?";

        try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

            for (String clave : props.stringPropertyNames()) {
                String valorArchivo = props.getProperty(clave);
                String valorBD = configBD.get(clave);

                if (valorBD == null) {
                    // No existe en BD → insert
                    pstmtInsert.setString(1, clave);
                    pstmtInsert.setString(2, valorArchivo);
                    pstmtInsert.executeUpdate();
                    cambios++;
                } else if (!valorArchivo.equals(valorBD)) {
                    // Existe pero el valor es distinto → update
                    pstmtUpdate.setString(1, valorArchivo);
                    pstmtUpdate.setString(2, clave);
                    pstmtUpdate.executeUpdate();
                    cambios++;
                }
            }
        }

        System.out.println("Propiedades sincronizadas (actualizadas o nuevas): " + cambios);
        return cambios;
    }

    // =========================
    // Métodos privados de ayuda
    // =========================

    /**
     * Crea la tabla 'configuracion' si no existe.
     *
     * @param conn conexión JDBC
     * @throws SQLException si hay error de BD
     */
    private static void crearTablaConfiguracionSiNoExiste(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS configuracion (
                    clave VARCHAR(100) PRIMARY KEY,
                    valor VARCHAR(255) NOT NULL
                )
                """;

        System.out.println("Creando tabla 'configuracion'...");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
}

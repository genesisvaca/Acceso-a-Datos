package edu.thepower.accesodatos.tercerapractica.ejercicio1.opcional;


import edu.thepower.accesodatos.tercerapractica.ejercicio1.Producto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidades para exportar e importar productos
 * entre una base de datos (tabla 'productos') y un archivo binario.
 */
public class GestorBackupProductos {

    /**
     * Exporta todos los productos de la base de datos a un archivo binario.
     * El archivo se sobrescribe si ya existía.
     *
     * @param conn    conexión JDBC activa
     * @param archivo ruta del archivo destino
     * @return número de productos exportados
     * @throws SQLException si hay error de BD
     * @throws IOException  si hay error de archivo
     */
    public static int exportarProductos(Connection conn, String archivo)
            throws SQLException, IOException {

        String sqlSelect = "SELECT id, nombre, precio, stock FROM productos";

        int contadorExportados = 0;

        System.out.println("Exportando productos...");

        // Recojo los productos de la BD
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");

                productos.add(new Producto(id, nombre, precio, stock));
            }
        }

        // Escribo los productos en el archivo binario
        try (DataOutputStream salidaBinaria = new DataOutputStream(
                new FileOutputStream(archivo))) {

            for (Producto producto : productos) {
                salidaBinaria.writeInt(producto.getId());
                salidaBinaria.writeUTF(producto.getNombre());
                salidaBinaria.writeDouble(producto.getPrecio());
                salidaBinaria.writeInt(producto.getStock());

                System.out.println("Producto exportado: ID=" + producto.getId()
                        + ", Nombre=" + producto.getNombre());
                contadorExportados++;
            }
        }

        return contadorExportados;
    }

    /**
     * Importa productos desde un archivo binario a la base de datos.
     * Inserta cada producto leído en la tabla 'productos'.
     *
     * @param conn    conexión JDBC activa
     * @param archivo ruta del archivo fuente
     * @return número de productos importados
     * @throws SQLException si hay error de BD
     * @throws IOException  si hay error de archivo
     */
    public static int importarProductos(Connection conn, String archivo)
            throws SQLException, IOException {

        File fichero = new File(archivo);
        if (!fichero.exists()) {
            throw new IOException("El archivo " + archivo + " no existe. No se puede importar.");
        }

        int contadorImportados = 0;
        System.out.println("Importando productos...");

        // SQL de inserción. Asumo que el id viene del archivo y será la PK.
        String sqlInsert = "INSERT INTO productos (id, nombre, precio, stock) VALUES (?, ?, ?, ?)";

        // Podemos usar una transacción para que quede más limpio si algo falla a medias
        boolean autoCommitOriginal = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try (DataInputStream entradaBinaria = new DataInputStream(
                new FileInputStream(fichero));
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            while (true) {
                try {
                    int id = entradaBinaria.readInt();
                    String nombre = entradaBinaria.readUTF();
                    double precio = entradaBinaria.readDouble();
                    int stock = entradaBinaria.readInt();

                    pstmt.setInt(1, id);
                    pstmt.setString(2, nombre);
                    pstmt.setDouble(3, precio);
                    pstmt.setInt(4, stock);

                    pstmt.executeUpdate();

                    System.out.println("Producto importado: ID=" + id + ", Nombre=" + nombre);
                    contadorImportados++;

                } catch (EOFException fin) {
                    // Fin del archivo, salgo del bucle
                    break;
                }
            }

            // Si tdo ha ido bien, confirmo la transacción
            conn.commit();

        } catch (SQLException | IOException e) {
            // Si algo falla, intento deshacer la transacción
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            throw e;
        } finally {
            // Restauro el auto-commit original
            try {
                conn.setAutoCommit(autoCommitOriginal);
            } catch (SQLException e) {
                System.err.println("No se pudo restaurar el autoCommit: " + e.getMessage());
            }
        }

        return contadorImportados;
    }
}

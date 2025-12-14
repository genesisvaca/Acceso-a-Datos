package edu.thepower.accesodatos.tercerapractica.ejercicio2.opcional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MainMigradorConfiguracion {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "root1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // Migrar de archivo a BD
            int migradas = MigradorConfiguracionBD.migrarPropertiesABD("app.properties", conn);
            System.out.println("Propiedades migradas a BD: " + migradas);

            // Modificar en BD (como en el ejemplo del enunciado)
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE configuracion SET valor = ? WHERE clave = ?");
            pstmt.setString(1, "3307");
            pstmt.setString(2, "db.port");
            pstmt.executeUpdate();
            pstmt.close();

            // Exportar de BD a archivo
            int exportadas = MigradorConfiguracionBD.exportarBDaProperties(
                    conn,
                    "config_exportado.properties"
            );
            System.out.println("Propiedades exportadas a archivo: " + exportadas);

            // (Opcional) Sincronizar de nuevo desde el archivo exportado
            // int cambios = MigradorConfiguracionBD.sincronizarPropiedades("config_exportado.properties", conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

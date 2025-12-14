package edu.thepower.accesodatos.tercerapractica.ejercicio1.opcional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainBackupProductos {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "root1234";

        String archivoBackup = "backup_productos.dat";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // (Opcional) Crear la tabla si no existe
            crearTablaProductosSiNoExiste(conn);

            // Exportar productos existentes
            int exportados = GestorBackupProductos.exportarProductos(conn, archivoBackup);
            System.out.println("Productos exportados: " + exportados);

            // Limpiar tabla (simulación de pérdida de datos)
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM productos");
            }

            // Importar desde el archivo binario
            int importados = GestorBackupProductos.importarProductos(conn, archivoBackup);
            System.out.println("Productos importados: " + importados);

        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea la tabla 'productos' si no existe.
     * No es parte obligatoria del ejercicio, pero viene bien para las pruebas.
     */
    private static void crearTablaProductosSiNoExiste(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS productos (
                    id INT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    precio DOUBLE NOT NULL,
                    stock INT NOT NULL
                )
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
}

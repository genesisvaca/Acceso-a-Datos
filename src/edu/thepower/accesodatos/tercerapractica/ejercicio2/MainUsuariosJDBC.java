package edu.thepower.accesodatos.tercerapractica.ejercicio2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainUsuariosJDBC {

    public static void main(String[] args) {

        // Ajusta estos valores a tu entorno si hace falta
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "root1234";

        // En un caso real, aquí podríamos desactivar el auto-commit si
        // quisiéramos agrupar varias operaciones en una transacción.
        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // Crear la tabla (si no existe)
            GestorUsuariosJDBC.crearTabla(conn);

            // Insertar dos usuarios de ejemplo
            int id1 = GestorUsuariosJDBC.insertarUsuario(conn,
                    "Juan Pérez", "juan@email.com", 25);

            int id2 = GestorUsuariosJDBC.insertarUsuario(conn,
                    "María García", "maria@email.com", 30);

            // Buscar por nombre
            List<Usuario> usuariosEncontrados = GestorUsuariosJDBC.buscarPorNombre(conn, "Juan");

            System.out.println("Usuarios encontrados:");
            for (Usuario u : usuariosEncontrados) {
                System.out.println(u);
            }

            // Actualizar email de Juan
            GestorUsuariosJDBC.actualizarEmail(conn, id1, "juan.nuevo@email.com");

            // Eliminar a María
            GestorUsuariosJDBC.eliminarUsuario(conn, id2);

        } catch (SQLException e) {
            // Mensaje algo más descriptivo para depurar más fácil
            System.err.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

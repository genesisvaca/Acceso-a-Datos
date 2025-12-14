package edu.thepower.accesodatos.tercerapractica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConexion {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "root1234";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("✔ Conexión JDBC correcta");
        } catch (SQLException e) {
            System.out.println("✘ Error en la conexión JDBC");
            e.printStackTrace();
        }
    }
}

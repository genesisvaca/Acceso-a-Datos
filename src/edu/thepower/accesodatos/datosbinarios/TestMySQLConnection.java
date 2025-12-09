package edu.thepower.accesodatos.datosbinarios;

// Importación de paquetes nativos
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQLConnection {
    public static void main(String[] args){
        // Inicialización de variables
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String user = "root";
        String password = "mysql";

        // try-catch en el que intentamos hacer ping a la BBDD creada
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            System.out.println("Conexión existosa MySQL");
        }catch(SQLException e){
            System.out.println("Error al conectarse con MySQL: " + e.getMessage());
        }
    }
}
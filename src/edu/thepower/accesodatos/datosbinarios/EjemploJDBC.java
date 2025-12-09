package edu.thepower.accesodatos.datosbinarios;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {
        // Inicialización de variables
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String user = "root";
        String password = "mysql";

        Connection conn = null; // Objeto de conexión a la base de datos para mysql
        Statement stmt = null;  // Objeto que captura sentencias de sql
        ResultSet rs = null;    // Objeto que captura el resultado de la sentencia sql

        try{
            // Establecer conexión
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida");

            // Crear statement y ejecutar consulta
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, nombre, edad FROM usuarios");

            // Procesar resultados
            while(rs.next()){
                // Inicialización y conversión de variables de mysql a java
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                System.out.println("ID:" + id + " -- Nombre:" + nombre + " -- Edad:"+ edad);
            }
        }catch (SQLException e){
            System.err.println("Error SQL: " + e.getMessage());
        }finally {
            // Cerrar las conexiones de cada uno de los objetos
            try{if (rs != null) rs.close();}catch (SQLException e){}
            try{if (stmt != null) rs.close();}catch (SQLException e){}
            try{if (conn != null) rs.close();}catch (SQLException e){}
        }
    }
}
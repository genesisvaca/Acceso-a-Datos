package edu.thepower.accesodatos.datosbinarios;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EjemploProperties {

    public static void main(String[] args) {
        Properties config = new Properties();

        try(FileInputStream fis = new FileInputStream("config.properties")) {

            config.load(fis);
            System.out.println("Configuración cargada desde el archivo");
        }catch(Exception e) {
            System.out.println("Creando configuración por defecto");
            config.setProperty("db.host", "localhost");
            config.setProperty("db.port", "3306");
            config.setProperty("db.name", "mi_base_datos");
            config.setProperty("db.debug", "false");
        }

        String host = config.getProperty("db.host");
        String port = config.getProperty("db.port");
        String db = config.getProperty("db.name");
        boolean debug = Boolean.parseBoolean(config.getProperty("db.debug"));

        //Mostrar la configuración leída
        System.out.println("***Configuración***");
        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
        System.out.println("DB: " + db);
        System.out.println("Debug: " + debug);

        try(FileOutputStream fos = new FileOutputStream("config.properties")) {
            config.store(fos, "Configuración de la aplicación ");
            System.out.println("\nConfiguración guardada desde el archiv    o");
        } catch (IOException e){
            System.err.println("Error al configurar el archivo" + e.getMessage());
        }

    }
}
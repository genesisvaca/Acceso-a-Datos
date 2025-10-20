package edu.thepower.accesodatos.primerapractica;

import java.io.File;

public class Fichero2 {
    public static void main(String[] args) {
        //Creamos un directorio padre en la ruta de documentos
        File archivo = new File("C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas-Java");

        // Verificar si la ruta especificada existe
        if(archivo.exists()){
            // Verificar si la ruta especificada es un directorio
            if(archivo.isDirectory()){
                // Si en la ruta el ultimo elemento es un directorio me muestra un mensaje
                // en pantalla de que la ruta especificada es un directorio
                System.out.println("La ruta presenta un directorio en: " + archivo.getAbsolutePath());
            } else { // Verificar si la ruta especificada es un archivo
                // Si en la ruta el ultimo elemento es un archivo me muestra un ,mensaje
                // en pantalla de que la ruta especificada es un archivo
                System.out.println("La ruta presenta un archivo en: " + archivo.getAbsolutePath());

            }

        }

    }
}
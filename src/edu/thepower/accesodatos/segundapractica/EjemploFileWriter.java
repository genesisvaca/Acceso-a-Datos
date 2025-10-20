package edu.thepower.accesodatos.segundapractica;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EjemploFileWriter {

    private static final String SALIDA = "C:\\Users\\AlumnoAfternoon\\Documents\\Acceso-a-Datos\\Acceso-a-Datos\\resources\\salida.txt";
    private static final String CONTENIDO = "Primera línea\nSegunda línea\nTercera línea";


    public static void main(String[] args) {

        // Variable para almacenar el caracter leído
        int caracter;

        // Try-catch cierra automáticamente el FileWrite
        // Por defecto sobreescribe el archivo si existe
        try (FileWriter fw = new FileWriter(SALIDA)){

            // Escribimos una cadena completa
         fw.write(CONTENIDO);
         // flush() se llama automáticamente al final

        } catch (IOException e){

            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }

    }
}

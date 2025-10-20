package edu.thepower.accesodatos.segundapractica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class EjemploBufferedReader {

    private static final String ENTRADA = "C:\\Users\\AlumnoAfternoon\\Documents\\Acceso-a-Datos\\Acceso-a-Datos\\resources\\entrada.txt";

    public static void main(String[] args) {

        // Variable para almacenar la linea leída
        String linea;

        // Contador de líneas
        int numLinea = 1;

        // BufferedReader envuelve al objeto FileReader para añadir Buffering
        // Try-catch cierra automáticamente
        try(BufferedReader br = new BufferedReader(new FileReader(ENTRADA))) {

            // readLine() devuelve null cuando no hay más líneas
            while ((linea = br.readLine()) != null){
                System.out.println(numLinea + ": "+ linea);
                numLinea ++;
            }

        } catch (IOException e){
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

}

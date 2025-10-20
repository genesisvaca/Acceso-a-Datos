package edu.thepower.accesodatos.segundapractica;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class EjemploBufferedWriter {

    // Array con líneas escribir
    private static final String[] LINEAS = {
            "Encabezado del documento",
            "Esta es la primera línea del contenido",
            "Esta es la segunda línea",
            "Final den documento"
    }; ;
    private static final String ENTRADA = "C:\\Users\\AlumnoAfternoon\\Documents\\Acceso-a-Datos\\Acceso-a-Datos\\resources\\salida_buffered.txt";

    public static void main(String[] args) {

        // BufferedWriter envuelve al objeto FileWriter para añadir Buffering, try-catch cierra automáticamente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ENTRADA))){

            // ucle for-each que escribe linea a linea con salto incluido
            for(String linea: LINEAS){

                // La escritura en el documento
                bw.write(linea);

                // El salto de línea
                bw.newLine();
            }
            // flush() se llama automáticamente al final
        } catch (IOException e) {
            System.err.println("Error al escribir eñ archivo: " + e.getMessage());
        }
        // Resultado esperado cada uno en su propia línea
    }

}

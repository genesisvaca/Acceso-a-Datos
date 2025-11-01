package edu.thepower.accesodatos.segundapractica;

/**
 * Importamos las librerias de RandomAccessFile y su manejo de excepciones
 */

import java.io.IOException;
import java.io.RandomAccessFile;

public class EjemploRandomAccessFile {

    private static final String DATOS = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\datos.bin";
    private static final String READWRITE = "rw";

    public static void main(String[] args) {

        // Try-catch cierra automáticamente el RandomAccessFile
        try(RandomAccessFile raf = new RandomAccessFile(DATOS, READWRITE)) {

            // Escribimos en diferentes posiciones
            raf.writeBytes("INICIO");

            // Nos movemos a la posición 20
            raf.seek(20);
            raf.writeBytes("MEDIO");

            // ÇNos movemos a la posición 40
            raf.seek(40);
            raf.writeBytes("FINAL");

            // Volver al INICIO para leer
            raf.seek(0);
            System.out.println("Posición 0: " + raf.readLine());

            // Volver al MEDIO para leer
            raf.seek(20);
            System.out.println("Posición 20: " + raf.readLine());

            // Volver al FINAL para leer
            raf.seek(40);
            System.out.println("Posición 40: " + raf.readLine());

            // Mostramos la longitud total del archivo
            System.out.println("Tamaño del archivo: " + raf.length() + " bytes");

        } catch(IOException e){
            System.err.println("Error al acceder al archivo: " + e.getMessage());
        }

        // Resultado esperado: escritura y lectura en posiciones específicas del archivo
    }
}

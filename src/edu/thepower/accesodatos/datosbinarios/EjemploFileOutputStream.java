package edu.thepower.accesodatos.datosbinarios;

import java.io.FileOutputStream;
import java.io.IOException;

public class EjemploFileOutputStream {
    public static void main(String[] args) throws IOException {
        // Inicilización de un array de números ASCII
        byte [] datos = {72, 111, 108, 97, 32, 77, 117, 110, 100, 111};

        try(FileOutputStream fos = new FileOutputStream("salida.bin")){
            // Escritura de caracteres ASCII en el fichero especificado
            fos.write(datos);
            System.out.println("Fichero salida.bin creado con éxito");
        }catch(IOException e){
            System.err.println("Error al leer el archivo \n" + e.getMessage());
        }
    }
}
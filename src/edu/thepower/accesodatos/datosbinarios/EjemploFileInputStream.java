package edu.thepower.accesodatos.datosbinarios;

import java.io.FileInputStream;
import java.io.IOException;

public class EjemploFileInputStream {
    public static void main(String[] args) {
        int b;

        try(FileInputStream fis = new FileInputStream("datos.bin")){
            while ((b = fis.read()) != -1){
                // Impresión del fichero con su conversión a ASCII
                System.out.print(b + " ");
            }
        }catch(IOException e){
            System.err.println("Error al leer el archivo \n" + e.getMessage());
        }
    }
}
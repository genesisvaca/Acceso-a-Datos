package edu.thepower.accesodatos.segundapractica;

import java.io.FileReader;
import java.io.IOException;

public class EjemploFileReader {

    private static final String ENTRADA = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\entrada.txt";

    public static void main(String[] args) {

        // Variable para almacenar el caracter leído
        int caracter;

        // Try-catch cierra automáticamente el FileReader
        try (FileReader fr = new FileReader(ENTRADA)){

            // read() retorna -1 cuando llega al final del archivo
            while ((caracter = fr.read()) != -1 ) {

                System.out.print((char) caracter);

            }

        } catch (IOException e){

            System.out.println("Error al cargar el archivo: " + e.getMessage());

        }
    }
}

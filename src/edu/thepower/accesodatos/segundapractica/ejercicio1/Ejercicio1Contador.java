package edu.thepower.accesodatos.segundapractica.ejercicio1;

import java.io.IOException;

/**
 * Clase de prueba para el ejercicio 1.
 * La dejo igual que los ejemplos del profesor: una constante con la entrada y otra con la salida.
 */
public class Ejercicio1Contador {

    // Rutas
    private static final String ENTRADA = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\archivo.txt";
    private static final String SALIDA = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\estadisticas.txt";

    public static void main(String[] args) {

        try {
            // 1. Analizo el archivo
            EstadisticasTexto stats = AnalizadorTexto.analizarArchivo(ENTRADA);

            // 2. Muestro por consola como pide el enunciado
            System.out.println(stats);

            // 3. Y además lo guardo en un archivo de salida
            AnalizadorTexto.guardarEstadisticas(stats, SALIDA);

        } catch (IOException e) {
            // Manejo básico de la excepción como en los ejemplos del profesor
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
    }
}
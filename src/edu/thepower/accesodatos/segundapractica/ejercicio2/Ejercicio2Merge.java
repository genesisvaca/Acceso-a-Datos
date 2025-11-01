package edu.thepower.accesodatos.segundapractica.ejercicio2;

import java.io.IOException;

/**
 * Clase de prueba para el ejercicio 2.
 * La dejo igual que las demás del proyecto para que el profesor pueda ejecutarla.
 */
public class Ejercicio2Merge {

    // Rutas
    private static final String ARCHIVO1 = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\archivo1.txt";
    private static final String ARCHIVO2 = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\archivo2.txt";
    private static final String SALIDA = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\combinado.txt";

    public static void main(String[] args) {

        // Array con los archivos a combinar
        String[] entradas = { ARCHIVO1, ARCHIVO2 };

        // Palabra que quiero filtrar (si la pongo a null, escribe todas las líneas)
        String filtro = "Java";

        try {
            int total = MergeArchivos.combinarArchivos(entradas, SALIDA, filtro);
            // Por si quiero verlo también aquí
            System.out.println("Líneas totales escritas: " + total);
        } catch (IOException e) {
            System.err.println("Error al combinar archivos: " + e.getMessage());
        }
    }
}
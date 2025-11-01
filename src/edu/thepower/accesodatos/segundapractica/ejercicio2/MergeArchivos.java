package edu.thepower.accesodatos.segundapractica.ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * Ejercicio 2: Merge de archivos con filtrado.
 * Aquí leo varios archivos de texto, me quedo solo con las líneas que cumplen
 * el filtro y las voy metiendo en un archivo combinado.
 * Sigo la teoría del profesor: validar, usar BufferedReader/Writer y UTF-8.
 */
public class MergeArchivos {

    /**
     * Combina múltiples archivos en uno solo, filtrando líneas
     * @param archivosEntrada array con las rutas de los archivos a combinar
     * @param archivoSalida ruta del archivo resultado
     * @param filtro palabra que debe contener la línea para incluirse (null = todas)
     * @return número total de líneas escritas
     * @throws IOException si hay error de lectura/escritura
     */
    public static int combinarArchivos(String[] archivosEntrada, String archivoSalida, String filtro) throws IOException {

        // Validación básica: que me pasen algo
        if (archivosEntrada == null || archivosEntrada.length == 0) {
            throw new IllegalArgumentException("No se han pasado archivos de entrada");
        }

        // Antes de empezar a escribir, compruebo que TODOS existen.
        for (String ruta : archivosEntrada) {
            File f = new File(ruta);
            if (!f.exists()) {
                throw new IOException("El archivo de entrada no existe: " + ruta);
            }
        }

        File destino = new File(archivoSalida);
        // Por si el usuario pone una carpeta que no existe
        if (destino.getParentFile() != null) {
            destino.getParentFile().mkdirs();
        }

        int totalLineasEscritas = 0;

        // Abro el escritor una sola vez, fuera del bucle, para ir escribiendo tdo en el mismo archivo
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(destino),
                        StandardCharsets.UTF_8
                )
        )) {
            // Recorro cada archivo de entrada
            for (String rutaEntrada : archivosEntrada) {

                int lineasCoinciden = 0; // contador por archivo, para mostrar en consola

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(rutaEntrada),
                                StandardCharsets.UTF_8
                        )
                )) {
                    String linea;
                    while ((linea = br.readLine()) != null) {

                        if (cumpleFiltro(linea, filtro)) {
                            // si la línea pasa el filtro, la escribo en el combinado
                            bw.write(linea);
                            bw.newLine();
                            lineasCoinciden++;
                            totalLineasEscritas++;
                        }
                    }
                }

                // Mensaje por consola como el del enunciado
                System.out.printf("Procesando %s: %d línea%s coincide%s%n",
                        rutaEntrada,
                        lineasCoinciden,
                        (lineasCoinciden == 1 ? "" : "s"),
                        (lineasCoinciden == 1 ? "" : "n"));
            }
        }

        // Mensaje final
        System.out.printf("Total: %d líneas escritas en %s%n", totalLineasEscritas, archivoSalida);

        return totalLineasEscritas;
    }

    /**
     * Verifica si una línea cumple el criterio de filtrado
     * @param linea línea a evaluar
     * @param filtro criterio de búsqueda (null = siempre true)
     * @return true si la línea debe incluirse
     */
    private static boolean cumpleFiltro(String linea, String filtro) {
        // Si no hay filtro o está vacío, me quedo con todas
        if (filtro == null || filtro.isBlank()) {
            return true;
        }
        // Si hay filtro, la línea tiene que contenerlo
        return linea != null && linea.contains(filtro);
    }
}
package edu.thepower.accesodatos.segundapractica.entrega;

/**
 * Ejercicio 3: Sistema de Log con Rotación
 * Objetivo: Implementar un sistema de logging que escriba en un archivo y lo rote
 * cuando alcance cierto tamaño.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Merge con filtrado: recorro cada archivo de entrada y sólo escribo las líneas que cumplen el criterio.
 * Me apoyo en try-with-resources anidados para asegurar cierres correctos incluso con excepciones.
 */

public class MergeArchivos {

    /**
     * Combina múltiples archivos en uno solo, filtrando líneas.
     * @param archivosEntrada array con las rutas de los archivos a combinar
     * @param archivoSalida ruta del archivo resultado
     * @param filtro palabra que debe contener la línea para incluirse (null = todas)
     * @return número total de líneas escritas
     * @throws IOException si hay error de lectura/escritura
     */
    public static int combinarArchivos(String[] archivosEntrada, String archivoSalida, String filtro) throws IOException {
        Objects.requireNonNull(archivosEntrada, "archivosEntrada no puede ser null");
        if (archivosEntrada.length == 0) {
            throw new IllegalArgumentException("Se ha pasado un array vacío de entradas");
        }

        // Valido upfront para fallar rápido y no dejar el proceso a medias.
        for (String a : archivosEntrada) {
            if (!Files.exists(Path.of(a))) {
                throw new IOException("Archivo de entrada no existe: " + a);
            }
        }

        Path out = Path.of(archivoSalida);
        if (out.getParent() != null) Files.createDirectories(out.getParent());

        int totalEscritas = 0;

        try (BufferedWriter bw = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            for (String entrada : archivosEntrada) {
                int coinciden = 0;
                try (BufferedReader br = Files.newBufferedReader(Path.of(entrada), StandardCharsets.UTF_8)) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        if (cumpleFiltro(linea, filtro)) {
                            bw.write(linea);
                            bw.newLine();
                            coinciden++;
                            totalEscritas++;
                        }
                    }
                }
                // Mensaje informativo por consola como en el enunciado.
                System.out.printf("Procesando %s: %d línea%s coincide%s%n", entrada, coinciden,
                        (coinciden == 1 ? "" : "s"), (coinciden == 1 ? "" : "n"));
            }
        }

        System.out.printf("Total: %d líneas escritas en %s%n", totalEscritas, archivoSalida);
        return totalEscritas;
    }

    /**
     * Verifica si una línea cumple el criterio de filtrado.
     * @param linea línea a evaluar
     * @param filtro criterio de búsqueda (null = siempre true)
     * @return true si la línea debe incluirse
     */
    static boolean cumpleFiltro(String linea, String filtro) {
        if (filtro == null || filtro.isBlank()) return true;
        return linea != null && linea.contains(filtro);
    }
}
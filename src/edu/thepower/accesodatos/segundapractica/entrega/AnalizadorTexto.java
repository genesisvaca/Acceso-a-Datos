package edu.thepower.accesodatos.segundapractica.entrega;

/**
 * Ejercicio 2: Merge de Archivos con Filtrado
 * Objetivo: Leer múltiples archivos de texto, filtrar líneas según un criterio y
 * combinar el resultado en un único archivo de salida.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Utilidades para analizar un archivo de texto y persistir sus estadísticas.
 * Decido usar BufferedReader/Writer + UTF-8 explícito para evitar sorpresas con encodings.
 */
public class AnalizadorTexto {

    /**
     * Lee un archivo y cuenta palabras, líneas y caracteres.
     * @param nombreArchivo ruta del archivo a analizar
     * @return objeto EstadisticasTexto con los resultados
     * @throws IOException si hay error al leer el archivo
     */
    public static EstadisticasTexto analizarArchivo(String nombreArchivo) throws IOException {
        Path path = Path.of(nombreArchivo);
        if (!Files.exists(path)) {
            throw new IOException("El archivo no existe: " + nombreArchivo);
        }

        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;
        String palabraMasLarga = "";

        // Leo línea a línea porque es más eficiente para procesar texto y evita cargar todo en memoria.
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas++;
                // Sumo caracteres tal y como vienen (sin contar los saltos de línea). Me interesa el contenido puro.
                caracteres += linea.length();

                if (!linea.isBlank()) {
                    // Divido por espacios en blanco (uno o más) y filtro vacíos por si hubiese separadores raros.
                    String[] trozos = linea.trim().split("\\s+");
                    trozos = Arrays.stream(trozos).filter(s -> !s.isEmpty()).toArray(String[]::new);
                    palabras += trozos.length;
                    for (String t : trozos) {
                        if (t.length() > palabraMasLarga.length()) {
                            palabraMasLarga = t;
                        }
                    }
                }
            }
        }

        return new EstadisticasTexto(lineas, palabras, caracteres, palabraMasLarga);
    }

    /**
     * Escribe las estadísticas en un archivo de salida.
     * @param estadisticas objeto con las estadísticas
     * @param archivoSalida ruta donde guardar el resultado
     * @throws IOException si hay error al escribir
     */
    public static void guardarEstadisticas(EstadisticasTexto estadisticas, String archivoSalida) throws IOException {
        Path out = Path.of(archivoSalida);
        // Creo la carpeta si no existe; me evita errores tontos al volcar resultados.
        if (out.getParent() != null) {
            Files.createDirectories(out.getParent());
        }
        try (BufferedWriter bw = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            bw.write(estadisticas.toString());
            bw.newLine();
        }
    }
}
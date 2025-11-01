package edu.thepower.accesodatos.segundapractica.ejercicio1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Aquí meto la lógica del ejercicio 1.
 * Sigo la teoría del PDF: BufferedReader para leer, try-with-resources y UTF-8 explícito.
 */
public class AnalizadorTexto {

    /**
     * Lee un archivo y cuenta palabras, líneas y caracteres.
     * @param nombreArchivo ruta del archivo a analizar
     * @return objeto EstadisticasTexto con los resultados
     * @throws IOException si hay error al leer el archivo
     */
    public static EstadisticasTexto analizarArchivo(String nombreArchivo) throws IOException {
        File fichero = new File(nombreArchivo);

        // Primero valido que el archivo existe porque el enunciado lo pide.
        if (!fichero.exists()) {
            throw new IOException("El archivo no existe: " + nombreArchivo);
        }

        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;
        String palabraMasLarga = "";

        // Leo línea a línea porque es más cómodo para contar palabras
        // y porque con BufferedReader va más rápido.
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fichero),
                        StandardCharsets.UTF_8
                )
        )) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas++;

                // IMPORTANTE: aquí solo sumo los caracteres de la línea, sin el salto.
                caracteres += linea.length();

                // Si la línea no está en blanco, la troceo por espacios
                if (!linea.isBlank()) {
                    String[] trozos = linea.trim().split("\\s+");
                    // filtro posibles vacíos por si hay espacios de más
                    trozos = Arrays.stream(trozos).filter(s -> !s.isEmpty()).toArray(String[]::new);
                    palabras += trozos.length;

                    // compruebo cuál es la palabra más larga
                    for (String palabra : trozos) {
                        if (palabra.length() > palabraMasLarga.length()) {
                            palabraMasLarga = palabra;
                        }
                    }
                }
            }
        }

        // Ojo: el ejemplo del enunciado da 35 caracteres. Dependiendo de si el archivo
        // tiene o no espacios finales o BOM puede salir 1 arriba o abajo.
        // Yo me quedo con lo que de verdad hay en el archivo.
        return new EstadisticasTexto(lineas, palabras, caracteres, palabraMasLarga);
    }

    /**
     * Escribe las estadísticas en un archivo de salida.
     * @param estadisticas objeto con las estadísticas
     * @param archivoSalida ruta donde guardar el resultado
     * @throws IOException si hay error al escribir
     */
    public static void guardarEstadisticas(EstadisticasTexto estadisticas, String archivoSalida) throws IOException {
        File salida = new File(archivoSalida);

        // Creo la carpeta si no existe (esto en clase suele dar problemas si no lo haces)
        if (salida.getParentFile() != null) {
            salida.getParentFile().mkdirs();
        }

        // try-with-resources para que el BufferedWriter se cierre solo
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(salida),
                        StandardCharsets.UTF_8
                )
        )) {
            // Aprovecho el toString() que ya devuelve el formato pedido
            bw.write(estadisticas.toString());
            bw.newLine();
        }
    }
}
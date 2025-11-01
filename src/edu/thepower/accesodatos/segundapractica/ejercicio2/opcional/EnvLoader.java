package edu.thepower.accesodatos.segundapractica.ejercicio2.opcional;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carga de variables desde un archivo .env.
 * Aquí simulo un sistema de variables de entorno en memoria usando un Map.
 * Sigo la teoría del PDF: uso BufferedReader, UTF-8 explícito y try-with-resources.
 */
public class EnvLoader {

    // Guardo las variables cargadas en un mapa estático para poder consultarlas luego con getEnv()
    private static Map<String, String> variables = new LinkedHashMap<>();

    /**
     * Lee un archivo .env y carga las variables
     * @param archivoEnv ruta del archivo .env
     * @return Map con las variables cargadas
     * @throws IOException si hay error de lectura
     */
    public static Map<String, String> cargarEnv(String archivoEnv) throws IOException {
        File fichero = new File(archivoEnv);
        if (!fichero.exists()) {
            throw new IOException("El archivo .env no existe: " + archivoEnv);
        }

        // Uso LinkedHashMap para mantener el orden original (más legible)
        Map<String, String> resultado = new LinkedHashMap<>();

        // Leo línea a línea con BufferedReader
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fichero),
                        StandardCharsets.UTF_8
                )
        )) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                // Ignoro líneas vacías o que empiezan con #
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                // Busco el signo = que separa clave y valor
                int indice = linea.indexOf('=');
                if (indice <= 0) {
                    // Si no hay = o está al principio, la línea no es válida
                    continue;
                }

                // Separo clave y valor
                String clave = linea.substring(0, indice).trim();
                String valor = linea.substring(indice + 1).trim();

                // Guardo la variable
                resultado.put(clave, valor);
            }
        }

        // Guardo en el mapa estático para poder acceder con getEnv()
        variables = new LinkedHashMap<>(resultado);

        System.out.println("Cargadas " + variables.size() + " variables desde .env");

        // Devuelvo un mapa inmutable para evitar modificaciones externas
        return Collections.unmodifiableMap(resultado);
    }

    /**
     * Obtiene el valor de una variable de entorno
     * @param clave nombre de la variable
     * @param valorPorDefecto valor si la variable no existe
     * @return valor de la variable o valorPorDefecto
     */
    public static String getEnv(String clave, String valorPorDefecto) {
        // Si no se ha llamado antes a cargarEnv, variables estará vacía
        if (variables == null || variables.isEmpty()) {
            return valorPorDefecto;
        }

        String valor = variables.get(clave);
        return (valor != null) ? valor : valorPorDefecto;
    }
}
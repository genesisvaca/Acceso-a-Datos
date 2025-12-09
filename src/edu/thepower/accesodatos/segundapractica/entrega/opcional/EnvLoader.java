package edu.thepower.accesodatos.segundapractica.entrega.opcional;

/**
 * Ejercicio Opcional 2: Carga de Variables de Entorno desde .env
 * Objetivo: Leer un archivo .env y cargar las variables en un Map que simule
 * variables de entorno.
 *
 * Resultado esperado: Sistema funcional de carga de configuración desde archivo
 * .env, ignorando comentarios y líneas vacías.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carga de variables tipo .env a un Map simple. Ignoro comentarios (#) y líneas vacías.
 */
public class EnvLoader {
    private static Map<String, String> cache = new LinkedHashMap<>();

    /** Lee un archivo .env y carga las variables. */
    public static Map<String, String> cargarEnv(String archivoEnv) throws IOException {
        Path path = Path.of(archivoEnv);
        if (!Files.exists(path)) throw new IOException("No existe: " + archivoEnv);
        Map<String, String> out = new LinkedHashMap<>();
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                int eq = line.indexOf('=');
                if (eq <= 0) continue; // línea inválida, la salto
                String key = line.substring(0, eq).trim();
                String value = line.substring(eq + 1).trim();
                out.put(key, value);
            }
        }
        cache = new LinkedHashMap<>(out);
        System.out.println("Cargadas " + cache.size() + " variables desde .env");
        return Collections.unmodifiableMap(out);
    }

    /** Obtiene el valor de una variable de entorno cargada, con valor por defecto si no existe. */
    public static String getEnv(String clave, String valorPorDefecto) {
        String v = cache.get(clave);
        return v != null ? v : valorPorDefecto;
    }
}

package edu.thepower.accesodatos.segundapractica.entrega.opcional;

/**
 * Ejercicio Opcional 1: Parser de JSON Simple
 * Objetivo: Leer y escribir archivos JSON básicos sin usar librerías externas, o
 * usando Gson si está disponible.
 *
 * Resultado esperado: Manejo básico de JSON con parsing manual de cadenas o
 * usando Gson para estructuras más complejas.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parser JSON muy básico para pares clave-valor planos (sin anidación, sin arrays).
 * Lo uso cuando no quiero añadir dependencias y sólo necesito config sencilla.
 */
public class JsonSimple {
    /** Lee un archivo JSON y extrae pares clave-valor simples. */
    public static Map<String, String> leerJsonSimple(String archivoJson) throws IOException {
        Path path = Path.of(archivoJson);
        if (!Files.exists(path)) throw new IOException("No existe: " + archivoJson);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line; while ((line = br.readLine()) != null) sb.append(line.trim());
        }
        String json = sb.toString();
        Map<String, String> out = new LinkedHashMap<>();
        // Quito llaves exteriores si existen
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length()-1);
        // Separación ingenua por comas de nivel superior (suficiente para pares simples)
        if (!json.isBlank()) {
            String[] pares = json.split(",");
            for (String par : pares) {
                String[] kv = par.split(":", 2);
                if (kv.length == 2) {
                    String k = limpiar(kv[0]);
                    String v = limpiar(kv[1]);
                    out.put(k, v);
                }
            }
        }
        System.out.println("JSON leído: " + out.size() + " propiedades");
        return out;
    }

    /** Escribe un Map como archivo JSON formateado. */
    public static void escribirJsonSimple(Map<String, String> datos, String archivoJson) throws IOException {
        Path out = Path.of(archivoJson);
        if (out.getParent() != null) Files.createDirectories(out.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            bw.write("{\n");
            int i = 0, size = datos.size();
            for (Map.Entry<String, String> e : datos.entrySet()) {
                bw.write(String.format("  \"%s\": \"%s\"%s\n", escapar(e.getKey()), escapar(e.getValue()),
                        (++i < size ? "," : "")));
            }
            bw.write("}\n");
        }
        System.out.println("JSON escrito: " + datos.size() + " propiedades en " + archivoJson);
    }

    // Helpers para limpiar comillas y espacios sobrantes
    private static String limpiar(String s) {
        s = s.trim();
        if (s.startsWith("\"")) s = s.substring(1);
        if (s.endsWith("\"")) s = s.substring(0, s.length()-1);
        return s;
    }
    private static String escapar(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
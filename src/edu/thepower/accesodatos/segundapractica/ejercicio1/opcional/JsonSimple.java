package edu.thepower.accesodatos.segundapractica.ejercicio1.opcional;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parser de JSON MUY sencillo para pares clave-valor planos.
 * Lo hago así porque el ejercicio dice "sin usar librerías externas".
 * Esto NO vale para arrays, objetos dentro de objetos o JSON raros.
 */
public class JsonSimple {

    /**
     * Lee un archivo JSON y extrae pares clave-valor simples
     * @param archivoJson ruta del archivo JSON
     * @return Map con las claves y valores parseados
     * @throws IOException si hay error de lectura
     */
    public static Map<String, String> leerJsonSimple(String archivoJson) throws IOException {
        File fichero = new File(archivoJson);
        if (!fichero.exists()) {
            throw new IOException("El archivo JSON no existe: " + archivoJson);
        }

        // Leo todo el archivo en un StringBuilder
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fichero),
                        StandardCharsets.UTF_8
                )
        )) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Quito espacios sobrantes a los lados
                sb.append(linea.trim());
            }
        }

        String json = sb.toString();

        // Voy a guardar las claves en orden de lectura
        Map<String, String> resultado = new LinkedHashMap<>();

        // Quito las llaves externas si están
        if (json.startsWith("{")) {
            json = json.substring(1);
        }
        if (json.endsWith("}")) {
            json = json.substring(0, json.length() - 1);
        }

        // Ahora json tiene algo tipo:
        //  "host": "localhost","puerto":"8080","debug":"true"
        // Lo separo por comas de primer nivel (esto es sencillo pero suficiente para el ejercicio)
        if (!json.isBlank()) {
            String[] pares = json.split(",");
            for (String par : pares) {
                // Cada par debería ser "clave":"valor"
                String[] kv = par.split(":", 2); // solo en el primer :
                if (kv.length == 2) {
                    String clave = limpiar(kv[0]);
                    String valor = limpiar(kv[1]);
                    resultado.put(clave, valor);
                }
            }
        }

        System.out.println("JSON leído: " + resultado.size() + " propiedades");
        return resultado;
    }

    /**
     * Escribe un Map como archivo JSON formateado
     * @param datos Map con los datos a escribir
     * @param archivoJson ruta del archivo de salida
     * @throws IOException si hay error de escritura
     */
    public static void escribirJsonSimple(Map<String, String> datos, String archivoJson) throws IOException {
        File salida = new File(archivoJson);
        if (salida.getParentFile() != null) {
            salida.getParentFile().mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(salida),
                        StandardCharsets.UTF_8
                )
        )) {
            bw.write("{");
            bw.newLine();

            int i = 0;
            int total = datos.size();

            for (Map.Entry<String, String> entry : datos.entrySet()) {
                String clave = entry.getKey();
                String valor = entry.getValue();

                // Escribo con comas entre pares, excepto el último
                bw.write("  \"" + escapar(clave) + "\": \"" + escapar(valor) + "\"");
                if (i < total - 1) {
                    bw.write(",");
                }
                bw.newLine();
                i++;
            }

            bw.write("}");
            bw.newLine();
        }

        System.out.println("JSON escrito: " + datos.size() + " propiedades en " + archivoJson);
    }

    /**
     * Quita comillas y espacios de una clave o valor tipo "host"
     */
    private static String limpiar(String texto) {
        String res = texto.trim();
        if (res.startsWith("\"")) {
            res = res.substring(1);
        }
        if (res.endsWith("\"")) {
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }

    /**
     * Escapo comillas dobles básicas para que el JSON no se rompa.
     */
    private static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
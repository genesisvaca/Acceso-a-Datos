package edu.thepower.accesodatos.segundapractica.ejercicio3.opcional;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Backup incremental muy sencillo:
 * - Recorre una carpeta origen
 * - Copia solo los archivos que son nuevos o que han sido modificados
 *   desde la fecha que hay guardada en el archivo de control
 * - Actualiza ese archivo de control al final
 *
 * Lo hago con java.io para mantener el mismo estilo que el resto de ejercicios.
 */
public class BackupIncremental {

    // Formato solo para mostrar la fecha bonita en consola
    private static final SimpleDateFormat FORMATO_FECHA =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Realiza backup incremental de una carpeta
     * @param carpetaOrigen ruta de la carpeta a respaldar
     * @param carpetaDestino ruta donde guardar el backup
     * @param archivoControl archivo que registra el último backup
     * @return número de archivos copiados
     * @throws IOException si hay error en el proceso
     */
    public static int backupIncremental(String carpetaOrigen,
                                        String carpetaDestino,
                                        String archivoControl) throws IOException {

        File origen = new File(carpetaOrigen);
        File destino = new File(carpetaDestino);
        File control = new File(archivoControl);

        if (!origen.exists() || !origen.isDirectory()) {
            throw new IOException("La carpeta de origen no existe o no es una carpeta: " + carpetaOrigen);
        }

        // Creo la carpeta destino si no existe
        if (!destino.exists()) {
            destino.mkdirs();
        }

        System.out.println("Iniciando backup incremental...");

        long ultimoBackup = leerUltimoBackup(archivoControl);
        if (ultimoBackup == 0L) {
            System.out.println("Último backup: nunca");
        } else {
            System.out.println("Último backup: " + FORMATO_FECHA.format(new Date(ultimoBackup)));
        }

        // Contador de archivos copiados
        int[] contador = {0};

        // Recorro la carpeta de origen de forma recursiva
        recorrerYCopiar(origen, origen, destino, ultimoBackup, contador);

        // Actualizo el archivo de control a la fecha/hora actual (en milisegundos)
        if (control.getParentFile() != null) {
            control.getParentFile().mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(control))) {
            long ahora = System.currentTimeMillis();
            bw.write(String.valueOf(ahora));
        }

        System.out.println("Backup completado: " + contador[0] + " archivo" + (contador[0] == 1 ? "" : "s"));
        System.out.println("Registro actualizado: " + FORMATO_FECHA.format(new Date()));

        return contador[0];
    }

    /**
     * Recorre la carpeta origen (de forma recursiva) y copia los archivos que
     * se han modificado desde el último backup.
     *
     * @param raizOrigen carpeta raíz original (para calcular rutas relativas)
     * @param actual carpeta/archivo actual que estoy recorriendo
     * @param destino carpeta destino base
     * @param ultimoBackup timestamp del último backup
     * @param contador contador de archivos copiados
     * @throws IOException si hay error al copiar
     */
    private static void recorrerYCopiar(File raizOrigen,
                                        File actual,
                                        File destino,
                                        long ultimoBackup,
                                        int[] contador) throws IOException {
        if (actual.isDirectory()) {
            // Si es carpeta, recorro su contenido
            File[] hijos = actual.listFiles();
            if (hijos != null) {
                for (File hijo : hijos) {
                    recorrerYCopiar(raizOrigen, hijo, destino, ultimoBackup, contador);
                }
            }
        } else {
            // Es un archivo
            long modificado = actual.lastModified();
            if (modificado > ultimoBackup) {
                // Calculo la ruta relativa para respetar la estructura
                String rutaRelativa = obtenerRutaRelativa(raizOrigen, actual);
                File destinoFinal = new File(destino, rutaRelativa);

                // Creo las carpetas que hagan falta
                if (destinoFinal.getParentFile() != null) {
                    destinoFinal.getParentFile().mkdirs();
                }

                copiarArchivo(actual, destinoFinal);

                // Mensaje por consola como el enunciado
                if (ultimoBackup == 0L) {
                    System.out.println("Copiando: " + rutaRelativa);
                } else {
                    System.out.println("Copiando: " + rutaRelativa + " (modificado)");
                }

                contador[0]++;
            }
        }
    }

    /**
     * Lee la fecha del último backup desde el archivo de control
     * @param archivoControl ruta del archivo de control
     * @return timestamp del último backup, o 0 si no existe
     * @throws IOException si hay error de lectura
     */
    private static long leerUltimoBackup(String archivoControl) throws IOException {
        File control = new File(archivoControl);
        if (!control.exists()) {
            // Si no existe, devuelvo 0 para indicar "nunca"
            return 0L;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(control))) {
            String linea = br.readLine();
            if (linea == null || linea.isBlank()) {
                return 0L;
            }
            try {
                return Long.parseLong(linea.trim());
            } catch (NumberFormatException e) {
                // Si el archivo está corrupto, hago copia completa
                return 0L;
            }
        }
    }

    /**
     * Copia un archivo de origen a destino
     * @param origen archivo fuente
     * @param destino archivo destino
     * @throws IOException si hay error en la copia
     */
    private static void copiarArchivo(File origen, File destino) throws IOException {
        // Copia clásica con buffer
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(origen));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destino))) {

            byte[] buffer = new byte[4096];
            int leidos;
            while ((leidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, leidos);
            }
        }
    }

    /**
     * Devuelve la ruta de 'archivo' relativa a 'raiz'
     * Ej: raiz = C:/docs , archivo = C:/docs/fotos/img.png -> fotos/img.png
     */
    private static String obtenerRutaRelativa(File raiz, File archivo) {
        String rutaRaiz = raiz.getAbsolutePath();
        String rutaArchivo = archivo.getAbsolutePath();

        if (rutaArchivo.startsWith(rutaRaiz)) {
            String relativa = rutaArchivo.substring(rutaRaiz.length());
            // Quito el separador inicial si lo hay
            if (relativa.startsWith(File.separator)) {
                relativa = relativa.substring(1);
            }
            return relativa;
        } else {
            // Si por alguna razón no puedo sacar relativa, devuelvo el nombre del archivo
            return archivo.getName();
        }
    }
}

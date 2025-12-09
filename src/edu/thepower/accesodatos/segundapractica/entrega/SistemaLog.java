package edu.thepower.accesodatos.segundapractica.entrega;

/**
 * Ejercicio 3: Sistema de Log con Rotación
 * Objetivo: Implementar un sistema de logging que escriba en un archivo y lo rote
 * cuando alcance cierto tamaño.
 *
 * Clases requeridas:
 *
 * class SistemaLog {
 * private String archivoLog;
 * private long tamanoMaximo; // en bytes
 * private int numeroRotacion;
 * public SistemaLog(String archivoLog, long tamanoMaximo) {
 * // Constructor
 * }
 * // Métodos anteriores
 * }
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Sistema de logging sencillo con rotación por tamaño.
 * Diseño: mantengo un contador interno de rotación y renombro a archivoLog.N cuando se supera el umbral.
 */
public class SistemaLog {
    private final Path archivoLog;
    private final long tamanoMaximo; // bytes
    private int numeroRotacion = 0;
    private final DateTimeFormatter ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * @param archivoLog ruta del archivo de log (p.ej. "app.log")
     * @param tamanoMaximo tamaño máximo en bytes para rotar (p.ej. 1024)
     */
    public SistemaLog(String archivoLog, long tamanoMaximo) {
        if (archivoLog == null || archivoLog.isBlank()) {
            throw new IllegalArgumentException("archivoLog inválido");
        }
        if (tamanoMaximo <= 0) {
            throw new IllegalArgumentException("tamanoMaximo debe ser > 0");
        }
        this.archivoLog = Path.of(archivoLog);
        this.tamanoMaximo = tamanoMaximo;
    }

    /**
     * Escribe un mensaje en el log con timestamp.
     * Hago flush implícito al cerrar el BufferedWriter en cada escritura para simplicidad.
     */
    public synchronized void escribirLog(String mensaje, NivelLog nivel) throws IOException {
        if (archivoLog.getParent() != null) Files.createDirectories(archivoLog.getParent());
        if (!Files.exists(archivoLog)) {
            Files.createFile(archivoLog);
        }
        String linea = String.format("[%s] [%s] %s", LocalDateTime.now().format(ISO), nivel, mensaje);

        // Escribo en append con UTF-8 explícito.
        try (BufferedWriter bw = Files.newBufferedWriter(archivoLog, StandardCharsets.UTF_8,
                StandardOpenOption.APPEND)) {
            bw.write(linea);
            bw.newLine();
        }
        System.out.println("Log escrito: " + mensaje);

        // Compruebo rotación después de escribir. Si toca, lo anuncio por consola como pide el ejemplo.
        if (rotarSiNecesario()) {
            System.out.printf("ROTACIÓN: %s renombrado a %s%n", archivoLog.getFileName(),
                    archivoLog.getFileName() + "." + numeroRotacion);
        }
    }

    /**
     * Verifica si el archivo debe rotarse y ejecuta la rotación.
     * @return true si se realizó la rotación
     */
    private synchronized boolean rotarSiNecesario() throws IOException {
        long size = obtenerTamanoLog();
        if (size <= tamanoMaximo) return false;

        numeroRotacion++;
        Path destino = archivoLog.resolveSibling(archivoLog.getFileName() + "." + numeroRotacion);
        // Renombro de forma atómica cuando sea posible.
        Files.move(archivoLog, destino, StandardCopyOption.REPLACE_EXISTING);
        // Creo un nuevo archivo vacío para seguir registrando.
        Files.createFile(archivoLog);
        return true;
    }

    /**
     * Obtiene el tamaño actual del archivo de log.
     */
    private long obtenerTamanoLog() throws IOException {
        return Files.exists(archivoLog) ? Files.size(archivoLog) : 0L;
    }
}

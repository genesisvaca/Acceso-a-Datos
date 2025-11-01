package edu.thepower.accesodatos.segundapractica.ejercicio3;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Sistema de logging sencillo con rotación por tamaño.
 * Lo hago con java.io (File + FileWriter) para seguir el mismo estilo que los ejemplos
 * de FileReader / BufferedReader del profesor.
 */
public class SistemaLog {

    // Ruta del archivo de log principal (ej: "app.log")
    private final File archivoLog;

    // Tamaño máximo en bytes antes de rotar
    private final long tamanoMaximo;

    // Número de rotación actual (app.log.1, app.log.2, ...)
    private int numeroRotacion = 0;

    // Formato de fecha tipo ISO 8601 como pide el enunciado
    private final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor del sistema de log.
     * Valido los parámetros porque el enunciado lo pide.
     *
     * @param archivoLog  ruta del archivo de log
     * @param tamanoMaximo tamaño máximo en bytes antes de rotar
     */
    public SistemaLog(String archivoLog, long tamanoMaximo) {
        if (archivoLog == null || archivoLog.isBlank()) {
            throw new IllegalArgumentException("La ruta del archivo de log no puede ser nula o vacía");
        }
        if (tamanoMaximo <= 0) {
            throw new IllegalArgumentException("El tamaño máximo debe ser mayor que 0");
        }
        this.archivoLog = new File(archivoLog);
        this.tamanoMaximo = tamanoMaximo;
    }

    /**
     * Escribe un mensaje en el log con timestamp.
     * @param mensaje contenido a registrar
     * @param nivel nivel del log (INFO, WARNING, ERROR)
     * @throws IOException si hay error al escribir
     */
    public synchronized void escribirLog(String mensaje, NivelLog nivel) throws IOException {
        // Creo la carpeta si hace falta (por si el alumno pone rutas largas)
        if (archivoLog.getParentFile() != null) {
            archivoLog.getParentFile().mkdirs();
        }

        // Texto que voy a escribir, con fecha + nivel
        String linea = String.format(
                "[%s] [%s] %s",
                LocalDateTime.now().format(FORMATO_FECHA),
                nivel,
                mensaje
        );

        // try-with-resources para que el BufferedWriter se cierre solo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoLog, true))) {
            bw.write(linea);
            bw.newLine();
            // flush implícito al cerrar
        }

        // Mensaje por consola como en el enunciado
        System.out.println("Log escrito: " + mensaje);

        // Después de escribir, compruebo si toca rotar
        if (rotarSiNecesario()) {
            System.out.printf("ROTACIÓN: %s renombrado a %s%n",
                    archivoLog.getName(),
                    archivoLog.getName() + "." + numeroRotacion);
        }
    }

    /**
     * Verifica si el archivo debe rotarse y ejecuta la rotación.
     * @return true si se realizó la rotación
     * @throws IOException si hay error en la rotación
     */
    private boolean rotarSiNecesario() throws IOException {
        long tamActual = obtenerTamanoLog();
        if (tamActual <= tamanoMaximo) {
            // No hace falta rotar
            return false;
        }

        // Si llega aquí, sí hay que rotar
        numeroRotacion++;

        // Nuevo nombre: app.log.1, app.log.2, ...
        File destino = new File(archivoLog.getParent(), archivoLog.getName() + "." + numeroRotacion);

        // Intento renombrar el archivo actual
        boolean renombrado = archivoLog.renameTo(destino);
        if (!renombrado) {
            // Si no se puede renombrar, lanzo excepción para enterarme
            throw new IOException("No se pudo rotar el log a: " + destino.getAbsolutePath());
        }

        // Creo un nuevo archivo vacío para seguir escribiendo
        boolean creado = archivoLog.createNewFile();
        if (!creado) {
            throw new IOException("No se pudo crear de nuevo el archivo de log: " + archivoLog.getAbsolutePath());
        }

        return true;
    }

    /**
     * Obtiene el tamaño actual del archivo de log.
     * @return tamaño en bytes
     */
    private long obtenerTamanoLog() {
        if (!archivoLog.exists()) {
            return 0L;
        }
        return archivoLog.length();
    }
}
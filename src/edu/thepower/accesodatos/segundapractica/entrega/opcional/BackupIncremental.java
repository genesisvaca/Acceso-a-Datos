package edu.thepower.accesodatos.segundapractica.entrega.opcional;

/**
 * Ejercicio Opcional 3: Backup Incremental
 * Objetivo: Crear un sistema que haga backup solo de archivos modificados desde
 * el último backup.
 *
 *Resultado esperado: Sistema que optimiza backups copiando solo archivos
 * nuevos o modificados, con registro persistente del último backup realizado
 */

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Backup incremental básico: copio sólo archivos nuevos o modificados desde el último backup.
 * Guardo el instante del último backup en un archivo de control.
 */
public class BackupIncremental {

    /**
     * Realiza backup incremental de una carpeta
     * @param carpetaOrigen ruta de la carpeta a respaldar
     * @param carpetaDestino ruta donde guardar el backup
     * @param archivoControl archivo que registra el último backup
     * @return número de archivos copiados
     */
    public static int backupIncremental(String carpetaOrigen, String carpetaDestino, String archivoControl) throws IOException {
        Path origen = Path.of(carpetaOrigen);
        Path destino = Path.of(carpetaDestino);
        Path control = Path.of(archivoControl);
        if (!Files.isDirectory(origen)) throw new IOException("Origen no es carpeta: " + origen);
        Files.createDirectories(destino);

        System.out.println("Iniciando backup incremental...");
        long ultimo = leerUltimoBackup(archivoControl);
        if (ultimo == 0) {
            System.out.println("Último backup: nunca");
        } else {
            LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(ultimo), ZoneId.systemDefault());
            System.out.println("Último backup: " + ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        final long ultimoCorte = ultimo;
        final int[] contador = {0};

        // Recorro árbol copiando rutas relativas para mantener estructura.
        Files.walkFileTree(origen, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // Copio sólo si es nuevo o modificado desde el último backup
                if (attrs.lastModifiedTime().toMillis() > ultimoCorte) {
                    Path relativo = origen.relativize(file);
                    Path destinoFile = destino.resolve(relativo);
                    Files.createDirectories(destinoFile.getParent());
                    Files.copy(file, destinoFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    System.out.println("Copiando: " + relativo + (ultimoCorte == 0 ? "" : " (modificado)"));
                    contador[0]++;
                }
                return FileVisitResult.CONTINUE;
            }
        });

        // Actualizo control a ahora (fin del proceso).
        Files.createDirectories(control.getParent());
        Files.writeString(control, String.valueOf(System.currentTimeMillis()), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Backup completado: " + contador[0] + " archivo" + (contador[0]==1?"":"s"));
        System.out.println("Registro actualizado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return contador[0];
    }

    /** Lee la fecha del último backup desde el archivo de control. */
    static long leerUltimoBackup(String archivoControl) throws IOException {
        Path control = Path.of(archivoControl);
        if (!Files.exists(control)) return 0L;
        String contenido = Files.readString(control).trim();
        try {
            return Long.parseLong(contenido);
        } catch (NumberFormatException e) {
            // Si el control está corrupto, prefiero forzar copia completa.
            return 0L;
        }
    }

    /** Copia un archivo de origen a destino, envolviendo Files.copy por claridad. */
    static void copiarArchivo(Path origen, Path destino) throws IOException {
        if (destino.getParent() != null) Files.createDirectories(destino.getParent());
        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
    }
}

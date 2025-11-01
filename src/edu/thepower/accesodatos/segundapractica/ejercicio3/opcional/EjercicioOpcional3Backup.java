package edu.thepower.accesodatos.segundapractica.ejercicio3.opcional;

import java.io.IOException;

/**
 * Clase de prueba para el backup incremental.
 * La dejo igual que el ejemplo del enunciado.
 */
public class EjercicioOpcional3Backup {

    // Rutas
    private static final String ORIGEN   = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\documentos";
    private static final String DESTINO  = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\backup";
    private static final String CONTROL  = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\backup\\.lastbackup";

    public static void main(String[] args) {

        try {
            int copiados = BackupIncremental.backupIncremental(ORIGEN, DESTINO, CONTROL);
            System.out.println("Backup completado: " + copiados + " archivos");
        } catch (IOException e) {
            System.err.println("Error en el backup: " + e.getMessage());
        }
    }
}
package edu.thepower.accesodatos.segundapractica.ejercicio3;

import java.io.IOException;

/**
 * Clase de prueba para el ejercicio 3.
 * Aquí pruebo a escribir varias líneas hasta que se haga la rotación.
 */
public class Ejercicio3Log {

    // Ruta LOG
    private static final String LOG = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\app.log";

    public static void main(String[] args) {

        // Tamaño pequeño para que se vea la rotación rápido (1 KB)
        SistemaLog log = new SistemaLog(LOG, 1024);

        try {
            log.escribirLog("Aplicación iniciada", NivelLog.INFO);
            log.escribirLog("Usuario conectado", NivelLog.INFO);
            log.escribirLog("Error de conexión", NivelLog.ERROR);

            // Escribo varias más para forzar la rotación
            for (int i = 0; i < 50; i++) {
                log.escribirLog("Mensaje de prueba #" + i, NivelLog.INFO);
            }

        } catch (IOException e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }
}
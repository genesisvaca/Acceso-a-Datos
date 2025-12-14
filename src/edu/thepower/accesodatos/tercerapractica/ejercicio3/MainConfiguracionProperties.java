package edu.thepower.accesodatos.tercerapractica.ejercicio3;

import java.io.IOException;
import java.util.Properties;

public class MainConfiguracionProperties {

    public static void main(String[] args) {

        String rutaArchivo = "app.properties";

        try {
            // Cargar configuración (si no existe se crea con valores por defecto)
            Properties config = ConfiguradorAplicacion.cargarConfiguracion(rutaArchivo);

            // Leer configuración usando los helpers
            String dbHost = ConfiguradorAplicacion.getString(config, "db.host", "localhost");
            int dbPort = ConfiguradorAplicacion.getInt(config, "db.port", 3306);
            boolean debug = ConfiguradorAplicacion.getBoolean(config, "app.debug", false);

            System.out.println("Host BD: " + dbHost);
            System.out.println("Puerto BD: " + dbPort);
            System.out.println("Debug: " + debug);

            System.out.println("=== Configuración Actual ===");
            ConfiguradorAplicacion.mostrarConfiguracion(config);

            // Modificar configuración (como en el ejemplo del enunciado)
            config.setProperty("app.idioma", "es");
            config.setProperty("ui.tema", "oscuro");
            config.setProperty("db.port", "3307");

            // Guardar cambios en el mismo archivo
            ConfiguradorAplicacion.guardarConfiguracion(
                    config,
                    rutaArchivo,
                    "Configuración de Mi Aplicación"
            );

        } catch (IOException e) {
            System.err.println("Error al manejar el archivo de configuración: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

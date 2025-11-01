package edu.thepower.accesodatos.segundapractica.ejercicio2.opcional;


import java.io.IOException;
import java.util.Map;

/**
 * Clase de prueba para el opcional 2 (.env).
 * Igual que las anteriores: rutas en constantes y comentarios claros.
 */
public class EjercicioOpcional2Env {

    private static final String ENV = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\.env";

    public static void main(String[] args) {

        try {
            // Cargo las variables desde el archivo
            Map<String, String> env = EnvLoader.cargarEnv(ENV);

            // Ejemplo del enunciado
            System.out.println("Base de datos: " + env.get("DB_HOST") + ":" + env.get("DB_PORT"));

            // Uso getEnv con valor por defecto
            String debug = EnvLoader.getEnv("DEBUG", "false");
            System.out.println("Debug mode: " + debug);

        } catch (IOException e) {
            System.err.println("Error al cargar el archivo .env: " + e.getMessage());
        }
    }
}
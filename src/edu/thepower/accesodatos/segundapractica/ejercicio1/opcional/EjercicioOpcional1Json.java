package edu.thepower.accesodatos.segundapractica.ejercicio1.opcional;


import java.io.IOException;
import java.util.Map;

/**
 * Clase de prueba para el opcional 1 (JSON simple).
 * La dejo igual que las otras: con rutas en constantes.
 */
public class EjercicioOpcional1Json {

    private static final String ENTRADA = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\config.json";
    private static final String SALIDA  = "D:\\ThePower\\DAM-II\\Acceso-a-Datos\\resources\\config_nuevo.json";

    public static void main(String[] args) {
        try {
            // Leo el JSON original
            Map<String, String> config = JsonSimple.leerJsonSimple(ENTRADA);

            // Ejemplo del enunciado
            System.out.println("Host: " + config.get("host"));

            // Añado una clave nueva
            config.put("version", "1.0");

            // Lo vuelvo a escribir en otro archivo
            JsonSimple.escribirJsonSimple(config, SALIDA);

        } catch (IOException e) {
            System.err.println("Error al trabajar con JSON: " + e.getMessage());
        }
    }
}
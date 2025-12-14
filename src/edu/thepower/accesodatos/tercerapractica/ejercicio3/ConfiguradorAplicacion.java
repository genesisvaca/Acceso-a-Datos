package edu.thepower.accesodatos.tercerapractica.ejercicio3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase de ayuda para cargar, leer, modificar y guardar
 * la configuración de la aplicación usando Properties.
 *
 * Aquí centralizo la lógica de valores por defecto y validaciones.
 */
public class ConfiguradorAplicacion {

    /**
     * Carga la configuración desde archivo.
     * Si el archivo no existe, crea una configuración por defecto y la devuelve.
     *
     * @param archivo ruta del archivo de configuración
     * @return objeto Properties cargado
     * @throws IOException si hay error de lectura/escritura
     */
    public static Properties cargarConfiguracion(String archivo) throws IOException {
        Properties props = new Properties();
        File ficheroConfig = new File(archivo);

        if (ficheroConfig.exists()) {
            // Si el archivo existe, simplemente lo cargamos
            try (FileInputStream fis = new FileInputStream(ficheroConfig)) {
                props.load(fis);
            }
            System.out.println("Configuración cargada: " + archivo);
        } else {
            // Si el archivo no existe, inicializamos con valores por defecto
            inicializarValoresPorDefecto(props);
            // Opcional: guardo ya el archivo con los valores por defecto
            try (FileOutputStream fos = new FileOutputStream(ficheroConfig)) {
                props.store(fos, "Configuración inicial por defecto");
            }
            System.out.println("Archivo de configuración no encontrado. " +
                    "Se ha creado uno nuevo con valores por defecto: " + archivo);
        }

        // Por si falta alguna clave en el archivo existente, me aseguro
        // de que haya valores por defecto para todas.
        rellenarFaltantesConDefecto(props);

        return props;
    }

    /**
     * Obtiene una propiedad como String con valor por defecto.
     *
     * @param props        objeto Properties
     * @param clave        clave de la propiedad
     * @param valorDefecto valor si no existe
     * @return valor de la propiedad o valorDefecto
     */
    public static String getString(Properties props, String clave, String valorDefecto) {
        if (props == null || clave == null) {
            return valorDefecto;
        }
        String valor = props.getProperty(clave);
        return (valor != null) ? valor : valorDefecto;
    }

    /**
     * Obtiene una propiedad como int con validación.
     * Si no existe o el formato no es numérico, devuelve el valor por defecto.
     *
     * @param props        objeto Properties
     * @param clave        clave de la propiedad
     * @param valorDefecto valor si no existe o es inválido
     * @return valor int de la propiedad
     */
    public static int getInt(Properties props, String clave, int valorDefecto) {
        if (props == null || clave == null) {
            return valorDefecto;
        }

        String valor = props.getProperty(clave);
        if (valor == null) {
            return valorDefecto;
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            // Si el valor no es un número válido, informo y devuelvo el por defecto
            System.err.println("Valor inválido para la propiedad '" + clave +
                    "': '" + valor + "'. Usando valor por defecto: " + valorDefecto);
            return valorDefecto;
        }
    }

    /**
     * Obtiene una propiedad como boolean.
     * Si no existe, devuelve el valor por defecto.
     *
     * @param props        objeto Properties
     * @param clave        clave de la propiedad
     * @param valorDefecto valor si no existe
     * @return valor boolean de la propiedad
     */
    public static boolean getBoolean(Properties props, String clave, boolean valorDefecto) {
        if (props == null || clave == null) {
            return valorDefecto;
        }

        String valor = props.getProperty(clave);
        if (valor == null) {
            return valorDefecto;
        }

        // Boolean.parseBoolean devuelve true solo si es "true" (ignorando mayúsculas/minúsculas)
        return Boolean.parseBoolean(valor.trim());
    }

    /**
     * Guarda la configuración en un archivo .properties.
     *
     * @param props      objeto Properties a guardar
     * @param archivo    ruta del archivo destino
     * @param comentario comentario para el archivo
     * @throws IOException si hay error de escritura
     */
    public static void guardarConfiguracion(Properties props,
                                            String archivo,
                                            String comentario) throws IOException {
        if (props == null) {
            throw new IllegalArgumentException("Las propiedades no pueden ser nulas");
        }

        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            props.store(fos, comentario);
        }

        System.out.println("Configuración guardada: " + archivo);
    }

    /**
     * Muestra todas las propiedades por consola.
     *
     * @param props objeto Properties
     */
    public static void mostrarConfiguracion(Properties props) {
        if (props == null) {
            System.out.println("No hay configuración para mostrar.");
            return;
        }

        // Recorro las claves en el orden que las devuelve stringPropertyNames().
        // Si quisieras orden alfabético, podrías volcarlas en un TreeSet.
        for (String clave : props.stringPropertyNames()) {
            String valor = props.getProperty(clave);
            System.out.println(clave + " = " + valor);
        }
    }

    // =========================
    // Métodos privados de ayuda
    // =========================

    /**
     * Inicializa el objeto Properties con una configuración por defecto.
     * Aquí separo las categorías (db, app, ui) para que sea más legible.
     */
    private static void inicializarValoresPorDefecto(Properties props) {
        // Configuración de base de datos
        props.setProperty("db.host", "localhost");
        props.setProperty("db.port", "3306");
        props.setProperty("db.name", "mi_base_datos");
        props.setProperty("db.user", "admin");
        props.setProperty("db.password", "password");

        // Configuración de aplicación
        props.setProperty("app.titulo", "Mi Aplicación");
        props.setProperty("app.version", "1.0.0");
        props.setProperty("app.debug", "false");
        props.setProperty("app.idioma", "en");

        // Configuración de interfaz
        props.setProperty("ui.tema", "claro");
        props.setProperty("ui.tamano_fuente", "12");
    }

    /**
     * Revisa que todas las claves importantes tengan algún valor.
     * Si falta alguna, se le pone el valor por defecto.
     */
    private static void rellenarFaltantesConDefecto(Properties props) {
        // Creo un objeto temporal con los valores por defecto
        Properties defaults = new Properties();
        inicializarValoresPorDefecto(defaults);

        // Para cada clave por defecto, si falta en props, la añado
        for (String claveDefecto : defaults.stringPropertyNames()) {
            if (!props.containsKey(claveDefecto)) {
                props.setProperty(claveDefecto, defaults.getProperty(claveDefecto));
            }
        }
    }
}

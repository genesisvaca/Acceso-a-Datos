package edu.thepower.accesodatos.primerapractica;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Fichero7 {
    // Ruta base donde se crearán las carpetas de categorías
    private static Scanner sc = new Scanner(System.in);
    private static final String DIR_BASE = "C:\\biblioteca";

    public void organizarBiblioteca() throws IOException {

        System.out.print("Introduce el nombre de la categoría: ");
        String categoria = sc.nextLine().trim();

        // Crea el directorio de la categoría dentro de la ruta base
        File dirCategoria = new File(DIR_BASE + "\\" + categoria);

        if (!dirCategoria.exists()) {
            dirCategoria.mkdirs();
            System.out.println("✓ Categoría '" + categoria + "' creada exitosamente");
        } else {
            System.out.println("✓ La categoría '" + categoria + "' ya existe");
        }

        // Crea el archivo catálogo dentro de la categoría
        File catalogo = new File(dirCategoria, "catalogo.txt");
        if (!catalogo.exists()) {
            catalogo.createNewFile();
            System.out.println("✓ Catálogo creado en: " + catalogo.getAbsolutePath());
        } else {
            System.out.println("✓ El catálogo ya existe en: " + catalogo.getAbsolutePath());
        }
    }

    public void verificarLibro() throws IOException {

        System.out.print("Introduce la categoría del libro: ");
        String categoria = sc.nextLine().trim();

        System.out.print("Introduce el nombre del libro (con extensión .txt): ");
        String nombreLibro = sc.nextLine().trim();

        File libro = new File(DIR_BASE + "\\" + categoria + "\\" + nombreLibro);

        if (libro.exists()) {
            System.out.println("✓ El libro existe en: " + libro.getAbsolutePath());
            System.out.println("Tamaño: " + libro.length() + " bytes");
        } else {
            System.out.println("✗ El libro no existe en: " + libro.getAbsolutePath());
            System.out.print("¿Quieres crear el libro? (s/n): ");
            String respuesta = sc.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")) {
                libro.getParentFile().mkdirs(); // crea directorios intermedios si no existen
                libro.createNewFile();
                System.out.println("✓ Libro creado exitosamente en: " + libro.getAbsolutePath());
            } else {
                System.out.println("No se ha creado el libro.");
            }
        }
    }

    public static void main(String[] args) throws IOException {

        Fichero7 organizador = new Fichero7();

        System.out.println("=== ORGANIZADOR DE BIBLIOTECA ===");
        System.out.println("1. Organizar biblioteca");
        System.out.println("2. Verificar existencia de libro");
        System.out.print("Selecciona una opción: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                organizador.organizarBiblioteca();
                break;
            case "2":
                organizador.verificarLibro();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }
}

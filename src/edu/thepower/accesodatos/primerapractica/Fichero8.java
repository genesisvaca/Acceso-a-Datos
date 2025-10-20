package edu.thepower.accesodatos.primerapractica;

import java.io.File;
import java.net.URI;
import java.util.Scanner;

public class Fichero8 {

    // Función principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== EXPLORADOR INTELIGENTE ===");
        System.out.print("Introduce la ruta a explorar: ");
        String ruta = sc.nextLine();

        explorarCarpeta(ruta);
        convertirAURI(ruta);
    }

    // Función que lista el contenido de una carpeta
    public static void explorarCarpeta(String ruta) {
        File carpeta = new File(ruta);

        if (!carpeta.exists()) {
            System.out.println("✗ La ruta no existe: " + ruta);
            return;
        }

        System.out.println("\nExplorando: " + carpeta.getPath());
        String[] elementos = carpeta.list();

        if (elementos == null || elementos.length == 0) {
            System.out.println("La carpeta está vacía.");
            return;
        }

        int totalElementos = 0;
        for (String nombre : elementos) {
            File elemento = new File(carpeta, nombre);
            analizarElemento(elemento);
            totalElementos++;
        }

        System.out.println("\nTotal de elementos encontrados: " + totalElementos);
    }

    // Función que analiza cada elemento (archivo o directorio)
    public static void analizarElemento(File elemento) {
        if (elemento.isFile()) {
            System.out.println("- " + elemento.getName() + " [ARCHIVO - " + elemento.length() + " bytes]");
        } else if (elemento.isDirectory()) {
            String[] contenido = elemento.list();
            int cantidad = (contenido == null) ? 0 : contenido.length;
            System.out.println("- " + elemento.getName() + " [DIRECTORIO - " + cantidad + " elementos]");
        } else {
            System.out.println("- " + elemento.getName() + " [DESCONOCIDO]");
        }
    }

    // Función que convierte una ruta a URI
    public static void convertirAURI(String ruta) {
        System.out.println("\nCONVERSIÓN A URI:");
        File archivo = new File(ruta);

        System.out.println("Ruta original: " + archivo.getPath());
        try {
            URI uri = archivo.toURI();
            System.out.println("URI equivalente: " + uri);

            // Comprobación de validez
            File desdeURI = new File(uri);
            if (archivo.equals(desdeURI)) {
                System.out.println("✓ La URI es válida y apunta al mismo elemento");
            } else {
                System.out.println("✗ Hay un problema con la conversión a URI");
            }
        } catch (Exception e) {
            System.out.println("✗ Error al convertir la ruta a URI: " + e.getMessage());
        }
    }
}

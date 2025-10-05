import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class Fichero9 {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        mostrarMenu();
    }

    // ===================== MENÚ PRINCIPAL =====================
    public static void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n=== ASISTENTE PERSONAL DE ARCHIVOS ===");
            System.out.println("1. Verificar archivo");
            System.out.println("2. Explorar carpeta");
            System.out.println("3. Crear carpeta");
            System.out.println("4. Crear archivo");
            System.out.println("5. Trabajar con URIs");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> verificarArchivo();
                    case 2 -> explorarDirectorio();
                    case 3 -> crearCarpeta();
                    case 4 -> crearArchivo();
                    case 5 -> trabajarConURI();
                    case 6 -> System.out.println("Saliendo del asistente. ¡Hasta pronto!");
                    default -> System.out.println("Opción no válida. Intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
            }

        } while (opcion != 6);
    }

    // ===================== OPCIÓN 1: Verificar archivo =====================
    public static void verificarArchivo() {
        System.out.print("Introduce la ruta completa del archivo: ");
        String ruta = sc.nextLine();
        File archivo = new File(ruta);

        if (archivo.exists() && archivo.isFile()) {
            System.out.println("✓ El archivo existe en: " + archivo.getAbsolutePath());
            System.out.println("Tamaño: " + archivo.length() + " bytes");
        } else {
            System.out.println("✗ El archivo no existe en la ruta especificada.");
        }
    }

    // ===================== OPCIÓN 2: Explorar carpeta =====================
    public static void explorarDirectorio() {
        System.out.print("Introduce la ruta de la carpeta a explorar: ");
        String ruta = sc.nextLine();
        File carpeta = new File(ruta);

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("✗ La ruta no existe o no es un directorio válido.");
            return;
        }

        System.out.println("\nContenido de la carpeta " + carpeta.getAbsolutePath() + ":");
        File[] elementos = carpeta.listFiles();

        if (elementos == null || elementos.length == 0) {
            System.out.println("La carpeta está vacía.");
            return;
        }

        int total = 0;
        for (File elemento : elementos) {
            if (elemento.isFile()) {
                System.out.println("- " + elemento.getName() + " [ARCHIVO - " + elemento.length() + " bytes]");
            } else if (elemento.isDirectory()) {
                System.out.println("- " + elemento.getName() + " [DIRECTORIO]");
            }
            total++;
        }
        System.out.println("Total de elementos encontrados: " + total);
    }

    // ===================== OPCIÓN 3: Crear carpeta =====================
    public static void crearCarpeta() {
        System.out.print("Introduce la ruta donde crear la carpeta: ");
        String ruta = sc.nextLine();
        File carpeta = new File(ruta);

        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println("✓ Carpeta creada exitosamente en: " + carpeta.getAbsolutePath());
            } else {
                System.out.println("✗ No se pudo crear la carpeta.");
            }
        } else {
            System.out.println("✗ La carpeta ya existe.");
        }
    }

    // ===================== OPCIÓN 4: Crear archivo =====================
    public static void crearArchivo() {
        System.out.print("Introduce la ruta completa del archivo (incluye el nombre y extensión): ");
        String ruta = sc.nextLine();
        File archivo = new File(ruta);

        try {
            if (!archivo.exists()) {
                if (archivo.createNewFile()) {
                    System.out.println("✓ Archivo creado correctamente en: " + archivo.getAbsolutePath());
                } else {
                    System.out.println("✗ No se pudo crear el archivo.");
                }
            } else {
                System.out.println("✗ El archivo ya existe en esa ruta.");
            }
        } catch (IOException e) {
            System.out.println("✗ Error al crear el archivo: " + e.getMessage());
        }
    }

    // ===================== OPCIÓN 5: Trabajar con URIs =====================
    public static void trabajarConURI() {
        System.out.print("Introduce la ruta del archivo o carpeta: ");
        String ruta = sc.nextLine();
        File elemento = new File(ruta);

        System.out.println("\nCONVERSIÓN A URI:");
        System.out.println("Ruta original: " + elemento.getAbsolutePath());

        try {
            URI uri = elemento.toURI();
            System.out.println("URI equivalente: " + uri);

            File desdeURI = new File(uri);
            if (elemento.equals(desdeURI)) {
                System.out.println("✓ La URI es válida y apunta al mismo elemento.");
            } else {
                System.out.println("✗ La URI no coincide con la ruta original.");
            }
        } catch (Exception e) {
            System.out.println("✗ Error al convertir a URI: " + e.getMessage());
        }
    }
}

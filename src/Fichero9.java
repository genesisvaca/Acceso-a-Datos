import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Fichero9 {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("¡Bienvenido a tu Asistente Personal de Archivos!");
        mostrarMenu();
    }

    // ===================== MENÚ PRINCIPAL =====================
    public static void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n======================================");
            System.out.println("        MI ASISTENTE DE ARCHIVOS");
            System.out.println("======================================");
            System.out.println("1. Verificar si un archivo existe");
            System.out.println("2. Explorar una carpeta");
            System.out.println("3. Crear una nueva carpeta");
            System.out.println("4. Crear un nuevo archivo");
            System.out.println("5. Trabajar con URIs");
            System.out.println("6. Salir");
            System.out.println("======================================");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> verificarArchivo();
                    case 2 -> explorarDirectorio();
                    case 3 -> crearCarpeta();
                    case 4 -> crearArchivo();
                    case 5 -> trabajarConURI();
                    case 6 -> System.out.println("¡Hasta pronto! Gracias por usar el asistente.");
                    default -> System.out.println("✗ Opción no válida. Intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Por favor, introduce un número válido.");
            }

        } while (opcion != 6);
    }

    // ===================== OPCIÓN 1: Verificar archivo =====================
    public static void verificarArchivo() {
        System.out.println("\n--- VERIFICAR ARCHIVO ---");

        System.out.print("Introduce el directorio padre: ");
        String dirPadre = sc.nextLine().trim();

        System.out.print("Introduce el nombre del archivo: ");
        String nombreArchivo = sc.nextLine().trim();

        if (dirPadre.isEmpty() || nombreArchivo.isEmpty()) {
            System.out.println("✗ No se puede dejar el campo vacío.");
            esperarEnter();
            return;
        }

        File archivo = new File(dirPadre, nombreArchivo);

        if (archivo.exists()) {
            System.out.println("✓ El archivo existe en: " + archivo.getAbsolutePath());
            if (archivo.isFile()) {
                System.out.println("Es un archivo de " + archivo.length() + " bytes");
            } else if (archivo.isDirectory()) {
                System.out.println("Es un directorio");
            }
        } else {
            System.out.println("✗ El archivo no existe en la ruta especificada.");
        }

        esperarEnter();
    }

    // ===================== OPCIÓN 2: Explorar carpeta =====================
    public static void explorarDirectorio() {
        System.out.println("\n--- EXPLORAR DIRECTORIO ---");
        System.out.print("Introduce la ruta del directorio: ");
        String ruta = sc.nextLine().trim();

        File carpeta = new File(ruta);

        if (!carpeta.exists()) {
            System.out.println("✗ La ruta no existe: " + ruta);
            esperarEnter();
            return;
        }

        if (!carpeta.isDirectory()) {
            System.out.println("✗ La ruta no corresponde a un directorio.");
            esperarEnter();
            return;
        }

        File[] elementos = carpeta.listFiles();
        if (elementos == null || elementos.length == 0) {
            System.out.println("⚠️ La carpeta está vacía.");
            esperarEnter();
            return;
        }

        System.out.println("Contenido del directorio: " + carpeta.getAbsolutePath());
        int total = 0;
        for (int i = 0; i < elementos.length; i++) {
            File e = elementos[i];
            String tipo = e.isDirectory() ? "[CARPETA]" : "[ARCHIVO - " + e.length() + " bytes]";
            System.out.println((i + 1) + ". " + e.getName() + " " + tipo);
            total++;
        }
        System.out.println("\nTotal: " + total + " elemento" + (total > 1 ? "s" : ""));
        esperarEnter();
    }

    // ===================== OPCIÓN 3: Crear carpeta =====================
    public static void crearCarpeta() {
        System.out.println("\n--- CREAR CARPETA ---");
        System.out.print("Introduce la ruta de la nueva carpeta: ");
        String ruta = sc.nextLine().trim();

        if (ruta.isEmpty()) {
            System.out.println("✗ No se puede dejar el campo vacío.");
            esperarEnter();
            return;
        }

        File carpeta = new File(ruta);

        if (carpeta.exists()) {
            if (carpeta.isFile()) {
                System.out.println("⚠️ Ya existe un archivo con ese nombre en: " + carpeta.getAbsolutePath());
            } else {
                System.out.println("⚠️ La carpeta ya existe en: " + carpeta.getAbsolutePath());
            }
        } else {
            if (carpeta.mkdirs()) {
                System.out.println("✓ Carpeta creada exitosamente en: " + carpeta.getAbsolutePath());
            } else {
                System.out.println("✗ No se pudo crear la carpeta. Verifica la ruta o permisos.");
            }
        }
        esperarEnter();
    }

    // ===================== OPCIÓN 4: Crear archivo =====================
    public static void crearArchivo() {
        System.out.println("\n--- CREAR ARCHIVO ---");
        System.out.print("Introduce la ruta completa del nuevo archivo (incluye el nombre y extensión): ");
        String ruta = sc.nextLine().trim();

        if (ruta.isEmpty()) {
            System.out.println("✗ No se puede dejar el campo vacío.");
            esperarEnter();
            return;
        }

        File archivo = new File(ruta);

        try {
            if (archivo.exists()) {
                System.out.println("⚠️ El archivo ya existe en: " + archivo.getAbsolutePath());
            } else {
                File padre = archivo.getParentFile();
                if (padre != null && !padre.exists()) {
                    padre.mkdirs();
                }
                if (archivo.createNewFile()) {
                    System.out.println("✓ Archivo creado exitosamente en: " + archivo.getAbsolutePath());
                } else {
                    System.out.println("✗ No se pudo crear el archivo.");
                }
            }
        } catch (IOException e) {
            System.out.println("✗ Error al crear el archivo: " + e.getMessage());
        }
        esperarEnter();
    }

    // ===================== OPCIÓN 5: Trabajar con URIs =====================
    public static void trabajarConURI() {
        System.out.println("\n--- TRABAJAR CON URIs ---");
        System.out.println("Elige una opción:");
        System.out.println("1. Verificar una URI existente");
        System.out.println("2. Convertir ruta a URI");
        System.out.print("Tu elección: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1" -> verificarURIExistente();
            case "2" -> convertirRutaAURI();
            default -> System.out.println("✗ Opción no válida.");
        }
        esperarEnter();
    }

    private static void verificarURIExistente() {
        System.out.print("Introduce la URI (ejemplo: file:///C:/ruta/archivo.txt): ");
        String uriStr = sc.nextLine().trim();

        try {
            URI uri = new URI(uriStr);
            File archivo = new File(uri);
            if (archivo.exists()) {
                String tipo = archivo.isDirectory() ? "directorio" : "archivo";
                System.out.println("✓ La URI representa un " + tipo + " en: " + uri);
            } else {
                System.out.println("✗ La URI no existe: " + uri);
            }
        } catch (URISyntaxException e) {
            System.out.println("✗ La URI introducida no es válida.");
        }
    }

    private static void convertirRutaAURI() {
        System.out.print("Introduce la ruta a convertir: ");
        String ruta = sc.nextLine().trim();
        File archivo = new File(ruta);

        System.out.println("Ruta original: " + archivo.getAbsolutePath());
        URI uri = archivo.toURI();
        System.out.println("URI generada: " + uri);

        if (archivo.exists()) {
            System.out.println("✓ La ruta existe y la URI es válida");
        } else {
            System.out.println("⚠️ La ruta no existe, pero la URI es válida");
        }
    }

    // ===================== PAUSA =====================
    private static void esperarEnter() {
        System.out.print("\nPresiona Enter para continuar...");
        sc.nextLine();
    }
}

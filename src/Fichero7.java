import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Fichero7 {
    private static final String DIR = "C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas-Java\\Categoria";
    private static final String FILE = "catalogo.txt";
    private static final String FILELIBRO = "libro.txt";
    
    public void organizarBiblioteca() throws IOException {

        System.out.println("Introduce el nombre de la categoría: ");
        String categoria = "";
        Scanner sc = new Scanner(categoria);

        categoria += DIR;

        File dirCategoria = new File(DIR);

        if (!dirCategoria.exists()){
            dirCategoria.mkdirs();
            System.out.println("La categoría '" + categoria + "' creada exitosamente");
        } else 
        /*
        // Directorio padre que acabamos de crear en la Ruta de documentos
        File dirCategoria = new File(DIR);

        // Nombre o ruta relativa al fichero que acabo de crear
        String archivoCatalogo= FILE;
        
        // Creo una instancia File utilizando el constructor y la variable de arriba
        File archivo = new File(dirCategoria, archivoCatalogo);

        boolean fin = false;

        do{
            if (!dirCategoria.exists()){
                System.out.println("El directorio no existe.");
                dirCategoria.mkdir();
                System.out.println("Directorio creado correctamente");
            } else if (!archivo.exists()){
                System.out.println("El directorio no existe.");
                archivo.createNewFile();
                System.out.println("Archivo creado correctamente.");
            } else {
                System.out.println("El directorio y el archivo ya existe");
                fin = true;
            }
        }while(!fin);*/
    }
    
    public void verificarLibro() {
        
    }
    
    public static void main (String[] args) throws IOException {
        
        System.out.println("ORGANIZADOR DE BIBLIOTECA");
        System.out.println("1. Organizar biblioteca.");
        System.out.println("2. Verificar Existencia Libro.");
        System.out.println("3. Crear nuevo directorio.");

        String respuesta = "";
        Scanner sc = new Scanner(respuesta);
        
        switch (respuesta){
            case "1":
                Fichero7 organizador = new Fichero7();
                organizador.organizarBiblioteca();
        }
        
        
    }

}

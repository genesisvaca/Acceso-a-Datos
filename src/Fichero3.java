import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Fichero3 {
    public static void main(String[] args) throws URISyntaxException {
        //  FICHERO 3. Hacer una ruta con URI. Crear el objeto.
        //  Verificar que existe(else if). Si es un directorio o archivo (else if) (try-catch)
        URI uri = new URI("file:///C:/Users/AlumnoAfternoon/Documents/Pruebas-Java/hijo.txt");
        File archivo = new File(uri);

        try {
            if (archivo.exists()) {
                // Verifica si el archivo existe y muestra en que ruta se encuentra
                System.out.println("Archivo existe en ruta: " + archivo.getAbsolutePath());
            } else {
                System.out.println("Archivo no encontrado");
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}

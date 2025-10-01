import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Fichero3 {
    // TODO FICHERO 3. Hacer una ruta con URI. Crear el objeto.
    //  Verificar que existe(else if). Si es un ruta o ruta (else if) (try-catch)
    /*public static void main(String[] args) throws URISyntaxException {
        //  FICHERO 3. Hacer una ruta con URI. Crear el objeto.
        //  Verificar que existe(else if). Si es un ruta o ruta (else if) (try-catch)
        URI uri = new URI("file:///C:/Users/AlumnoAfternoon/Documents/Pruebas-Java/hijo.txt");
        File ruta = new File(uri);

        try {
            if (ruta.exists()) {
                // Verifica si el ruta existe y muestra en que ruta se encuentra
                System.out.println("ruta existe en ruta: " + ruta.getAbsolutePath());
            } else {
                System.out.println("ruta no encontrado");
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }*/

    // Versión clase
    public static void main(String[] args){
        // Utilizamos try-catch porque el objeto URI tiende a 
        // dar errores al trabajar con este
        try{
            // Crea un objeto URI apartir de una cadena de texto
          String uriString = "file:///C:/Users/AlumnoAfternoon/Documents/Pruebas-Java/hijo.txt"; 
          URI uri = new URI(uriString);
          
          // Crea una instancia de FIle utilizando el constructor
          File ruta = new File(uri);

            if(ruta.exists()){
                // Verificar si la ruta especificada es un directorio
                if(ruta.isDirectory()){
                    // Si en la ruta el ultimo elemento es un directorio me muestra un mensaje
                    // en pantalla de que la ruta especificada es un directorio
                    System.out.println("La ruta presenta un directorio en: " + uri.toString());
                } else if (ruta.isFile()){ // Verificar si la ruta especificada es un ruta
                    // Si en la ruta el ultimo elemento es un ruta me muestra un ,mensaje
                    // en pantalla de que la ruta especificada es un ruta
                    System.out.println("La ruta presenta un ruta en: " + uri.toString());

                }

            } else
                System.out.println("La URI no existe: " + uri.toString());
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }
}
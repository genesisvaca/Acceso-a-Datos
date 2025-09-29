import java.io.File;

public class Fichero1 {
    public static void main(String[] args) {
        // Directorio padre que acabamos de crear en la Ruta de documentos
        File directorioPadre = new File("C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas-Java");

        // Nombre o ruta relativa al fichero que acabo de crear
        String nomHijo = "hijo.txt";

        // Creo una instancia File utilizando el constructor y la variable de arriba
        File archivo = new File(directorioPadre, nomHijo);

        // Verificar si el archivo existe
        if (archivo.exists()){
            // Si el archivo existe me muestra un mensaje en pantalla
            // de que existe mostrando la  ruta completa especificada
            System.out.println("El archivo existe en la ruta: " + archivo.getAbsolutePath());
        } else {
            // Si el archivo NO existe me muestra un mensaje en pantalla
            // de que NO existe mostrando la  ruta completa especificada
            System.out.println("El archivo NO existe en la ruta: " + archivo.getAbsolutePath());
        }
    }

}
// TODO FICHERO 3. Hacer una ruta con URI. Crear el objeto. Verificar que existe(else if). Si es un directorio o archivo (else if) (try-catch)
// TODO Fichero 4. Copia exacta del fichero 1, pero mostrar dentro del if el contenido de la carpeta sus nombres(list(),length)
// TODO Fichero 5/6. Verificar que dicho directorio(s) o archivo existe, en caso de que no existe crearlo.
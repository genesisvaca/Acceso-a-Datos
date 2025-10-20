package edu.thepower.accesodatos.primerapractica;

import java.io.IOException;
import java.io.File;

public class Fichero5 {
    // TODO Fichero 5/6. Verificar que dicho directorio(s)
    //  o archivo existe, en caso de que no existe crearlo.
    public static void main (String[] args) throws IOException {
        // Rutas de archivo y directorio cmo cadena de texto
        String directorio= "C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas-Java\\Padre";
        String archivo = "C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas-Java\\hijo.txt";

        // Crear instancias File utilizando su constructor
        File dirPadre = new File(directorio);
        File archivoHijo = new File(archivo);

        // Verificar si el archivo y directorio existe
        boolean fin = false;

        do{
            if (!dirPadre.exists()){
                System.out.println("El directorio no existe.");
                dirPadre.mkdir();
                System.out.println("Directorio creado correctamente");
            } else if (!archivoHijo.exists()){
                System.out.println("El directorio no existe.");
                archivoHijo.createNewFile();
                System.out.println("Archivo creado correctamente.");
            } else {
                System.out.println("El directorio y el archivo ya existe");
                fin = true;
            }
        }while(!fin);
    }
}

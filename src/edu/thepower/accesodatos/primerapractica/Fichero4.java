package edu.thepower.accesodatos.primerapractica;

import java.io.File;

public class Fichero4 {
    // TODO Fichero 4. Copia exacta del fichero 1,
    //  pero mostrar dentro del if el contenido de la carpeta sus nombres(list(),length)
    /*public static void main(String[] args) {
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
            for (String listDir : directorioPadre.list()){
                if(archivo.isFile())
                    System.out.println(listDir + " [ARCHIVO]");
                else if (archivo.isDirectory())
                    System.out.println(listDir + "[DIRECTORIO]");
            }
        } else {
            // Si el archivo NO existe me muestra un mensaje en pantalla
            // de que NO existe mostrando la  ruta completa especificada
            System.out.println("El archivo NO existe en la ruta: " + archivo.getAbsolutePath());
        }
    }*/
    // Versión Clase
    public static void main(String[] args){
        // Ruta de directorio como cadena de texto
        String dirPadre = "C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas-Java";

        // Creación de la instacia utilizando el constructor File
        File directorio = new File(dirPadre);

        // Verifica si el archivo existe y si es un directorio
        if(directorio.exists() && directorio.isDirectory()){
            // Creación de un array del contenido dentro de una carpeta
            String[]  contenido = directorio.list();

            // Bucle con el cual pasamos mostrando el contenido de la carpeta de uno en uno
            for (int i= 0 ; i< contenido.length; i++){
                System.out.println(contenido[i]);
            }
        } else {
            System.out.println("La siguiente ruta ni es un directorio o no existe");
        }
    }
}

package edu.thepower.accesodatos.datosbinarios;

import java.io.*;

public class EjemploDataStreams {
    public static void main(String[] args) {

        // Escritura
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream("primitivos.dat"))){
            // Escritura de caracteres ASCII en el fichero especificado
            dos.writeInt(12345);
            dos.writeDouble(99.99);
            dos.writeUTF("Producto de ejemplo");
            dos.writeBoolean(true);
        }catch(IOException e){
            System.err.println("Error al escribir \n" + e.getMessage());
        }

        // Lectura
        try(DataInputStream dis = new DataInputStream(new FileInputStream("primitivos.dat"))){
            // Escritura de caracteres ASCII en el fichero especificado
            int numero = dis.readInt();
            double precio = dis.readDouble();
            String nombre = dis.readUTF();
            boolean activo = dis.readBoolean();

            System.out.println("Número: " + numero);
            System.out.println("Precio: " + precio);
            System.out.println("Nombre: " + nombre);
            System.out.println("Activo: " + activo);

        }catch(IOException e){
            System.err.println("Error al escribir \n" + e.getMessage());
        }

    }
}
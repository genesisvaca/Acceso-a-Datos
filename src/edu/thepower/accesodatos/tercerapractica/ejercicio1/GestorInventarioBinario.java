package edu.thepower.accesodatos.tercerapractica.ejercicio1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidad para gestionar un inventario de productos
 * almacenado en un archivo binario usando DataInputStream/DataOutputStream.
 */
public class GestorInventarioBinario {

    /**
     * Escribe un producto en el archivo binario.
     * Si el archivo ya existía, se sobrescribe entero.
     *
     * @param archivo  ruta del archivo donde guardar
     * @param producto objeto Producto a guardar
     * @throws IOException si hay error al escribir
     */
    public static void escribirProducto(String archivo, Producto producto) throws IOException {

        // try-with-resources para asegurarnos de que el stream se cierra siempre
        try (DataOutputStream salidaBinaria = new DataOutputStream(
                new FileOutputStream(archivo))) {

            // Importante: mantener siempre el mismo orden de escritura
            salidaBinaria.writeInt(producto.getId());
            salidaBinaria.writeUTF(producto.getNombre());
            salidaBinaria.writeDouble(producto.getPrecio());
            salidaBinaria.writeInt(producto.getStock());

            System.out.println("Producto guardado: " + producto.getNombre());
        }
    }

    /**
     * Añade un producto al final del archivo (modo append).
     *
     * @param archivo  ruta del archivo
     * @param producto producto a añadir
     * @throws IOException si hay error
     */
    public static void agregarProducto(String archivo, Producto producto) throws IOException {

        // FileOutputStream en modo append = true para no machacar el archivo existente
        try (DataOutputStream salidaBinaria = new DataOutputStream(
                new FileOutputStream(archivo, true))) {

            salidaBinaria.writeInt(producto.getId());
            salidaBinaria.writeUTF(producto.getNombre());
            salidaBinaria.writeDouble(producto.getPrecio());
            salidaBinaria.writeInt(producto.getStock());

            System.out.println("Producto añadido: " + producto.getNombre());
        }
    }

    /**
     * Lee todos los productos del archivo binario.
     *
     * @param archivo ruta del archivo a leer
     * @return lista de productos leídos (vacía si no existe el archivo)
     * @throws IOException si hay error al leer
     */
    public static List<Producto> leerProductos(String archivo) throws IOException {

        List<Producto> productosLeidos = new ArrayList<>();

        File ficheroInventario = new File(archivo);

        // Si el archivo aún no existe, devolvemos lista vacía y avisamos por consola
        if (!ficheroInventario.exists()) {
            System.out.println("El archivo " + archivo + " no existe todavía. No hay productos que leer.");
            return productosLeidos;
        }

        try (DataInputStream entradaBinaria = new DataInputStream(
                new FileInputStream(ficheroInventario))) {

            // Bucle de lectura hasta que llegamos al final del archivo (EOFException)
            while (true) {
                try {
                    int id = entradaBinaria.readInt();
                    String nombre = entradaBinaria.readUTF();
                    double precio = entradaBinaria.readDouble();
                    int stock = entradaBinaria.readInt();

                    Producto producto = new Producto(id, nombre, precio, stock);
                    productosLeidos.add(producto);

                } catch (EOFException finDeArchivo) {
                    // Cuando salta esta excepción significa que ya no quedan más productos
                    break;
                }
            }
        }

        return productosLeidos;
    }
}

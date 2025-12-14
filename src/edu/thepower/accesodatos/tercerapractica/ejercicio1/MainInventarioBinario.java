package edu.thepower.accesodatos.tercerapractica.ejercicio1;

import java.io.IOException;
import java.util.List;

public class MainInventarioBinario {
    public static void main(String[] args) {
        try {
            Producto p1 = new Producto(1, "Laptop", 999.99, 10);
            Producto p2 = new Producto(2, "Mouse", 19.99, 50);

            GestorInventarioBinario.escribirProducto("inventario.dat", p1);
            GestorInventarioBinario.agregarProducto("inventario.dat", p2);

            List<Producto> productos = GestorInventarioBinario.leerProductos("inventario.dat");

            for (Producto producto : productos) {
                System.out.println(producto);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

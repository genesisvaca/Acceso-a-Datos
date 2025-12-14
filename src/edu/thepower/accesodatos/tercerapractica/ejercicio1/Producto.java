package edu.thepower.accesodatos.tercerapractica.ejercicio1;

/**
 * Clase modelo Producto.
 * Representa un producto del inventario con su id, nombre, precio y stock disponible.
 */
public class Producto {

    // Atributos del producto
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    // Constructor vacío (útil si más adelante se usa alguna librería de serialización)
    public Producto() {
    }

    // Constructor con todos los campos
    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        // Formato de salida igual al que pide el enunciado
        return "ID: " + id +
                ", Nombre: " + nombre +
                ", Precio: " + precio +
                ", Stock: " + stock;
    }
}

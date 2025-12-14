package edu.thepower.accesodatos.tercerapractica.ejercicio3.opcional;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un dato individual encontrado en el archivo binario.
 */
public class ElementoDato {

    // Posición inicial en el archivo (en bytes)
    private int posicion;
    private String tipo;
    private String valor;

    public ElementoDato(int posicion, String tipo, String valor) {
        this.posicion = posicion;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}

/**
 * Contiene toda la información del análisis de un archivo binario.
 */
class Reporte {

    private String nombreArchivo;
    private long tamañoBytes;
    private List<ElementoDato> elementos;

    private int totalInts;
    private int totalDoubles;
    private int totalStrings;
    private int totalBooleans;

    public Reporte(String nombreArchivo, long tamañoBytes) {
        this.nombreArchivo = nombreArchivo;
        this.tamañoBytes = tamañoBytes;
        this.elementos = new ArrayList<>();
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public long getTamañoBytes() {
        return tamañoBytes;
    }

    public void setTamañoBytes(long tamañoBytes) {
        this.tamañoBytes = tamañoBytes;
    }

    public List<ElementoDato> getElementos() {
        return elementos;
    }

    public void setElementos(List<ElementoDato> elementos) {
        this.elementos = elementos;
    }

    public int getTotalInts() {
        return totalInts;
    }

    public void setTotalInts(int totalInts) {
        this.totalInts = totalInts;
    }

    public int getTotalDoubles() {
        return totalDoubles;
    }

    public void setTotalDoubles(int totalDoubles) {
        this.totalDoubles = totalDoubles;
    }

    public int getTotalStrings() {
        return totalStrings;
    }

    public void setTotalStrings(int totalStrings) {
        this.totalStrings = totalStrings;
    }

    public int getTotalBooleans() {
        return totalBooleans;
    }

    public void setTotalBooleans(int totalBooleans) {
        this.totalBooleans = totalBooleans;
    }

    public void incrementarInts() {
        totalInts++;
    }

    public void incrementarDoubles() {
        totalDoubles++;
    }

    public void incrementarStrings() {
        totalStrings++;
    }

    public void incrementarBooleans() {
        totalBooleans++;
    }
}

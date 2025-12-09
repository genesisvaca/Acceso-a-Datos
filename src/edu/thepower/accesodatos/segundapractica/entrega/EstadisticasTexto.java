package edu.thepower.accesodatos.segundapractica.entrega;

/***
 * Ejercicio 1: Contador de Palabras y Estadísticas
 * Objetivo: Leer un archivo de texto y generar estadísticas completas sobre su
 * contenido.
 */
import java.util.Objects;

/**
 * Clase de modelo para encapsular estadísticas de un archivo de texto.
 * He mantenido esta clase lo más simple posible para facilitar testeo y reutilización.
 */

public class EstadisticasTexto {
    private int numeroLineas;
    private int numeroPalabras;
    private int numeroCaracteres; // Nota: caracteres "tal cual" leídos, no bytes
    private String palabraMasLarga;

    public EstadisticasTexto(int numeroLineas, int numeroPalabras, int numeroCaracteres, String palabraMasLarga) {
        this.numeroLineas = numeroLineas;
        this.numeroPalabras = numeroPalabras;
        this.numeroCaracteres = numeroCaracteres;
        this.palabraMasLarga = palabraMasLarga;
    }

    public int getNumeroLineas() {
        return numeroLineas;
    }

    public void setNumeroLineas(int numeroLineas) {
        this.numeroLineas = numeroLineas;
    }

    public int getNumeroPalabras() {
        return numeroPalabras;
    }

    public void setNumeroPalabras(int numeroPalabras) {
        this.numeroPalabras = numeroPalabras;
    }

    public int getNumeroCaracteres() {
        return numeroCaracteres;
    }

    public void setNumeroCaracteres(int numeroCaracteres) {
        this.numeroCaracteres = numeroCaracteres;
    }

    public String getPalabraMasLarga() {
        return palabraMasLarga;
    }

    public void setPalabraMasLarga(String palabraMasLarga) {
        this.palabraMasLarga = palabraMasLarga;
    }

    @Override
    public String toString() {
        String masLarga = (palabraMasLarga == null ? "" : palabraMasLarga);
        return new StringBuilder()
                .append("=== Estadísticas del archivo ===\n")
                .append("Líneas: ").append(numeroLineas).append('\n')
                .append("Palabras: ").append(numeroPalabras).append('\n')
                .append("Caracteres: ").append(numeroCaracteres).append('\n')
                .append("Palabra más larga: ")
                .append(masLarga)
                .append(masLarga.isEmpty() ? "" : " (" + masLarga.length() + " caracteres)")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadisticasTexto that = (EstadisticasTexto) o;
        return numeroLineas == that.numeroLineas && numeroPalabras == that.numeroPalabras && numeroCaracteres == that.numeroCaracteres && Objects.equals(palabraMasLarga, that.palabraMasLarga);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroLineas, numeroPalabras, numeroCaracteres, palabraMasLarga);
    }
}

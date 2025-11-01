package edu.thepower.accesodatos.segundapractica.ejercicio1;

/**
 * Clase sencilla para guardar los datos que saco del análisis del archivo.
 * Aquí no hago lógica, solo guardo la información.
 */
public class EstadisticasTexto {

    private int numeroLineas;
    private int numeroPalabras;
    private int numeroCaracteres;
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

    /**
     * Lo dejo formateado como en el enunciado para poder imprimirlo tal cual.
     */
    @Override
    public String toString() {
        String masLarga = (palabraMasLarga == null) ? "" : palabraMasLarga;
        StringBuilder sb = new StringBuilder();
        sb.append("=== Estadísticas del archivo ===").append(System.lineSeparator());
        sb.append("Líneas: ").append(numeroLineas).append(System.lineSeparator());
        sb.append("Palabras: ").append(numeroPalabras).append(System.lineSeparator());
        sb.append("Caracteres: ").append(numeroCaracteres).append(System.lineSeparator());
        if (!masLarga.isEmpty()) {
            sb.append("Palabra más larga: ").append(masLarga)
                    .append(" (").append(masLarga.length()).append(" caracteres)");
        } else {
            sb.append("Palabra más larga: ");
        }
        return sb.toString();
    }
}

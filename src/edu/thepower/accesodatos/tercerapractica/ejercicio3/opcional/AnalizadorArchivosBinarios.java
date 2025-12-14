package edu.thepower.accesodatos.tercerapractica.ejercicio3.opcional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class AnalizadorArchivosBinarios {

    /**
     * Analiza un archivo binario y genera un reporte con su contenido.
     *
     * @param archivo ruta del archivo a analizar
     * @return objeto Reporte con la información del análisis
     * @throws IOException si hay error al leer
     */
    public static Reporte analizarArchivoBinario(String archivo) throws IOException {

        File fichero = new File(archivo);
        if (!fichero.exists()) {
            throw new FileNotFoundException("El archivo binario no existe: " + archivo);
        }

        long tamaño = fichero.length();
        Reporte reporte = new Reporte(fichero.getName(), tamaño);

        // Leo tdo el archivo en memoria para poder "mirar" desde distintas posiciones
        byte[] datos = new byte[(int) tamaño];
        try (FileInputStream fis = new FileInputStream(fichero)) {
            int leidos = fis.read(datos);
            if (leidos != tamaño) {
                throw new IOException("No se han podido leer todos los bytes del archivo.");
            }
        }

        int posicionActual = 0;

        while (posicionActual < datos.length) {
            int bytesRestantes = datos.length - posicionActual;

            // DataInputStream "local" que empieza en la posición actual
            ByteArrayInputStream bais = new ByteArrayInputStream(datos, posicionActual, bytesRestantes);
            DataInputStream disLocal = new DataInputStream(bais);

            String tipoDetectado = detectarTipoDato(disLocal, bytesRestantes, datos, posicionActual);

            // Ahora, en función del tipo, vuelvo a leer el valor y calculo cuántos bytes consume
            String valorComoTexto;
            int bytesConsumidos;

            switch (tipoDetectado) {
                case "UTF": {
                    int len = ((datos[posicionActual] & 0xFF) << 8) | (datos[posicionActual + 1] & 0xFF);
                    bytesConsumidos = 2 + len;

                    ByteArrayInputStream baisUtf = new ByteArrayInputStream(datos, posicionActual, bytesConsumidos);
                    try (DataInputStream disUtf = new DataInputStream(baisUtf)) {
                        String s = disUtf.readUTF();
                        valorComoTexto = "\"" + s + "\"";
                    }
                    reporte.incrementarStrings();
                    break;
                }

                case "DOUBLE": {
                    bytesConsumidos = 8;
                    ByteArrayInputStream baisDouble = new ByteArrayInputStream(datos, posicionActual, bytesConsumidos);
                    try (DataInputStream disDouble = new DataInputStream(baisDouble)) {
                        double d = disDouble.readDouble();
                        valorComoTexto = String.valueOf(d);
                    }
                    reporte.incrementarDoubles();
                    break;
                }

                case "BOOLEAN": {
                    bytesConsumidos = 1;
                    int b = datos[posicionActual] & 0xFF;
                    boolean boolValue = (b != 0);
                    valorComoTexto = String.valueOf(boolValue);
                    reporte.incrementarBooleans();
                    break;
                }

                case "INT":
                default: {
                    bytesConsumidos = Math.min(4, bytesRestantes);
                    ByteArrayInputStream baisInt = new ByteArrayInputStream(datos, posicionActual, bytesConsumidos);
                    try (DataInputStream disInt = new DataInputStream(baisInt)) {
                        int valorInt = disInt.readInt();
                        valorComoTexto = String.valueOf(valorInt);
                    } catch (EOFException e) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < bytesConsumidos; i++) {
                            sb.append(datos[posicionActual + i] & 0xFF).append(" ");
                        }
                        valorComoTexto = "BYTES(" + sb.toString().trim() + ")";
                    }
                    reporte.incrementarInts();
                    break;
                }
            }

            ElementoDato elemento = new ElementoDato(posicionActual, tipoDetectado, valorComoTexto);
            reporte.getElementos().add(elemento);

            posicionActual += bytesConsumidos;
        }

        return reporte;
    }

    /**
     * Intenta detectar el tipo de dato en la posición actual.
     * OJO: no es perfecto, simplemente intenta adivinar usando algunas reglas.
     *
     * @param dis             DataInputStream posicionado (solo lectura local)
     * @param bytesRestantes  bytes que quedan desde esta posición
     * @param datos           array completo de bytes
     * @param offset          posición actual dentro del array
     * @return String con el tipo detectado: "INT", "DOUBLE", "UTF" o "BOOLEAN"
     * @throws IOException si hay error
     */
    private static String detectarTipoDato(DataInputStream dis,
                                           int bytesRestantes,
                                           byte[] datos,
                                           int offset) throws IOException {

        // 1) Intento ver si parece una cadena UTF (writeUTF)
        if (bytesRestantes >= 3) { // mínimo 2 de longitud + 1 char
            int len = ((datos[offset] & 0xFF) << 8) | (datos[offset + 1] & 0xFF);
            if (len > 0 && len <= bytesRestantes - 2) {
                try {
                    ByteArrayInputStream baisUtf = new ByteArrayInputStream(datos, offset, 2 + len);
                    try (DataInputStream disUtf = new DataInputStream(baisUtf)) {
                        String s = disUtf.readUTF();
                        // Compruebo que los caracteres sean "normales" (sin cosas raras)
                        boolean todosVisibles = s.chars().allMatch(c ->
                                !Character.isISOControl(c) || c == '\n' || c == '\r' || c == '\t');
                        if (todosVisibles && s.length() <= 100) {
                            return "UTF";
                        }
                    }
                } catch (IOException ignored) {
                    // Si falla, sigo probando otros tipos
                }
            }
        }

        // 2) Si solo queda 1 byte y es 0 o 1, sospecho que es un boolean
        if (bytesRestantes == 1) {
            int b = datos[offset] & 0xFF;
            if (b == 0 || b == 1) {
                return "BOOLEAN";
            }
        }

        // 3) Intento ver si tiene pinta de double (8 bytes, con parte decimal)
        if (bytesRestantes >= 8) {
            try {
                ByteArrayInputStream baisDouble = new ByteArrayInputStream(datos, offset, 8);
                try (DataInputStream disDouble = new DataInputStream(baisDouble)) {
                    double d = disDouble.readDouble();
                    if (!Double.isNaN(d) && !Double.isInfinite(d)) {
                        double parteEntera = Math.rint(d);
                        double diferencia = Math.abs(d - parteEntera);
                        if (diferencia > 1e-6) {
                            return "DOUBLE";
                        }
                    }
                }
            } catch (IOException ignored) {
            }
        }

        // 4) Si llego aquí, por defecto lo trato como int (4 bytes) cuando se pueda
        return "INT";
    }

    /**
     * Guarda el reporte en un archivo de texto.
     *
     * @param reporte objeto Reporte
     * @param archivo ruta del archivo destino
     * @throws IOException si hay error al escribir
     */
    public static void guardarReporte(Reporte reporte, String archivo) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            escribirReporteEnWriter(reporte, pw);
        }
    }

    /**
     * Muestra el reporte por consola con formato.
     *
     * @param reporte objeto Reporte
     */
    public static void mostrarReporte(Reporte reporte) {
        PrintWriter pw = new PrintWriter(System.out, true);
        escribirReporteEnWriter(reporte, pw);
    }

    // =========================
    // Métodos privados de ayuda
    // =========================

    private static void escribirReporteEnWriter(Reporte reporte, PrintWriter pw) {

        pw.println("=== Reporte de Análisis de Archivo Binario ===");
        pw.println("Archivo: " + reporte.getNombreArchivo());
        pw.println("Tamaño: " + reporte.getTamañoBytes() + " bytes");
        pw.println("Estructura detectada:");

        int posAcumulado = 0;

        for (ElementoDato elemento : reporte.getElementos()) {
            String tipo = elemento.getTipo();
            String valor = elemento.getValor();

            int bytesElemento = switch (tipo) {
                case "INT" -> 4;
                case "DOUBLE" -> 8;
                case "BOOLEAN" -> 1;
                case "UTF" -> {
                    // Aprox: 2 bytes de longitud + nº de caracteres (si son ASCII)
                    int longitudCadena = 0;
                    String v = valor;
                    if (v != null && v.startsWith("\"") && v.endsWith("\"")) {
                        v = v.substring(1, v.length() - 1);
                    }
                    longitudCadena = v != null ? v.length() : 0;
                    yield 2 + longitudCadena;
                }
                default -> 1;
            };

            int inicio = posAcumulado;
            int fin = inicio + bytesElemento - 1;

            if ("UTF".equals(tipo)) {
                String texto = valor;
                if (texto == null) texto = "\"\"";
                pw.printf("[Pos %d-%d] %s: %s (%d caracteres)%n",
                        inicio, fin, tipo, texto, extraerLongitudCadena(valor));
            } else {
                pw.printf("[Pos %d-%d] %s: %s%n", inicio, fin, tipo, valor);
            }

            posAcumulado = fin + 1;
        }

        pw.println("Resumen:");
        pw.println("Enteros (int): " + reporte.getTotalInts());
        pw.println("Decimales (double): " + reporte.getTotalDoubles());
        pw.println("Cadenas (UTF): " + reporte.getTotalStrings());
        pw.println("Booleanos: " + reporte.getTotalBooleans());
        pw.println("Total elementos: " + reporte.getElementos().size());
    }

    private static int extraerLongitudCadena(String valorConComillas) {
        if (valorConComillas == null) return 0;
        String s = valorConComillas;
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            s = s.substring(1, s.length() - 1);
        }
        return s.length();
    }
}

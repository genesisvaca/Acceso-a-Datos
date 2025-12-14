package edu.thepower.accesodatos.tercerapractica.ejercicio3.opcional;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainAnalizadorBinario {

    public static void main(String[] args) {

        String archivoDatos = "datos.dat";
        String archivoReporte = "reporte_datos.txt";

        // 1. Creo el archivo binario de ejemplo
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivoDatos))) {
            dos.writeInt(100);
            dos.writeUTF("Producto A");
            dos.writeDouble(99.99);
            dos.writeBoolean(true);
            dos.writeInt(200);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 2. Lo analizo y genero reporte
        try {
            Reporte reporte = AnalizadorArchivosBinarios.analizarArchivoBinario(archivoDatos);
            AnalizadorArchivosBinarios.mostrarReporte(reporte);
            AnalizadorArchivosBinarios.guardarReporte(reporte, archivoReporte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

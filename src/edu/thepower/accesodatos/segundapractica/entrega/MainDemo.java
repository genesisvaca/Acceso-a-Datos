package edu.thepower.accesodatos.segundapractica.entrega;

import edu.thepower.accesodatos.segundapractica.entrega.opcional.BackupIncremental;
import edu.thepower.accesodatos.segundapractica.entrega.opcional.EnvLoader;
import edu.thepower.accesodatos.segundapractica.entrega.opcional.JsonSimple;

public class MainDemo {


    private static final String ARCHIVO = "C:\\Users\\AlumnoAfternoon\\Documents\\Acceso-a-Datos\\resources\\archivo.txt";
    private static final String ENTRADA = "C:\\Users\\AlumnoAfternoon\\Documents\\Acceso-a-Datos\\resources\\entrada.txt";
    private static final String SALIDA = "C:\\Users\\AlumnoAfternoon\\Documents\\Acceso-a-Datos\\resources\\salida";

    public static void main(String[] args) throws Exception {
        // --- Ejercicio 1 ---
        EstadisticasTexto est = AnalizadorTexto.analizarArchivo(ARCHIVO);
        System.out.println(est);
        AnalizadorTexto.guardarEstadisticas(est, "salida/estadisticas.txt");

        // --- Ejercicio 2 ---
        String[] entradas = {"archivo1.txt", "archivo2.txt"};
        MergeArchivos.combinarArchivos(entradas, "salida/combinado.txt", "Java");

        // --- Ejercicio 3 ---
        SistemaLog log = new SistemaLog("app.log", 1024);
        log.escribirLog("Aplicación iniciada", NivelLog.INFO);
        log.escribirLog("Usuario conectado", NivelLog.INFO);
        log.escribirLog("Error de conexión", NivelLog.ERROR);

        // --- Opcionales ---
        // JsonSimple
        var cfg = JsonSimple.leerJsonSimple("config.json");
        cfg.put("version", "1.0");
        JsonSimple.escribirJsonSimple(cfg, "config_nuevo.json");

        // EnvLoader
        EnvLoader.cargarEnv(".env");
        System.out.println("Debug mode: " + EnvLoader.getEnv("DEBUG", "false"));

        // BackupIncremental
        BackupIncremental.backupIncremental("./documentos", "./backup", "./backup/.lastbackup");
    }
}

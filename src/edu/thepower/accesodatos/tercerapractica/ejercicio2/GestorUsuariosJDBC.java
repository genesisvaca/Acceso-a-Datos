package edu.thepower.accesodatos.tercerapractica.ejercicio2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidades para trabajar con la tabla 'usuarios'
 * usando JDBC y PreparedStatement.
 */
public class GestorUsuariosJDBC {

    /**
     * Crea la tabla usuarios si no existe.
     *
     * @param conn conexión a la base de datos
     * @throws SQLException si hay error al crear
     */
    public static void crearTabla(Connection conn) throws SQLException {
        String sqlCrearTabla = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL UNIQUE,
                    edad INT NOT NULL
                )
                """;

        // Para DDL me apaño con un Statement simple
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sqlCrearTabla);
            System.out.println("Tabla 'usuarios' creada");
        }
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param conn   conexión activa
     * @param nombre nombre del usuario
     * @param email  email del usuario
     * @param edad   edad del usuario
     * @return ID generado del usuario insertado
     * @throws SQLException si hay error
     */
    public static int insertarUsuario(Connection conn,
                                      String nombre,
                                      String email,
                                      int edad) throws SQLException {

        // Validaciones básicas antes de lanzar SQL
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (edad <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor que cero");
        }

        String sqlInsertar = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";

        // Pido las claves generadas para poder devolver el id
        try (PreparedStatement pstmt = conn.prepareStatement(
                sqlInsertar, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, email);
            pstmt.setInt(3, edad);

            int filas = pstmt.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se ha insertado ningún usuario");
            }

            // Recupero el ID autogenerado
            try (ResultSet claves = pstmt.getGeneratedKeys()) {
                if (claves.next()) {
                    int idGenerado = claves.getInt(1);
                    System.out.println("Usuario insertado con ID: " + idGenerado);
                    return idGenerado;
                } else {
                    throw new SQLException("No se pudo obtener el ID generado");
                }
            }
        }
    }

    /**
     * Busca usuarios por nombre (búsqueda parcial).
     *
     * @param conn   conexión activa
     * @param nombre fragmento de nombre a buscar
     * @return lista de usuarios encontrados
     * @throws SQLException si hay error
     */
    public static List<Usuario> buscarPorNombre(Connection conn,
                                                String nombre) throws SQLException {

        List<Usuario> usuariosEncontrados = new ArrayList<>();

        String sqlBuscar = "SELECT id, nombre, email, edad FROM usuarios WHERE nombre LIKE ?";

        // Si el nombre viene null, lo trato como cadena vacía
        String patronBusqueda = (nombre == null) ? "" : nombre.trim();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlBuscar)) {

            // Búsqueda parcial con LIKE
            pstmt.setString(1, "%" + patronBusqueda + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombreUsuario = rs.getString("nombre");
                    String email = rs.getString("email");
                    int edad = rs.getInt("edad");

                    usuariosEncontrados.add(new Usuario(id, nombreUsuario, email, edad));
                }
            }
        }

        return usuariosEncontrados;
    }

    /**
     * Actualiza el email de un usuario.
     *
     * @param conn       conexión activa
     * @param id         ID del usuario
     * @param nuevoEmail nuevo email
     * @return true si se actualizó, false si no existe
     * @throws SQLException si hay error
     */
    public static boolean actualizarEmail(Connection conn,
                                          int id,
                                          String nuevoEmail) throws SQLException {

        if (nuevoEmail == null || nuevoEmail.isBlank()) {
            throw new IllegalArgumentException("El nuevo email no puede estar vacío");
        }

        String sqlActualizar = "UPDATE usuarios SET email = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlActualizar)) {

            pstmt.setString(1, nuevoEmail);
            pstmt.setInt(2, id);

            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Email actualizado para usuario ID: " + id);
                return true;
            } else {
                // No ha encontrado ese ID
                System.out.println("No se ha encontrado usuario con ID: " + id);
                return false;
            }
        }
    }

    /**
     * Elimina un usuario por ID.
     *
     * @param conn conexión activa
     * @param id   ID del usuario a eliminar
     * @return true si se eliminó, false si no existía
     * @throws SQLException si hay error
     */
    public static boolean eliminarUsuario(Connection conn,
                                          int id) throws SQLException {

        String sqlEliminar = "DELETE FROM usuarios WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlEliminar)) {

            pstmt.setInt(1, id);

            int filasEliminadas = pstmt.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Usuario eliminado: ID " + id);
                return true;
            } else {
                System.out.println("No se ha encontrado usuario con ID: " + id);
                return false;
            }
        }
    }
}

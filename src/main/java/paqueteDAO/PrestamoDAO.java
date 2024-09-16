
package paqueteDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import paqueteDTO.Fecha;
import paqueteDTO.PrestamoDTO;

public class PrestamoDAO {

    // Método para obtener la conexión (puede variar según tu configuración)
    private Connection getConnection() throws SQLException {
        // Ejemplo de obtener conexión (cambia según tu configuración)
        String url = "jdbc:mysql://localhost:3306/tu_basedatos";
        String username = "tu_usuario";
        String password = "tu_contraseña";
        return DriverManager.getConnection(url, username, password);
    }

    // Insertar prestamo con manejo de conexión
    public void insertarPrestamo(PrestamoDTO prestamo) throws SQLException {
        String sql = "INSERT INTO Prestamo (legajoDocente, codigoRecurso, estaPrestado, fechaPrestamo, horaPrestamo, observacionPrestamo) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, prestamo.getLegajoDocente());
            ps.setString(2, prestamo.getCodigoRecurso());
            ps.setBoolean(3, prestamo.isEstaPrestado());
            ps.setObject(4, prestamo.getFechaPrestamo());
            ps.setObject(5, prestamo.getHoraPrestamo());
            ps.setString(6, prestamo.getObservacionPrestamo());
            ps.executeUpdate();
        }
    }

    // Obtener prestamo con manejo de conexión
    public PrestamoDTO obtenerPrestamo(String legajoDocente, String codigoRecurso) throws SQLException {
        String sql = "SELECT * FROM Prestamo WHERE legajoDocente = ? AND codigoRecurso = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, legajoDocente);
            ps.setString(2, codigoRecurso);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PrestamoDTO prestamo = new PrestamoDTO();
                    prestamo.setLegajoDocente(rs.getString("legajoDocente"));
                    prestamo.setCodigoRecurso(rs.getString("codigoRecurso"));
                    prestamo.setEstaPrestado(rs.getBoolean("estaPrestado"));
                    prestamo.setFechaPrestamo(rs.getObject("fechaPrestamo", Fecha.class));
                    prestamo.setHoraPrestamo(rs.getObject("horaPrestamo", Fecha.class));
                    prestamo.setObservacionPrestamo(rs.getString("observacionPrestamo"));
                    return prestamo;
                }
            }
        }
        return null;
    }

    // Actualizar prestamo con manejo de conexión
    public void actualizarPrestamo(PrestamoDTO prestamo) throws SQLException {
        String sql = "UPDATE Prestamo SET estaPrestado = ?, fechaPrestamo = ?, horaPrestamo = ?, observacionPrestamo = ? " +
                     "WHERE legajoDocente = ? AND codigoRecurso = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, prestamo.isEstaPrestado());
            ps.setObject(2, prestamo.getFechaPrestamo());
            ps.setObject(3, prestamo.getHoraPrestamo());
            ps.setString(4, prestamo.getObservacionPrestamo());
            ps.setString(5, prestamo.getLegajoDocente());
            ps.setString(6, prestamo.getCodigoRecurso());
            ps.executeUpdate();
        }
    }

    // Eliminar prestamo con manejo de conexión
    public void eliminarPrestamo(String legajoDocente, String codigoRecurso) throws SQLException {
        String sql = "DELETE FROM Prestamo WHERE legajoDocente = ? AND codigoRecurso = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, legajoDocente);
            ps.setString(2, codigoRecurso);
            ps.executeUpdate();
        }
    }

    // Obtener todos los prestamos con manejo de conexión
    public List<PrestamoDTO> obtenerTodosLosPrestamos() throws SQLException {
        List<PrestamoDTO> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM Prestamo";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PrestamoDTO prestamo = new PrestamoDTO();
                prestamo.setLegajoDocente(rs.getString("legajoDocente"));
                prestamo.setCodigoRecurso(rs.getString("codigoRecurso"));
                prestamo.setEstaPrestado(rs.getBoolean("estaPrestado"));
                prestamo.setFechaPrestamo(rs.getObject("fechaPrestamo", Fecha.class));
                prestamo.setHoraPrestamo(rs.getObject("horaPrestamo", Fecha.class));
                prestamo.setObservacionPrestamo(rs.getString("observacionPrestamo"));
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }
}


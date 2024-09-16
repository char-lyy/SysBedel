package paqueteDAO;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import paqueteDTO.Fecha;
import paqueteDTO.ObservacionDTO;

public class ObservacionDAO {

    // Método para obtener la conexión (cambia según tu configuración)
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tu_basedatos";
        String username = "tu_usuario";
        String password = "tu_contraseña";
        return DriverManager.getConnection(url, username, password);
    }

    // Método para insertar una nueva observación
    public void insertarObservacion(ObservacionDTO observacion) throws SQLException {
        String sql = "INSERT INTO Observacion (codigoObservacion, numeroAula, descripcion, fechaObservacion) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, observacion.getCodigoObservacion());
            ps.setInt(2, observacion.getNumeroAula());
            ps.setString(3, observacion.getDescripcion());
            ps.setObject(4, observacion.getFechaObservacion());
            ps.executeUpdate();
        }
    }

    // Método para obtener una observación por su código
    public ObservacionDTO obtenerObservacion(int codigoObservacion) throws SQLException {
        String sql = "SELECT * FROM Observacion WHERE codigoObservacion = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, codigoObservacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ObservacionDTO observacion = new ObservacionDTO();
                    observacion.setCodigoObservacion(rs.getInt("codigoObservacion"));
                    observacion.setNumeroAula(rs.getInt("numeroAula"));
                    observacion.setDescripcion(rs.getString("descripcion"));
                    observacion.setFechaObservacion(rs.getObject("fechaObservacion", Fecha.class));
                    return observacion;
                }
            }
        }
        return null;
    }

    // Método para actualizar una observación existente
    public void actualizarObservacion(ObservacionDTO observacion) throws SQLException {
        String sql = "UPDATE Observacion SET numeroAula = ?, descripcion = ?, fechaObservacion = ? " +
                     "WHERE codigoObservacion = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, observacion.getNumeroAula());
            ps.setString(2, observacion.getDescripcion());
            ps.setObject(3, observacion.getFechaObservacion());
            ps.setInt(4, observacion.getCodigoObservacion());
            ps.executeUpdate();
        }
    }

    // Método para eliminar una observación por su código
    public void eliminarObservacion(int codigoObservacion) throws SQLException {
        String sql = "DELETE FROM Observacion WHERE codigoObservacion = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, codigoObservacion);
            ps.executeUpdate();
        }
    }

    // Método para obtener todas las observaciones
    public List<ObservacionDTO> obtenerTodasLasObservaciones() throws SQLException {
        List<ObservacionDTO> observaciones = new ArrayList<>();
        String sql = "SELECT * FROM Observacion";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ObservacionDTO observacion = new ObservacionDTO();
                observacion.setCodigoObservacion(rs.getInt("codigoObservacion"));
                observacion.setNumeroAula(rs.getInt("numeroAula"));
                observacion.setDescripcion(rs.getString("descripcion"));
                observacion.setFechaObservacion(rs.getObject("fechaObservacion", Fecha.class));
                observaciones.add(observacion);
            }
        }
        return observaciones;
    }
}


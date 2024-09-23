package paqueteDAO;

import paqueteDTO.ActividadDTO;
import paqueteDTO.FechaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActividadDAO {

    private Connection connection;

    public ActividadDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar una actividad en la base de datos
    public boolean insertarActividad(ActividadDTO actividad) throws SQLException {
        String query = "INSERT INTO actividad (idActividad, fechaInicio, fechaFin, periodoActividad, tipoActividad) "
                     + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, actividad.getIdActividad());
            ps.setDate(2, actividad.getFechaInicioActividad().toSqlDate());
            ps.setDate(3, actividad.getFechaFinActividad().toSqlDate());
            ps.setString(4, actividad.getPeriodoActividad().name()); // Almacenamos el nombre del enum
            ps.setString(5, actividad.getTipoActividad().name()); // Almacenamos el nombre del enum

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        }
    }

    // Método para actualizar una actividad existente en la base de datos
    public boolean actualizarActividad(ActividadDTO actividad) throws SQLException {
        String query = "UPDATE actividad SET fechaInicio = ?, fechaFin = ?, periodoActividad = ?, tipoActividad = ? "
                     + "WHERE idActividad = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, actividad.getFechaInicioActividad().toSqlDate());
            ps.setDate(2, actividad.getFechaFinActividad().toSqlDate());
            ps.setString(3, actividad.getPeriodoActividad().name()); // Actualizamos el nombre del enum
            ps.setString(4, actividad.getTipoActividad().name()); // Actualizamos el nombre del enum
            ps.setInt(5, actividad.getIdActividad());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    // Método para eliminar una actividad de la base de datos
    public boolean eliminarActividad(int idActividad) throws SQLException {
        String query = "DELETE FROM actividad WHERE idActividad = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idActividad);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        }
    }
//
//    // Método para obtener una actividad por su ID
//    public ActividadDTO obtenerActividadPorId(int idActividad) throws SQLException {
//        String query = "SELECT * FROM actividad WHERE idActividad = ?";
//
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setInt(1, idActividad);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    FechaDTO fechaInicio = new FechaDTO(rs.getDate("fechaInicio"));
//                    FechaDTO fechaFin = new FechaDTO(rs.getDate("fechaFin"));
//                    ActividadDTO.PeriodoActividad periodo = ActividadDTO.PeriodoActividad.valueOf(rs.getString("periodoActividad"));
//                    ActividadDTO.TipoActividad tipo = ActividadDTO.TipoActividad.valueOf(rs.getString("tipoActividad"));
//
//                    return new ActividadDTO(idActividad, fechaInicio, fechaFin, periodo, tipo);
//                }
//            }
//        }
//
//        return null; // Si no se encontró la actividad con ese ID
//    }
}

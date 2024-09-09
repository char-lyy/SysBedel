package paqueteDAO;

import paqueteDTO.CatedraDocenteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatedraDocenteDAO {

    private Connection connection;

    // Constructor que recibe una conexión a la base de datos
    public CatedraDocenteDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para agregar una nueva relación Cátedra-Docente
    public boolean agregarCatedraDocente(CatedraDocenteDTO catedraDocente) {
        String sql = "INSERT INTO CatedraDocente (legajoDocente, codigoCatedra) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, catedraDocente.getLegajoDocente());
            ps.setInt(2, catedraDocente.getCodigoCatedra());
            return ps.executeUpdate() > 0; // Devuelve true si se insertó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(ps, null);
        }
    }

    // Método para obtener una relación Cátedra-Docente por legajoDocente y codigoCatedra
    public CatedraDocenteDTO obtenerCatedraDocente(int legajoDocente, int codigoCatedra) {
        String sql = "SELECT * FROM CatedraDocente WHERE legajoDocente = ? AND codigoCatedra = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, legajoDocente);
            ps.setInt(2, codigoCatedra);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new CatedraDocenteDTO(
                    rs.getString("legajoDocente"),
                    rs.getString("codigoCatedra")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(ps, rs);
        }
        return null; // Devuelve null si no se encontró la relación
    }

    // Método para obtener todas las relaciones Cátedra-Docente
    public List<CatedraDocenteDTO> obtenerTodasLasCatedrasDocentes() {
        List<CatedraDocenteDTO> catedrasDocentes = new ArrayList<>();
        String sql = "SELECT * FROM CatedraDocente";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CatedraDocenteDTO catedraDocente = new CatedraDocenteDTO(
                    rs.getString("legajoDocente"),
                    rs.getString("codigoCatedra")
                );
                catedrasDocentes.add(catedraDocente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(ps, rs);
        }
        return catedrasDocentes;
    }

    // Método para actualizar una relación Cátedra-Docente
    public boolean actualizarCatedraDocente(CatedraDocenteDTO catedraDocente) {
        String sql = "UPDATE CatedraDocente SET codigoCatedra = ? WHERE legajoDocente = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, catedraDocente.getCodigoCatedra());
            ps.setInt(2, catedraDocente.getLegajoDocente());
            return ps.executeUpdate() > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(ps, null);
        }
    }

    // Método para eliminar una relación Cátedra-Docente por legajoDocente y codigoCatedra
    public boolean eliminarCatedraDocente(String legajoDocente, String codigoCatedra) {
        String sql = "DELETE FROM CatedraDocente WHERE legajoDocente = ? AND codigoCatedra = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, legajoDocente);
            ps.setInt(2, codigoCatedra);
            return ps.executeUpdate() > 0; // Devuelve true si se eliminó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(ps, null);
        }
    }

    // Método para cerrar los recursos de la base de datos
    private void cerrarRecursos(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close(); // Cerramos la conexión después de cada operación
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

package paqueteDAO;

import paqueteDTO.CuentaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import utilidades.ConnectionManager;
import utilidades.Fecha;
import utilidades.FechaTiempo;

public class CuentaDAO {

    // Método para buscar una cuenta por email
    public CuentaDTO buscarPorEmail(String email) throws Exception {
        String query = "SELECT * FROM cuentas WHERE email = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                
                
                return new CuentaDTO(
                        
                    resultSet.getInt("id"),
                    resultSet.getString("password_hash"),
                    FechaTiempo.fromLocalDateTime(resultSet.getTimestamp("created_at").toLocalDateTime()));
               
            }
        }
        return null; // Si no se encuentra la cuenta
    }

    // Método para registrar una nueva cuenta
    public boolean registrarCuenta(CuentaDTO cuenta) throws Exception {
        String query = "INSERT INTO cuentas (email, password_hash, created_at) VALUES (?, ?, NOW())";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cuenta.getEmail());
            statement.setString(2, cuenta.getPasswordHash());
            return statement.executeUpdate() > 0;
        }
    }
}

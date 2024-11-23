package paqueteDAO;

import java.sql.PreparedStatement;
import paqueteDTO.LogDTO;
import utilidades.ConnectionManager;

public class LogDAO {

    // Método para registrar un log de acceso
    public boolean registrarLog(LogDTO log) throws Exception {
        String query = "INSERT INTO login_logs (user_id, login_time, ip_address, status) VALUES (?, NOW(), ?, ?)";
        PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
        statement.setInt(1, log.getUserId());
        statement.setString(2, log.getIpAddress());
        statement.setString(3, log.getStatus());
        return statement.executeUpdate() > 0;
    }
}

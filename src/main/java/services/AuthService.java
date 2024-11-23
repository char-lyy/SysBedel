package services;

import paqueteDAO.CuentaDAO;
import paqueteDAO.LogDAO;
import paqueteDTO.CuentaDTO;
import paqueteDTO.LogDTO;

public class AuthService {
    private CuentaDAO cuentaDAO;
    private LogDAO logDAO;

    public AuthService() {
        this.cuentaDAO = new CuentaDAO();
        this.logDAO = new LogDAO();
    }

    /**
     * Método para autenticar a un usuario.
     * 
     * @param mail  Nombre de usuario proporcionado por el cliente.
     * @param password  Contraseña proporcionada por el cliente.
     * @param ipAddress Dirección IP desde donde se realiza el intento.
     * @return true si el login es exitoso, false en caso contrario.
     * @throws Exception si ocurre algún error durante el proceso.
     */
    public boolean login(String mail, String password, String ipAddress) throws Exception {
        // Buscar la cuenta en la base de datos por el nombre de usuario
        CuentaDTO cuenta = cuentaDAO.buscarPorEmail(mail);

        // Crear un log para registrar el intento de inicio de sesión
        LogDTO log = new LogDTO();
        log.setIpAddress(ipAddress);

        if (cuenta != null && cuenta.getPasswordHash().equals(password)) {
            // Login exitoso
            log.setUserId(cuenta.getId());
            log.setStatus("SUCCESS");
            logDAO.registrarLog(log); // Registrar el log de acceso
            return true;
        } else {
            // Login fallido
            log.setUserId(cuenta != null ? cuenta.getId() : 0); // 0 si el usuario no existe
            log.setStatus("FAIL");
            logDAO.registrarLog(log); // Registrar el log de intento fallido
            return false;
        }
    }
}

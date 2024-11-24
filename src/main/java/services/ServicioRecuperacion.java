package services;

import java.util.logging.Level;
import java.util.logging.Logger;
import paqueteDAO.CuentaDAO;
import paqueteDTO.CuentaDTO;

public class ServicioRecuperacion {

    private CuentaDAO cuentaDAO;
    private EmailService emailService;
    private String token;
    private String email;
    
    public ServicioRecuperacion() {

        cuentaDAO = new CuentaDAO();
        emailService = new EmailService();
        token = "";
        email = "";
    }

    public boolean enviarCodigoRecuperacion(String email) {
        
        this.email = email;
        
        try {

            CuentaDTO cuentaDTO = cuentaDAO.buscarPorEmail(email);

            if (cuentaDTO == null) {
                return false;
            }

            token = generarCodigoRecuperacion();

            emailService.enviarCorreo(email, "Recuperacion de contraseña SysBedel", token);

            return true;
        } catch (Exception ex) {
            Logger.getLogger(ServicioRecuperacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private String generarCodigoRecuperacion() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    public boolean verificarCodigo(String token) {
        return this.token.equals(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}

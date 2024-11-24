package services;

import java.util.logging.Level;
import java.util.logging.Logger;
import paqueteDAO.CuentaDAO;
import paqueteDTO.CuentaDTO;

public class ServicioRecuperacion {

    private CuentaDAO cuentaDAO;
    private EmailService emailService;
    private String token;
    
    public ServicioRecuperacion() {

        cuentaDAO = new CuentaDAO();
        emailService = new EmailService();
        token = "";
    }

    public boolean enviarCodigoRecuperacion(String email) {

        try {

            CuentaDTO cuentaDTO = cuentaDAO.buscarPorEmail(email);

            if (cuentaDTO == null) {
                return false;
            }
            
            token = generarCodigoRecuperacion();
            
            emailService.enviarCorreo(email, "Recuperacion de contraseña SysBedel", token);

        } catch (Exception ex) {
            Logger.getLogger(ServicioRecuperacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private String generarCodigoRecuperacion() {
        return String.valueOf((int) (Math.random() * 1000000));
    }
}

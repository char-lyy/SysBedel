package vista.panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import paqueteDAO.CuentaDAO;
import services.ServicioRecuperacion;
import vista.VentanaPrincipal;
import vista.frames.FrameLogin;

public class PanelCambiarContraseña extends JPanel {

    private JTextField codigoField;
    private JPasswordField nuevaContrasenaField;
    private JButton cambiarButton;
    private JLabel mensajeLabel;
    private JFrame frame;
    private ServicioRecuperacion servicioRecuperacion;

    public PanelCambiarContraseña(JFrame frame, ServicioRecuperacion servicioRecuperacion) {

        this.frame = frame;

        // Crear componentes
        codigoField = new JTextField(20);
        nuevaContrasenaField = new JPasswordField(20);
        cambiarButton = new JButton("Cambiar Contraseña");
        mensajeLabel = new JLabel("");

        cambiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigoField.getText();
                String nuevaContrasena = nuevaContrasenaField.getText();

                if (codigo.isEmpty() || nuevaContrasena.isEmpty()) {
                    mensajeLabel.setText("Por favor, complete todos los campos.");
                } else {
                    // Verificar el código y cambiar la contraseña
                    boolean exito = servicioRecuperacion.verificarCodigo(codigo);
                    if (exito) {
                        CuentaDAO cuentaDAO = new CuentaDAO();
                        try {
                            cuentaDAO.modificarCuenta(servicioRecuperacion.getEmail(), nuevaContrasena);
                        } catch (Exception ex) {
                            Logger.getLogger(PanelCambiarContraseña.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        JOptionPane.showMessageDialog(null, "Te hemos enviado un código de recuperación al correo.");
                        
                        frame.dispose();
                        
                        FrameLogin frameLogin = new FrameLogin();
                        
                        frameLogin.setVisible(true);
                    } else {
                        mensajeLabel.setText("Código inválido.");
                    }
                }
            }
        });

        // Organizar los componentes en el panel
        this.add(new JLabel("Ingrese el código de recuperación:"));
        this.add(codigoField);
        this.add(new JLabel("Nueva contraseña:"));
        this.add(nuevaContrasenaField);
        this.add(cambiarButton);
        this.add(mensajeLabel);
    }

}

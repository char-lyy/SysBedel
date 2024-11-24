package vista.panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import services.ServicioRecuperacion;

public class PanelRecuperarContraseña extends JPanel {

    private JTextField emailField;
    private JButton enviarButton;
    private JLabel mensajeLabel;
    private JFrame frame;
    private ServicioRecuperacion servicioRecuperacion;

    public PanelRecuperarContraseña(JFrame frame) {

        this.frame = frame;
        servicioRecuperacion = new ServicioRecuperacion();

        // Crear componentes
        emailField = new JTextField(20);
        enviarButton = new JButton("Enviar Código");
        mensajeLabel = new JLabel("");

        // Agregar ActionListener para el botón
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                if (email.isEmpty()) {
                    mensajeLabel.setText("Por favor, ingrese su correo.");
                } else {
                    // Aquí se debe llamar al servicio para enviar el código de recuperación
                    boolean exito = servicioRecuperacion.enviarCodigoRecuperacion(email);
                    if (exito) {

                        JOptionPane.showMessageDialog(null, "Te hemos enviado un código de recuperación al correo.");

                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(PanelRecuperarContraseña.this,
                                "Mail no encontrado. Revise la casilla o contacte con servicio tecnico.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        // Organizar los componentes en el panel
        this.add(new JLabel("Ingrese su correo electrónico:"));
        this.add(emailField);
        this.add(enviarButton);
        this.add(mensajeLabel);
    }

}

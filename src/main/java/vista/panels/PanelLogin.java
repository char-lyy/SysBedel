package vista.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import paqueteDTO.CuentaDTO;
import services.AuthService;
import vista.VentanaPrincipal;
import vista.frames.FrameLogin;
import vista.frames.FrameRecuperarContraseña;

/**
 *
 * @author carlos
 */
public class PanelLogin extends JPanel {

    private JTextField emailField;       
    private JPasswordField passwordField; 
    private JButton loginButton;       
    private JButton recoverPasswordButton;
    private JButton cancelButton;      
    private FrameLogin frame;

    public PanelLogin(FrameLogin frame) {

        this.frame = frame;

        inicializarComponentes();

        configurarComponentes();
    }

    void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10)); // Espaciado entre componentes

        // Crear el formulario en el Centro
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Dos filas, dos columnas
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes

        JLabel userLabel = new JLabel("Mail:");
        emailField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField(15);

        formPanel.add(userLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // Crear y agregar los botones en el Sur
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        loginButton = new JButton("Iniciar sesión");
        recoverPasswordButton = new JButton("Recuperar contraseña");
        cancelButton = new JButton("Cancelar");
        buttonPanel.add(loginButton);
        buttonPanel.add(recoverPasswordButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    void configurarComponentes() {
        configurarBotonIniciarSesion();
        configurarBotonRecuperarContraseña();
        configurarBotonCancelar();

    }

    private void configurarBotonIniciarSesion() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores de los campos
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                // Validar campos vacíos
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(PanelLogin.this,
                            "Por favor, complete todos los campos.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!CuentaDTO.mailValido(email)) {
                    JOptionPane.showMessageDialog(PanelLogin.this,
                            "Mail invalido. Escriba nuevamente.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Crear instancia del servicio de autenticación
                    AuthService authService = new AuthService();

                    // Obtener dirección IP simulada (puede ser extraída dinámicamente)
                    String ipAddress = "127.0.0.1";

                    // Intentar iniciar sesión
                    boolean loginExitoso = authService.login(email, password, ipAddress);

                    if (loginExitoso) {

                        JOptionPane.showMessageDialog(PanelLogin.this,
                                "Inicio de sesión exitoso. ¡Bienvenido, " + email + "!",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);

                        SwingUtilities.invokeLater(() -> {
                            new VentanaPrincipal().setVisible(true);
                        });

                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(PanelLogin.this,
                                "Credenciales incorrectas. Por favor, inténtelo nuevamente.",
                                "Error de autenticación",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PanelLogin.this,
                            "Ocurrió un error al intentar iniciar sesión. Intente más tarde.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void configurarBotonRecuperarContraseña() {
        
       recoverPasswordButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               
               FrameRecuperarContraseña frame = new FrameRecuperarContraseña();
               
           }
       });
    }

    private void configurarBotonCancelar() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

    }

    // Getters para acceder a los componentes desde otra clase
    public JTextField getUserField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}

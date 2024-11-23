package vista.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import services.AuthService;
import utilidades.ConnectionManager;
import vista.VentanaPrincipal;

/**
 *
 * @author carlos
 */
public class PanelLogin extends JPanel {

    private JTextField userField;       // Campo de texto para el usuario
    private JPasswordField passwordField; // Campo de texto para la contraseña
    private JButton loginButton;       // Botón para iniciar sesión
    private JButton cancelButton;      // Botón para cancelar

    public PanelLogin() {
        
        inicializarComponentes();
        
        configurarComponentes();
    }

    void inicializarComponentes() {
        // Configurar el diseño del panel
        setLayout(new BorderLayout(10, 10)); // Espaciado entre componentes

        // Crear el formulario en el Centro
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Dos filas, dos columnas
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes

        JLabel userLabel = new JLabel("Usuario:");
        userField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField(15);

        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // Crear y agregar los botones en el Sur
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        loginButton = new JButton("Iniciar sesión");
        cancelButton = new JButton("Cancelar");
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    void configurarComponentes(){
        configurarBotonIniciarSesion();
    }
    
    private void configurarBotonIniciarSesion() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores de los campos
                String username = userField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                // Validar campos vacíos
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(PanelLogin.this,
                            "Por favor, complete todos los campos.",
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
                    boolean loginExitoso = authService.login(username, password, ipAddress);

                    if (loginExitoso) {
                        JOptionPane.showMessageDialog(PanelLogin.this,
                                "Inicio de sesión exitoso. ¡Bienvenido, " + username + "!",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                        SwingUtilities.invokeLater(() -> {
                            new VentanaPrincipal().setVisible(true);
                        });
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

    // Getters para acceder a los componentes desde otra clase
    public JTextField getUserField() {
        return userField;
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

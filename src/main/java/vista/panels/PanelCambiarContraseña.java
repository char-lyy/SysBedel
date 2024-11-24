//package vista.panels;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import services.ServicioRecuperacion;
//
//public class PanelCambiarContraseña extends JPanel {
//
//    private JTextField codigoField;
//    private JTextField nuevaContrasenaField;
//    private JButton cambiarButton;
//    private JLabel mensajeLabel;
//
//    public PanelCambiarContraseña() {
//        // Crear componentes
//        codigoField = new JTextField(20);
//        nuevaContrasenaField = new JTextField(20);
//        cambiarButton = new JButton("Cambiar Contraseña");
//        mensajeLabel = new JLabel("");
//
//        // Agregar ActionListener para el botón
//        cambiarButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String codigo = codigoField.getText();
//                String nuevaContrasena = nuevaContrasenaField.getText();
//
//                if (codigo.isEmpty() || nuevaContrasena.isEmpty()) {
//                    mensajeLabel.setText("Por favor, complete todos los campos.");
//                } else {
//                    // Verificar el código y cambiar la contraseña
//                    boolean exito = verificarCodigoYCambiarContrasena(codigo, nuevaContrasena);
//                    if (exito) {
//                        mensajeLabel.setText("Contraseña cambiada con éxito.");
//                    } else {
//                        mensajeLabel.setText("Código inválido.");
//                    }
//                }
//            }
//        });
//
//        // Organizar los componentes en el panel
//        this.add(new JLabel("Ingrese el código de recuperación:"));
//        this.add(codigoField);
//        this.add(new JLabel("Nueva contraseña:"));
//        this.add(nuevaContrasenaField);
//        this.add(cambiarButton);
//        this.add(mensajeLabel);
//    }
//
//    private boolean verificarCodigoYCambiarContrasena(String codigo, String nuevaContrasena) {
//        // Verificar que el código sea válido y cambiar la contraseña
//        ServicioRecuperacion servicio = new ServicioRecuperacion();
//        if (servicio.verificarToken("usuario@dominio.com", codigo)) {
//            // Aquí debería ir la lógica para actualizar la contraseña en la base de datos
//            System.out.println("Contraseña actualizada a: " + nuevaContrasena);
//            return true;
//        }
//        return false;
//    }
//}

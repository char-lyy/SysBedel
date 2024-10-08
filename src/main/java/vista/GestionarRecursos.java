package vista;

import paqueteDTO.RecursoDTO;
import principal.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionarRecursos extends JFrame {

    /**
     * Constructor que configura la interfaz de la clase.
     */
    public GestionarRecursos() {
        setTitle("Gestionar Recursos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes

        // Panel central con GridBagLayout para organizar los componentes
        JPanel centralPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Etiqueta y campo de texto para el nombre del recurso
        JLabel labelNombreRecurso = new JLabel("Nombre del Recurso:");
        JTextField textNombreRecurso = new JTextField(15);
        centralPanel.add(labelNombreRecurso, gbc);

        gbc.gridx = 1;
        centralPanel.add(textNombreRecurso, gbc);

        // Etiqueta y campo de texto para la cantidad
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel labelCantidad = new JLabel("Cantidad:");
        JTextField textCantidad = new JTextField(15);
        centralPanel.add(labelCantidad, gbc);

        gbc.gridx = 1;
        centralPanel.add(textCantidad, gbc);

        // Botones para registrar y consultar recursos
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Panel para los botones
        JButton buttonRegistrar = new JButton("Registrar Recurso");
        JButton buttonConsultar = new JButton("Consultar Recursos");
        buttonPanel.add(buttonRegistrar);
        buttonPanel.add(buttonConsultar);

        // Agregar el panel de botones al panel central
        centralPanel.add(buttonPanel, gbc);

        // Acción para registrar recurso en la base de datos
        buttonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombreRecurso = textNombreRecurso.getText();
                    int cantidad = Integer.parseInt(textCantidad.getText());

                    if (!nombreRecurso.isEmpty() && cantidad > 0) {
                        RecursoDTO recurso = new RecursoDTO(0, nombreRecurso, cantidad); // 0 para el código ya que es autogenerado
                        registrarRecurso(recurso);
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, ingresa valores válidos.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un valor numérico válido para la cantidad.");
                }
            }
        });

        // Acción para consultar recursos en la base de datos
        buttonConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarRecursos();
            }
        });

        // Añadir el panel central al panel principal
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        // Añadir el panel principal a la ventana
        add(mainPanel);
    }

    /**
     * Método para registrar recurso en la base de datos
     * @param recurso
     */
    private void registrarRecurso(RecursoDTO recurso) {
        String query = "INSERT INTO recurso (descripcionRecurso, cantidadRecurso) VALUES (?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, recurso.getDescripcionRecurso());
            statement.setInt(2, recurso.getCantidadRecurso());
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Recurso registrado exitosamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar recurso: " + ex.getMessage());
        }
    }

    /**
     * Método para consultar los recursos en la base de datos
     */
    private void consultarRecursos() {
        String query = "SELECT codigoRecurso, descripcionRecurso, cantidadRecurso FROM recurso"; // Asegúrate que el nombre de la tabla sea correcto
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            StringBuilder recursos = new StringBuilder("Recursos Registrados:\n\n");

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigoRecurso");
                String descripcion = resultSet.getString("descripcionRecurso");
                int cantidad = resultSet.getInt("cantidadRecurso");
                recursos.append("Código: ").append(codigo).append(", Descripción: ").append(descripcion)
                        .append(", Cantidad: ").append(cantidad).append("\n");
            }

            JOptionPane.showMessageDialog(this, recursos.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar recursos: " + ex.getMessage());
        }
    }

    /**
     * Este método muestra la interfaz por pantalla.
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionarRecursos frame = new GestionarRecursos();
            frame.setVisible(true);
        });
    }
}

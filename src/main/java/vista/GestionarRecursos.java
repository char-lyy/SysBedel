package vista;

import paqueteDTO.RecursoDTO;
import utilidades.ConnectionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestionarRecursos extends JFrame {

    private DefaultTableModel recursosTableModel;
    private JTable recursosTable;
    private DefaultTableModel solicitudesTableModel;
    private JTable solicitudesTable;

    /**
     * Constructor que configura la interfaz de la clase.
     */
    public GestionarRecursos() {
        setTitle("Gestionar Recursos");
        setSize(800, 600);
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

        // Etiqueta y campo de texto para el nombre del responsable
        JLabel labelResponsable = new JLabel("Nombre del Responsable:");
        JTextField textResponsable = new JTextField(15);
        centralPanel.add(labelResponsable, gbc);

        gbc.gridx = 1;
        centralPanel.add(textResponsable, gbc);

        // Etiqueta y campo de texto para la cantidad a solicitar
        JLabel labelCantidad = new JLabel("Cantidad a Solicitar:");
        JTextField textCantidad = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centralPanel.add(labelCantidad, gbc);

        gbc.gridx = 1;
        centralPanel.add(textCantidad, gbc);

        // Botón para solicitar recursos
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton buttonSolicitar = new JButton("Solicitar Recurso");
        centralPanel.add(buttonSolicitar, gbc);

        // Tabla para mostrar los recursos disponibles
        String[] recursosColumnNames = {"Código", "Descripción", "Cantidad"};
        recursosTableModel = new DefaultTableModel(recursosColumnNames, 0);
        recursosTable = new JTable(recursosTableModel);
        JScrollPane recursosScrollPane = new JScrollPane(recursosTable);
        mainPanel.add(recursosScrollPane, BorderLayout.CENTER);

        // Tabla para mostrar las solicitudes
        String[] solicitudesColumnNames = {"Código Recurso", "Descripción", "Cantidad Solicitada", "Responsable", "Fecha", "Hora"};
        solicitudesTableModel = new DefaultTableModel(solicitudesColumnNames, 0);
        solicitudesTable = new JTable(solicitudesTableModel);
        JScrollPane solicitudesScrollPane = new JScrollPane(solicitudesTable);
        mainPanel.add(solicitudesScrollPane, BorderLayout.SOUTH);

        // Acción para solicitar recurso en la base de datos
        buttonSolicitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = recursosTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int codigoRecurso = (int) recursosTableModel.getValueAt(selectedRow, 0);
                        String descripcionRecurso = (String) recursosTableModel.getValueAt(selectedRow, 1);
                        int cantidadSolicitar = Integer.parseInt(textCantidad.getText());
                        String responsable = textResponsable.getText();

                        solicitarRecurso(codigoRecurso, descripcionRecurso, cantidadSolicitar, responsable);
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, selecciona un recurso de la tabla.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un valor numérico válido para la cantidad a solicitar.");
                }
            }
        });

        // Añadir el panel central al panel principal
        mainPanel.add(centralPanel, BorderLayout.NORTH);

        // Añadir el panel principal a la ventana
        add(mainPanel);
        consultarRecursos(); // Inicializar la consulta al abrir la interfaz
        consultarSolicitudes(); // Inicializar la consulta de solicitudes
    }

    /**
     * Método para consultar los recursos en la base de datos.
     */
    private void consultarRecursos() {
        String query = "SELECT codigoRecurso, descripcionRecurso, cantidadRecurso FROM recurso"; // Asegúrate que el nombre de la tabla sea correcto
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            recursosTableModel.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigoRecurso");
                String descripcion = resultSet.getString("descripcionRecurso");
                int cantidad = resultSet.getInt("cantidadRecurso");
                recursosTableModel.addRow(new Object[]{codigo, descripcion, cantidad});
            }

            JOptionPane.showMessageDialog(this, "Recursos actualizados.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar recursos: " + ex.getMessage());
        }
    }

    /**
     * Método para consultar las solicitudes en la base de datos.
     */
    private void consultarSolicitudes() {
        String query = "SELECT codigoRecurso, descripcionRecurso, cantidadSolicitar, responsable, fecha, hora FROM solicitudes"; // Asegúrate que el nombre de la tabla sea correcto
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            solicitudesTableModel.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            while (resultSet.next()) {
                int codigoRecurso = resultSet.getInt("codigoRecurso");
                String descripcionRecurso = resultSet.getString("descripcionRecurso");
                int cantidadSolicitar = resultSet.getInt("cantidadSolicitar");
                String responsable = resultSet.getString("responsable");
                String fecha = resultSet.getString("fecha");
                String hora = resultSet.getString("hora");
                solicitudesTableModel.addRow(new Object[]{codigoRecurso, descripcionRecurso, cantidadSolicitar, responsable, fecha, hora});
            }

            JOptionPane.showMessageDialog(this, "Solicitudes actualizadas.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar solicitudes: " + ex.getMessage());
        }
    }

    /**
     * Método para solicitar un recurso en la base de datos.
     * @param codigoRecurso
     * @param descripcionRecurso
     * @param cantidadSolicitar
     * @param responsable
     */
    private void solicitarRecurso(int codigoRecurso, String descripcionRecurso, int cantidadSolicitar, String responsable) {
        String query = "UPDATE recurso SET cantidadRecurso = cantidadRecurso - ? WHERE codigoRecurso = ? AND cantidadRecurso >= ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, cantidadSolicitar);
            statement.setInt(2, codigoRecurso);
            statement.setInt(3, cantidadSolicitar);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Guardar la solicitud en otra tabla (suponiendo que tienes una tabla de solicitudes)
                guardarSolicitud(codigoRecurso, descripcionRecurso, cantidadSolicitar, responsable);
                JOptionPane.showMessageDialog(this, "Recurso solicitado exitosamente.");
                consultarSolicitudes(); // Actualizar la tabla de solicitudes
            } else {
                JOptionPane.showMessageDialog(this, "No se posee suficiente cantidad del recurso solicitado.");
            }
            consultarRecursos(); // Actualizar la tabla de recursos después de solicitar
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al solicitar recurso: " + ex.getMessage());
        }
    }

    /**
     * Método para guardar la solicitud en la base de datos.
     * @param codigoRecurso
     * @param descripcionRecurso
     * @param cantidadSolicitar
     * @param responsable
     */
    private void guardarSolicitud(int codigoRecurso, String descripcionRecurso, int cantidadSolicitar, String responsable) {
        String query = "INSERT INTO solicitudes (codigoRecurso, descripcionRecurso, cantidadSolicitar, responsable, fecha, hora) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, codigoRecurso);
            statement.setString(2, descripcionRecurso);
            statement.setInt(3, cantidadSolicitar);
            statement.setString(4, responsable);
            statement.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            statement.setString(6, new SimpleDateFormat("HH:mm:ss").format(new Date()));
            statement.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la solicitud: " + ex.getMessage());
        }
    }

    /**
     * Método principal para ejecutar la aplicación.
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionarRecursos gestionarRecursos = new GestionarRecursos();
            gestionarRecursos.setVisible(true);
        });
    }
    * */
}

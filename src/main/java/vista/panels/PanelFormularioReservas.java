/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.panels;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import paqueteDAO.AulaDAO;
import paqueteDAO.ReservaDAO;
import paqueteDTO.AulaDTO;
import paqueteDTO.ReservaDTO;
import utilidades.ConnectionManager;
import utilidades.Fecha;
import utilidades.Tiempo;

/**
 *
 * @author carlos
 */
public class PanelFormularioReservas extends JPanel {

    private JPanel panelDatos;
    private JPanel panelPeriodo;
    private JRadioButton radioButtonAnual;
    private JRadioButton radioButtonCuatrimestral;
    private JRadioButton radioButtonUnico;
    private ButtonGroup buttonGroupTiempoReserva;
    private JLabel labelPeriodo;
    private JLabel labelDescripcion;
    private JTextField textDescripcion;
    private JLabel labelAula;
    private JComboBox<String> comboBoxAulas;
    private JTextField textResponsable;
    private JLabel labelResponsable;
    private JDateChooser dateChooser;
    private JComboBox<String> comboBoxDiasSemana;
    private JPanel panelHoraInicio;
    private JComboBox<String> comboBoxHoraInicio;
    private JComboBox<String> comboBoxMinutosInicio;
    private JPanel panelHoraFin;
    private JComboBox<String> comboBoxHoraFin;
    private JComboBox<String> comboBoxMinutosFin;

    private JPanel panelBotones;
    private JButton buttonGuardar;
    private JButton buttonConsultarReservas;

    private static final int ROWS_PANEL_DATOS = 8;
    private static final int COLS_PANEL_DATOS = 1;

    public PanelFormularioReservas() {

        setLayout(new BorderLayout());

        inicializarComponentes();

        configurarPanel();

        Controlador controlador = new Controlador();

        controlador.configurarEventos();
    }

    /**
     * Este metodo inicializa los componentes swing.
     */
    private void inicializarComponentes() {

        panelDatos = new JPanel(new GridLayout(ROWS_PANEL_DATOS, COLS_PANEL_DATOS));

        labelPeriodo = new JLabel("Periodo");
        panelPeriodo = new JPanel(new GridLayout(1, 4));

        labelDescripcion = new JLabel("Descripción:");
        textDescripcion = new JTextField();

        labelAula = new JLabel("Aula:");
        comboBoxAulas = new JComboBox<>();

        try {
            inicializarComboBoxAulas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar aulas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(PanelFormularioReservas.class.getName()).log(Level.SEVERE, null, ex);
        }

        buttonGuardar = new JButton("Guardar");
        buttonConsultarReservas = new JButton("Historial de reservas");
        panelBotones = new JPanel(new GridLayout(1, 2));
        panelBotones.add(buttonGuardar);
        panelBotones.add(buttonConsultarReservas);

        radioButtonAnual = new JRadioButton("Anual");
        radioButtonCuatrimestral = new JRadioButton("Cuatrimestral");
        radioButtonUnico = new JRadioButton("Único");
        buttonGroupTiempoReserva = new ButtonGroup();
        buttonGroupTiempoReserva.add(radioButtonAnual);
        buttonGroupTiempoReserva.add(radioButtonCuatrimestral);
        buttonGroupTiempoReserva.add(radioButtonUnico);

        labelResponsable = new JLabel("Responsable");
        textResponsable = new JTextField(20);

        dateChooser = new JDateChooser();

        String[] diasDeLaSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        comboBoxDiasSemana = new JComboBox<>(diasDeLaSemana);

        String[] horas = new String[17]; // Desde 6 hasta 22, total 17 elementos
        for (int i = 0; i < 17; i++) {
            horas[i] = String.format("%02d", i + 6);
        }

        String[] minutos = {"00", "30"};

        // Crear y configurar los ComboBox para hora y minutos de inicio y fin
        comboBoxHoraInicio = new JComboBox<>(horas);
        comboBoxMinutosInicio = new JComboBox<>(minutos);
        comboBoxHoraFin = new JComboBox<>(horas);
        comboBoxMinutosFin = new JComboBox<>(minutos);

    }

    /**
     * Este metodo configura el panel de la vista.
     */
    private void configurarPanel() {

        panelPeriodo = new JPanel(new GridLayout(1, 4));
        panelPeriodo.add(radioButtonAnual);
        panelPeriodo.add(radioButtonCuatrimestral);
        panelPeriodo.add(radioButtonUnico);

        panelDatos.add(labelPeriodo);
        panelDatos.add(panelPeriodo);

        panelDatos.add(labelDescripcion);
        panelDatos.add(textDescripcion);

        panelDatos.add(labelAula);
        panelDatos.add(comboBoxAulas);

        panelDatos.add(labelResponsable);
        panelDatos.add(textResponsable);

        panelDatos.add(new JLabel("Fecha"));
        panelDatos.add(dateChooser);

        panelDatos.add(new JLabel("Día: "));
        panelDatos.add(comboBoxDiasSemana);

        panelDatos.add(new JLabel("Hora inicio:"));
        panelHoraInicio = new JPanel(new GridLayout(1, 2));
        panelHoraInicio.add(comboBoxHoraInicio);
        panelHoraInicio.add(comboBoxMinutosInicio);
        panelDatos.add(panelHoraInicio);

        panelDatos.add(new JLabel("Hora fin:"));
        panelHoraFin = new JPanel(new GridLayout(1, 2));
        panelHoraFin.add(comboBoxHoraFin);
        panelHoraFin.add(comboBoxMinutosFin);
        panelDatos.add(panelHoraFin);
        panelBotones.add(buttonGuardar);
        panelBotones.add(buttonConsultarReservas);

        add(BorderLayout.SOUTH, panelBotones);
        add(BorderLayout.CENTER, panelDatos);
    }

    /**
     * Coloca los numeros de aula en el JComboBox de aulas
     *
     * @return
     * @throws SQLException
     */
    public JComboBox<String> inicializarComboBoxAulas() throws SQLException {

        AulaDAO aulaDAO = new AulaDAO();
        List<AulaDTO> listaAulas = aulaDAO.obtenerAulas();
        for (AulaDTO next : listaAulas) {
            comboBoxAulas.addItem(next.getNroAula() + "");
        }

        return comboBoxAulas;
    }

    /**
     * Clase que gestiona los ActionListener (Eventos) de los botones
     */
    private class Controlador {

        public Controlador() {
            configurarEventos();
        }

        /**
         * Este metodo configura los eventos.
         */
        private void configurarEventos() {

            configurarRadioButtons();
            configurarBotonGuardar();
        }

        private void configurarBotonGuardar() {
            buttonGuardar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection connection = null; // Inicializar la conexión
                    try {
                        // Obtener la conexión a la base de datos
                        ConnectionManager cm = new ConnectionManager();
                        connection = cm.getConnection();
                        ReservaDAO reservaDAO = new ReservaDAO(connection);

                        // Obtener los valores ingresados por el usuario
                        String descripcion = textDescripcion.getText().trim();
                        String aula = (String) comboBoxAulas.getSelectedItem(); // Obtener aula del JComboBox
                        String responsable = textResponsable.getText().trim();
                        // Validar los campos requeridos
                        if (descripcion.isEmpty() || aula == null) {
                            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos requeridos.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Obtener el día de la semana seleccionado
                        String diaSemanaSeleccionado = (String) comboBoxDiasSemana.getSelectedItem();
                        DayOfWeek diaSemana = convertirStringADayOfWeek(diaSemanaSeleccionado);

                        // Obtener las horas de inicio y fin
                        int horasInicio = Integer.parseInt((String) comboBoxHoraInicio.getSelectedItem());
                        int minutosInicio = Integer.parseInt((String) comboBoxMinutosInicio.getSelectedItem());
                        Tiempo horaInicio = new Tiempo(horasInicio, minutosInicio);

                        int horasFin = Integer.parseInt((String) comboBoxHoraFin.getSelectedItem());
                        int minutosFin = Integer.parseInt((String) comboBoxMinutosFin.getSelectedItem());
                        Tiempo horaFin = new Tiempo(horasFin, minutosFin);

                        // Delegar la creación de reservas al DAO según el tipo seleccionado
                        ReservaDTO reserva = new ReservaDTO(1, Integer.parseInt(aula), horaInicio, horaFin, diaSemana, descripcion);
                        java.sql.Date fechaSqlHoy = new java.sql.Date(System.currentTimeMillis()); // Obtener la fecha de hoy
                        reserva.setFechaReserva(Fecha.fromSqlDate(fechaSqlHoy));

                        if (radioButtonUnico.isSelected()) {
                            // Obtener la fecha seleccionada
                            java.util.Date utilDate = dateChooser.getDate();
                            if (utilDate == null) {
                                JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            java.sql.Date fechaSeleccionada = new java.sql.Date(utilDate.getTime());
                            if (reservaDAO.guardarReserva(
                                    aula,
                                    horaInicio,
                                    horaFin,
                                    diaSemanaSeleccionado,
                                    Fecha.fromSqlDate(fechaSeleccionada),
                                    descripcion,
                                    responsable
                            )) {
                                JOptionPane.showMessageDialog(null, "Reserva guardada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Aula ya reservada.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (radioButtonCuatrimestral.isSelected()) {
                            reservaDAO.guardarReservaCuatrimestral(reserva, diaSemana, 16); // Ajusta la cantidad de semanas según el cuatrimestre
                            JOptionPane.showMessageDialog(null, "Reserva cuatrimestral guardada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else if (radioButtonAnual.isSelected()) {
                            reservaDAO.guardarReservaAnual(reserva, diaSemana, 52); // 52 semanas para el año
                            JOptionPane.showMessageDialog(null, "Reserva anual guardada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        }

                        // Limpiar campos después de guardar
                        textDescripcion.setText("");
                        comboBoxAulas.setSelectedIndex(-1); // Limpiar el JComboBox
                        dateChooser.setDate(null);
                        comboBoxHoraInicio.setSelectedIndex(0); // Restablecer el JComboBox a su valor por defecto
                        comboBoxHoraFin.setSelectedIndex(0); // Restablecer el JComboBox a su valor por defecto
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al guardar la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        // Cerrar la conexión si se abrió
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(PanelFormularioReservas.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            });
        }

        /**
         * Este metodo configura los radio buttons.
         */
        private void configurarRadioButtons() {
            radioButtonUnico.setSelected(true);
            radioButtonAnual.addActionListener(new ActionListener() { // Anual
                @Override
                public void actionPerformed(ActionEvent e) {
                    dateChooser.setEnabled(false); // Desactivar selección de fecha
                    comboBoxDiasSemana.setEnabled(true); // Activar selección de día de la semana
                }
            });

            radioButtonCuatrimestral.addActionListener(new ActionListener() { // Cuatrimestral
                @Override
                public void actionPerformed(ActionEvent e) {
                    dateChooser.setEnabled(false); // Desactivar selección de fecha
                    comboBoxDiasSemana.setEnabled(true); // Activar selección de día de la semana
                }
            });

            radioButtonUnico.addActionListener(new ActionListener() { // Único
                @Override
                public void actionPerformed(ActionEvent e) {
                    dateChooser.setEnabled(true); // Activar selección de fecha
                    comboBoxDiasSemana.setEnabled(false); // Desactivar selección de día de la semana
                }
            });

            dateChooser.setEnabled(true);
            comboBoxDiasSemana.setEnabled(false);
        }

        /**
         * Este metodo convierte en String a un dia de la semana establecido.
         *
         * @param dia
         * @return
         */
        public static DayOfWeek convertirStringADayOfWeek(String dia) {
            switch (dia.toLowerCase()) {
                case "lunes":
                    return DayOfWeek.MONDAY;
                case "martes":
                    return DayOfWeek.TUESDAY;
                case "miércoles":
                case "miercoles":
                    return DayOfWeek.WEDNESDAY;
                case "jueves":
                    return DayOfWeek.THURSDAY;
                case "viernes":
                    return DayOfWeek.FRIDAY;
                case "sábado":
                case "sabado":
                    return DayOfWeek.SATURDAY;
                case "domingo":
                    return DayOfWeek.SUNDAY;
                default:
                    throw new IllegalArgumentException("Día no válido: " + dia);
            }
        }

    }

}

package vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;
import paqueteDAO.ReservaDAO;
import paqueteDTO.AulaDTO;
import paqueteDTO.FechaDTO;
import paqueteDTO.ReservaDTO;
import paqueteDTO.TiempoDTO;
import principal.ConnectionManager;

public class GestionarActividades extends JFrame {

    private JLabel labelDescripcion;
    private JTextField textDescripcion;
    private JLabel labelAula;
    private JTextField textAula;
    private JButton buttonGuardar;
    private JButton buttonConsultar;
    private JRadioButton radioButtonAnual;
    private JRadioButton radioButtonCuatrimestral;
    private JRadioButton radioButtonUnico;
    private ButtonGroup group;
    private JDateChooser dateChooser;
    private JComboBox<String> comboBoxDiasSemana;
    private JSpinner spinnerHoraInicio;
    private JSpinner spinnerHoraFin;

    public GestionarActividades() {
        setTitle("Gestionar Actividades Académicas");
        setSize(400, 300);
        setLayout(new GridLayout(8, 1));
        setLocationRelativeTo(null);
        inicializarComponentes();
        configurarPanel();
    }

    private void inicializarComponentes() {
        labelDescripcion = new JLabel("Descripción:");
        textDescripcion = new JTextField();

        labelAula = new JLabel("Aula:");
        textAula = new JTextField();

        buttonGuardar = new JButton("Guardar");
        buttonConsultar = new JButton("Consultar Disponibilidad");

        radioButtonAnual = new JRadioButton("Anual");
        radioButtonCuatrimestral = new JRadioButton("Cuatrimestral");
        radioButtonUnico = new JRadioButton("Único");

        group = new ButtonGroup();
        group.add(radioButtonAnual);
        group.add(radioButtonCuatrimestral);
        group.add(radioButtonUnico);

        dateChooser = new JDateChooser();

        String[] diasDeLaSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        comboBoxDiasSemana = new JComboBox<>(diasDeLaSemana);

        spinnerHoraInicio = new JSpinner(new SpinnerDateModel());
        spinnerHoraInicio.setEditor(new JSpinner.DateEditor(spinnerHoraInicio, "HH:mm"));

        spinnerHoraFin = new JSpinner(new SpinnerDateModel());
        spinnerHoraFin.setEditor(new JSpinner.DateEditor(spinnerHoraFin, "HH:mm"));
    }

    private void configurarPanel() {
        JPanel panelPeriodo = new JPanel(new GridLayout(1, 4));
        panelPeriodo.add(radioButtonAnual);
        panelPeriodo.add(radioButtonCuatrimestral);
        panelPeriodo.add(radioButtonUnico);

        add(new JLabel("Periodo"));
        add(panelPeriodo);
        add(labelDescripcion);
        add(textDescripcion);
        add(labelAula);
        add(textAula);
        add(new JLabel("Fecha"));
        add(dateChooser);
        add(new JLabel("Día: "));
        add(comboBoxDiasSemana);
        add(new JLabel("Hora inicio:"));
        add(spinnerHoraInicio);
        add(new JLabel("Hora fin:"));
        add(spinnerHoraFin);
        add(buttonGuardar);
        add(buttonConsultar);

        configurarEventos();
    }

    private void configurarEventos() {

        configurarRadioButtons();
        configurarBotonGuardar();
        configurarBotonConsultarDisponibilidad();
    }

    private void configurarBotonConsultarDisponibilidad() {
        buttonConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Conexión a la base de datos
                    ConnectionManager cm = new ConnectionManager();
                    Connection connection = cm.getConnection();
                    ReservaDAO reservaDAO = new ReservaDAO(connection);

                    // Obtener fecha y horas seleccionadas
                    java.util.Date utilDate = dateChooser.getDate();
                    java.sql.Date fecha = new java.sql.Date(utilDate.getTime());

                    // Obtiene las horas y minutos del spinner
                    java.util.Date fechaSeleccionada = (java.util.Date) spinnerHoraInicio.getValue();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaSeleccionada);

                    int horasInicio = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutosInicio = calendar.get(Calendar.MINUTE);

                    fechaSeleccionada = (java.util.Date) spinnerHoraFin.getValue();
                    calendar = Calendar.getInstance();
                    calendar.setTime(fechaSeleccionada);

                    int horasFin = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutosFin = calendar.get(Calendar.MINUTE);

                    TiempoDTO horaInicio = new TiempoDTO(horasInicio, minutosInicio);
                    TiempoDTO horaFin = new TiempoDTO(horasFin, minutosFin);

                    reservaDAO.mostrarTablaAulasDisponibles(fecha, horaInicio, horaFin);

                    connection.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al consultar disponibilidad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void configurarBotonGuardar() {
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener la conexión a la base de datos
                    ConnectionManager cm = new ConnectionManager();
                    Connection connection = cm.getConnection();
                    ReservaDAO reservaDAO = new ReservaDAO(connection);

                    // Obtener los valores ingresados por el usuario
                    String descripcion = textDescripcion.getText().trim();
                    String aula = textAula.getText().trim();

                    // Validar los campos requeridos
                    if (descripcion.isEmpty() || aula.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos requeridos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Obtener el día de la semana seleccionado
                    String diaSemanaSeleccionado = (String) comboBoxDiasSemana.getSelectedItem();
                    DayOfWeek diaSemana;
                    diaSemana = DayOfWeek.valueOf(convertirStringADayOfWeek(diaSemanaSeleccionado).toString());

                    // Obtener las horas de inicio y fin
                    java.util.Date horaInicioDate = (java.util.Date) spinnerHoraInicio.getValue();
                    Calendar calendarInicio = Calendar.getInstance();
                    calendarInicio.setTime(horaInicioDate);
                    int horasInicio = calendarInicio.get(Calendar.HOUR_OF_DAY);
                    int minutosInicio = calendarInicio.get(Calendar.MINUTE);
                    TiempoDTO horaInicio = new TiempoDTO(horasInicio, minutosInicio);

                    java.util.Date horaFinDate = (java.util.Date) spinnerHoraFin.getValue();
                    Calendar calendarFin = Calendar.getInstance();
                    calendarFin.setTime(horaFinDate);
                    int horasFin = calendarFin.get(Calendar.HOUR_OF_DAY);
                    int minutosFin = calendarFin.get(Calendar.MINUTE);
                    TiempoDTO horaFin = new TiempoDTO(horasFin, minutosFin);

                    // Delegar la creación de reservas al DAO según el tipo seleccionado
                    ReservaDTO reserva = new ReservaDTO(1, Integer.parseInt(aula), horaInicio, horaFin, diaSemana, descripcion);
                    java.sql.Date fechaSqlHoy = Date.valueOf(LocalDate.now());
                    reserva.setFechaReserva(FechaDTO.fromSqlDate(fechaSqlHoy));

                    if (radioButtonUnico.isSelected()) {

                        // Obtener la fecha seleccionada
                        java.util.Date utilDate = dateChooser.getDate();
                        if (utilDate == null) {
                            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        java.sql.Date fechaSeleccionada = new java.sql.Date(utilDate.getTime());

                        if (reservaDAO.guardarReserva(
                                1,
                                aula,
                                horaInicio,
                                horaFin,
                                diaSemanaSeleccionado,
                                FechaDTO.fromSqlDate(fechaSeleccionada),
                                descripcion
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
                    textAula.setText("");
                    dateChooser.setDate(null);
                    spinnerHoraInicio.setValue(new java.util.Date());
                    spinnerHoraFin.setValue(new java.util.Date());

                    // Cerrar la conexión
                    connection.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al guardar la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void configurarRadioButtons() {
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

        dateChooser.setEnabled(false);
        comboBoxDiasSemana.setEnabled(false);
    }

    public static void main(String[] args) {
        GestionarActividades frame = new GestionarActividades();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

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

package vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.util.Calendar;
import paqueteDAO.ReservaDAO;
import utilidades.ConnectionManager;
import utilidades.Tiempo;

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
    private JButton buttonMostrarReservas;
    private JTextField textResponsable;
    private JLabel labelResponsable;

    /**
     * Este metodo configura la vista de Actividades.
     */
    public GestionarActividades() {
        setTitle("Gestionar Actividades Académicas");
        setSize(600, 300);
        setLayout(new GridLayout(8, 1));
        setLocationRelativeTo(null);
        inicializarComponentes();
        configurarPanel();
    }

    /**
     * Este metodo inicializa los componentes.
     */
    private void inicializarComponentes() {
        labelDescripcion = new JLabel("Descripción:");
        textDescripcion = new JTextField();

        labelAula = new JLabel("Aula:");
        textAula = new JTextField();

        buttonGuardar = new JButton("Guardar");
        buttonConsultar = new JButton("Consultar Disponibilidad");
        buttonMostrarReservas = new JButton("Mostrar reservas");
        
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
        
        textResponsable = new JTextField(20);
        labelResponsable = new JLabel("Resposnable:");
        

    }

    /**
     * Este metodo configura el panel de la vista.
     */
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
        
        JPanel panelDeBotonesInferior = new JPanel();
        panelDeBotonesInferior.setLayout(new GridLayout(1,3));
        panelDeBotonesInferior.add(buttonGuardar);
        panelDeBotonesInferior.add(buttonConsultar);
        add(buttonMostrarReservas);
        
        add(panelDeBotonesInferior);

        configurarEventos();
    }

    /**
     * Este metodo configura los eventos.
     */
    private void configurarEventos() {

        configurarRadioButtons();
        configurarBotonGuardar();
        configurarBotonConsultarDisponibilidad();
        configurarBotonMostrarReservas();
    }

    /**
     * Este metodo configura el boton para consultar disponibilidad.
     */
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

                    Tiempo horaInicio = new Tiempo(horasInicio, minutosInicio);
                    Tiempo horaFin = new Tiempo(horasFin, minutosFin);

                    reservaDAO.mostrarTablaAulasDisponibles(fecha, horaInicio, horaFin);

                    connection.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al consultar disponibilidad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Este metodo ocnfigura el boton para guardar.
     */
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

                // Validar el rango de aulas
                int numeroAula;
                try {
                    numeroAula = Integer.parseInt(aula);
                    if (numeroAula < 20 || numeroAula > 31) {
                        JOptionPane.showMessageDialog(null, "Solo se pueden reservar aulas entre 20 y 31.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El número de aula debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Continuar con el proceso de guardado...
                // (El resto del código que ya tienes sigue aquí)

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
}

    /**
     * Este metodo configura los radio buttons.
     */
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
    
    public void configurarBotonMostrarReservas(){
           buttonMostrarReservas.addActionListener(new ActionListener() {
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

//                    TiempoDTO horaInicio = new TiempoDTO(horasInicio, minutosInicio);
//                    TiempoDTO horaFin = new TiempoDTO(horasFin, minutosFin);

                    reservaDAO.mostrarTablaReservasPorFecha(fecha);

                    connection.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al consultar disponibilidad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    /**
     * Muestra la interfaz por pantalla.
     *
     * @param args
     */
    public static void main(String[] args) {
        GestionarActividades frame = new GestionarActividades();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

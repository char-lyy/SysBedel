package vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class GestionarActividades extends JFrame {

    public GestionarActividades() {
        setTitle("Gestionar Actividades Académicas");
        setSize(400, 300);
        setLayout(new GridLayout(10, 1));
        setLocationRelativeTo(null);

        JLabel labelActividad = new JLabel("Descripción:");
        JTextField textActividad = new JTextField();

        JLabel labelAula = new JLabel("Aula:");
        JTextField textAula = new JTextField();

        JLabel labelHorario = new JLabel("Horario:");
        JTextField textHorario = new JTextField();

        JButton buttonGuardar = new JButton("Guardar");
        JButton buttonConsultar = new JButton("Consultar Disponibilidad");

        JPanel panelPeriodo = new JPanel(new GridLayout(1, 4));

        JRadioButton button1 = new JRadioButton("Anual");
        JRadioButton button2 = new JRadioButton("Cuatrimestral");
        JRadioButton button3 = new JRadioButton("Único");

        ButtonGroup group = new ButtonGroup();
        group.add(button1);
        group.add(button2);
        group.add(button3);

        panelPeriodo.add(button1);
        panelPeriodo.add(button2);
        panelPeriodo.add(button3);

        add(new JLabel("Periodo"));
        add(panelPeriodo);

        add(labelActividad);
        add(textActividad);

        add(labelAula);
        add(textAula);

        add(labelHorario);
        add(textHorario);

        add(new JLabel("Fecha"));
        JDateChooser dateChooser = new JDateChooser();
        add(dateChooser);

        add(new JLabel("Día: "));
        // Array con los días de la semana
        String[] diasDeLaSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        JComboBox<String> comboBoxDiasSemana = new JComboBox<>(diasDeLaSemana);
        add(comboBoxDiasSemana);

        SpinnerDateModel modelHoraInicio = new SpinnerDateModel();
        JSpinner spinnerHoraInicio = new JSpinner(modelHoraInicio);
        JSpinner.DateEditor editorHoraInicio = new JSpinner.DateEditor(spinnerHoraInicio, "HH:mm");
        spinnerHoraInicio.setEditor(editorHoraInicio);
        add(new JLabel("Hora inicio:"));
        add(spinnerHoraInicio);

        SpinnerDateModel modelHoraFin = new SpinnerDateModel();
        JSpinner spinnerHoraFin = new JSpinner(modelHoraFin);
        JSpinner.DateEditor editorHoraFin = new JSpinner.DateEditor(spinnerHoraFin, "HH:mm");
        spinnerHoraFin.setEditor(editorHoraFin);
        add(new JLabel("Hora fin:"));
        add(spinnerHoraFin);

        // Botón para mostrar la hora seleccionada
//        JButton buttonMostrarHora = new JButton("Mostrar Hora Seleccionada");
//        buttonMostrarHora.addActionListener(e -> {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime((java.util.Date) spinnerHora.getValue());
//            int hora = cal.get(Calendar.HOUR_OF_DAY);
//            int minutos = cal.get(Calendar.MINUTE);
//            JOptionPane.showMessageDialog(this, "Hora seleccionada: " + hora + ":" + minutos);
//        });
//        add(buttonMostrarHora);
        add(buttonGuardar);
        add(buttonConsultar);

        // Lógica para habilitar/deshabilitar controles según la selección
        button1.addActionListener(new ActionListener() { // Anual
            @Override
            public void actionPerformed(ActionEvent e) {
                dateChooser.setEnabled(false); // Desactivar selección de fecha
                comboBoxDiasSemana.setEnabled(true); // Activar selección de día de la semana
            }
        });

        button2.addActionListener(new ActionListener() { // Cuatrimestral
            @Override
            public void actionPerformed(ActionEvent e) {
                dateChooser.setEnabled(false); // Desactivar selección de fecha
                comboBoxDiasSemana.setEnabled(true); // Activar selección de día de la semana
            }
        });

        button3.addActionListener(new ActionListener() { // Único
            @Override
            public void actionPerformed(ActionEvent e) {
                dateChooser.setEnabled(true); // Activar selección de fecha
                comboBoxDiasSemana.setEnabled(false); // Desactivar selección de día de la semana
            }
        });

        // Inicialmente, ambos controles están desactivados hasta que se elija un periodo
        dateChooser.setEnabled(false);
        comboBoxDiasSemana.setEnabled(false);
    }

    public static void main(String[] args) {
        // Crear y mostrar la ventana
        GestionarActividades frame = new GestionarActividades();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

package vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class GestionarActividades extends JFrame {

    public GestionarActividades() {
        setTitle("Gestionar Actividades Académicas");
        setSize(400, 300);
        setLayout(new GridLayout(10, 1));
        setLocationRelativeTo(null);

        JLabel labelActividad = new JLabel("Descripcion:");
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
        JRadioButton button3 = new JRadioButton("Unico");

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

        add(new JLabel("Dia: "));
//        
        // Array con los días de la semana
        String[] diasDeLaSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        JComboBox<String> comboBoxDiasSemana = new JComboBox<>(diasDeLaSemana);
        
        add(comboBoxDiasSemana);
        add(buttonGuardar);
        add(buttonConsultar);

    }
}

package vista.panels;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import services.ServicioReserva;
import utilidades.Fecha;
import utilidades.FechaTiempo;

public class PanelTablaReservas extends JPanel {

    private PanelNorte panelNorte;
    private PanelCentral panelCentral;

    public PanelTablaReservas() throws SQLException {
        this.panelNorte = new PanelNorte();
        this.panelCentral = new PanelCentral();

        setLayout(new BorderLayout());
        add(panelNorte, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    class PanelNorte extends JPanel {

        private JComboBox<String> comboBoxAulas;
        private JComboBox<String> selectorConsulta;
        private JDateChooser dateChooserConsulta;

        public PanelNorte() throws SQLException {
            setLayout(new GridLayout(1, 4, 5, 5));
            inicializarComponentes();
            configurarPanel();
            ControladorPanelNorte controller = new ControladorPanelNorte();
            controller.configurarEventos();
        }

        private void inicializarComponentes() throws SQLException {
            String[] opciones = {"Por aula", "Por día"};
            selectorConsulta = new JComboBox<>(opciones);

            PanelFormularioReservas panelFormularioReservas = new PanelFormularioReservas();
            comboBoxAulas = panelFormularioReservas.inicializarComboBoxAulas();

            dateChooserConsulta = new JDateChooser(FechaTiempo.ahora().toSqlDateTime());
        }

        private void configurarPanel() {
            add(selectorConsulta);
            add(comboBoxAulas);
            add(dateChooserConsulta);
        }

        public JComboBox<String> getSelectorConsulta() {
            return selectorConsulta;
        }

        public JComboBox<String> getComboBoxAulas() {
            return comboBoxAulas;
        }

        public JDateChooser getDateChooserConsulta() {
            return dateChooserConsulta;
        }

        private class ControladorPanelNorte {

            public ControladorPanelNorte() {
            }

            private void configurarEventos() {
                configurarSelector();
                configurarComboBoxAulas(); // Añadir ActionListener al comboBox
                configurarDateChooser(); // Añadir ActionListener al dateChooser
            }

            private void configurarSelector() {
                selectorConsulta.setSelectedIndex(1);
                comboBoxAulas.setEnabled(false);
                selectorConsulta.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (selectorConsulta.getSelectedIndex() == 0) {
                            dateChooserConsulta.setEnabled(false);
                            comboBoxAulas.setEnabled(true);
                        } else {
                            dateChooserConsulta.setEnabled(true);
                            comboBoxAulas.setEnabled(false);
                        }
                        panelCentral.configurarTablaSeleccionada(); // Actualizar tabla al cambiar el selector
                    }
                });
            }

            private void configurarComboBoxAulas() {
                comboBoxAulas.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panelCentral.configurarTablaSeleccionada(); // Actualizar tabla al seleccionar un aula
                    }
                });
            }

            private void configurarDateChooser() {
                dateChooserConsulta.addPropertyChangeListener("date", evt -> {
                    panelCentral.configurarTablaSeleccionada(); // Actualizar tabla al cambiar la fecha
                });
            }
        }
    }

    class PanelCentral extends JPanel {

        private PanelTablaHorariosPorAula panelTablaHorariosAula;
        private PanelTablaHorariosPorDia panelTablaHorariosDia;

        public PanelCentral() {
            this.panelTablaHorariosAula = new PanelTablaHorariosPorAula();
            this.panelTablaHorariosDia = new PanelTablaHorariosPorDia();

            setLayout(new BorderLayout());
            configurarTablaSeleccionada();
        }

        private void configurarTablaSeleccionada() {
            removeAll(); // Limpiar el panel central

            JTable nuevaTabla;
            if (panelNorte.getSelectorConsulta().getSelectedIndex() == 1) {
                nuevaTabla = panelTablaHorariosDia.getTable();
            } else {
                nuevaTabla = panelTablaHorariosAula.getTable();
            }

            String aulaSeleccionada = (String) panelNorte.getComboBoxAulas().getSelectedItem();
            Fecha fechaSeleccionada = Fecha.fromUtilDate(panelNorte.getDateChooserConsulta().getDate());

            cargarCeldas(nuevaTabla, aulaSeleccionada, fechaSeleccionada);

            JScrollPane scrollPane = new JScrollPane(nuevaTabla);
            add(scrollPane, BorderLayout.CENTER);

            revalidate();
            repaint();
        }

        private void cargarCeldas(JTable nuevaTabla, String aula, Fecha fecha) {

            if (fecha == null) {
                return;
            }

            if (panelNorte.getSelectorConsulta().getSelectedIndex() == 1) {
                cargarCeldasPorDia(nuevaTabla, fecha);
            } else {
                cargarCeldasPorAula(nuevaTabla, aula, fecha);
            }
        }

        private void cargarCeldasPorDia(JTable nuevaTabla, Fecha fecha) {
            // Obtener el modelo de la tabla
            TableModel tableModel = nuevaTabla.getModel();

            // Crear instancia de ServicioReserva y obtener la matriz de reservas
            ServicioReserva servicioReserva = new ServicioReserva();
            String[][] matrizReservas = servicioReserva.obtenerReservasPorDia(fecha);

            // Validar que la matriz no sea nula antes de intentar cargarla en la tabla
            if (matrizReservas == null) {
                System.out.println("Error al obtener las reservas para la fecha proporcionada.");
                return;
            }

            // Llenar cada celda de la tabla, a partir de la segunda columna (columna de índice 1)
            for (int fila = 0; fila < matrizReservas.length; fila++) {
                for (int col = 0; col < matrizReservas[fila].length; col++) {
                    // Usar setValueAt para llenar la celda correspondiente en la tabla
                    tableModel.setValueAt(matrizReservas[fila][col], fila, col + 1); // Desplazamiento a partir de la columna 1
                }
            }
        }

        private void cargarCeldasPorAula(JTable nuevaTabla, String aula, Fecha fecha) {

        }
    }
}

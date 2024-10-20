package vista.frames;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.SQLException;
import javax.swing.JFrame;
import vista.panels.PanelFormularioReservas;
import vista.panels.PanelTablaReservas;

/**
 *
 * @author carlos
 */
public class FrameReservas extends JFrame {

    private final PanelFormularioReservas panelFormularioReservas;
    private final PanelTablaReservas panelReservas;

    public FrameReservas() throws SQLException {
        
        panelReservas = new PanelTablaReservas();
        
        panelFormularioReservas = new PanelFormularioReservas();
        
        setTitle("Reservas");
        
        Toolkit display001 = Toolkit.getDefaultToolkit();

        Dimension displaySize = display001.getScreenSize();

        setBounds(displaySize.width / 5,
                displaySize.height / 5,
                (int) (displaySize.width / 1.5),
                (int) (displaySize.height / 1.5));

        setLayout(new GridLayout(1, 2));

        setVisible(true);
        
        add(panelFormularioReservas);
        
        add(panelReservas);
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

//    public static void main(String[] args) throws SQLException {
//        FrameReservas frame = new FrameReservas();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
}

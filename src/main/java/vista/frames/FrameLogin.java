package vista.frames;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import vista.panels.PanelLogin;


/**
 *
 * @author carlos
 */
public class FrameLogin extends JFrame {

    public FrameLogin() {

        PanelLogin panelLogin = new PanelLogin();

        setTitle("Login Sysbedel");

        Toolkit display001 = Toolkit.getDefaultToolkit();

        Dimension displaySize = display001.getScreenSize();

        setBounds(displaySize.width / 3,
                displaySize.height / 3,
                (int) (displaySize.width / 4),
                (int) (displaySize.height / 4));

        setLayout(new GridLayout(1, 2));

        setVisible(true);

        add(panelLogin);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

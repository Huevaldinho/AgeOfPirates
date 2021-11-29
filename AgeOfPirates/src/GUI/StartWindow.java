package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame{
    private JLabel lbl_AgeOfPirates;
    private JButton btn_Iniciar;
    private JButton salirButton;
    private JPanel mainPanel;
    private static JFrame interfacePrincipal;

    public StartWindow(String nombre) {
        super("Age Of Pirates");
        setContentPane(mainPanel);
        setResizable(true);
        this.setTitle(nombre);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        btn_Iniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainWindow();
                interfacePrincipal.setVisible(false);
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        // create a window frame and set the title in the toolbar
        JFrame interfaz = new StartWindow("Age of Pirates");
        interfaz.setSize(700,575);
        interfaz.setVisible(true);
        interfacePrincipal=interfaz;

    }
}



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainWindow extends JFrame {
    JFrame frame;
    JPanel mapaJugador;
    JPanel mensajes;
    JPanel mapaRival;


    MainWindow(){
        createUIComponents();
    }

    /**
     * Se crean los widgets aquí debajo
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        frame = new JFrame("Age of Pirates");
        JTabbedPane tabbedPane = new JTabbedPane();

        Dimension dimension = new Dimension(800,800);
        mapaJugador = new Grid();
        mapaJugador.setBackground(Color.CYAN);
        mapaJugador.setEnabled(true);
        mapaJugador.setPreferredSize(dimension);
        mapaJugador.setMinimumSize(dimension);
        mapaJugador.setMaximumSize(dimension);

        mensajes = new JPanel();
        mensajes.setBackground(Color.gray);
        mensajes.setEnabled(true);
        mensajes.setPreferredSize(dimension);
        mensajes.setMinimumSize(dimension);
        mensajes.setMaximumSize(dimension);

        mapaRival = new JPanel();
        mapaRival.setBackground(Color.RED);
        mapaRival.setEnabled(true);
        mapaRival.setPreferredSize(dimension);
        mapaRival.setMinimumSize(dimension);
        mapaRival.setMaximumSize(dimension);

        tabbedPane.setBounds(0,0,800,800);
        tabbedPane.add("Mapa",mapaJugador);
        tabbedPane.add("Mensajes",mensajes);
        tabbedPane.add("Mapa Rival",mapaRival);

        frame.add(tabbedPane);
        frame.setSize(new Dimension(800,850));
        frame.setBackground(Color.BLUE);
        frame.setLayout(null);
        frame.setVisible(true);
    }


}
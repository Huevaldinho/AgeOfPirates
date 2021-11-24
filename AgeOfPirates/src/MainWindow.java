import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainWindow extends JFrame implements ActionListener {
    private final int DELAY = 25;
    Timer timer;
    JFrame frame;
    JPanel mapaJugador;
    JPanel mensajes;
    JPanel mapaRival;

    /**
    * Se crean los componentes en las primeras lineas, desde que se declara el timer,
    * se inicia el hilo del juego, que se hace con el ActionListener, que a su vez entonces la acción realizada
    * es la que está en la función "actionPerformed", para correr la actionPerformed, se necesita un Timer, que
    * genera un delay de n segundos para ir corriendo (esto supongo que se hace para no sobrecargar al cpu (nuestro
    * delay es de 25 ms, se puede cambiar)), que se guarda en la variable DELAY. creado el timer, se hace
    * timer.start() para dar inicio al hilo, lo que esté en el actionPerformed se mantiene durante toda la partida,
    * así que básicamente es la función más importante.
    */

    MainWindow(){
        createUIComponents();

        timer = new Timer (DELAY,this);
        timer.start();
    }

    /**
     * Se crean los widgets aquí debajo
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        frame = new JFrame("Age of Pirates");
        JTabbedPane tabbedPane = new JTabbedPane();

        Dimension dimension = new Dimension(700,700);
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

        tabbedPane.setBounds(0,0,700,700);
        tabbedPane.add("Mapa",mapaJugador);
        tabbedPane.add("Mensajes",mensajes);
        tabbedPane.add("Mapa Rival",mapaRival);

        frame.add(tabbedPane);
        frame.setSize(new Dimension(700,750));
        frame.setBackground(Color.BLUE);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
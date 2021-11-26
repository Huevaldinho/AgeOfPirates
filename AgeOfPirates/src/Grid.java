import Cliente.Client;
import General.Peticion;
import General.TipoAccion;
import ObjetosJuego.Item;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;


public class Grid extends JPanel implements ActionListener {
    public static final int ROWS = 20;
    public static final int COLUMNS = 20;
    private ArrayList<CellPane> celdasOcupadas;
    private final int DELAY = 25;
    private Timer timer;
    private Player jugador;

    /**
     * Se crean los componentes en las primeras lineas, desde que se declara el timer,
     * se inicia el hilo del juego, que se hace con el ActionListener, que a su vez entonces la acción realizada
     * es la que está en la función "actionPerformed", para correr la actionPerformed, se necesita un Timer, que
     * genera un delay de n segundos para ir corriendo (esto supongo que se hace para no sobrecargar al cpu (nuestro
     * delay es de 25 ms, se puede cambiar)), que se guarda en la variable DELAY. creado el timer, se hace
     * timer.start() para dar inicio al hilo, lo que esté en el actionPerformed se mantiene durante toda la partida,
     * así que básicamente es la función más importante.
     */
    public Grid() {

        crearTablero();
        //pintarItems();

        timer = new Timer(DELAY,this);
        timer.start();

        //REGISTRA EL JUGADOR 1
        Peticion peticionRegistrarJugador = new Peticion(TipoAccion.REGISTRAR_PLAYER,null);
        Client conexionRegistrarJugador = new Client(peticionRegistrarJugador);
        Object respuestaRegistrarJugador = conexionRegistrarJugador.getRespuestaServer();
        //ESTE ES EL ID QUE SE LE VA A ASIGNAR AL PLAYER
        jugador = new Player();
        jugador.setID((int)respuestaRegistrarJugador);//Se le pasa el ID al player
        System.out.println("Jugador tiene ID:"+jugador.getID());

        jugador.setGrid(this);
        System.out.println(jugador.toString());


    }
    public int getPlayerID(){
        return jugador.getID();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver
        // because Component implements the ImageObserver interface, and JPanel
        // extends from Component. So "this" Board instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }


    private void crearTablero(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                CellPane cellPane = new CellPane(new Point(col, row));
//                try {
//                    BufferedImage myPicture = ImageIO.read(new File("images/coin.png"));
//                    JLabel w = new JLabel(new ImageIcon(myPicture));
//                    cellPane.add(w);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                Border border = null;
                if (row < ROWS-1) {
                    if (col < ROWS-1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < COLUMNS-1) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                cellPane.setBorder(border);
                add(cellPane, gbc);
            }
        }
//        String listaItems [] = {"Fuente de Energía ($12000)",
//                "Mercado ($2000)",
//                "Mina ($1000)",
//                "Templo de la Bruja ($2500)",
//                "Armería ($1500)"};
//        JComboBox cmbBoxItems = new JComboBox(listaItems);
//        gbc.gridx=COLUMNS/2;
//        gbc.gridy=ROWS;
//        add(cmbBoxItems,gbc);
//
//        JButton btnListo = new JButton("Listo");
//        btnListo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
    }
}



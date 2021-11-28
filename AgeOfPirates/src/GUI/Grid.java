package GUI;

import Cliente.Client;
import GUI.CellPane;
import General.Peticion;
import General.TipoAccion;
import ObjetosJuego.Item;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;


public class Grid extends JPanel implements ActionListener, Serializable {
    public static final int ROWS = 20;
    public static final int COLUMNS = 20;
    private ArrayList<CellPane> totalCeldas;
    private AjustesJuego ajustesJuego;
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
    public Grid(){
        //REGISTRA EL JUGADOR 1
        Peticion peticionRegistrarJugador = new Peticion(TipoAccion.REGISTRAR_PLAYER,null);
        Client conexionRegistrarJugador = new Client(peticionRegistrarJugador);
        Object respuestaRegistrarJugador = conexionRegistrarJugador.getRespuestaServer();

        //ESTE ES EL ID QUE SE LE VA A ASIGNAR AL PLAYER
        jugador = new Player();
        jugador.setID((int)respuestaRegistrarJugador);//Se le pasa el ID al player
        //Jugador ahora tiene grid
        //jugador.setGrid(this);

        totalCeldas=new ArrayList<>();

        crearTablero();
        //pintarItems();

        timer = new Timer(DELAY,this);
        timer.start();



        Peticion petiAgregarJugador = new Peticion(TipoAccion.AGREGAR_JUGADOR,jugador);
        Client conexion = new Client(petiAgregarJugador);
        Object respuesta = conexion.getRespuestaServer();

        ajustesJuego = new AjustesJuego();

        ajustesJuego.id=jugador.getID();
        ajustesJuego.setVisible(true);
        ajustesJuego.lblAjustesJugador.setText("Ajustes jugador: "+jugador.getID());

    }
//    public int getPlayerID(){
//        return jugador.getID();
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
/*
       Primer click del tablero es para colocar la fuente de poder
       Segundo para el mercado.
       Seleccionar celda se autocompleta, para esto tiene que revisar
 */
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
                cellPane.IDJugador=jugador.getID();
                totalCeldas.add(cellPane);
            }
        }
//        JOptionPane.showMessageDialog(null,"El primer Click en el tablero es para colocar la Fuente de Energia" +
//                "\nEl segundo Click es para colocar el Mercado");
    }
    public boolean dibujarCelda (Point punto , Item item){ //dibuja la imagen en la celda, con el punto y el item
        for (CellPane celda : totalCeldas)
            if (celda.getCellCoordinate().x == punto.x && celda.getCellCoordinate().y == punto.y) {
                if (!celda.usada){
                    celda.draw(item.imagen);
                    celda.usada = true;
                    return true;
                }
            }
        return false;
    }
}



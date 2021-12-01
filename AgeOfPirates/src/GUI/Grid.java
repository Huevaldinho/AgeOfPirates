package GUI;

import Cliente.Client;
import GUI.CellPane;
import General.Intercambio;
import General.Peticion;
import General.TipoAccion;
import ObjetosJuego.Item;
import ObjetosJuego.Remolino;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
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
    private ArrayList<Color> coloresCeldas;
    private int contadorColores;

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
        contadorColores=0;
        coloresCeldas = new ArrayList<>();
        coloresCeldas.add(Color.BLUE);
        coloresCeldas.add(Color.GREEN);
        coloresCeldas.add(Color.YELLOW);
        coloresCeldas.add(Color.CYAN);
        coloresCeldas.add(Color.ORANGE);
        coloresCeldas.add(Color.MAGENTA);
        coloresCeldas.add(Color.PINK);
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

        timer = new Timer(DELAY,this);
        timer.start();



        Peticion petiAgregarJugador = new Peticion(TipoAccion.AGREGAR_JUGADOR,jugador);
        Client conexion = new Client(petiAgregarJugador);
        Object respuesta = conexion.getRespuestaServer();

        ajustesJuego = new AjustesJuego();

        ajustesJuego.id=jugador.getID();
        ajustesJuego.setVisible(true);
        ajustesJuego.lblAjustesJugador.setText("Ajustes del jugador "+jugador.getID());

        agregarRemolinos();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        Peticion peti = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,jugador.getID());
        Client conexion = new Client(peti);
        jugador = (Player) conexion.getRespuestaServer();

        dibujarCelda();
        PintarCeldaConectores();
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

    }
    public void agregarRemolinos(){
        Random xRandom = new Random();
        Random yRandom = new Random();
        Point punto1 = new Point(xRandom.nextInt(20),yRandom.nextInt(20));
        Point punto2 = new Point(xRandom.nextInt(20),yRandom.nextInt(20));

        Item remo1 = new Remolino();
        remo1.setAgregadoAlGrid(true);
        ArrayList<Point> puntos = remo1.getPuntosUbicacion();
        puntos.add(punto1);


        Item remo2 = new Remolino();
        remo2.setAgregadoAlGrid(true);
        ArrayList<Point> puntos2 = remo2.getPuntosUbicacion();
        puntos2.add(punto2);

        Peticion peticionRemo1 = new Peticion(TipoAccion.REALIZAR_COMPRA_ITEM,remo1);
        peticionRemo1.setDatosSalida(jugador.getID());
        Client conexion1 = new Client(peticionRemo1);
        Object resp1= conexion1.getRespuestaServer();

        Peticion peticionRemo2 = new Peticion(TipoAccion.REALIZAR_COMPRA_ITEM,remo2);
        peticionRemo2.setDatosSalida(jugador.getID());
        Client conexion2 = new Client(peticionRemo2);
        Object resp2= conexion2.getRespuestaServer();



        CellPane celdaremolino1 = obtenerCelda(punto1);
        celdaremolino1.draw(remo1.loadImage());
        CellPane celdaremolino2 = obtenerCelda(punto2);
        celdaremolino2.draw(remo2.loadImage());
    }
    public void dibujarCelda(){
        if (jugador.isCambiosEnInventario()){
            System.out.println("\nDIBUJAR CELDA");
            for (Item itemActual: jugador.getItems()){
                if (itemActual.getAgregadoAlGrid()){
                    for (Point puntoActual:itemActual.getPuntosUbicacion()){
                        CellPane celdaItem = obtenerCelda(puntoActual);
                        celdaItem.draw(itemActual.loadImage());

                    }
                }
            }
            jugador.setCambiosEnInventario(false);
            Peticion cambiarEstadoJugador = new Peticion(TipoAccion.SET_CAMBIOS_JUGADOR_ITEMS,jugador.getID());
            Client conexion = new Client(cambiarEstadoJugador);
        }
    }
    public CellPane obtenerCelda(Point punto){
        for (CellPane celda : totalCeldas){
            if (celda.getCellCoordinate().equals(punto))
                return celda;
        }
        return null;
    }
    public void PintarCeldaConectores(){//SOLO PINTA LAS CELDAS DE LOS CONECTORES
        Peticion peticionConectores = new Peticion(TipoAccion.GET_CONECTORES,jugador.getID());
        Client cliente = new Client(peticionConectores);
        if (cliente.getRespuestaServer()!=null){
            //Lista de conectores
            ArrayList<Item> conectores = (ArrayList<Item>) cliente.getRespuestaServer();
            for (Item actualConector:conectores) {
                //Si tiene ubicacion (ya esta en el grid)
                if (actualConector.getPuntosUbicacion().size() != 0) {

                    CellPane celda = obtenerCelda(actualConector.getPuntosUbicacion().get(0));
                    //Reinicia color en caso de ser necesario
                    if (contadorColores >= coloresCeldas.size())
                        contadorColores = 0;
                    //Si ya esta pintada, ponga el mismo color
                    if (celda.pintado) {//coloresCeldas.get(contadorColores)
                        celda.pintarCelda(celda.getColor());
                    } else {//Si no tiene que poner uno diferente
                        celda.pintarCelda(coloresCeldas.get(contadorColores));
                        contadorColores++;
                    }
                    pintarSoloConector(actualConector.getItemsConectados(),celda.getColor());
                }
            }
        }
    }
    public void pintarSoloConector(ArrayList<Item> itemsConectados,Color colorConector){
            //For para todos los items
            //For para todos los puntos
            if (itemsConectados.size()!=0){
                for (Item actual: itemsConectados){
                    ArrayList<Point> puntosItem = actual.getPuntosUbicacion();
                    if (puntosItem.size()!=0){
                        for (Point actualPunto:puntosItem){
                            CellPane celda = obtenerCelda(actualPunto);
                            celda.pintado=true;
                            celda.pintarCelda(colorConector);
                        }
                    }
                }
            }
    }
}



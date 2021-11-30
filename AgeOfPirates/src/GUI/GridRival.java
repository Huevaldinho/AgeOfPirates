package GUI;

import Cliente.Client;
import General.Peticion;
import General.TipoAccion;
import ObjetosJuego.Item;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class GridRival extends JPanel implements ActionListener, Serializable {
    public static final int ROWS = 20;
    public static final int COLUMNS = 20;
    private ArrayList<CellPane> totalCeldas;
    private Player jugador;
    private final int DELAY = 25;
    private Timer timer;

    GridRival(int numJugador){
        Peticion peti = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,numJugador);
        Client conexion = new Client(peti);
        jugador = (Player) conexion.getRespuestaServer();

        totalCeldas=new ArrayList<>();

        crearTablero();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Peticion peti = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,jugador.getID());
        Client conexion = new Client(peti);
        jugador = (Player) conexion.getRespuestaServer();

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
    public void dibujarCelda(){
        if (jugador.isCambiosEnInventario()){
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

}

package GUI;

import Cliente.Client;
import General.Peticion;
import General.TipoAccion;
import ObjetosJuego.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CellPane extends JPanel {

    private Color defaultBackground;
    private final Point cellCoordinate;
    public boolean usada;
    public int IDJugador;
    public boolean pintado=false;

    public CellPane(Point cellCoordinate) {
        usada = false;
        this.cellCoordinate = cellCoordinate;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                defaultBackground = getBackground();
                setBackground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBackground);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                //Here is where it is supposed to be
                System.out.println("Did click cell @ " + getCellCoordinate().x + "x" + getCellCoordinate().y);

                Peticion peti = new Peticion(TipoAccion.AGREGAR_ULTIMO_PUNTO,cellCoordinate);
                peti.setDatosSalida(IDJugador);
                Client cliente = new Client(peti);


            }
        });
    }
    public Point getCellCoordinate() {
        return cellCoordinate;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(32, 32);
    }

    public void draw(BufferedImage img) {
        JLabel l = new JLabel(new ImageIcon(img));
        this.add(l);
    }
    public void pintarCelda(){
        setBackground(Color.BLUE);
    }
    public void pintarCelda(Color color){
        setBackground(color);
        pintado=true;
    }
    public Color getColor(){
        return getBackground();
    }
}
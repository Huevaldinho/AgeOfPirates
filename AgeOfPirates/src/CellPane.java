import ObjetosJuego.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellPane extends JPanel {

    private Color defaultBackground;
    private final Point cellCoordinate;
    private Point  ultimoClick;
    private Item item;//Item que le mete desde la GUI

    public CellPane(Point cellCoordinate) {
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
                ultimoClick=new Point(getCellCoordinate().x,getCellCoordinate().y);
            }
        });
    }
    public Point getCellCoordinate() {
        return cellCoordinate;
    }
    public Point getUltimaCeldaClickeada(){
        return ultimoClick;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(32, 32);
    }

    public void draw(ImageIcon image) {

    }
    public Item getItemEnCelda(){
        if (item!=null)
            return item;
        return null;
    }


}
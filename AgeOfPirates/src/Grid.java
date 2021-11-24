import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;


public class Grid extends JPanel {
    private static final int ROWS = 20;
    private static final int COLUMNS = 20;
    private ArrayList<Item> items;

    public Grid() {
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
            }
        }
    }
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        // when calling g.drawImage() we can use "this" for the ImageObserver
//        // because Component implements the ImageObserver interface, and JPanel
//        // extends from Component. So "this" Board instance, as a Component, can
//        // react to imageUpdate() events triggered by g.drawImage()
//
//        // draw our graphics.
//        drawBackground(g);
//        drawScore(g);
//        for (Coin coin : coins) {
//            coin.draw(g, this);
//        }
//        player.draw(g, this);
//
//        // this smooths out animations on some systems
//        Toolkit.getDefaultToolkit().sync();
//    }

    public void crearItems(){

    }
}



package ObjetosJuego;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    String nombre;
    int numero;
    ArrayList<Point> puntosUbicacion = new ArrayList<>();

    public Item(){

    }
}

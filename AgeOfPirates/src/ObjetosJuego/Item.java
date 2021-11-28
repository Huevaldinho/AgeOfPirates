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
    public String nombre;
    public int numero=0;
    public int precio;
    public BufferedImage imagen;
    ArrayList<Point> puntosUbicacion = new ArrayList<>();

    public Item(){

    }
    public int setIDItem(){
        numero++;//como esta en 0, retorna 1, cuando vuelva va a estar en 1, retorna 2...
        return numero;
    }
}

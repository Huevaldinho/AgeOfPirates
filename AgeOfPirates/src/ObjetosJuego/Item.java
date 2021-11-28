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
    public boolean agregadoAlGrid;
    public int numero=0;
    public int precio;
    public int cantidadDeEspacios;
    public BufferedImage imagen;
    ArrayList<Point> puntosUbicacion;

    public Item(){

    }
    public int setIDItem(){
        numero++;//como esta en 0, retorna 1, cuando vuelva va a estar en 1, retorna 2...
        return numero;
    }
    public ArrayList<Point> getPuntosUbicacion() {
        return puntosUbicacion;
    }
    public void setPuntosUbicacion(ArrayList<Point> puntos){
        this.puntosUbicacion=puntos;
    }
    public void setAgregadoAlGrid(boolean estado){
        agregadoAlGrid=estado;
    }
    public boolean getAgregadoAlGrid(){
        return agregadoAlGrid;
    }
    public String getNombre(){
        return nombre;
    }
}


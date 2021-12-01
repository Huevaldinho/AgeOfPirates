package Arma;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Arma implements Serializable {
    ArrayList<Point> puntosUbicacion = new ArrayList<>();
    public String nombre;
    public int costo;
    public int cantidadDisparos;
    public Arma(){
        nombre="Arma";
    }
    public String getnombre(){
        return nombre;
    }
}

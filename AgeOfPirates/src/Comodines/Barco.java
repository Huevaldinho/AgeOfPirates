package Comodines;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Barco implements Serializable {
    ArrayList<Point> puntosUbicacion = new ArrayList<>();
    String nombre;
    public Barco(){
        nombre="Barco Fantasma";
    }
}

package Arma;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Arma implements Serializable {
    ArrayList<Point> puntosUbicacion = new ArrayList<>();
    String nombre;
    public Arma(){
        nombre="Arma";
    }
}

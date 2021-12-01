package Comodines;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class Kraken implements Serializable {
    ArrayList<Point> puntosUbicacion = new ArrayList<>();
    String nombre;
    public Kraken(){
        nombre="Kraken";
    }
}

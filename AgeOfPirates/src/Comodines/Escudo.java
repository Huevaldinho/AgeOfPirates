package Comodines;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Escudo implements Serializable {
    ArrayList<Point> puntosUbicacion = new ArrayList<>();
    String nombre;
    private int protegidoPorXDisparos;
    public Escudo(){
        nombre="Escudo";
        Random random = new Random();
        protegidoPorXDisparos = random.nextInt(5 - 2) +2;
    }

    public ArrayList<Point> getPuntosUbicacion() {
        return puntosUbicacion;
    }

    public void setPuntosUbicacion(ArrayList<Point> puntosUbicacion) {
        this.puntosUbicacion = puntosUbicacion;
    }

    public int getProtegidoPorXDisparos() {
        return protegidoPorXDisparos;
    }

    public void setProtegidoPorXDisparos(int protegidoPorXDisparos) {
        this.protegidoPorXDisparos = protegidoPorXDisparos;
    }
}

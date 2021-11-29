package ObjetosJuego;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Barco extends Item{
    String nombre;
    public Barco(){
        super.vivo=true;
        this.nombre = "Barco Fantasma";
        this.precio = 2500;
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.yaTieneConector=false;
        //No tiene imagen aun
    }
}

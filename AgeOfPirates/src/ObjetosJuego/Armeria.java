package ObjetosJuego;

import Arma.Arma;

import java.util.ArrayList;

public class Armeria extends Item{
    public static Arma tipoArma;
    public Armeria(){
        super.vivo=true;
        super.precio = 1500;
        super.nombre = "Armería";
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        //super.cantidadDeEspacios=1;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/armeria.png";
    }

    public Arma getTipoArma() {
        return tipoArma;
    }

    public void setTipoArma(Arma tipoArma) {
        Armeria.tipoArma = tipoArma;
    }
}

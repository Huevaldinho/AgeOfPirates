package ObjetosJuego;

import Arma.CannonBarbaRoja;

import java.util.ArrayList;

public class ArmeriaCannonBarbaRoja extends Armeria{
    public ArmeriaCannonBarbaRoja(){
        super.setTipoArma(new CannonBarbaRoja());
        super.vivo=true;
        super.precio = 1500;
        super.nombre = "Armería Cannon Barba Roja";
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        //super.cantidadDeEspacios=1;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/armeria.png";
        super.yaTieneConector=false;
    }
}

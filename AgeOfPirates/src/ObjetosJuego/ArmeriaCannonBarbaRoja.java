package ObjetosJuego;

import java.util.ArrayList;

public class ArmeriaCannonBarbaRoja extends Armeria{
    public ArmeriaCannonBarbaRoja(){
        super.vivo=true;
        super.precio = 1500;
        super.nombre = "Armería Cannon Barba Roja";
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        //super.cantidadDeEspacios=1;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/armeria.png";
    }
}

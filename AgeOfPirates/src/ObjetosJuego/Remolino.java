package ObjetosJuego;

import Arma.Arma;

import java.util.ArrayList;

public class Remolino extends Item {
    public Remolino(){
        super.nombre="Remolino";
        super.vivo=true;
        super.numero=setIDItem();
        super.cantidadDeEspacios=1;
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/tornado.png";
    }
}

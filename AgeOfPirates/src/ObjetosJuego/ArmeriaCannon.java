package ObjetosJuego;

import java.util.ArrayList;

public class ArmeriaCannon extends Armeria{
    public ArmeriaCannon(){
        super.vivo=true;
        super.precio = 1500;
        super.nombre = "Armería Cannon";
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        //super.cantidadDeEspacios=1;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/armeria.png";
        super.yaTieneConector=false;
    }
}

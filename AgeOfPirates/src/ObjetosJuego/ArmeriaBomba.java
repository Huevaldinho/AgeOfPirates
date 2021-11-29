package ObjetosJuego;

import java.util.ArrayList;

public class ArmeriaBomba extends Armeria{
    public ArmeriaBomba(){
        super.vivo=true;
        super.precio = 1500;
        super.nombre = "Armería Bomba";
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        //super.cantidadDeEspacios=1;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/armeria.png";
        super.yaTieneConector=false;
    }
}

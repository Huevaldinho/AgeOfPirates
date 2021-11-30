package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Conector extends Item{
    public static Arma tipoArma;
    private boolean pegadoALaFuente;//Si ya se inserto o si despicharon la fuente

    public Conector(){
        pegadoALaFuente=false;
        super.vivo=true;
        super.nombre = "Conector";
        super.precio = 100;
        super.numero=setIDItem();
        super.cantidadDeEspacios=1;
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/conector.png";
        super.yaTieneConector=false;
        super.itemsConectados=new ArrayList<>();

    }
    public void setPegadoALaFuente(boolean estado){
        pegadoALaFuente=estado;
    }
    public boolean getPegadoALaFuente(){
        return pegadoALaFuente;
    }

}

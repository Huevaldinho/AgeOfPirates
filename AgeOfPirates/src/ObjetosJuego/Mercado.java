package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Mercado extends Item{
    public static Arma tipoArma;
    public Mercado(){
        super.vivo=true;
        super.nombre = "Mercado";
        super.precio = 2000;
        super.numero=setIDItem();
        super.cantidadDeEspacios=2;
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/mercado.png";
    }
}

package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Armeria extends Item{
    public static Arma tipoArma;
    public Armeria(){
        super.precio = 1500;
        super.nombre = "Armería";
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        //super.cantidadDeEspacios=1;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/armeria.png";
    }
}

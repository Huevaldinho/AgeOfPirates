package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bruja extends Item{
    public static Arma tipoArma;
    public Bruja(){
        super.vivo=true;
        this.nombre = "Templo de la Bruja";
        this.precio = 2500;
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/witch.png";
    }
}

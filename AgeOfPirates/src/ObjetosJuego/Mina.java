package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Mina extends Item{
    public static Arma tipoArma;
    private int velocidad;
    private int cantidadProcesamiento;
    public Mina(){
        super.nombre = "Mina";
        super.precio = 1000;
        super.numero=setIDItem();
        super.cantidadDeEspacios=2;
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/mina.png";
    }
}

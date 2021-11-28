package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class FuenteEnergia extends Item{
    public FuenteEnergia(){
        super.nombre = "Fuente de Energía";
        super.precio=12000;
        super.numero=setIDItem();
        super.cantidadDeEspacios=4;
        super.puntosUbicacion= new ArrayList<>();
        rutaImage="images/fuentepoder.png";
    }
}

package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Conector extends Item{
    public static Arma tipoArma;
    public Conector(){
        loadImage();
        super.nombre = "Conector";
        super.precio = 100;
        super.numero=setIDItem();
        super.cantidadDeEspacios=1;
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
    }
    private void loadImage(){
        try {
            imagen = ImageIO.read(new File("images/conector.png"));
        } catch (IOException e) {
            System.out.println("No se pudo abrir la imagen");
            e.printStackTrace();
        }
    }

}

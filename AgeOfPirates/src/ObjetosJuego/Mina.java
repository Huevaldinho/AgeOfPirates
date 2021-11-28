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
        loadImage();
        super.nombre = "Mina";
        super.precio = 1000;
        super.numero=setIDItem();
        super.cantidadDeEspacios=2;
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
    }
    private void loadImage(){
        try {
            imagen = ImageIO.read(new File("images/mina.png"));
        } catch (IOException e) {
            System.out.println("No se pudo abrir la imagen");
            e.printStackTrace();
        }
    }
}

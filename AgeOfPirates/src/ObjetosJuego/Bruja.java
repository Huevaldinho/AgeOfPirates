package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bruja extends Item implements ActionListener {
    private int DELAY = super.getVelocidadBruja();
    private Timer timer;
    public Bruja(){
        super.vivo=true;
        this.nombre = "Templo de la Bruja";
        this.precio = 2500;
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/witch.png";
        super.yaTieneConector=false;

        timer = new Timer(DELAY,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Hilo de bruja crea un comodin
        System.out.println("\nHilo bruja corre\n");

    }
}

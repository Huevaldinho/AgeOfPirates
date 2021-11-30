package ObjetosJuego;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    public String nombre;
    public boolean vivo;//Si no lo han destruido , false es que ya lo despicharon
    public boolean agregadoAlGrid;
    //TENEMOS QUE SABER A CUAL CONECTOR SE AGREGO
    public boolean yaTieneConector;//Se cambia en el proceso de insertar items a conector
    public int numero=0;
    public int precio;
    public int cantidadDeEspacios;
    public BufferedImage imagen;
    ArrayList<Point> puntosUbicacion;
    public String rutaImage;
    public ArrayList<Item> itemsConectados;//Los items que se van a pegar al conector

    public Item(){

    }
    public int setIDItem(){
        numero++;//como esta en 0, retorna 1, cuando vuelva va a estar en 1, retorna 2...
        return numero;
    }
    public ArrayList<Point> getPuntosUbicacion() {
        return puntosUbicacion;
    }
    public void setPuntosUbicacion(ArrayList<Point> puntos){
        this.puntosUbicacion=puntos;
    }
    public void setAgregadoAlGrid(boolean estado){
        agregadoAlGrid=estado;
    }
    public boolean getAgregadoAlGrid(){
        return agregadoAlGrid;
    }
    public String getNombre(){
        return nombre;
    }
    public BufferedImage loadImage(){
        try {
            return ImageIO.read(new File(rutaImage));
        } catch (IOException e) {
            System.out.println("No se pudo abrir la imagen");
            e.printStackTrace();
        }
        return null;
    }
    public void setVivo(boolean estado){
        this.vivo=estado;
    }
    public boolean isVivo(){
        return vivo;
    }
    public int getPrecio(){
        return precio;
    }
    public int getNumero(){
        return numero;
    }
    public ArrayList<Item> getItemsConectados(){
        return itemsConectados;
    }
    public void agregarItemConectado(Item item){
        itemsConectados.add(item);
        System.out.println("EN CONECTOR HAY:" +itemsConectados.size());
        //SE REINICIA POR EL CASTEO
    }

    public boolean isYaTieneConector() {
        return yaTieneConector;
    }

    public void setYaTieneConector(boolean yaTieneConector) {
        this.yaTieneConector = yaTieneConector;
    }

    public void eliminaritemConectado(Item item){
        itemsConectados.remove(item);
    }
}


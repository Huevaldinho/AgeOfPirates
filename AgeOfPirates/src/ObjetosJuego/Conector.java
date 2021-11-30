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
    private ArrayList<Item> itemsConectados;//Los items que se van a pegar al conector
    public Conector(){
        itemsConectados=new ArrayList<>();
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
    }
    public void setPegadoALaFuente(boolean estado){
        pegadoALaFuente=estado;
    }
    public boolean getPegadoALaFuente(){
        return pegadoALaFuente;
    }
    public ArrayList<Item> getItemsConectados(){
        return itemsConectados;
    }
    public void agregarItemConectado(Item item){
        itemsConectados.add(item);
        System.out.println("EN CONECTOR HAY:" +itemsConectados.size());
    }
    public void eliminaritemConectado(Item item){
        itemsConectados.remove(item);
    }
}

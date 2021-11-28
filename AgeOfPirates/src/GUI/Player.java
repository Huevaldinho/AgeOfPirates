package GUI;

import Arma.Arma;
import Comodines.Barco;
import Comodines.Escudo;
import Comodines.Kraken;
import GUI.Grid;
import ObjetosJuego.FuenteEnergia;
import ObjetosJuego.Item;
import ObjetosJuego.Mercado;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private ArrayList<Arma> armas;//Para atacar, se muestran en combobox del mapa rival
    private ArrayList<Item> items;//Cada componente, se muestra en mi mapa
    private Barco barco;
    private Escudo escudo;
    private Kraken kraken;

    private Grid grid;

    private int ID;
    private int dinero;
    private boolean estado;//Ataca o no
    private boolean listo;

    public Player() {
        estado=false;//Aun no puede atacar
        listo = false;
        dinero=4000;

        items=new ArrayList<>();
        armas=new ArrayList<>();

//      items.add(new FuenteEnergia());
//      items.add(new Mercado());
        //Estos objetos no extienden de item ni arma, asi que los manejamos individualmente
        barco = null;
        escudo = null;
        kraken = null;
    }
    public boolean isListo() {
        return listo;
    }
    public void setListo(boolean listo) {
        this.listo = listo;
    }
    public void setID(int id){
        this.ID=id;
    }
    public int getID(){
        return ID;
    }
    public ArrayList<Arma> getArmas() {
        return armas;
    }
    public void setArmas(ArrayList<Arma> armas) {
        this.armas = armas;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    public Grid getGrid() {
        return grid;
    }
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
    public int getDinero() {
        return dinero;
    }
    public void setDinero(int dinero) {
        this.dinero = dinero;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public Barco getBarco() {
        return barco;
    }
    public void setBarco(Barco barco) {
        this.barco = barco;
    }
    public Escudo getEscudo() {
        return escudo;
    }
    public void setEscudo(Escudo escudo) {
        this.escudo = escudo;
    }
    public Kraken getKraken() {
        return kraken;
    }
    public void setKraken(Kraken kraken) {
        this.kraken = kraken;
    }

    @Override
    public String toString() {
        return "Player{" +
                ", ID=" + ID +
                ", dinero=" + dinero +
                ", estado=" + estado +
                ", listo=" + listo +
                "armas=" + armas +
                ", items=" + items +
                ", grid=" + grid +
                '}';
    }
}

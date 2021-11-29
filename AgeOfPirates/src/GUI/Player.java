package GUI;

import Arma.Arma;
import ObjetosJuego.Barco;
import Comodines.Escudo;
import Comodines.Kraken;
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
    private int acero;

    private boolean estado;//Ataca o no
    private boolean listo;
    private boolean vivo;
    private boolean cambiosEnInventario;

    public Player() {
        cambiosEnInventario=false;
        vivo=true;//Se muere cuando se queda sin items
        estado=false;//Aun no puede atacar
        listo = false;
        dinero=4000;
        acero=0;

        items=new ArrayList<>();
        armas=new ArrayList<>();

        items.add(new FuenteEnergia());
        items.add(new Mercado());
        //Estos objetos no extienden de item ni arma, asi que los manejamos individualmente
        barco = null;
        escudo = null;
        kraken = null;
    }

    public boolean isCambiosEnInventario() {
        return cambiosEnInventario;
    }
    public void setCambiosEnInventario(boolean cambiosEnInventario) {
        this.cambiosEnInventario = cambiosEnInventario;
    }

    public void setAcero(int acero){
        this.acero=acero;
    }
    public int getAcero(){
        return acero;
    }
    public boolean isVivo(){
        return vivo;
    }
    public void setVivo(boolean vivo){
        this.vivo=vivo;
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
    public void agregarNuevoItem(Item nuevo){
        items.add(nuevo);
    }
    @Override
    public String toString() {
        return "Player{" +
                ", ID=" + ID +
                ", dinero=" + dinero +
                ", estado=" + estado +
                ", listo=" + listo +
                ", armas=" + armas +
                ", items=" + items +
                ", grid=" + grid +
                '}';
    }
}

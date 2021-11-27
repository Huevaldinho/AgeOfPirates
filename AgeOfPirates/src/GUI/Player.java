package GUI;

import Arma.Arma;
import GUI.Grid;
import ObjetosJuego.FuenteEnergia;
import ObjetosJuego.Item;
import ObjetosJuego.Mercado;

import java.util.ArrayList;

public class Player {
    private ArrayList<Arma> armas;//Para atacar, se muestran en combobox del mapa rival
    private ArrayList<Item> items;//Cada componente, se muestra en mi mapa
    private Grid grid;
    private int ID;
    private int dinero;


    public Player() {
        items=new ArrayList<>();
        dinero=4000;

        items.add(new FuenteEnergia());
        items.add(new Mercado());

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
    @Override
    public String toString() {
        return "Player{" +
                "armas=" + armas +
                ", items=" + items +
                ", grid=" + grid +
                ", ID=" + ID +
                ", dinero=" + dinero +
                '}';
    }
}

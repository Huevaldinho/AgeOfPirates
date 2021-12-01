package General;

import java.io.Serializable;

import Arma.Arma;

public class Intercambio implements Serializable {
    private int jugadorVendedor;
    private int jugadorComprador;
    private Arma arma;
    private int precio;

    public Intercambio(){

    }
    public Intercambio(int vendedor,int comprador,int precio,Arma nombre){
        this.jugadorVendedor=vendedor;
        this.jugadorComprador=comprador;
        this.precio=precio;
        this.arma=nombre;
    }
    public int getJugadorVendedor() {
        return jugadorVendedor;
    }

    public void setJugadorVendedor(int jugadorVendedor) {
        this.jugadorVendedor = jugadorVendedor;
    }

    public int getJugadorComprador() {
        return jugadorComprador;
    }

    public void setJugadorComprador(int jugadorComprador) {
        this.jugadorComprador = jugadorComprador;
    }

    public Arma getNombreArma() {
        return arma;
    }

    public void setNombreArma(Arma nombreArma) {
        this.arma = nombreArma;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}

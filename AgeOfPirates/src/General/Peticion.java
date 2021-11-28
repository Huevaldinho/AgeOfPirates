package General;

import java.io.Serializable;
import GUI.*;
/**
 * Modelo de una peticion para ser enviada al server o al cliente
 */
public class Peticion implements Serializable{
    
    private TipoAccion accion;
    private Object datosEntrada;
    private Object datosSalida;
    private Object datosExtra;

    public Peticion() {
    }
    /**
     * Recibe el tipo de accion (interfaz TipoAccion) y los datos de entrada (Object)
     * */
    public Peticion(TipoAccion accion, Object datosEntrada) {
        this.accion = accion;
        this.datosEntrada = datosEntrada;
    }
    public void setDatosExtra(Object obj){
        datosExtra=obj;
    }
    public Object getDatosExtra(){
        return datosExtra;
    }
    public void setAccion(TipoAccion accion) {
        this.accion = accion;
    }
    public TipoAccion getAccion() {
        return accion;
    }
    public void setDatosEntrada(Object datosEntrada) {
        this.datosEntrada = datosEntrada;
    }
    public Object getDatosEntrada() {
        return datosEntrada;
    }
    public Object getDatosSalida() {
        return datosSalida;
    }
    public void setDatosSalida(Object datosSalida) {
        this.datosSalida = datosSalida;
    }
    @Override
    public String toString() {
        return "Peticion{" + "accion=" + accion + ", datosEntrada=" + datosEntrada + ", datosSalida=" + datosSalida + '}';
    }
}

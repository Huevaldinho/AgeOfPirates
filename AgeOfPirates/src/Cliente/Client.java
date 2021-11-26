package Cliente;

import General.IConstantes;
import General.Peticion;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
/**
 * Modelo de cliente
 */
public class Client {

    private Peticion nuevaPeticion;

    public void setNuevaPeticion(Peticion nuevaPeticion) {
        this.nuevaPeticion = nuevaPeticion;
    }

    public Peticion getNuevaPeticion() {
        return nuevaPeticion;
    }

    public Object getRespuestaServer(){
        return nuevaPeticion.getDatosSalida();
    }
    public Client() {
        try {
            // establezco comunicacion con el servidor
            Socket skCliente = new Socket(IConstantes.HOST, IConstantes.PUERTO);
            // abrir el canal de recepcion del socket que viene desde el servidor
            InputStream auxEntrada = skCliente.getInputStream();
            DataInputStream flujoEntrada = new DataInputStream(auxEntrada);
            // abrir el canal de envío del socket que va hacia el servidor
            OutputStream auxSalida = skCliente.getOutputStream();
            DataOutputStream flujoSalida = new DataOutputStream(auxSalida);
            // envio al servidor
            flujoSalida.writeUTF("XXX");
            // recibiendo la respuesta del servidor
            System.out.println(flujoEntrada.readUTF());
            //desconecto el socket
            skCliente.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public Client(Peticion laPeticion) {//USAR ESTE PORQUE YA TIENE LO DE ENVIAR OBJETOS
        nuevaPeticion = laPeticion;
        try {
            //Establezco comunicacion con el servidor
            Socket skCliente = new Socket(IConstantes.HOST, IConstantes.PUERTO);
            //Abrir el canal de envío del socket que va hacia el servidor
            OutputStream auxSalida = skCliente.getOutputStream();
            ObjectOutputStream flujoSalida = new ObjectOutputStream(auxSalida);
            //Abrir el canal de recepcion del socket que viene desde el servidor
            InputStream auxEntrada = skCliente.getInputStream();
            ObjectInputStream flujoEntrada = new ObjectInputStream(auxEntrada);
            //Envio al servidor
            flujoSalida.writeObject(nuevaPeticion);
            try {
                //Recibiendo la respuesta del servidor
                nuevaPeticion = (Peticion) flujoEntrada.readObject();
                //System.out.println("Dados salida cliente: "+nuevaPeticion.getDatosSalida());
            } catch (ClassNotFoundException ex) {
                System.out.println("Problemas de casting en Client");
                ex.printStackTrace();
            }
            //Desconecto el socket
            skCliente.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

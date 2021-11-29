package Servidor;

import General.IConstantes;
import General.Peticion;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Modelo de servidor
 * @author Felipe Obando y Sebastian Bermudez
 */
public class Server {
    private Controlador accesoApp;
    private int numCli;
    public Server() {
        accesoApp = new Controlador();//Controlador para manejar las peticiones
        numCli = 0;//No hace nada, solo para ver las peticiones
        try {
            ServerSocket skServidor = new ServerSocket(IConstantes.PUERTO);
            System.out.println("Inicializando servidor en puerto " + IConstantes.PUERTO+'\n');
            while (true) {
                numCli++;
                System.out.println("Servidor esperando petición...");
                Socket skCliente = skServidor.accept(); //Espera la activacion de una peticion
                //Establece el canal de salida del servidor hacia el cliente
                OutputStream auxSalida = skCliente.getOutputStream();
                ObjectOutputStream flujoSalida = new ObjectOutputStream(auxSalida);
                //Establece el canal de entrada desde el cliente hacia el servidor
                InputStream auxEntrada = skCliente.getInputStream();
                ObjectInputStream flujoEntrada = new ObjectInputStream(auxEntrada);
                //Espera peticion de la GUI
                Peticion peticionRecibida = (Peticion) flujoEntrada.readObject();
                // transfiere la petición a la logica de aplicación y esta le devuelve la respuesta en la misma peticion
                //Se va a Controlador para atender la peti
                peticionRecibida = accesoApp.procesarPeticion(peticionRecibida);
                //Servidor envia respuesta al cliente
                flujoSalida.writeObject(peticionRecibida);
                //Desconecta la comunicacion con el cliente
                skCliente.close();
                System.out.println("Desconectando a la peticion: " + numCli+'\n');
            }//Ciclo while (true)
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
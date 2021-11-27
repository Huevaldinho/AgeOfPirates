package Servidor;

import General.Peticion;

import java.awt.*;

public class Controlador {
    private Admin admin;
    public Controlador() {
        admin = new Admin();
    }
    /**
     * Metodo para procesar el tipo de peticion que le entra al servidor,
     * Recibe una peticion, esta trae el tipo de peticion y
     * Los datos de entrada.
     * */
    public Peticion procesarPeticion(Peticion peticionRecibida) {
        switch (peticionRecibida.getAccion()){
            case SALUDAR: {
                //Le asigna el ID al jugador
                peticionRecibida.setDatosSalida("Hola playito!");
                break;
            }
            case REGISTRAR_PLAYER:{
                peticionRecibida.setDatosSalida(admin.RegistrarPlayer(peticionRecibida));
                break;
            }
            case GET_CANTIDAD_PLAYERS_CONECTADOS:{
                peticionRecibida.setDatosSalida(admin.GetCantidadPlayersActivos());
                break;
            }
            case ENVIAR_MENSAJE:{
                peticionRecibida.setDatosSalida(admin.EscribirEnCuadroTexto(peticionRecibida));
                break;
            }
            case AGREGAR_PANEl_CHAT:{
                admin.AgregarPanelArrayList(peticionRecibida);
                peticionRecibida.setDatosSalida(1);
                break;
            }
            case AGREGAR_ULTIMO_PUNTO:{
                admin.InsertarUltimoPunto((Point) peticionRecibida.getDatosEntrada());
                break;
            }
        }
        return peticionRecibida;//Retorna la peticion con datos modificados
    }
}

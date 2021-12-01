package GUI;

import Arma.Arma;
import Cliente.Client;
import General.Peticion;
import General.TipoAccion;
import ObjetosJuego.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaIntercambio extends JFrame{
    private JPanel panel;
    private JComboBox comboBoxItem;
    private JTextField txtPrecio;
    private JComboBox comboBoxJugadores;
    private JButton solicitarIntercambioButton;
    private JSpinner spinerAcero;
    private JRadioButton aceroRadioButton;
    private JRadioButton armaRadioButton;
    public int IDJugador;
    public int jugadorComprador;


    public VentanaIntercambio(int id){
        super("Intercambio entre jugadores");
        IDJugador = id;

        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        ButtonGroup grupoRadioBtn = new ButtonGroup();
        grupoRadioBtn.add(aceroRadioButton);
        grupoRadioBtn.add(armaRadioButton);

        agregarJugadoresComboBox();//Llena los players
        agregarArmasComboBox();//LLena las armas



        solicitarIntercambioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//BOTON SOLICITAR INTERCAMBIO
                int precio = sacarPrecio();
                int jugador =  jugadorSeleccionado();
                if (armaRadioButton.isSelected()){//Arma
                    //Sacar jugador, sacar arma del jugador, sacar plata enviar
                    Arma arma = sacarArma();
                    solicitudVentaItem(jugador,precio,arma);
                }else if (aceroRadioButton.isSelected()){//Acero
                    //Validar acero, sacar plata  y enviar
                    if (validarAcero()){
                        //Si tiene el acero
                        solicitudVentaAcero(jugador,precio,(int)spinerAcero.getValue());
                    }
                }else{//No selecciono mamador
                    JOptionPane.showMessageDialog(null,"Debe seleccionar Acero o Arma");
                }
                setVisible(false);
            }
        });
    }
    public void solicitudVentaItem(int jugadorVendedor, int precio, Arma arma){
        //Entrada ID jugador vendedor
        //Salida precio
        //Extra arma
        //ID ugador comprador
        Peticion solicitud = new Peticion(TipoAccion.INTERCAMBIO_ARMA,IDJugador);
        solicitud.setDatosSalida(precio);
        solicitud.setDatosExtra(arma);
        solicitud.setDatoComprador(jugadorComprador);

        Client conexion = new Client(solicitud);

    }
    public void solicitudVentaAcero(int jugadorVendedor, int precio, int acero){
        //Entrada ID jugador
        //Acero
        //Salida precio
        Peticion solicitud = new Peticion(TipoAccion.INTERCAMBIO_ACERO,IDJugador);
        solicitud.setDatosSalida(precio);
        solicitud.setDatosExtra(acero);
        solicitud.setDatoComprador(jugadorComprador);
        Client conexion = new Client(solicitud);

    }
    public Arma sacarArma( ){
        Peticion peticionJugador = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,IDJugador);
        Client conexionJugador = new Client(peticionJugador);
        //Llena combobox Items
        if (conexionJugador!=null){
            Player jugador = (Player) conexionJugador.getRespuestaServer();
            ArrayList<Arma> armasJugador = jugador.getArmas();
            for (Arma actualArma:armasJugador){
                if (actualArma.getnombre().equals(comboBoxItem.getSelectedItem().toString())){
                    System.out.println("ARMA IGUAL del jugador: "+jugador.getID());
                    return actualArma;
                }
            }
        }
        return  null;
    }
    public int jugadorSeleccionado(){
        Peticion peticion=null;
        Client cliente=null;
        switch (comboBoxJugadores.getSelectedItem().toString()){
            case "Jugador 1":{
                peticion = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,1);
                jugadorComprador=1;
                break;
            }
            case "Jugador 2":{
                peticion = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,2);
                jugadorComprador=2;
                break;
            }
            case "Jugador 3":{
                peticion = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,3);
                jugadorComprador=3;
                break;
            }
            case "Jugador 4":{
                peticion = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,4);
                jugadorComprador=4;
                break;
            }
        }
        cliente = new Client(peticion);
        if (cliente!=null){
            return jugadorComprador;
        }
        return 0;
    }
    public boolean validarAcero(){
        //Acero
        int cantidadAceroAVender = (int) spinerAcero.getValue();
        Peticion peticionValidarAcero = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_ACERO,IDJugador);
        peticionValidarAcero.setDatosSalida(cantidadAceroAVender);
        Client conexionValidarAcero = new Client(peticionValidarAcero);
        if (conexionValidarAcero!=null){
            boolean respuestaValidarAcero =(boolean) conexionValidarAcero.getRespuestaServer();
            if (respuestaValidarAcero){
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"Cantidad insuficiente de acero.");
                setVisible(false);
                return false;
            }
        }
        return false;
    }
    public void agregarJugadoresComboBox(){
        Peticion petiJugadoresActivos = new Peticion(TipoAccion.GET_CANTIDAD_PLAYERS_CONECTADOS,null);
        Client conexion = new Client(petiJugadoresActivos);
        int cntPlayers = (int) conexion.getRespuestaServer();
        int [] arr = new int[3];
        for (int i=1;i<cntPlayers;i++){
            if (IDJugador == 1) arr = new int[]{2, 3, 4};
            else if (IDJugador == 2) arr = new int[]{1, 3, 4};
            else if (IDJugador == 3) arr = new int[]{1, 2, 4};
            else if (IDJugador == 4) arr = new int[]{1, 2, 3};
            comboBoxJugadores.addItem("Jugador "+arr[i-1]);
        }
    }
    public void agregarArmasComboBox(){
        Peticion peticionJugador = new Peticion(TipoAccion.GET_JUGADOR_POR_ID,IDJugador);
        Client conexionJugador = new Client(peticionJugador);
        //Llena combobox Items
        if (conexionJugador!=null){
            Player jugador = (Player) conexionJugador.getRespuestaServer();
            ArrayList<Arma> armasJugador = jugador.getArmas();
            for (Arma actualArma:armasJugador){
                comboBoxItem.addItem(actualArma.getnombre());
            }
        }
    }
    public int sacarPrecio(){
        return  Integer.parseInt(txtPrecio.getText());
    }
}

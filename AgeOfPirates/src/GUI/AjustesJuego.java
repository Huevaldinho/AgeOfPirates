package GUI;

import Cliente.Client;
import General.Peticion;
import General.TipoAccion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AjustesJuego extends JFrame implements ActionListener{
    public JComboBox cmbBoxItems;
    public JButton btnListo;
    public JPanel panel;
    public JLabel lbl_TextDinero;
    public JLabel lbl_turno;
    public JLabel lblAjustesJugador;
    private JLabel lblAcero;
    private JButton comprarButton;
    private JComboBox comboBoxInventario;
    private JButton insertarItemAlMarButton;
    public int id;
    private final int DELAY = 3000;//3 segundo
    private Timer timer;
    private boolean cambiosEnInventario;

    public AjustesJuego() {
        super("Opciones del Juego");
        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        cambiosEnInventario=true;
        timer = new Timer(DELAY,this);
        timer.start();

        btnListo.addActionListener(new ActionListener() {//BOTON LISTO JUGADOR
            @Override
            public void actionPerformed(ActionEvent e) {
                Peticion peticion= new Peticion(TipoAccion.JUGADOR_LISTO,id);
                Client conexion = new Client(peticion);
            }
        });
        comprarButton.addActionListener(new ActionListener() {//BOTON COMPRAR
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        insertarItemAlMarButton.addActionListener(new ActionListener() {//BOTON INSERTAR ITEM AL MAR
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccionado = comboBoxInventario.getSelectedIndex();
                if (seleccionado>=0){
                    System.out.println("Item selecionado: "+comboBoxInventario.getItemAt(seleccionado));
                    InsertarItemEnGrid();//Funcion hace peticion del punto seleccionado
                    /*
                        Pasos para insetar un item: Seleccionar el item del comboBox, Clickear el boton "Insertar Item",
                        Luego clickear la posicion deseada
                    * */
                    //Hay que cambiar el estado del item para que no aparezca otra vez en el combobox

                }else{
                    JOptionPane.showMessageDialog(null,"Debe seleccionar un item del ComboBox...");
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        SetComboBoxInventario();
        ActualizarDinero();//Actualiza constantemente la etiqueta del dinero del player
        ActualizarAcero();//Actualiza el acero
//        try {
//            System.out.println("Hilo de ventana ajustes dormido");
//            Thread.sleep(3500);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
    }
    public void SetComboBoxInventario(){
        if (cambiosEnInventario){
            Peticion peticion = new Peticion(TipoAccion.SET_INVENTARIO_COMBOBOX,id);
            Client conexion = new Client(peticion);
            String [] items = (String[]) conexion.getRespuestaServer();
            //System.out.println("CANTIDAD DE ITEMS: "+items.length);
            for (int i=0;i<items.length;i++){
                comboBoxInventario.addItem(items[i]);
            }
            comboBoxInventario.setVisible(true);
            comboBoxInventario.setEditable(false);
            cambiosEnInventario=false;//ESTA VARA SE PONE EN TRUE CUANDO METE EL ITEM A LA INTERFAZ Y
            //CUANDO COMPRA o VENDE UN NUEVO ITEM
        }
    }
    public void ActualizarDinero(){//ACTUALIZA LA ETIQUETA DEL DINERO
        Peticion peticionActualizaDinero = new Peticion(TipoAccion.ACTUALIZAR_DINERO,id);
        Client conexionActualizarDinero = new Client(peticionActualizaDinero);
        lbl_TextDinero.setText("Dinero disponible: "+ (int)conexionActualizarDinero.getRespuestaServer());
    }
    public void ActualizarAcero(){
        Peticion peticionActualizarAcero = new Peticion(TipoAccion.ACTUALIZAR_ACERO,id);
        Client conexionActualizarAcero = new Client(peticionActualizarAcero);
        lblAcero.setText("Acero Disponible: "+ (int)conexionActualizarAcero.getRespuestaServer());
    }
    public void InsertarItemEnGrid(){
        //Pide el punto seleccionado

        Peticion peticionPunto = new Peticion(TipoAccion.OBTENER_ULTIMO_PUNTO,id);
        Client conexion = new Client(peticionPunto);
        Point puntoSeleccionado = (Point) conexion.getRespuestaServer();
        if (puntoSeleccionado!=null){
            System.out.println("PUNTO SELECCIONADO PARA INSERTAR: "+puntoSeleccionado);
            //Primero revisa si esta disponible
            ArrayList<Point> puntos=SeleccionarPuntosParaItem(puntoSeleccionado);
            //El arrayList puede no tener puntos y es porque no estaba disponible o era una esquina o algo asi



        }else{
            System.out.println("TIENE QUE SELECIONAR EL ITEM, LUEGO EL PUNTO Y POR ULTIMO DARLE AL BOTON");
        }
    }
    public ArrayList<Point> SeleccionarPuntosParaItem(Point puntoClickeado){
        ArrayList<Point> puntos = new ArrayList<>();
        if (RevisarSiElPuntoEstaDisponible(puntoClickeado)){//Si esta disponible
            //Calcula los puntos automaticamente

        }else{//No esta disponible
        }
        return puntos;
    }
    public boolean RevisarSiElPuntoEstaDisponible(Point punto){
        //Revisar si el punto esta disponible, o sea, si ya hay un item ahi (Recorrer items del player)
        /*
            Este mae llama al servidor con le id del jugador que esta intentando insertar el item en el grid,
            y tambien le pasa el punto en donde lo quiere ingresar. El servidor retorna si esta disponible o no
        */
        Peticion peticion = new Peticion(TipoAccion.CONSULTA_CELDA_DISPONIBLE,id);
        peticion.setDatosSalida(punto);
        Client client = new Client(peticion);
        return (boolean) client.getRespuestaServer();
    }

}

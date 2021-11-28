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
            comboBoxInventario.removeAllItems();//Lo limpia
            Peticion peticion = new Peticion(TipoAccion.SET_INVENTARIO_COMBOBOX,id);
            Client conexion = new Client(peticion);
            String [] items = (String[]) conexion.getRespuestaServer();//Nuevos items para el combobox
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

        //Obtiene ultimo punto seleccionado
        Peticion peticionPunto = new Peticion(TipoAccion.OBTENER_ULTIMO_PUNTO,id);
        Client conexion = new Client(peticionPunto);
        Point puntoSeleccionado = (Point) conexion.getRespuestaServer();//Punto seleccionado
        if (puntoSeleccionado!=null){
            ArrayList<Point> puntos= SeleccionarPuntosParaItem(puntoSeleccionado);//Saca los puntos faltantes
            if (puntos!=null){//Si es null es porque el punto no estaba disponible
                //Peticion llega, puntos, id jugador y el nombre del item
                Peticion peti = new Peticion(TipoAccion.SET_PUNTOS_ITEM,id);//Jugador
                peti.setDatosSalida(puntos);//Puntos
                peti.setDatosExtra(comboBoxInventario.getSelectedItem().toString());//Nombre item
                Client cliente = new Client(peti);
                cambiosEnInventario=true;
            }
        }else{
            System.out.println("TIENE QUE SELECIONAR EL ITEM, LUEGO EL PUNTO Y POR ULTIMO DARLE AL BOTON");
            JOptionPane.showMessageDialog(null,"Celda ocupada, intente en otra celda!");
        }
    }
    public ArrayList<Point> SeleccionarPuntosParaItem(Point puntoClickeado){
        ArrayList<Point> puntos = new ArrayList<>();
        if (RevisarSiElPuntoEstaDisponible(puntoClickeado)){//Si esta disponible
            //Calcula los puntos automaticamente
            //Segun tipo de Item
            String seleccion =comboBoxInventario.getSelectedItem().toString();
            switch (seleccion){
                case "Fuente de Energía":{//Usa 4 puntos
                    System.out.println("SELECCIONO FUENTE DE ENERGIA SACAR LOS PUNTOS QUE FALTAN");
                    puntos.add(puntoClickeado);//Se agrega solo para probar porque se tienen que agregar
                    //hasta que confirme los 4 puntos
                    break;
                }
                case "Mercado":{//Usa 2 puntos
                    System.out.println("SELECCIONO MERCADO SACAR PUNTO QUE FALTA");
                    puntos.add(puntoClickeado);//Se agrega solo para probar porque se tienen que agregar
                    break;
                }
                case "Mina":{//Usa 2 puntos
                    System.out.println("SELECCIONO MINA, SACAR PUNTO QUE FALTA");
                    puntos.add(puntoClickeado);//Se agrega solo para probar porque se tienen que agregar
                    break;
                }
                case "Templo de la Bruja":{//Usa 2 puntos
                    System.out.println("SELECCIONO TEMPO BURJA,  SACAR PUNTO QUE FALTA");
                    puntos.add(puntoClickeado);//Se agrega solo para probar porque se tienen que agregar
                    break;
                }
                case "Conector":{//Usa 1 conector
                    System.out.println("SELECCIONO CONECTOR, SOLO USA EL PUNTO SELECCIONADO");
                    puntos.add(puntoClickeado);//Solo el que selecciono
                    break;
                }
                case "Armería":{//Usa 2 conectores
                    System.out.println("SELECCIONO ARMERIA, SACAR PUNTO QUE FALTA");
                    puntos.add(puntoClickeado);//Se agrega solo para probar porque se tienen que agregar
                    break;
                }
            }
            return puntos;
        }else{//No esta disponible

            return null;
        }
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

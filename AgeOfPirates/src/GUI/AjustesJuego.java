package GUI;

import Cliente.Client;
import ObjetosJuego.*;
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
                comprarItem();
            }
        });
        insertarItemAlMarButton.addActionListener(new ActionListener() {//BOTON INSERTAR ITEM AL MAR
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccionado = comboBoxInventario.getSelectedIndex();
                if (seleccionado>=0){
                    InsertarItemEnGrid();//Funcion hace peticion del punto seleccionado
                    /*
                        Pasos para insetar un item: Seleccionar el item del comboBox, Clickear el boton "Insertar Item",
                        Luego clickear la posicion deseada
                    * */
                    //Hay que cambiar el estado del item para que no aparezca otra vez en el combobox

                }else{
                    JOptionPane.showMessageDialog(null,"Debe seleccionar un ítem del ComboBox...");
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        SetComboBoxInventario();//Actualiza constantemente el combobox de items del player
        ActualizarDinero();//Actualiza constantemente la etiqueta del dinero del player
        ActualizarAcero();//Actualiza el acero
    }
    public void SetComboBoxInventario(){
        if (cambiosEnInventario){
            comboBoxInventario.removeAllItems();//Lo limpia
            Peticion peticion = new Peticion(TipoAccion.SET_INVENTARIO_COMBOBOX,id);
            Client conexion = new Client(peticion);
            String [] items = (String[]) conexion.getRespuestaServer();//Nuevos items para el combobox
            System.out.println("CANTIDAD DE ITEMS: "+items.length);
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
                cambiosEnInventario=true;//Se utiliza para actualizar el combobox de inventario
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
                    puntos = AgregarPuntosFaltantes(puntoClickeado,4);
                    break;
                }
                case "Mercado":{//Usa 2 puntos
                    System.out.println("SELECCIONO MERCADO SACAR PUNTO QUE FALTA");
                    puntos = AgregarPuntosFaltantes(puntoClickeado,3);
                    break;
                }
                case "Mina":{//Usa 2 puntos
                    System.out.println("SELECCIONO MINA, SACAR PUNTO QUE FALTA");
                    puntos = AgregarPuntosFaltantes(puntoClickeado,2);
                    break;
                }
                case "Templo de la Bruja":{//Usa 2 puntos
                    System.out.println("SELECCIONO TEMPO BURJA,  SACAR PUNTO QUE FALTA");
                    puntos = AgregarPuntosFaltantes(puntoClickeado,2);
                    break;
                }
                case "Conector":{//Usa 1 conector
                    System.out.println("SELECCIONO CONECTOR, SOLO USA EL PUNTO SELECCIONADO");
                    puntos = AgregarPuntosFaltantes(puntoClickeado,1);
                    break;
                }
                case "Armería Cannon":{//Usa 2 conectores
                    System.out.println("SELECCIONO ARMERIA CANNON, SACAR PUNTO QUE FALTA");
                    puntos = AgregarPuntosFaltantes(puntoClickeado,2);
                    break;
                }
                case "Armería Cannon Multiple":{//Usa 2 conectores
                    System.out.println("SELECCIONO ARMERIA CANNON MULTIPLE, SACAR PUNTO QUE FALTA");
                    puntos = AgregarPuntosFaltantes(puntoClickeado,2);
                    break;
                }
                case "Armería Bomba":{//Usa 2 conectores
                    System.out.println("SELECCIONO ARMERIA BOMBA, SACAR PUNTO QUE FALTA");
                    puntos = AgregarPuntosFaltantes(puntoClickeado,2);
                    break;
                }
                case "Armería Cannon Barba Roja":{//Usa 2 conectores
                    System.out.println("SELECCIONO ARMERIA CANNON BARBA ROJA, SACAR PUNTO QUE FALTA");
                    puntos = AgregarPuntosFaltantes(puntoClickeado,2);
                    break;
                }
            }
            return puntos;
        }else{//No esta disponible
            JOptionPane.showMessageDialog(null,"La celda seleccionada ya contiene un item, intente en otra...");
            return null;
        }
    }
    public boolean RevisarSiElPuntoEstaDisponible(Point punto ){
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
    public ArrayList<Point> AgregarPuntosFaltantes(Point punto,int tipoItem){
        /*
          -------- Dependiendo de como se llama en el switch de SeleccionarPuntosParaItem(Point) --------

          Casillas 4x4 (Fuente de poder)---------> tipoItem = 4;
          Casillas 1x2 (Mercado)-----------------> tipoItem = 3;
          Casillas 2x1 (Mina, Templo, etc...) ---> tipoItem = 2;
          Casillas 1x1 --------------------------> tipoItem = 1;

         */
        ArrayList <Point> points = new ArrayList<>();
        Point puntoValido = new Point();

        if (tipoItem == 4){ //4x4
            if (punto.x>=0 && punto.x<=9 && punto.y>=0 && punto.y<=9) { //c1
                puntoValido = devolverPuntosParaDibujar(punto, 1);
                points.add(puntoValido);
                points.add(new Point(puntoValido.x+1,puntoValido.y));
                points.add(new Point(puntoValido.x,puntoValido.y+1));
                points.add(new Point(puntoValido.x+1,puntoValido.y+1));
            }
            else if (punto.x>=0 && punto.x<=9 && punto.y>=10 && punto.y<=19) { //c2
                puntoValido = devolverPuntosParaDibujar(punto, 2);
                points.add(puntoValido);
                points.add(new Point(puntoValido.x+1,puntoValido.y));
                points.add(new Point(puntoValido.x,puntoValido.y-1));
                points.add(new Point(puntoValido.x+1,puntoValido.y-1));
            }
            else if (punto.x>=10 && punto.x<=19 && punto.y>=0 && punto.y<=9) { //c3
                puntoValido = devolverPuntosParaDibujar(punto, 3);
                points.add(puntoValido);
                points.add(new Point(puntoValido.x-1,puntoValido.y));
                points.add(new Point(puntoValido.x,puntoValido.y+1));
                points.add(new Point(puntoValido.x-1,puntoValido.y+1));
            }
            else { //c4
                puntoValido = devolverPuntosParaDibujar(punto, 4);
                points.add(puntoValido);
                points.add(new Point(puntoValido.x-1,puntoValido.y));
                points.add(new Point(puntoValido.x,puntoValido.y-1));
                points.add(new Point(puntoValido.x-1,puntoValido.y-1));
            }
        }else if (tipoItem == 3) {
            puntoValido = devolverPuntosParaDibujar(punto, 5);
            points.add(puntoValido);
            points.add(new Point(puntoValido.x,puntoValido.y+1));
        }else if (tipoItem == 2) {
            puntoValido = devolverPuntosParaDibujar(punto, 6);
            points.add(puntoValido);
            points.add(new Point(puntoValido.x+1,puntoValido.y));
        }else{
            puntoValido = devolverPuntosParaDibujar(punto , 7);
            points.add(puntoValido);
         }
        return points;
    }
    public Point devolverPuntosParaDibujar(Point punto , double tipoError){
        /*                  TIPO DE ERROR
                1   --> 4X4 ; cuadrante 1 (0 , 0) ; (0 , 9) ; (9 , 0) ; (9 , 9)
                2   --> 4x4 ; cuadrante 2 (0 , 10) ; (0 , 19) ; (9 , 10) ; (9 , 19)
                3   --> 4x4 ; cuadrante 3 (10 , 0) ; (10 , 9) ; (19 , 0) ; (19 , 9)
                4   --> 4x4 ; cuadrante 4 (10 , 10) ; (10 , 19) ; (19 , 10) ; (19 , 19)
                5   --> 1x2 ; (n , 19)
                6   --> 2x1 ; (19 , n)
                7   --> 1x1 ; *no es un error, solo busca en diagonal*
         */
        if (tipoError == 1){
            if (RevisarSiElPuntoEstaDisponible(punto)&&
                RevisarSiElPuntoEstaDisponible(new Point(punto.x+1,punto.y))&& //derecha
                RevisarSiElPuntoEstaDisponible(new Point(punto.x,punto.y+1))&& //abajo
                RevisarSiElPuntoEstaDisponible(new Point(punto.x+1,punto.y+1))) //diagonal derecha abajo
                    return punto;
            else
                return devolverPuntosParaDibujar(new Point (punto.x+1,punto.y+1),tipoError);

        } else if (tipoError == 2){
            if (RevisarSiElPuntoEstaDisponible(punto)&&
                RevisarSiElPuntoEstaDisponible(new Point(punto.x+1,punto.y))&& //derecha
                RevisarSiElPuntoEstaDisponible(new Point(punto.x,punto.y-1))&& //arriba
                RevisarSiElPuntoEstaDisponible(new Point(punto.x+1,punto.y-1))) //diagonal derecha arriba
                    return punto;
            else
                return devolverPuntosParaDibujar(new Point (punto.x+1,punto.y-1),tipoError);

        } else if (tipoError == 3){
            if (RevisarSiElPuntoEstaDisponible(punto)&&
                RevisarSiElPuntoEstaDisponible(new Point(punto.x-1,punto.y))&& //izquierda
                RevisarSiElPuntoEstaDisponible(new Point(punto.x,punto.y+1))&& //abajo
                RevisarSiElPuntoEstaDisponible(new Point(punto.x-1,punto.y+1))) //diagonal izquierda abajo
                    return punto;
            else
                return devolverPuntosParaDibujar(new Point (punto.x-1,punto.y+1),tipoError);


        } else if (tipoError == 4){
            if (RevisarSiElPuntoEstaDisponible(punto)&&
                RevisarSiElPuntoEstaDisponible(new Point(punto.x-1,punto.y))&& //izquierda
                RevisarSiElPuntoEstaDisponible(new Point(punto.x,punto.y-1))&& //arriba
                RevisarSiElPuntoEstaDisponible(new Point(punto.x-1,punto.y-1))) //diagonal izquierda arriba
                    return punto;
            else
                return devolverPuntosParaDibujar(new Point (punto.x-1,punto.y-1),tipoError);
        } else if (tipoError == 5){
            if (punto.x == 19 && punto.y == 19)
                return devolverPuntosParaDibujar(new Point(19,0),tipoError);
            else if (RevisarSiElPuntoEstaDisponible(punto) && RevisarSiElPuntoEstaDisponible(new Point(punto.x , punto.y+1)))
                return punto;
            else
                return devolverPuntosParaDibujar(new Point(punto.x , punto.y+1),tipoError);
        } else if (tipoError == 6){
            if (punto.x == 19 && punto.y == 19)
                return devolverPuntosParaDibujar(new Point(0,19),tipoError);
            else if (RevisarSiElPuntoEstaDisponible(punto) && RevisarSiElPuntoEstaDisponible(new Point(punto.x+1 , punto.y)))
                return punto;
            else
                return devolverPuntosParaDibujar(new Point(punto.x+1 , punto.y),tipoError);
        } else {
            if (RevisarSiElPuntoEstaDisponible(punto))
                return punto;
            else
                return devolverPuntosParaDibujar(new Point(punto.x+1,punto.y+1),tipoError);
        }

    }
    public void comprarItem(){//Solo filtra si selecciono el item, si tiene mercado y manda a hacer la compra
        int seleccionado = cmbBoxItems.getSelectedIndex();
        if (seleccionado>=0){
            //Revisar si tiene Mercado para poder comprar o vender
            Peticion peticion = new Peticion(TipoAccion.BUSCAR_ITEM_VIVO, "Mercado");//Nombre del Item
            peticion.setDatosSalida(id);
            Client conexion = new Client(peticion);
            boolean respuesta = (boolean)conexion.getRespuestaServer();
            if (respuesta){
                realizarCompra(nombreItemSinPrecio(cmbBoxItems.getSelectedItem().toString()));//Saca el nombre del item
            }else{
             JOptionPane.showMessageDialog(null,"Para comprar o vender items se necesita el Mercado");
            }
        }else{
          JOptionPane.showMessageDialog(null,"Debe seleccionar un item del ComboBox del Mercado");
        }
    }
    public String nombreItemSinPrecio (String nombreConPrecio){
        switch (nombreConPrecio){
            case "Fuente de Energía ($12000)":return "Fuente de Energía";
            case "Conectores ($100)": return "Conector";
            case "Mina ($1000)": return "Mina";
            case "Templo de la Bruja ($2500)": return "Templo de la Bruja";
            case "Barco Fantasma ($2500)": return "Barco Fantasma";
            case "Armería Cannon ($1500)": return "Armería Cannon";
            case "Armería Cannon Multiple ($1500)": return "Armería Cannon Multiple";
            case "Armería Bomba ($1500)": return "Armería Bomba";
            case "Armería Cannon Barba Roja ($1500)": return "Armería Cannon Barba Roja";
        }
        return null;
    }
    public void realizarCompra(String nombreItemAComprar){
        //MANEJAR EL PRECIO Y ID JUGADOR PARA REALIZAR LA COMPRA
        Peticion peti = null;
        Item nuevo = null;
        switch (nombreItemAComprar){
            case "Fuente de Energía":{//12k
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(12000);
                nuevo = new FuenteEnergia();
                break;
            }
            case "Conector":{//100
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(100);
                nuevo = new Conector();
                break;
            }
            case "Templo de la Bruja":{//2500
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(2500);
                nuevo = new Bruja();
                break;
            }
            case "Barco Fantasma":{
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(2500);
                nuevo = new Barco();
                break;
            }
            case "Mina":{//1k
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(1000);
                nuevo = new Mina();
                break;
            }
            case "Armería Cannon":{//1500
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(1500);
                nuevo = new ArmeriaCannon();
                break;
            }
            case "Armería Cannon Multiple":{
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(1500);
                nuevo = new ArmeriaCannonMultiple();
                break;
            }
            case "Armería Bomba":{
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(1500);
                nuevo = new ArmeriaBomba();
                break;
            }
            case "Armería Cannon Barba Roja":{
                peti = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_DINERO,id);
                peti.setDatosSalida(1500);
                nuevo = new ArmeriaCannonBarbaRoja();
                break;
            }
        }

        Client conexion = new Client(peti);
        if ((boolean)conexion.getRespuestaServer()){
            //Realizar la compra
            Peticion peticionInsertarCompraNueva = new Peticion(TipoAccion.REALIZAR_COMPRA_ITEM,nuevo);
            peticionInsertarCompraNueva.setDatosSalida(id);
            Client conexionCompra = new Client(peticionInsertarCompraNueva);
            cambiosEnInventario=true;
        }else{
            JOptionPane.showMessageDialog(null,"No tiene sufuciente dinero para comprar ese item...");
        }
    }
}

package sample.ui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Memorama extends Stage implements EventHandler {

    private Label lblTarjetas;
    private Label lblContMovimientos;
    private TextField txtNoTarjetas;
    private HBox hBox;
    private VBox vBox;
    private GridPane gdpMesa;
    private Button btnAceptar;
    private Button[][] arTarjetas;
    private String[][] arAsignacion;
    private String[] arImagenes={"1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg","9.jpg","10.jpg"};
    private String btnComparador1,btnComparador2;
    private Scene escena;

    private boolean tarjIguales,tarjNoIguales;
    private int noPares;
    private int totPares;
    private int divPares;
    private int nuevFila,nuevFila2;
    private int nuevColum,nuevColum2;
    private int posAnteriorI,posAnteriorJ,posAnteriorI2,posAnteriorJ2;
    private int parejEncontradas = 0;
    private int noMovimientos=0;

    public Memorama(){
        CrearUI();
        this.setTitle("Memorama");
        this.setScene(escena);
        this.setResizable(false);
        this.show();
    }
    private void CrearUI() {
        lblTarjetas=new Label("Número de pares:");
        txtNoTarjetas=new TextField();
        btnAceptar=new Button("Voltear y revolver");
        lblContMovimientos= new Label("Número de movimientos: "+noMovimientos);
        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Convertir el texto a Integer,
                // la excepción será si el usuario no introdujo un número.
                try {
                    noPares = Integer.parseInt(txtNoTarjetas.getText());
                } catch (NumberFormatException excep) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Introducir un número", ButtonType.YES);
                    ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
                    txtNoTarjetas.setText("");
                }
            }
        });
        hBox=new HBox();
        hBox.getChildren().addAll(lblTarjetas,txtNoTarjetas,btnAceptar,lblContMovimientos);
        hBox.setSpacing(10);

        gdpMesa = new GridPane();
        vBox = new VBox();
        vBox.getChildren().addAll(hBox,gdpMesa);
        escena = new Scene (vBox,890,659);
    }

    @Override
    public void handle(Event event) {
            lblContMovimientos.setText("Número de movimientos: "+noMovimientos);
            noMovimientos=0;
            parejEncontradas=0;
            vBox.getChildren().remove(gdpMesa);
            gdpMesa = new GridPane();
            llenarArreglo();
            vBox.getChildren().add(gdpMesa);
    }
    private void llenarArreglo() {
        if(noPares==1||noPares==2||noPares==3){
            arAsignacion= new String[2][noPares];
            revolver3();
            arTarjetas= new Button[2][noPares];
            for (int i = 0; i < 2; i++) {
                for (int j=0; j<noPares; j++){
                    Image img = new Image("sample/assets/rev.jpg");
                    ImageView imv = new ImageView(img);
                    imv.setFitHeight(150);
                    imv.setPreserveRatio(true);
                    arTarjetas[i][j] = new Button();
                    int finalI = i;
                    int finalJ = j;
                    arTarjetas[i][j].setOnAction(event1->verTarjeta(finalI,finalJ));
                    arTarjetas[i][j].setGraphic(imv);
                    arTarjetas[i][j].setPrefSize(40,40);
                    gdpMesa.add(arTarjetas[i][j],j,i);
                }
            }
        }
        if(noPares==4||noPares==5||noPares==6||noPares==8)
        {
            nuevFila2=(noPares/2);
            nuevColum2=(noPares*2)/nuevFila2;
            arAsignacion = new String[nuevFila2][nuevColum2];
            revolver2();
            arTarjetas = new Button[nuevFila2][noPares];
            for (int i=0; i<nuevFila2; i++){//filas
                for( int j=0; j<nuevColum2; j++){//columnas
                    Image img = new Image("sample/assets/rev.jpg");
                    ImageView imv = new ImageView(img);
                    imv.setFitHeight(150);
                    imv.setPreserveRatio(true);
                    arTarjetas[i][j] = new Button();
                    int finalI=i;
                    int finalJ=j;
                    arTarjetas[i][j].setOnAction(event1 -> verTarjeta(finalI,finalJ));
                    arTarjetas[i][j].setGraphic(imv);
                    arTarjetas[i][j].setPrefSize(40,40);
                    gdpMesa.add(arTarjetas[i][j],j,i);
                }
            }

        }
        else if (noPares==7||noPares==9||noPares==10){
            nuevFila=(noPares/2)-1;
            nuevColum=(noPares*2)/nuevFila;
            arAsignacion = new String[nuevFila][nuevColum];
            revolver();
            arTarjetas = new Button[nuevFila][noPares];
            for (int i=0; i<nuevFila; i++){//filas
                for( int j=0; j<nuevColum; j++){//columnas
                    Image img = new Image("sample/assets/rev.jpg");
                    ImageView imv = new ImageView(img);
                    imv.setFitHeight(150);
                    imv.setPreserveRatio(true);

                    arTarjetas[i][j] = new Button();
                    int finalI=i;
                    int finalJ=j;
                    arTarjetas[i][j].setOnAction(event1 -> verTarjeta(finalI,finalJ));
                    arTarjetas[i][j].setGraphic(imv);
                    arTarjetas[i][j].setPrefSize(40,40);
                    gdpMesa.add(arTarjetas[i][j],j,i);
                }
            }
        }
        else if(noPares>10){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No válido por ahora", ButtonType.YES);
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
            txtNoTarjetas.setText("");
        }
    }
    private void tarjetasNoIguales(){
        Image img = new Image("sample/assets/rev.jpg");
        ImageView imv = new ImageView(img);
        imv.setFitHeight(150);
        imv.setPreserveRatio(true);
        arTarjetas[posAnteriorI][posAnteriorJ].setGraphic(imv);

        Image img2 = new Image("sample/assets/rev.jpg");
        ImageView imv2 = new ImageView(img2);
        imv2.setFitHeight(150);
        imv2.setPreserveRatio(true);
        arTarjetas[posAnteriorI2][posAnteriorJ2].setGraphic(imv2);
        posAnteriorI2 = 0;
        posAnteriorJ2 = 0;
    }
    private void verTarjeta(int finalI,int finalJ) {
        Image img = new Image("sample/assets/"+arAsignacion[finalI][finalJ]);
        ImageView imv = new ImageView(img);
        imv.setFitHeight(150);
        imv.setPreserveRatio(true);
        btnComparador1 = "" + arAsignacion[finalI][finalJ];
        arTarjetas[finalI][finalJ].setGraphic(imv);

        if (tarjIguales == false) {
            System.out.println("nopar:"+tarjNoIguales);
            if(tarjNoIguales==true){
                tarjetasNoIguales();
                tarjNoIguales=false;
            }
            tarjIguales = true;
            posAnteriorI = finalI;
            posAnteriorJ = finalJ;
            btnComparador2 = "" + arAsignacion[finalI][finalJ];

            Image img4 = new Image("sample/assets/"+arAsignacion[finalI][finalJ]);
            ImageView imv4 = new ImageView(img4);
            imv4.setFitHeight(150);
            imv4.setPreserveRatio(true);
            arTarjetas[finalI][finalJ].setGraphic(imv4);

        }
        else if (tarjIguales == true) {
            if( btnComparador1.equals(btnComparador2) ){//Son iguales
                parejEncontradas++;
            }
            else{//No son iguales
                btnComparador1="";
                btnComparador2="";
                tarjIguales=false;
                tarjNoIguales=true;
                posAnteriorI2=finalI;
                posAnteriorJ2=finalJ;
            }
            tarjIguales = false;
            noMovimientos++;
            lblContMovimientos.setText("Número de movimientos: " + noMovimientos);

            if (parejEncontradas == (noPares)) {
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Has ganado!"+" con "+noMovimientos+" intentos", ButtonType.YES);
                 ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
                 txtNoTarjetas.setText("");
                lblContMovimientos.setText("Número de movimientos: " + 0);
             }
            }
        }

    private void revolver() {
        for(int i=0; i<nuevFila; i++)
            for(int j=0; j<nuevColum; j++){
                arAsignacion[i][j] = new String();
            }

        int posx, posy, cont = 0;
        for(int i=0; i<noPares;){
            posx = (int)(Math.random() * nuevFila);
            posy = (int)(Math.random() * nuevColum);
            if( arAsignacion[posx][posy].equals("") ){
                arAsignacion[posx][posy] = arImagenes[i];
                cont++;
            }
            if(cont == 2){ // Sirve para comprobar que la imagen se asignó 2 veces
                i++;
                cont = 0;
            }
        }
    }
    private void revolver2() {
        for(int i=0; i<nuevFila2; i++)
            for(int j=0; j<nuevColum2; j++){
                arAsignacion[i][j] = new String();
            }
        int posx, posy, cont = 0;
        for(int i=0; i<noPares;){
            posx = (int)(Math.random() * nuevFila2);
            posy = (int)(Math.random() * nuevColum2);
            if( arAsignacion[posx][posy].equals("") ){
                arAsignacion[posx][posy] = arImagenes[i];
                cont++;
            }
            if(cont == 2){
                i++;
                cont = 0;
            }
        }
    }
    private void revolver3() {
        for(int i=0; i<2; i++)
            for(int j=0; j<noPares; j++){
                arAsignacion[i][j] = new String();
            }

        int posx, posy, cont = 0;
        for(int i=0; i<noPares;){
            posx = (int)(Math.random() * 2);
            posy = (int)(Math.random() * noPares);
            if( arAsignacion[posx][posy].equals("") ){
                arAsignacion[posx][posy] = arImagenes[i];
                cont++;
            }

            if(cont == 2){
                i++;
                cont = 0;
            }
        }
    }
}

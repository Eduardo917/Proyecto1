package sample.ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Taquimecanografo extends Stage implements EventHandler<KeyEvent> {

    //Bandera para detectar cambios de color en las teclas
    boolean banColor = false;

    //Arreglos para etiquetar los botones del teclado
    private String arLblBtn1[] = {"Esc","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12"," Supr "};
    private String arLblBtn2[] = {" | "," 1 "," 2 "," 3 "," 4 "," 5 "," 6 "," 7 "," 8 "," 9 "," 0 "," ' "," ¡ ","            ←"};
    private String arLblBtn3[] = {"   Tab  "," Q "," W "," E "," R "," T "," Y "," U "," I "," O "," P "," ´ "," + " , "  }  "};
    private String arLblBtn4[] = {"BMayus"," A "," S "," D "," F "," G "," H "," J "," K "," L "," Ñ ","{","     Enter     "};
    private String arLblBtn5[] = {"     Shift     "," Z "," X "," C "," V "," B "," N "," M "," , "," . "," - ","          fin          "};
    private String arLblBtn6[] = {"Ctrl","Win","   alt   ","                  Espacio                   ","  alt gr  ","><","←","↑","↓","→"};

    //Elementos para el toolbar
    private ToolBar tlbMenu;
    private Button btnAbrir;

    //Elementos para la escritura
    private TextArea txtContenido, txtEscritura;

    //Elementos para guardar temporalmente el texto del archivo
    private String txtArchivo = "";
    private String st;
    FileReader fr = null;
    BufferedReader br = null;

    //Elementos para la estadistica de las palabras
    private HBox totalPalabras;
    private Label lblPalabras;
    private int noPalabras=0;

    //Elementos para el teclado
    private HBox[] arHBoxTeclas = new HBox[6];
    private VBox vBoxTeclado;
    private Button[] arBtnTeclado1 = new Button[14];
    private Button[] arBtnTeclado2 = new Button[14];
    private Button[] arBtnTeclado3 = new Button[14];
    private Button[] arBtnTeclado4 = new Button[13];
    private Button[] arBtnTeclado5 = new Button[12];
    private Button[] arBtnTeclado6 = new Button[10];

    //Elementos de agrupacion global
    private VBox vBoxPrincipal;
    private Scene escena;

    public Taquimecanografo(){
        CrearUI();
        this.setTitle("Tutor de Taquimecanógrafía");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        CrearToolbar();
        CrearEscritura();
        CrearTeclado();
        lblPalabras = new Label("Palabras: "+noPalabras);
        totalPalabras = new HBox();
        totalPalabras.setSpacing(10);
        totalPalabras.getChildren().addAll(lblPalabras);
        totalPalabras.setTranslateX(300.0);//Trasladar el contenido del totalPalabras a una parte central de la escena

        vBoxPrincipal = new VBox();
        vBoxPrincipal.getChildren().addAll(tlbMenu,totalPalabras, txtContenido, txtEscritura, vBoxTeclado);
        vBoxPrincipal.setSpacing(5);
        vBoxPrincipal.setPadding(new Insets(10));
        escena = new Scene(vBoxPrincipal,800,500);
    }

    private void CrearTeclado() {
        vBoxTeclado = new VBox();
        vBoxTeclado.setSpacing(8);
        vBoxTeclado.setTranslateX(100.0);//Trasladar el contenido de VBoxTeclado a una parte central de la escena

        for (int i=0; i<arHBoxTeclas.length; i++){
            arHBoxTeclas[i] = new HBox();
            arHBoxTeclas[i].setSpacing(10);
        }
        for( int i=0; i<arBtnTeclado1.length; i++ ){
            arBtnTeclado1[i] = new Button(arLblBtn1[i]);
            arBtnTeclado1[i].setStyle("-fx-background-color: #85D4D6;");
            arBtnTeclado2[i] = new Button(arLblBtn2[i]);
            arBtnTeclado2[i].setStyle("-fx-background-color: #85D4D6;");
            arBtnTeclado3[i]=new Button(arLblBtn3[i]);
            arBtnTeclado3[i].setStyle("-fx-background-color: #85D4D6;");
            arHBoxTeclas[0].getChildren().addAll(arBtnTeclado1[i]);
            arHBoxTeclas[1].getChildren().add(arBtnTeclado2[i]);
            arHBoxTeclas[2].getChildren().add(arBtnTeclado3[i]);
        }
        for( int i=0; i<arBtnTeclado4.length; i++ ){
            arBtnTeclado4[i]=new Button(arLblBtn4[i]);
            arBtnTeclado4[i].setStyle("-fx-background-color: #85D4D6;");
            arHBoxTeclas[3].getChildren().add(arBtnTeclado4[i]);
        }
        for( int i=0; i<arBtnTeclado5.length; i++ ){
            arBtnTeclado5[i]=new Button(arLblBtn5[i]);
            arBtnTeclado5[i].setStyle("-fx-background-color: #85D4D6;");
            arHBoxTeclas[4].getChildren().add(arBtnTeclado5[i]);
        }
        for( int i=0; i<arBtnTeclado6.length; i++ ){
            arBtnTeclado6[i]=new Button(arLblBtn6[i]);
            arBtnTeclado6[i].setStyle("-fx-background-color: #85D4D6;");
            arHBoxTeclas[5].getChildren().add(arBtnTeclado6[i]);
        }
        vBoxTeclado.getChildren().addAll(arHBoxTeclas[0],arHBoxTeclas[1],arHBoxTeclas[2],arHBoxTeclas[3],arHBoxTeclas[4],arHBoxTeclas[5]);
    }

    private void CrearEscritura() {
        txtContenido = new TextArea();
        txtContenido.setEditable(false);
        txtContenido.setPrefRowCount(6);
        txtEscritura = new TextArea();
        txtEscritura.setPrefRowCount(6);
        txtEscritura.setOnKeyPressed(this);
        txtEscritura.setOnKeyReleased(this);
        //addEventHandler(KeyEvent.KEY_TYPED,this);
    }

    private void CrearToolbar() {
        tlbMenu = new ToolBar();
        btnAbrir = new Button();
        btnAbrir.setOnAction(event -> eventoTaqui(1));
        btnAbrir.setPrefSize(25,25);

        //Asignar la imagen al botón dentro del toolbar
        Image img = new Image("sample/assets/abrirtxt.png");
        ImageView imv = new ImageView(img);
        imv.setFitHeight(40);
        imv.setPreserveRatio(true);
        btnAbrir.setGraphic(imv);
        tlbMenu.getItems().addAll(btnAbrir);
    }

    private void eventoTaqui(int opc) {
        switch (opc){
            case 1:
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Abrir archivo...");
                //Filtrar para que solo se puedan abrir archivos .txt
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Archivos de texto", "*.txt*")
                );
                File file = fileChooser.showOpenDialog(this);
                //Leer el archivo
                if(file!=null){
                    txtArchivo ="";
                    try {
                        //Objeto para abrir el flujo de datos y realizar la lectura
                        fr = new FileReader(file);
                        //Objeto para leer en buffer y almacenar datos a travez de fr
                        br = new BufferedReader(fr);
                        //Guardar una linea de texto leida
                        st = br.readLine();
                        while (st != null) {//Mientras haya texto
                            txtArchivo  = txtArchivo + st; //Almacenar en texto cada linea leida y guardada en st
                            st = br.readLine();
                            if(st!=null)
                            {
                                txtArchivo =txtArchivo +"\n";//Almacenar las lineas leidas junto a los saltos de linea
                            }
                        }
                        txtContenido.setText(txtArchivo);//Agregar el texto almacenado del archivo al TextArea
                        fr.close();//Cerrar el flujo de datos
                    } catch (Exception e) {
                        txtContenido.setText(e.toString());//En caso de excepcion devolver una cadena
                    } finally {//Cerrar el archivo
                        try {
                        } catch (Exception e2) {
                            txtContenido.setText(e2.toString());
                        }
                    }

                }

        }
    }
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode().toString()){
            case "ESCAPE":
                if( banColor == false )
                    arBtnTeclado1[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[0].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F1":
                if( banColor == false )
                    arBtnTeclado1[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[1].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F2":
                if( banColor == false )
                    arBtnTeclado1[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[2].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F3":
                if( banColor == false )
                    arBtnTeclado1[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[3].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F4":
                if( banColor == false )
                    arBtnTeclado1[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[4].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F5":
                if( banColor == false )
                    arBtnTeclado1[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[5].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F6":
                if( banColor == false )
                    arBtnTeclado1[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[6].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F7":
                if( banColor == false )
                    arBtnTeclado1[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[7].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F8":
                if( banColor == false )
                    arBtnTeclado1[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[8].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F9":
                if( banColor == false )
                    arBtnTeclado1[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[9].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F10":
                if( banColor == false )
                    arBtnTeclado1[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[10].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F11":
                if( banColor == false )
                    arBtnTeclado1[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[11].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F12":
                if( banColor == false )
                    arBtnTeclado1[12].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[12].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DELETE":
                if( banColor == false )
                    arBtnTeclado1[13].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado1[13].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT1":
                if( banColor == false )
                    arBtnTeclado2[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[1].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT2":
                if( banColor == false )
                    arBtnTeclado2[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[2].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT3":
                if( banColor == false )
                    arBtnTeclado2[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[3].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT4":
                if( banColor == false )
                    arBtnTeclado2[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[4].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT5":
                if( banColor == false )
                    arBtnTeclado2[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[5].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT6":
                if( banColor == false )
                    arBtnTeclado2[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[6].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT7":
                if( banColor == false )
                    arBtnTeclado2[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[7].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT8":
                if( banColor == false )
                    arBtnTeclado2[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[8].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT9":
                if( banColor == false )
                    arBtnTeclado2[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[9].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DIGIT0":
                if( banColor == false )
                    arBtnTeclado2[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[10].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "QUOTE":
                if( banColor == false )
                    arBtnTeclado2[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[11].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "BACK_SPACE":
                if( banColor == false )
                    arBtnTeclado2[13].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[13].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "TAB":
                if( banColor == false )
                    arBtnTeclado3[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[0].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "Q":
                if( banColor == false )
                    arBtnTeclado3[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[1].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "W":
                if( banColor == false )
                    arBtnTeclado3[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[2].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "E":
                if( banColor == false )
                    arBtnTeclado3[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[3].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "R":
                if( banColor == false )
                    arBtnTeclado3[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[4].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "T":
                if( banColor == false )
                    arBtnTeclado3[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[5].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "Y":
                if( banColor == false )
                    arBtnTeclado3[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[6].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "U":
                if( banColor == false )
                    arBtnTeclado3[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[7].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "I":
                if( banColor == false )
                    arBtnTeclado3[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[8].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "O":
                if( banColor == false )
                    arBtnTeclado3[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[9].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "P":
                if( banColor == false )
                    arBtnTeclado3[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[10].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DEAD_ACUTE":
                if( banColor == false )
                    arBtnTeclado3[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[11].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "PLUS":
                if( banColor == false )
                    arBtnTeclado3[12].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[12].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "BRACERIGHT":
                if( banColor == false )
                    arBtnTeclado3[13].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado3[13].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "CAPS":
                if( banColor == false )
                    arBtnTeclado4[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[0].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "A":
                if( banColor == false )
                    arBtnTeclado4[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[1].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "S":
                if( banColor == false )
                    arBtnTeclado4[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[2].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "D":
                if( banColor == false )
                    arBtnTeclado4[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[3].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "F":
                if( banColor == false )
                    arBtnTeclado4[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[4].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "G":
                if( banColor == false )
                    arBtnTeclado4[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[5].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "H":
                if( banColor == false )
                    arBtnTeclado4[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[6].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "J":
                if( banColor == false )
                    arBtnTeclado4[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[7].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "K":
                if( banColor == false )
                    arBtnTeclado4[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[8].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "L":
                if( banColor == false )
                    arBtnTeclado4[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[9].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "BRACELEFT":
                if( banColor == false )
                    arBtnTeclado4[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[11].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "ENTER":
                if( banColor == false )
                    arBtnTeclado4[12].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[12].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "SHIFT":
                if( banColor == false )
                    arBtnTeclado5[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[0].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "Z":
                if( banColor == false )
                    arBtnTeclado5[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[1].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "X":
                if( banColor == false )
                    arBtnTeclado5[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[2].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "C":
                if( banColor == false )
                    arBtnTeclado5[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[3].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "V":
                if( banColor == false )
                    arBtnTeclado5[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[4].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "B":
                if( banColor == false )
                    arBtnTeclado5[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[5].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "N":
                if( banColor == false )
                    arBtnTeclado5[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[6].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "M":
                if( banColor == false )
                    arBtnTeclado5[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[7].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "COMMA":
                if( banColor == false )
                    arBtnTeclado5[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[8].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "PERIOD":
                if( banColor == false )
                    arBtnTeclado5[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[9].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "MINUS":
                if( banColor == false )
                    arBtnTeclado5[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[10].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "END":
                if( banColor == false )
                    arBtnTeclado5[11].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado5[11].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "CONTROL":
                if( banColor == false )
                    arBtnTeclado6[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[0].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "WINDOWS":
                if( banColor == false )
                    arBtnTeclado6[1].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[1].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "ALT":
                if( banColor == false )
                    arBtnTeclado6[2].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[2].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "SPACE":
                if( banColor == false )
                    arBtnTeclado6[3].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[3].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "ALT_GR":
                if( banColor == false )
                    arBtnTeclado6[4].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[4].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "LESS":
                if( banColor == false )
                    arBtnTeclado6[5].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[5].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "LEFT":
                if( banColor == false )
                    arBtnTeclado6[6].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[6].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "UP":
                if( banColor == false )
                    arBtnTeclado6[7].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[7].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "DOWN":
                if( banColor == false )
                    arBtnTeclado6[8].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[8].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "RIGHT":
                if( banColor == false )
                    arBtnTeclado6[9].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado6[9].setStyle("-fx-background-color: #85D4D6;");
                break;
        }
        switch(event.getText())
        {
            case "|":
                if( banColor == false )
                    arBtnTeclado2[0].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado2[0].setStyle("-fx-background-color: #85D4D6;");
                break;
            case "ñ":
                if( banColor == false )
                    arBtnTeclado4[10].setStyle("-fx-background-color: #1d1d1d;");
                else
                    arBtnTeclado4[10].setStyle("-fx-background-color: #85D4D6;");
                break;
        }
        banColor = !banColor;
        if(event.getEventType().equals(KeyEvent.KEY_PRESSED))
        {
            if (txtEscritura.getText().length() <= txtArchivo .length())//Sacar el número de palabras
                {
                    int palEspacio=txtEscritura.getText().split(" ").length;
                    int palSalto=txtEscritura.getText().split("\n").length - 1 ;

                    noPalabras = palEspacio+palSalto;
                    lblPalabras.setText("Palabras: " + noPalabras);
                }
            }
        if(txtEscritura.getText().equals(txtArchivo))//Verificar si las cadenas son iguales
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Has introducido correctamente el texto!", ButtonType.YES);
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
            txtEscritura.setText("");
            lblPalabras.setText("");
        }
    }
}

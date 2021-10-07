package reproductor;

import com.jfoenix.controls.JFXSlider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.SortEvent;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javafx.util.Callback;
import javafx.util.Duration;

public class MainController implements Initializable {

    @FXML
    private SplitMenuButton Opciones;
    @FXML
    private HBox parent;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnAnt;
    @FXML
    private Button btnDesp;
    @FXML
    private Button btnStop;
    @FXML
    private TableView<Cancion2> tabla;
    @FXML
    private TableColumn<Cancion2, String> NOMBRE;
    @FXML
    private TableColumn<Cancion2, String> ARTISTA;
    @FXML
    private TableColumn<Cancion2, String> ALBUM;
    @FXML
    private Button btnSALIR;
    @FXML
    private Slider volumen;
    @FXML
    private Button btnAnadir;
    @FXML
    private MenuItem btnEliminarPlay;
    @FXML
    private Button btnCrearPlay;
    @FXML
    private Button btnBuscarPlay;

    @FXML
    private TableView<Cancion2> tablaPlayList;
    @FXML
    private TableColumn<Cancion2, String> NOMBRE1;
    @FXML
    private TableColumn<Cancion2, String> ARTISTA1;
    @FXML
    private TableColumn<Cancion2, String> ALBUM1;

    private static int numeroDeCanciones;
    @FXML
    private TextField TEXTOPLAY;
/**
 * <p> con una Observable List muestra las canciones de la Biblioteca y devuelve una libreria<p>
 * @return libreria
 */
    public ObservableList<Cancion2> showEnTable() {
        ObjectInputStream lectorDeObjetos;
        ObservableList<Cancion2> libreria = FXCollections.observableArrayList();
        try {
            lectorDeObjetos = new ObjectInputStream(new FileInputStream("Biblioteca.dat"));
            while (true) {
                try {
                    Cancion song = (Cancion) lectorDeObjetos.readObject();
                    libreria.add(new Cancion2(song.getNombre(), song.getArtista(), song.getAlbum()));

                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        numeroDeCanciones = libreria.size();
        return libreria;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colocarImagenBotones();
        NOMBRE.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ARTISTA.setCellValueFactory(new PropertyValueFactory<>("artista"));
        ALBUM.setCellValueFactory(new PropertyValueFactory<>("album"));
        tabla.setItems(showEnTable());
        tabla.setRowFactory(new Callback<TableView<Cancion2>, TableRow<Cancion2>>() {
            @Override
            public TableRow<Cancion2> call(TableView<Cancion2> tv) {
                TableRow<Cancion2> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Cancion2 rowData = row.getItem();

                        try {
                            reprod(rowData);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                return row;
            }
        });

    }
    MediaPlayer mediaplayer;
    Media media;
    boolean sonando = false;
    boolean endOfMedia = false;

    @FXML
    public void button1Action(ActionEvent event) {
        Status status = mediaplayer.getStatus();
        if (status == Status.UNKNOWN || status == Status.STALLED) {
            return;
        }
        if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
            if (endOfMedia) {
                mediaplayer.seek(mediaplayer.getStartTime());
            }
            mediaplayer.play();
        } else {
            mediaplayer.pause();
        }
    }

    @FXML
    public void button2Action(ActionEvent event) {

        mediaplayer.stop();

    }
    File cancion;
/**
 * <p>encargada de la reproduccion de las canciones de la listya de la biblioteca<p>
 * @param nCancion
 * @throws MalformedURLException 
 */
    public void reprod(Cancion2 nCancion) throws MalformedURLException {
        if (sonando == true) {
            mediaplayer.stop();
            sonando = false;
        }
        String nombreCancion = nCancion.getNombre();
        establecerIndice(nCancion);
        String ruta = Serializar.DIC.get(nombreCancion);
        cancion = new File(ruta);
        String Data = cancion.toURI().toURL().toExternalForm();
        Media music = new Media(Data);
        mediaplayer = new MediaPlayer(music);
        volumen.setValue(mediaplayer.getVolume() * 100);
        volumen.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaplayer.setVolume(volumen.getValue() / 100);
            }

        });
        mediaplayer.play();
        sonando = true;
        mediaplayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (cancionSeleccionada + 1 != numeroDeCanciones) {
                    sonando = false;
                    try {
                        Comprobacion(1);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sonando = true;
                }
            }

        });

    }
/**
 * <p> encargado de poner a los botones imagenes, que se encuentran en una carpeta dentro del package<p>
 *  

 */
    public void colocarImagenBotones() {
        URL linkplay = getClass().getResource("/img/play.png");
        URL linkexit = getClass().getResource("/img/exit.png");
        URL linkstop = getClass().getResource("/img/stop.png");
        URL linknext = getClass().getResource("/img/Siguiente.png");
        URL linkback = getClass().getResource("/img/anterior.png");
        Image imagenplay = new Image(linkplay.toString(), 24, 24, false, true);
        Image imagenstop = new Image(linkstop.toString(), 24, 24, false, true);
        Image imagennext = new Image(linknext.toString(), 24, 24, false, true);
        Image imagenback = new Image(linkback.toString(), 24, 24, false, true);
        Image imagenexit = new Image(linkexit.toString(), 24, 24, false, true);
        btnSALIR.setGraphic((new ImageView(imagenexit)));
        btnPlay.setGraphic((new ImageView(imagenplay)));
        btnStop.setGraphic((new ImageView(imagenstop)));
        btnDesp.setGraphic((new ImageView(imagennext)));
        btnAnt.setGraphic((new ImageView(imagenback)));

    }

//SIGUIENTE
    @FXML
    private void button5Action(ActionEvent event) throws MalformedURLException {
        if (cancionSeleccionada + 1 != numeroDeCanciones) {
            sonando = false;
            Comprobacion(1);
            sonando = true;
        }
            if (cancionSeleccionada2 + 1 != PlayList.numeroDeCanciones2) {
            sonando = false;
            Comprobacion2(1);
            sonando = true;
        }
    }

//ANTERIOR
    @FXML
    private void button4Action(ActionEvent event) throws MalformedURLException {
        if (cancionSeleccionada != 0) {
            sonando = false;
            Comprobacion(-1);
            sonando = true;
        }
            if (cancionSeleccionada2 != 0) {
            sonando = false;
            Comprobacion2(-1);
            sonando = true;
        }
    }
/**
 * <p> encargado de comprobar si es posible de pasar a siguiente o anterior cancion en la biblioteca<p>
 * @param i
 * @throws MalformedURLException 
 */
    private void Comprobacion(int i) throws MalformedURLException {
        int pista = cancionSeleccionada;
        if (cancion != null & pista != -1) {
            Cancion2 siguienteOAnteriorCancion = tabla.getItems().get(pista + i);
            mediaplayer.stop();
            reprod(siguienteOAnteriorCancion);
        }
    }
    /**
     * <p> encargado de comprobar si es posible de pasar a siguiente o anterior cancion en la PlayList<p>
     * @param i
     * @throws MalformedURLException 
     */
       private void Comprobacion2(int i) throws MalformedURLException {
        int pista = cancionSeleccionada2;
        if (cancion != null & pista != -1) {
            Cancion2 siguienteOAnteriorCancion = tablaPlayList.getItems().get(pista + i);
            mediaplayer.stop();
            reprod2(siguienteOAnteriorCancion);
        }
    }

    private int cancionSeleccionada;
/**
 * <p>Da el indice de la cancio seleccionada para Biblioteca<p>
 * @param cancion 
 */
    private void establecerIndice(Cancion2 cancion) {
        cancionSeleccionada = tabla.getItems().indexOf(cancion);
    }

    @FXML
    private void buttonEXIT(ActionEvent event) {
        Reproductor.stage.close();
    }


    @FXML
    private void buttonDELETEPLAY(ActionEvent event) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Eliminacion de PlayList");
        FileChooser Directorio = new FileChooser();
        File recordsDir = new File(System.getProperty("user.home", "/Documents/NetBeansProjects/Reproductor"));
        Directorio.setInitialDirectory(recordsDir);
        Directorio.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PlayList", "*.dat"));
        Button button = new Button("Seleccione Playlist");
        VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 400, 50);
        primaryStage.setScene(scene);
        primaryStage.show();
        button.setOnAction(e -> {
            File selectedFile = Directorio.showOpenDialog(primaryStage);
            if (selectedFile.getName() != "Biblioteca.dat") {
                System.out.println(rutaPlay);
                selectedFile.delete();
                primaryStage.close();
                Stage borrado = new Stage();
                borrado.setTitle("PlayList borrada");
                Label Playlist = new Label("La PlayList se ha borrado exitosamente");
                VBox vBox2 = new VBox(Playlist);
                Scene scene2 = new Scene(vBox2, 220, 50);
                borrado.setScene(scene2);
                borrado.show();
            } else {
                Stage error = new Stage();
                error.setTitle("Error en borrar");
                Label Playlist = new Label("La PlayList no se puede borrar, ya que es la Biblioteca principal");
                VBox vBox2 = new VBox(Playlist);
                Scene scene2 = new Scene(vBox2, 400, 30);
                error.setScene(scene2);
                error.show();
            }

        });
    }

    @FXML
    private void buttonCREATE(ActionEvent event) {
        if (!TEXTOPLAY.getText().isEmpty()) {
            String contenidoTexto = TEXTOPLAY.getText();
            String formato = ".dat";
            String TextoTotal = contenidoTexto + formato;
            System.out.println(TextoTotal);
            PlayList PlaylistNueva = new PlayList();
            PlaylistNueva.ArchivoNuevo(TextoTotal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("PlayList creada");
            Label Playlist = new Label("La PlayList se ha creado exitosamente");
            VBox vBox = new VBox(Playlist);
            Scene scene = new Scene(vBox, 200, 50);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
    String rutaPlay;

    @FXML
    private void buttonSEARCHPLAY(ActionEvent event) {

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Seleccion de Playlist");
        FileChooser Directorio = new FileChooser();
        File recordsDir = new File(System.getProperty("user.home", "/Documents/NetBeansProjects/Reproductor"));
        Directorio.setInitialDirectory(recordsDir);
        Directorio.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PlayList", "*.dat"));
        Button button = new Button("Seleccione Playlist");
        VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 350, 50);
        primaryStage.setScene(scene);
        primaryStage.show();
        button.setOnAction(e -> {
            File selectedFile = Directorio.showOpenDialog(primaryStage);
            rutaPlay = selectedFile.getName();
            NOMBRE1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            ARTISTA1.setCellValueFactory(new PropertyValueFactory<>("artista"));
            ALBUM1.setCellValueFactory(new PropertyValueFactory<>("album"));
            tablaPlayList.setItems(PlayList.showEnTable2(rutaPlay));
            tablaPlayList.setRowFactory(new Callback<TableView<Cancion2>, TableRow<Cancion2>>() {
                @Override
                public TableRow<Cancion2> call(TableView<Cancion2> tv) {
                    TableRow<Cancion2> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && (!row.isEmpty())) {
                            Cancion2 rowData = row.getItem();

                            try {
                                reprod2(rowData);
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    return row;
                }

            });
            primaryStage.close();
        });

    }
    private int cancionSeleccionada2;
/**
 * <p>Da el indice de la cancio seleccionada para PlayList<p>
 * @param cancion 
 */
    private void establecerIndice2(Cancion2 cancion) {
        cancionSeleccionada2 = tablaPlayList.getItems().indexOf(cancion);
    }
/**
 * <p>encargada de la reproduccion de las canciones de la listya de la PlayList<p>
 * @param nCancion
 * @throws MalformedURLException 
 */
    public void reprod2(Cancion2 nCancion) throws MalformedURLException {
        if (sonando == true) {
            mediaplayer.stop();
            sonando = false;
        }
        String nombreCancion = nCancion.getNombre();
        establecerIndice2(nCancion);
        String ruta = Serializar.DIC.get(nombreCancion);
        cancion = new File(ruta);
        String Data = cancion.toURI().toURL().toExternalForm();
        Media music = new Media(Data);
        mediaplayer = new MediaPlayer(music);
        volumen.setValue(mediaplayer.getVolume() * 100);
        volumen.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaplayer.setVolume(volumen.getValue() / 100);
            }

        });
        mediaplayer.play();
        sonando = true;
        mediaplayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (cancionSeleccionada2 + 1 != PlayList.numeroDeCanciones2) {
                    sonando = false;
                    try {
                        Comprobacion2(1);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sonando = true;
                }
            }

        });

    }

    @FXML
    private void buttonADD(ActionEvent event) {
        Cancion2 Song = tabla.getSelectionModel().getSelectedItem();
        String nombreParaRuta = Song.getNombre();
        String ruta = Serializar.DIC.get(nombreParaRuta);
        File path = new File(ruta);
        Cancion nuevaAAnadir = new Cancion(Song.getArtista(), Song.getAlbum(), Song.getNombre(), path);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Seleccion de Playlist");
        FileChooser Directorio = new FileChooser();
        File recordsDir = new File(System.getProperty("user.home", "/Documents/NetBeansProjects/Reproductor"));
        Directorio.setInitialDirectory(recordsDir);
        Directorio.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PlayList", "*.dat"));
        Button button = new Button("Seleccione Playlist");
        VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 350, 50);
        primaryStage.setScene(scene);
        primaryStage.show();
        button.setOnAction(e -> {
            File selectedFile = Directorio.showOpenDialog(primaryStage);
            String rutaPlay1 = selectedFile.getName();
            PlayList.escribirCancion(nuevaAAnadir, rutaPlay1);
            primaryStage.close();
        });

    }

}

package reproductor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Reproductor extends Application {

    public static Stage stage = null;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("MMP3");
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
        existeDirectorio();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * <p>
     * encargada de averiguar se"Biblioteca.dat" esta para o sino crearla<p>
     */
    private void existeDirectorio() {
        File archivo = new File("Biblioteca.dat");
        if (!archivo.exists()) {
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Fichero Biblioteca");
            DirectoryChooser Directorio = new DirectoryChooser();
            Button button = new Button("Seleccione Fichero");
            button.setOnAction(e -> {
                File selectedFile = Directorio.showDialog(primaryStage);
                try {
                    biblioteca.buscador(selectedFile.toString());

                } catch (IOException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }

                primaryStage.close();
                Stage cierreStage = new Stage();
                cierreStage.setTitle("Abrir nuevamente");
                Label aviso = new Label("Favor volver a abrir el reproductor para cargar libreria");
                VBox vBox2 = new VBox(aviso);
                Scene scene2 = new Scene(vBox2, 300, 50);
                cierreStage.setScene(scene2);
                cierreStage.show();
                stage.close();
            });
            VBox vBox = new VBox(button);
            Scene scene = new Scene(vBox, 300, 50);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            Serializar serializadora2 = new Serializar();
            serializadora2.leer("Biblioteca.dat");
        }

    }

}

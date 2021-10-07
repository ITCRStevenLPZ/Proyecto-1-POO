package reproductor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static reproductor.Serializar.diccionario;
import static reproductor.biblioteca.listarFilesPorCarpeta;
import static reproductor.biblioteca.para;

public class PlayList {

    private static ObjectInputStream lectorDeObjetos;
    private static ObjectOutputStream escritorDeObjetos;
/**
 * <p>Crea el archivo . dat playlist vacio, para que el usuario luego lo pueda seleccionar y editar<p>
 * @param nombrePlaylist 
 */
    public static void ArchivoNuevo(String nombrePlaylist) {
        try {
            escritorDeObjetos = new ObjectOutputStream(new FileOutputStream(nombrePlaylist));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
/**
 * <p>recibe la cancion y el nombre de la playlist seleccionada por el usuario y an;ade dicha song en la playlist<p>
 * @param song
 * @param nombrePlaylist 
 */
    public static void escribirCancion(Cancion song, String nombrePlaylist) {
        if (!ExistenArchivos(nombrePlaylist)) {
            try {
                escritorDeObjetos = new ObjectOutputStream(new FileOutputStream(nombrePlaylist));
                escritorDeObjetos.writeObject(song);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            try {
                escritorDeObjetos = new ObjectOutputStream(new FileOutputStream(nombrePlaylist));
                for (Object cancionactual : EXISTENTES) {
                    escritorDeObjetos.writeObject(cancionactual);
                }
                escritorDeObjetos.writeObject(song);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    static ArrayList<Cancion> EXISTENTES;
/**
 * <p>comprueban si, anteriormente en una playlist que esta a punto de ser editada, habian canciones ya serializadas y anade estas a una array<p>
 * @param nombrePlaylist
 * @return 
 */
    private static boolean ExistenArchivos(String nombrePlaylist) {
        EXISTENTES = new ArrayList<Cancion>();
        try {
            lectorDeObjetos = new ObjectInputStream(new FileInputStream(nombrePlaylist));
            while (true) {
                try {
                    Cancion songYaExistetnte = (Cancion) lectorDeObjetos.readObject();
                    EXISTENTES.add(songYaExistetnte);

                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return true;
    }
    static int numeroDeCanciones2;
/**
 * <p> similar a showEnTable, solo que esta va dirigida a PlayList y recibe el nombre de dicha playlist a anadir en tablaPlayList<p>
 * @param PlayList
 * @return 
 */
    public static ObservableList<Cancion2> showEnTable2(String PlayList) {
        ObjectInputStream lectorDeObjetos;
        ObservableList<Cancion2> libreria2 = FXCollections.observableArrayList();
        try {
            lectorDeObjetos = new ObjectInputStream(new FileInputStream(PlayList));
            while (true) {
                try {
                    Cancion song = (Cancion) lectorDeObjetos.readObject();
                    libreria2.add(new Cancion2(song.getNombre(), song.getArtista(), song.getAlbum()));

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
        numeroDeCanciones2 = libreria2.size();
        return libreria2;
    }
}

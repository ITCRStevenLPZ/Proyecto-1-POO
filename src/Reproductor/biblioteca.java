package reproductor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class biblioteca implements FilenameFilter {

    static Parametros para = new Parametros();
    static Serializar serializadora = new Serializar();
    static ArrayList<Cancion> directorio = new ArrayList<Cancion>();
/**
 * <p>encargada de aceptar si la extension termina en .mp3 o no mientras se realiza un for en listarFilesPorCarpeta<p>
 * @param nombre
 * @return 
 */
    public static boolean accept(String nombre) {
        String extension = ".mp3";
        if (nombre.endsWith(extension)) {
            return true;
        }
        return false;
    }
/**
 * <p>encargado de agarrar la file de la biblioteca y por cada .mp3 que encuentre, los convierte en objetos Cancion, aparte de esto, serializa y lee dicha serializacion<p>
 * @param Carpeta
 * @throws IOException 
 */
    public static void listarFilesPorCarpeta(final File Carpeta) throws IOException {
        for (final File FileEntrante : Carpeta.listFiles()) {
            if (FileEntrante.isDirectory()) {
                listarFilesPorCarpeta(FileEntrante);
            } else {
                if (accept(FileEntrante.getName())) {
                    Cancion song = new Cancion(para.getArtista(FileEntrante), para.getAlbum(FileEntrante), para.getNombre(FileEntrante), FileEntrante);
                    ((List<Cancion>) directorio).add(song);
                }
            }

        }
        serializadora.escribir(directorio);
        serializadora.leer("Biblioteca.dat");

    }
/**
 * <p>recibe la string ruta de DirectoryChooser y la convierte en file para que listarFilesPorCarpeta la pueda usar<p>
 * @param ruta
 * @throws IOException 
 */
    public static void buscador(String ruta) throws IOException {
        File Root = new File(ruta);
        listarFilesPorCarpeta(Root);

    }

    @Override
    public boolean accept(File dir, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

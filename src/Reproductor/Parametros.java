package reproductor;


import java.net.URL;
import java.io.File;
import java.net.MalformedURLException;

import java.util.regex.Pattern;

public class Parametros {
/**
 * <p>recibe una ruta y por medio del split y de URL se dividen los nombres de las canciones<p>
 * @param ruta
 * @return
 * @throws MalformedURLException 
 */
	public String getNombre(File ruta) throws MalformedURLException {
		URL url = ruta.toURI().toURL();
		String[] partes= url.getPath().split(Pattern.quote("/"));
		String name =partes[partes.length-1];
		String espacios= name.replace("%20", " ");
		return espacios;
		
		
	}
        /**
         * <p>recibe una ruta y por medio del split y de URL se dividen los albumes de las canciones<p>
         * @param ruta
         * @return
         * @throws MalformedURLException 
         */
	public String getAlbum(File ruta) throws MalformedURLException {
		URL url = ruta.toURI().toURL();
		String[] partes= url.getPath().split(Pattern.quote("/"));
		String album =partes[partes.length-2];
		String espacios= album.replace("%20", " ");
		return espacios;
	}
        /**
         * <p>recibe una ruta y por medio del split y de URL se dividen los artistas de las canciones<p>
         * @param ruta
         * @return
         * @throws MalformedURLException 
         */
	public String getArtista(File ruta) throws MalformedURLException {
		URL url = ruta.toURI().toURL();
		String[] partes= url.getPath().split(Pattern.quote("/"));
		String artista =partes[partes.length-3];
		String espacios= artista.replace("%20", " ");
		return espacios;
		
	}
}

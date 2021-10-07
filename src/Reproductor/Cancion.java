package reproductor;

import java.io.File;
import java.io.Serializable;

public class Cancion implements Serializable {
	private String Album;
	private File Ruta;
	private String Artista;	
	private String Nombre;
	
	
	public Cancion(String artista , String album, String nombre,File ruta) {
		super();
		Album = album;
		Ruta = ruta;
		Artista = artista;
		Nombre = nombre;
	}
	
	
	public String getAlbum() {
		return Album;
	}
	
	
	public void setAlbum(String album) {
		Album = album;
	}
	
	
	public File getRuta() {
		return Ruta;
	}
	
	
	public void setRuta(File ruta) {
		Ruta = ruta;
	}
	
	
	public String getArtista() {
		return Artista;
	}
	
	
	public void setArtista(String artista) {
		Artista = artista;
	}
	
	
	public String getNombre() {
		return Nombre;
	}
	
	
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
}
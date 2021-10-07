/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor;

import java.io.File;
import javafx.beans.property.SimpleStringProperty;


public class Cancion2  {
	private SimpleStringProperty Album;
	private SimpleStringProperty Artista;	
	private SimpleStringProperty Nombre;
	
	
	public Cancion2(String nombre , String artista, String album) {
		this.Album=new SimpleStringProperty(album);
                this.Artista=new SimpleStringProperty(artista);
                this.Nombre=new SimpleStringProperty(nombre);
	}

    public String getAlbum() {
        return Album.get();
    }

    public void setAlbum(SimpleStringProperty Album) {
        this.Album = Album;
    }

    public String getArtista() {
        return Artista.get();
    }

    public void setArtista(SimpleStringProperty Artista) {
        this.Artista = Artista;
    }

    public String getNombre() {
        return Nombre.get();
    }

    public void setNombre(SimpleStringProperty Nombre) {
        this.Nombre = Nombre;
    }
	

}
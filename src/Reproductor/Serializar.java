package reproductor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Serializar {
	
	private static ObjectInputStream lectorDeObjetos;
	private static ObjectOutputStream escritorDeObjetos;
/**
 * <p>con el directorio biblioteca de parametro, en base a este, serializa anadiendo cada elemento .mp3 a Biblioteca.dat<p>
 * @param directorio 
 */
	public static void escribir(ArrayList<Cancion> directorio) {
		try {
			escritorDeObjetos = new ObjectOutputStream(new FileOutputStream("Biblioteca.dat"));
			for(Object cancionactual: directorio) {
				escritorDeObjetos.writeObject(cancionactual);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
 /**
  * <p>lee el String nombre de la biblioteca(biblioteca.dat) y lee este y crea un diccionario<p>
  * @param dat 
  */       
	public static void leer(String dat) {
		ArrayList<String> DIR2=new ArrayList<String>();
		try {
			lectorDeObjetos= new ObjectInputStream(new FileInputStream(dat));
			while(true) {
				try {
					Cancion song=(Cancion)lectorDeObjetos.readObject();
					DIR2.add(song.getNombre());
					DIR2.add(song.getRuta().getAbsolutePath());
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
                
		diccionario(DIR2);
		 
                

	}
	public static Hashtable<String,String> DIC;
        /**
         * <p> crea un diccionario, en el cualse ingresa el nombre de una cancion y se devuelve la ruta de dicha cancion<p>
         * @param DIR2
         * @return 
         */
	public static Hashtable<String,String> diccionario(ArrayList<String> DIR2){
		DIC= new Hashtable<String, String>();
		for(int i=0;i<DIR2.size()-1;++i) {
			DIC.put(DIR2.get(i), DIR2.get(i+1));
			
		
		}
		return DIC;
	
	}
	
}

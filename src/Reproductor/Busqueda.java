package Reproductor;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Busqueda {
	public static List archivosvarios;
	public static String[] canciones;
	public static String[] serializado;

	
	public static void Rute(String ruta) throws IOException{
	}
	public static void main(String[] args) throws IOException{
			File contenido= new File("C:\\Users\\lolil\\Desktop\\Soundtracks\\Jonas Myrstrom\\Magicka Vietnam");
			/*String[] listacont= contenido.list();
			for(int i=0;i==listacont.length-1;i++) {
				archivosvarios.add(listacont[i]);
			
		}*/BufferedReader lector= new BufferedReader(new InputStreamReader(new FileInputStream(contenido)));
		String primeralinea = lector.readLine();
		lector.close();
		System.out.println("Hola"+primeralinea);
		
}
}	



				
				
			


package hwtracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

// Esta clase permite realizar las busquedas de los productos mediante técnicas
// de webscraping y una api de amazon, contiene el nombre del producto que se desea
// buscar y una lista de producto que contiene el resultado de la busqueda
public class Busqueda {
	String producto;
	ArrayList<Producto> listaProductos;
	
	// Constructor de busqueda
	public Busqueda(String producto) {
		this.producto = producto;
		listaProductos = new ArrayList<>();
	}
	
	// Metodo para comparar dos producto. Se dice que un producto es mayor que otro
	// si su precio es mayor, se usa para ordenar por precio ascendente los productos
	// obtenidos en la busqueda
	Comparator<Producto> compararPorPrecio = new Comparator<Producto>() {
		@Override
		public int compare(Producto p1, Producto p2) {
			float precio1 = p1.getPrecio();
			float precio2 = p2.getPrecio();
			
			return Float.compare(precio1, precio2);
		}
	};
	
	// Metodo que se encarga de la busqueda de productos mediante webscraping
	// Crea una instancia de Scraping y este llama a su metodo de busqueda correspondiente
	private void busquedaScraping() throws IOException {
		Scraping s = new Scraping(producto);
		s.buscar();
		
		listaProductos.addAll(s.getListaProductos());
	}
	
	// Metodo que se encarga de la busqueda de productos mediante la api de Amazon
	// Crea una instancia de RainforestAPI y este llama a su metodo de busqueda correspondiente
	private void busquedaAmazon() throws IOException, ParserConfigurationException, SAXException {
		RainforestAPI api = new RainforestAPI(producto);
		api.buscar();
		
		listaProductos.addAll(api.getListaProductos());
	}
	
	// Metodo que llama a las dos funciones de busquedas implemetadas, para combinar los productos
	// obtenidos de ambas funciones en un ArrayList que posteriormente se ordena por precio. También
	// se implementa un post-procesado de la busqueda que busca si las palabras de la busqueda se
	// encuentran en el nombre del producto. Se hace para evitar que aparezcan productos no deseados
	public void busqueda() throws IOException, ParserConfigurationException, SAXException {
		busquedaAmazon();
		busquedaScraping();
		
		// Se separan las palabras de la busqueda
		String palabras[] = producto.split(" ");
		
		// Se convierten las palabras de la busqueda a mayusculas. Esto se hace para evitar problemas
		// como que la primera letra este escrita en mayuscula
		for (int i = 0; i < palabras.length; i++) {
			palabras[i] = palabras[i].toUpperCase();
		}
		
		// Se crea un ArrayList de productos auxiliar donde se meteran los productos que cumplen el
		// post-procesado
		ArrayList<Producto> listaAuxiliar = new ArrayList<>();
		
		// Para cada producto del ArrayList original se comprueba si al menos una de las palabras de
		// la busqueda está presente en el nombre del producto (también puesto en mayusculas), si es
		// el caso se añade al ArrayList auxiliar.
		for (int i = 0; i < listaProductos.size(); i++){
			boolean palabraEncontrada = false;
			for(int j = 0; j < palabras.length && !palabraEncontrada; j++) {
				String nombre = listaProductos.get(i).getNombre().toUpperCase();
				if(nombre.contains(palabras[j])) {
					palabraEncontrada = true;
				}
			}
			
			if(palabraEncontrada) {
				palabraEncontrada = false;
				listaAuxiliar.add(listaProductos.get(i));
			}
				
		}
		
		// Se borra el ArrayList original para copiarle los datos del auxiliar que contiene los producto
		// post-procesados
		listaProductos.clear();
		listaProductos = listaAuxiliar;
		
		// Se ordena por precio
		Collections.sort(listaProductos, compararPorPrecio);
	}
	
	// Getter de producto
	public String getProducto() {
		return producto;
	}
	
	// Setter de producto
	public void setProducto(String producto) {
		this.producto = producto;
	}
	
	// Getter de la lista de productos
	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}
	
	// Setter de la lista de productos
	public void setListaProductos(ArrayList<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
}

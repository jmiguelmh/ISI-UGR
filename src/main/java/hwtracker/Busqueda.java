package hwtracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Busqueda {
	String producto;
	ArrayList<Producto> listaProductos;
	
	public Busqueda(String producto) {
		this.producto = producto;
		listaProductos = new ArrayList<>();
	}
	
	Comparator<Producto> compararPorPrecio = new Comparator<Producto>() {
		@Override
		public int compare(Producto p1, Producto p2) {
			float precio1 = p1.getPrecio();
			float precio2 = p2.getPrecio();
			
			return Float.compare(precio1, precio2);
		}
	};
	
	private void busquedaScraping() throws IOException {
		Scraping s = new Scraping(producto);
		s.buscar();
		
		listaProductos.addAll(s.getListaProductos());
	}
	
	
	private void busquedaAmazon() throws IOException, ParserConfigurationException, SAXException {
		RainforestAPI api = new RainforestAPI(producto);
		api.buscar();
		
		listaProductos.addAll(api.getListaProductos());
	}
	
	
	public void busqueda() throws IOException, ParserConfigurationException, SAXException {
		busquedaAmazon();
		busquedaScraping();
		
		String palabras[] = producto.split(" ");
		
		for (int i = 0; i < palabras.length; i++) {
			palabras[i] = palabras[i].toUpperCase();
			System.out.println(palabras[i]);
		}
		
		ArrayList<Producto> listaAuxiliar = new ArrayList<>();
		
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
		
		listaProductos.clear();
		listaProductos = listaAuxiliar;
		
		Collections.sort(listaProductos, compararPorPrecio);
	}
	
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(ArrayList<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
}

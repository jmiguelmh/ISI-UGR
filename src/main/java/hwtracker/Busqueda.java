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
		
		listaProductos = s.getListaProductos();
	}
	
	/*
	private void busquedaAmazon() throws IOException, ParserConfigurationException, SAXException {
		JavaCodeSnippet jcs = new JavaCodeSnippet(producto);
		jcs.buscarAmazon();
		
		listaProductos.addAll(jcs.getListaProductos());	
	}
	*/
	
	public void busqueda() throws IOException, ParserConfigurationException, SAXException {
		busquedaScraping();
		//busquedaAmazon();
		
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
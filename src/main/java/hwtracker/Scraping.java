package hwtracker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

// Esta clase permite realizar la busqueda de productos en las paginas de VSGamers y PCBox
// mediante tecnicas de webscraping (JSoup). Contiene el nombre del producto para ambas tiendas
// ya que cada una los representa de manera diferente y una lista de productos que contendra el
// resultado del webscraping
public class Scraping {
	String producto;
	String productoPCBox;
	ArrayList<Producto> listaProductos;
	
	// Constructor de Scraping
	public Scraping(String producto) {
		// Cambia los espacios por %20, que es como se representa en las URL
		this.producto = producto.replaceAll("\\s+","%20");
		String[] partes = producto.split(" ");
		this.productoPCBox = partes[0];
		listaProductos = new ArrayList<>();
	}
	
	// Metodo que realiza webscraping para buscar productos en PCBox
	private void buscarPcBox() throws IOException {
		// Se crea la URL de busqueda con el nombre del producto
		String url1 = "https://www.pcbox.com/";
		String urlPage = url1 + this.productoPCBox + "?_q=" + this.productoPCBox + "&map=ft";
				  
		System.out.println("Comprobando entradas de: " + urlPage);
		 
		// Se realiza una conexion a la URL, si tiene exito
		if (getStatusConnectionCode(urlPage) == 200) {
			
			// Se obtiene el HTML de la pagina
			Document document = getHtmlDocument(urlPage);
			
			// Se obtienen todos los divs de los productos
			Elements entradas = document.select("div.vtex-search-result-3-x-galleryItem.vtex-search-result-3-x-galleryItem--product-gallery.vtex-search-result-3-x-galleryItem--normal.vtex-search-result-3-x-galleryItem--product-gallery--normal.vtex-search-result-3-x-galleryItem--grid.vtex-search-result-3-x-galleryItem--product-gallery--grid.pa4");
			// Por cada div se extrae el nombre, precio, link e imagen del producto
			for (Element elem : entradas) {
				String titulo = "";
				String precio = "";
				String link = "";
				String urlImagen = "";
		
				titulo = elem.getElementsByClass("vtex-product-summary-2-x-nameContainer flex items-start justify-center pv6").text();
				precio = elem.getElementsByClass("vtex-product-price-1-x-currencyInteger vtex-product-price-1-x-currencyInteger--summary-selling-price").text();
				precio = precio + "." + elem.getElementsByClass("vtex-product-price-1-x-currencyFraction vtex-product-price-1-x-currencyFraction--summary-selling-price").text();

				
				float precioNumerico = 0.0f;
				
				
				try {
					precioNumerico = Float.parseFloat(precio);
				} catch (Exception e) {
					//System.out.println("No se puede convertir en float " + precio);
				}
				
				link = "https://www.pcbox.com" + elem.getElementsByTag("a").attr("href");
				urlImagen = elem.getElementsByTag("img").attr("src");
				
				//System.out.println(link);
				// Se crea el producto y se añade a la lista de productos
				Producto p = new Producto(titulo, link, urlImagen, "pcbox_logo.png", precioNumerico);
				listaProductos.add(p);	
			}               
		}else{
			System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(urlPage));
		}     
	}
	
	// Metodo que realiza webscraping para buscar productos en VSGamers
	private void buscarVSGamers() throws IOException {
		// Se crea la URL de busqueda con el nombre del producto
		String url1 = "https://www.vsgamers.es/search?q=";
		String urlPage = url1 + this.producto;
				  
		System.out.println("Comprobando entradas de: " + urlPage);
		 
		// Se realiza una conexion a la URL, si tiene exito
		if (getStatusConnectionCode(urlPage) == 200) {
			
			// Se obtiene el HTML de la pagina
			Document document = getHtmlDocument(urlPage);
			
			// Se obtienen todos los divs de los productos
			Elements entradas = document.select("div.vs-product-card");
			
			// Por cada div se extrae el nombre, precio, link e imagen del producto
			for (Element elem : entradas) {
				String titulo = "";
				String precio = "";
				String link = "";
				String urlImagen = "";
		
				titulo = elem.getElementsByClass("vs-product-card-title").text();
				precio = elem.getElementsByClass("vs-product-card-prices-price").text();
				
				String[] partes = precio.split(" ");
				
				float precioNumerico = 0.0f;
				StringBuilder precioAux = new StringBuilder(partes[0]);
				
				try {
					precioAux.deleteCharAt(precioAux.indexOf("."));
				} catch (Exception e) {
					// System.out.println("Precio " + precioAux.toString() + ", menor que 1000");
				}
				
				String precioCorrecto = precioAux.toString();
				precioCorrecto = precioCorrecto.replace(",", ".");
				
				try {
					precioNumerico = Float.parseFloat(precioCorrecto);
				} catch (Exception e) {
					//System.out.println("No se puede convertir en float " + partes[0]);
				}
			


				link = "https://www.vsgamers.es" + elem.getElementsByTag("a").attr("href");
				urlImagen = "https://www.vsgamers.es"+elem.getElementsByTag("img").attr("src");
				
				// Se crea el producto y se añade a la lista de productos
				Producto p = new Producto(titulo, link, urlImagen, "vsgamers_logo.png", precioNumerico);
				listaProductos.add(p);	
			}               
		}else{
			System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(urlPage));
		}     
	}
	
	// Metodo que llama a las dos funciones de webscraping definidas anteriormente
	public void buscar() throws IOException {
		buscarPcBox();
		buscarVSGamers();
	}
	
	// Funcion para determinar el codigo de conexion a la pagina web
	public static int getStatusConnectionCode(String url) throws IOException {
        Response response = null;
		
        response =Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        return response.statusCode();
    }
	
	// Funcion que permite obtener el documento HTML de una pagina
	public static Document getHtmlDocument(String url) throws IOException {

        Document doc = null;

        doc = Jsoup.connect(url).get();

        return doc;
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

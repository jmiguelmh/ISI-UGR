package hwtracker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Scraping {
	String producto;

	ArrayList<Producto> listaProductos;
	
	public Scraping(String producto) {
		this.producto = producto.replaceAll("\\s+","%20");;
		listaProductos = new ArrayList<>();
	}
	
	private void buscarPcComponentes() throws IOException {
		String url1 = "https://www.pccomponentes.com/buscar/?query=";
		String urlPage = url1 + this.producto;
				  
		System.out.println("Comprobando entradas de: " + urlPage);
		 
		// Compruebo si me da un 200 al hacer la petición
		if (getStatusConnectionCode(urlPage) == 200) {

			// Obtengo el HTML de la web en un objeto Document2
			Document document = getHtmlDocument(urlPage);
		  
			// Busco todas las historias de meneame que estan dentro de:
			Elements entradas = document.select("div.sc-eMrmnt.hQPnfu.product-card");
		  
			// Parseo cada una de las entradas
			for (Element elem : entradas) {
				String titulo = "";
				String precio = "";
				String link = "";
				String urlImagen = "";
		
				//Examinando el código HTML, se obtiene la información que nos interesa,
				//la cual se encuentra entre etiquetas que tienen como clase estas descripciones
				titulo = elem.getElementsByClass("sc-fYdeDz gTWfKO").text();
				precio = elem.getElementsByClass("sc-hqUeQp cAFWDp").text();

			
				//El link del producto se extrae accediendo a la etiqueta <a> y al atributo href. 
				//Se ha tenido que añadir 'https://www.hipercor.es' a la URL ya que sólo devuelve
				//la parte restante de la URL.
				link = elem.getElementsByTag("a").attr("href");
				urlImagen = "https:"+elem.getElementsByTag("img").attr("src");
				
				Producto p = new Producto(titulo, link, urlImagen, Float.parseFloat(precio));
				listaProductos.add(p);	
			}               
		}else{
			System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(urlPage));
		}     
	}
	
	private void buscarVSGamers() throws IOException {
		String url1 = "https://www.vsgamers.es/search?q=";
		String urlPage = url1 + this.producto;
				  
		System.out.println("Comprobando entradas de: " + urlPage);
		 
		if (getStatusConnectionCode(urlPage) == 200) {

			Document document = getHtmlDocument(urlPage);

			Elements entradas = document.select("div.vs-product-card");
		  
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
					System.out.println("Precio " + precioAux.toString() + ", menor que 1000");
				}
				
				String precioCorrecto = precioAux.toString();
				precioCorrecto = precioCorrecto.replace(",", ".");
				
				try {
					precioNumerico = Float.parseFloat(precioCorrecto);
				} catch (Exception e) {
					System.out.println("No se puede convertir en float " + partes[0]);
				}
			


				link = "https://www.vsgamers.es" + elem.getElementsByTag("a").attr("href");
				urlImagen = "https:"+elem.getElementsByTag("img").attr("src");
				
				Producto p = new Producto(titulo, link, urlImagen, precioNumerico);
				listaProductos.add(p);	
			}               
		}else{
			System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(urlPage));
		}     
	}
	
	public void buscar() throws IOException {
		// buscarPcComponentes();
		buscarVSGamers();
	}
	
	public static int getStatusConnectionCode(String url) throws IOException {
        Response response = null;
		
        response =Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        return response.statusCode();
    }
	
	public static Document getHtmlDocument(String url) throws IOException {

        Document doc = null;

        doc = Jsoup.connect(url).get();

        return doc;
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

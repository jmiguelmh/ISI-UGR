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
		Document document = getHtmlDocument(urlPage);
		System.out.println(document);
		 
		// Compruebo si me da un 200 al hacer la petición
		if (getStatusConnectionCode(urlPage) == 200) {

			// Obtengo el HTML de la web en un objeto Document2
			document = getHtmlDocument(urlPage);
		  
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
	
	public void buscar() throws IOException {
		buscarPcComponentes();
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

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
	String productoPCBox;
	ArrayList<Producto> listaProductos;
	
	public Scraping(String producto) {
		this.producto = producto.replaceAll("\\s+","%20");
		String[] partes = producto.split(" ");
		this.productoPCBox = partes[0];
		listaProductos = new ArrayList<>();
	}
	
	private void buscarPcBox() throws IOException {
		String url1 = "https://www.pcbox.com/";
		String urlPage = url1 + this.productoPCBox + "?_q=" + this.productoPCBox + "&map=ft";
				  
		System.out.println("Comprobando entradas de: " + urlPage);
		 
		if (getStatusConnectionCode(urlPage) == 200) {

			Document document = getHtmlDocument(urlPage);

			Elements entradas = document.select("div.vtex-search-result-3-x-galleryItem.vtex-search-result-3-x-galleryItem--product-gallery.vtex-search-result-3-x-galleryItem--normal.vtex-search-result-3-x-galleryItem--product-gallery--normal.vtex-search-result-3-x-galleryItem--grid.vtex-search-result-3-x-galleryItem--product-gallery--grid.pa4");
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
				Producto p = new Producto(titulo, link, urlImagen, "pcbox_logo.png", precioNumerico);
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
				
				Producto p = new Producto(titulo, link, urlImagen, "vsgamers_logo.png", precioNumerico);
				listaProductos.add(p);	
			}               
		}else{
			System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(urlPage));
		}     
	}
	
	public void buscar() throws IOException {
		buscarPcBox();
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

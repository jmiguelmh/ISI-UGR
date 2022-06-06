package hwtracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Esta clase permite la busqueda de productos en Amazon mediante el uso de una api
// La API utilizada en este proyecto se trata de RainforestAPI, que incluye una version
// de prueba en la que se permiten realizar un número limitado de consultas, para la
// entrega del proyecto quedan unas 40 peticiones restantes más o menos
// La clase contiene el nombre del producto a buscar y una lista de productos que contendra
// los productos que se obtengan en la busqueda
public class RainforestAPI {
	String producto;
	ArrayList<Producto> listaProductos;
	
	// Constructor de RainforestAPI
	public RainforestAPI(String producto) {
		this.producto = producto.replaceAll("\\s+","+");
		this.listaProductos = new ArrayList<>();
	}
	
	// Metodo de la clase que realiza la peticion a la API y obtiene los productos
	public void buscar() {
		// Se crea el enlace para realizar la peticion de busqueda
		String urlString = "https://api.rainforestapi.com/request?api_key=4173A4D3BBA44C9D86FDA4D6F6BCA1B8&type=search&amazon_domain=amazon.es&search_term=";
		urlString = urlString + this.producto + "&category_id=937912031";
		
		// Se realiza una peticion GET para obtener un objeto JSON con los productos encontrados
		try {
			// Se establece la conexion
			URL url = new URL(urlString);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			
			// Tipo de peticion: GET
			// Se pone un timeout de 5 segundos
			conexion.setRequestMethod("GET");
			conexion.setConnectTimeout(5000);
			conexion.setReadTimeout(5000);
			
			// Codigo de conexion
			int status = conexion.getResponseCode();
			System.out.println("Codigo conexion RainforestAPI: " + status);
			
			// Si tiene exito la conexion
			if (status == 200) {
				// Leemos la peticion recibida y se almacena en un string
				BufferedReader reader;
				String line;
				StringBuffer responseContent = new StringBuffer();
				
				reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
				
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
				
				// A través de la peticion convertida en string se construye un objeto JSON
				JSONObject json = new JSONObject(responseContent.toString());
				
				// Se accede al array del JSON que contiene los productos de la busqueda
				JSONArray productos = json.getJSONArray("search_results");
				
				// Para cada producto en el array, creamos un objeto de tipo producto con sus valores correspondientes
				for(int i = 0; i < productos.length(); i++) {
					JSONObject obj = productos.getJSONObject(i);
					String nombre = obj.getString("title");
					String urlProducto = obj.getString("link");
					String urlImagen = obj.getString("image");
					
					if (obj.has("price")) {
						obj = obj.getJSONObject("price");
						float precio = obj.getFloat("value");
						Producto producto = new Producto(nombre, urlProducto, urlImagen, "amazon_logo.png", precio);
						this.listaProductos.add(producto);
					}
				}
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

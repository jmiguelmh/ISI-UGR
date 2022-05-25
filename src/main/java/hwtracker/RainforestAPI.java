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

public class RainforestAPI {
	String producto;
	ArrayList<Producto> listaProductos;
	
	public RainforestAPI(String producto) {
		this.producto = producto.replaceAll("\\s+","+");
		this.listaProductos = new ArrayList<>();
	}
	
	public void buscar() {
		String urlString = "https://api.rainforestapi.com/request?api_key=5B5D034EC4374775BF97F29B615AFA5D&type=search&amazon_domain=amazon.es&search_term=";
		urlString = urlString + this.producto + "&category_id=937912031";
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			
			conexion.setRequestMethod("GET");
			conexion.setConnectTimeout(5000);
			conexion.setReadTimeout(5000);
			
			int status = conexion.getResponseCode();
			System.out.println("Codigo conexion: " + status);
			
			if (status == 200) {
				BufferedReader reader;
				String line;
				StringBuffer responseContent = new StringBuffer();
				
				reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
				
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
				
				JSONObject json = new JSONObject(responseContent.toString());
				
				JSONArray productos = json.getJSONArray("search_results");
				
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

package hwtracker;

// Esta clase se utiliza para representar un producto en la página web
// Contiene el nombre del producto, url de la tienda web, url de la imagen
// nombre de la tienda donde se vente y precio
public class Producto {
	private String nombre;
	private String urlProducto;
	private String urlImagen;
	private String tienda;
	private float precio;
	
	// Constructor de la clase Producto
	public Producto(String nombre, String urlProducto, String urlImagen, String tienda, float precio) {
		this.nombre = nombre;
		this.urlProducto = urlProducto;
		this.urlImagen = urlImagen;
		this.tienda = tienda;
		this.precio = precio;
	}
	
	// Getter de nombre
	public String getNombre() {
		return nombre;
	}
	
	// Setter de nombre
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// Getter de la url del producto
	public String getUrlProducto() {
		return urlProducto;
	}
	
	// Setter de la url del producto
	public void setUrlProducto(String urlProducto) {
		this.urlProducto = urlProducto;
	}
	
	// Getter de la url de la imagen del producto
	public String getUrlImagen() {
		return urlImagen;
	}
	
	// Setter de la url de la imagen del producto
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	
	// Getter de la tienda
	public String getTienda() {
		return tienda;
	}
	
	// Setter de la tienda
	public void setTienda(String tienda) {
		this.tienda = tienda;
	}
	
	// Getter del precio
	public float getPrecio() {
		return precio;
	}
	
	// Setter del precio
	public void setPrecio(float precio) {
		this.precio = precio;
	}
}

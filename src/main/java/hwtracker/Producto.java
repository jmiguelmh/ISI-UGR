package hwtracker;

public class Producto {
	private String nombre;
	private String urlProducto;
	private String urlImagen;
	private String tienda;
	private float precio;
	
	public Producto(String nombre, String urlProducto, String urlImagen, String tienda, float precio) {
		this.nombre = nombre;
		this.urlProducto = urlProducto;
		this.urlImagen = urlImagen;
		this.tienda = tienda;
		this.precio = precio;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getUrlProducto() {
		return urlProducto;
	}
	
	public void setUrlProducto(String urlProducto) {
		this.urlProducto = urlProducto;
	}
	
	public String getUrlImagen() {
		return urlImagen;
	}
	
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	
	public String getTienda() {
		return tienda;
	}

	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

	public float getPrecio() {
		return precio;
	}
	
	public void setPrecio(float precio) {
		this.precio = precio;
	}
}

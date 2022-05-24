package hwtracker;

public class Producto {
	private String nombre;
	private String urlProducto;
	private String urlImagen;
	private float precio;
	
	public Producto(String nombre, String urlProducto, String urlImagen, float precio) {
		this.nombre = nombre;
		this.urlProducto = urlProducto;
		this.urlImagen = urlImagen;
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
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
}

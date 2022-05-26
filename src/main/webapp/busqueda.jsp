<%@page import="java.util.ArrayList"%>
<%@page import="hwtracker.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="style.css" />
    <title>HWTracker</title>
    
  </head>

  <body>
    <div class="header">
    	<h1>HWTracker</h1>
    </div>
    
    <form action="busqueda.jsp" method="get" class="search-form">
		<fieldset>
			<legend>Búsqueda</legend>

			<input	type="search" id="busqueda" name="producto" class="search-box" autofocus>
			<input type="submit" class="search-button" value="Buscar">
		</fieldset>
	</form>
	
	<h1>Productos</h1>
	
	<ul class="lista-productos">
		<%
			String producto = request.getParameter("producto");
		if (!producto.equals(""))
		{
			Busqueda b = new Busqueda(producto);
			b.busqueda();
			
			ArrayList<Producto> listaProductos = b.getListaProductos();
			
			for(int i=0 ; i<listaProductos.size() ; i++){
				Producto p = listaProductos.get(i);
				
				// System.out.println("Enlace: " + p.getUrlProducto());
				String imgProducto = "<img src=\"" + p.getUrlImagen() + "\" width=\"50\" height=\"50\"/>";
				String imgTienda = "\"><img src=\"./imgs/" + p.getTienda() + "\" height=\"50\" alt=\"Enlace\"/>";
				//System.out.println(listaProductos.size());
				out.println("<li><h2>" + imgProducto + "\t" + p.getNombre() + " Precio: " + p.getPrecio()+ " €\t" + "<a href=\"" + p.getUrlProducto() + imgTienda + "</a>" + "</h2></li>");
			}
		} else {
			out.println("<h3>Introduzca un producto para buscar</h3>");
		}
		%>
	</ul>
	
  </body>
</html>
<%@page import="java.util.ArrayList"%>
<%@page import="hwtracker.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="../style.css" />
    <title>HWTracker</title>
    
  </head>

  <body>
    <div class="header">
    	<h1>Procesadores</h1>
    </div>
    
    <form action="busqueda.jsp" method="get" class="search-form">
		<fieldset>
			<legend>Búsqueda</legend>

			<input	type="search" id="busqueda" name="producto" class="search-box" autofocus>
			<input type="submit" class="search-button" value="Buscar">

		</fieldset>
	</form>
	
	<h2>Productos</h2>
	
	<ul class="lista-productos">
		<%
			String producto = request.getParameter("producto");
			Busqueda b = new Busqueda(producto);
			b.busqueda();
			
			ArrayList<Producto> listaProductos = b.getListaProductos();
			
			for(int i=0 ; i<listaProductos.size() ; i++){
				Producto p = listaProductos.get(i);
				
				// System.out.println("Enlace: " + p.getUrlProducto());
				out.println("<li><h3>" + "Nombre: " + p.getNombre() + " Precio: " + p.getPrecio()+ " €" + "<a href=" + p.getUrlProducto() + ">Enlace</a>" + "</h3></li>");
			}
		%>
	</ul>
	
  </body>
</html>
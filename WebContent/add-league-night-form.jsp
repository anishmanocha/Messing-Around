<%@ page import= "java.util.*, com.anishmanocha.*" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>TTManager</title>


</head>


<body>

<div id= "wrapper">

<div id= "header">

	

</div>

</div>

<div id="container">

<h2>Add League Night</h2>

<form action= "LeagueNightControllerServlet" method= "GET">


<input type= "hidden" name= "command" value= "add">



<table>
	<tbody>
		<tr>
			<td><label>Date (yyyy-mm-dd):</label></td>
			<td><input type= "text" name= "date"></td>
		</tr>
		
		<tr>
			<td><input type= "submit" value= "Save" class="save"></td>
		</tr>

	</tbody>

</table>

</form>

<div style= "clear: both;">



</div>

</div>

</body>
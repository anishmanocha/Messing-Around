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

<h2>Add Player</h2>

<form action= "PlayerControllerServlet" method= "GET">


<input type= "hidden" name= "command" value= "add">



<table>
	<tbody>
		<tr>
		
			<td><label>First name:</label></td>
			<td><input type= "text" name= "firstName"></td>
		</tr>
		<tr>
		
			<td><label>Last name:</label></td>
			<td><input type= "text" name= "lastName"></td>
		</tr>
		
		<tr>
		
			<td><label>Rating:</label></td>
			<td><input type= "text" name= "rating"></td>
		</tr>
		
		<tr>
		
			<td><label>Email:</label></td>
			<td><input type= "text" name= "email"></td>
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
</html>
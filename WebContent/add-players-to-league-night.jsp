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

	<h2>Add Players To League Night</h2>

</div>


<div id="container">

<form action= "LeagueNightControllerServlet" method= "GET">


<input type= "hidden" name= "command" value= "addPlayersToLeagueNight">


<table>
	<tbody>
		<tr>
			<td><label>Date (yyyy-mm-dd):</label></td>
			<td><input type= "text" name= "dateOfLeagueNight" value= "${dateForLeagueNight}" readonly></td>
		</tr>
		
		<c:forEach var= "player" items= "${listOfPlayers}">

		<tr>
			<td><input type= "checkbox" name= "selectedPlayer" value= "${player.playerId}"></td>
			<td> ${player.firstName} </td>
			<td>${player.lastName}</td>
			<td>${player.email}</td>
			<td>${player.rating}</td>
				
		</tr>
			
			

		</c:forEach>
		
			<tr>
		
				<td><input type= "submit" value= "Save" class="save"></td>
			
			</tr>

	</tbody>

</table>

</form>

<div style= "clear: both;">

</div>

</div>

<a href= "LeagueNightControllerServlet">Back to League Night List</a>
</div>

</body>
</html>
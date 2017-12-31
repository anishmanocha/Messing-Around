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

	<h2>Player Roster</h2>

</div>

<div id="container">

<div id= "content">
<input type= "button" value= "Add Player" onclick= "window.location.href= 'add-player-form.jsp'; return false;" class="add-student-button">
<table>

<tr>

<th>First Name</th>
<th>Last Name</th>
<th>Email</th>
<th>Rating</th>
<th>Action</th>

</tr>

<c:forEach var= "player" items= "${listOfPlayers}">

	<c:url var= "templink" value= "PlayerControllerServlet">
		<c:param name= "command" value="Load" />
		<c:param name= "playerId" value= "${player.playerId}" />
	</c:url>
	
	<c:url var= "deletelink" value= "PlayerControllerServlet">
		<c:param name= "command" value="delete" />
		<c:param name= "playerId" value= "${player.playerId}" />
	</c:url>


	<tr>
	<td> ${player.firstName} </td>
	<td>${player.lastName}</td>
	<td>${player.email}</td>
	<td>${player.rating}</td>
	<td><a href="${templink}">Update</a><span> | </span><a href="${deletelink}">Delete</a></td>
	</tr>

</c:forEach>




</table>

	<c:url var= "redirectLink" value= "PlayerControllerServlet">
		<c:param name= "command" value="redirect" />
	</c:url>

<a href= "${redirectLink}">List of League Nights</a>

</div>
</div>
</div>




</body>
</html>
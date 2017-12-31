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

	<h2>List of Players in Round Robin Pools</h2>

</div>

<div id="container">

<div id= "content">


	
<table>
		
	<tr>

		<th>First Name</th>
		<th>Last Name</th>
		<th>Rating</th>
		<th>Email</th>
			
	</tr>
		
<c:forEach var= "playerInLeagueNight" items= "${listOfPlayersInLeagueNight}">

	<tr>
		<td> ${playerInLeagueNight.firstName} </td>
		<td> ${playerInLeagueNight.lastName} </td>
		<td> ${playerInLeagueNight.rating} </td>
		<td> ${playerInLeagueNight.email} </td>
	
	</tr>
	
	

</c:forEach>


</table>

</div>

</div>


<a href= "PlayerControllerServlet">Back to Player List</a>
<br>



</div>

</body>
</html>
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

	<h2>List of League Nights</h2>

</div>

<div id="container">

<div id= "content">
	<input type= "button" value= "Add League Night" onclick= "window.location.href= 'add-league-night-form.jsp'; return false;">
	
	<table>
		
		<tr>

		<th>Date</th>
		<th>Action</th>
			
		</tr>
		
<c:forEach var= "LeagueNight" items= "${listOfLeagueNights}"> 

	<c:url var= "templink" value= "LeagueNightControllerServlet">
		<c:param name= "command" value="Load" />
		<c:param name= "playerId" value= "${LeagueNight.leagueNightId}" />
	</c:url>
	
	<c:url var= "deletelink" value= "LeagueNightControllerServlet">
		<c:param name= "command" value="delete" />
		<c:param name= "playerId" value= "${LeagueNight.leagueNightId}" />
	</c:url>
	
	<c:url var= "getlistofplayerslink" value= "PlayerControllerServlet">
		<c:param name= "command" value="getListOfPlayers" />
		<c:param name= "playerId" value= "${LeagueNight.leagueNightId}" />
		<c:param name= "leagueNightDate" value= "${LeagueNight.leagueDate}" />
	</c:url>
	
	<c:url var= "getlistofplayersInLeagueNight" value= "LeagueNightControllerServlet">
		<c:param name= "command" value="getListOfPlayersInLeagueNight" />
		<c:param name= "leagueNightId" value= "${LeagueNight.leagueNightId}" />
		<c:param name= "leagueNightDate" value= "${LeagueNight.leagueDate}" />
	</c:url>
	
	<c:url var= "getlistofRoundRobinPools" value= "RoundRobinControllerServlet">
		<c:param name= "command" value="getListOfRoundRobinPools" />
		<c:param name= "leagueNightId" value= "${LeagueNight.leagueNightId}" />
		<c:param name= "leagueNightDate" value= "${LeagueNight.leagueDate}" />
	</c:url>
	
	<c:url var= "redirectToRoundRobinPool" value= "RoundRobinControllerServlet">
		<c:param name= "command" value="redirectToRoundRobinPool" />
		<c:param name= "leagueNightId" value= "${LeagueNight.leagueNightId}" />
		<c:param name= "leagueNightDate" value= "${LeagueNight.leagueDate}" />
	</c:url>
	


	<tr>
	<td> ${LeagueNight.leagueDate} </td>
	<td><a href="${templink}">Update</a><span> | </span><a href="${deletelink}">Delete</a><span> | </span><a href="${getlistofplayerslink}">Add Players To League Night</a><span> | </span><a href="${getlistofplayersInLeagueNight}">View Players in League Night</a><span> | </span><a href="${redirectToRoundRobinPool}">Add Round Robin Pool</a><span> | </span><a href="${getlistofRoundRobinPools}">View Round Robin Pools</a> </td>
	</tr>
	
	

</c:forEach>


</table>

</div>

</div>

<a href= "PlayerControllerServlet">Back to Player List</a>
</div>

</body>
</html>
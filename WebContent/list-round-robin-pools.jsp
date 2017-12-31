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

	<h2>List of Round Robin Pools</h2>

</div>

<div id="container">

<div id= "content">
	
	
<c:set var = "i" value = "0"/>
<c:set var = "j" value = "${indexPlayersPerGroup}"/>
		
<c:forEach var= "RoundRobinPool" items= "${listOfRoundRobinPools}">


<h3>Group #: ${RoundRobinPool.idRoundRobinPool}</h3>

	<table>
	
		
		
		<tr>

		<th>First Name</th>
		<th>Last Name</th>
		<th>Rating</th>

			
		</tr>

	<c:forEach var= "Player" items= "${listOfPlayersToSendToFrontEnd}" begin="${i}" end="${j}" step="1"> 	


		<tr>
			<td>${Player.firstName}</td>
			<td>${Player.lastName}</td>
			<td>${Player.rating}</td>
			
	
		</tr>
	

	</c:forEach>	
	
	</table>
	
	<c:set var = "i" value = "${i+ playersPerGroup}"/>
	<c:set var = "j" value = "${j+ playersPerGroup}"/>
	
	

</c:forEach>

<c:url var= "generatePairings" value= "RoundRobinControllerServlet">
		<c:param name= "command" value="generatePairings" />
		<c:param name= "listOfRoundRobinPools" value= "${listOfRoundRobinPools}" />
		<c:param name= "playersPerGroup" value= "${playersPerGroup}" />
</c:url> 


</div>

</div>

<form action= "RoundRobinControllerServlet" method= "GET">


<input type= "hidden" name= "leagueNightId" value= "${leagueNightId}">

<input type= "hidden" name= "command" value= "generateRoundRobinPools">



<table>
	<tbody>
		<tr>
		
			<td><label>How many individuals would you like in each group:</label></td>
			<td><input type= "text" name= "playersPerPool"></td>
		</tr>
		
		<tr>
		
			<td><input type= "submit" value= "Save" class="save"></td>
			
		</tr>
		

	</tbody>

</table>

</form>

<form action= "RoundRobinControllerServlet" method= "GET">

<input type= "hidden" name= "leagueNightId" value= "${leagueNightId}">

<input type= "hidden" name= "command" value= "generatePairings">



<table>
	<tbody>
		<tr>
		
			<td><input type= "submit" value= "Generate Pairings"></td>
			
		</tr>
		

	</tbody>

</table>

</form>





<a href= "PlayerControllerServlet">Back to Player List</a>

</div>

</body>
</html>
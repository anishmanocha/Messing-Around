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

	<h2>Pairings in Round Robin Pools</h2>

</div>

<div id="container">

<div id= "content">
	
	
<c:set var = "i" value = "0"/>
<c:set var = "j" value = "${dividingNumber}"/>
<c:set var = "k" value = "${numberOfRoundRobinPools}"/>

		
<c:forEach var= "RoundRobinPool" begin="0" end="${k-1}" step="1">
	<table>
	
		
		
		<tr>

		<th>First Name</th>
		<th>Last Name</th>
		<th>Rating</th>

			
		</tr>

	<c:forEach var= "Player" items= "${listOfPlayers}" begin="${i}" end="${j-1}" step="2" varStatus="status"> 	


		<tr>
			<td>${Player.firstName}</td>
			<td>${Player.lastName}</td>
			<td>${Player.rating}</td>
			
		</tr>
		
		
		<span>versus</span>
		
		<tr>
			<td>${Player.firstName[status.index]}</td>
			<td>${Player.lastName[status.index]}</td>
			<td>${Player.rating[status.index]}</td>
			
		</tr>
	

	</c:forEach>	
	
	</table>
	
	<c:set var = "i" value = "${i+ dividingNumber}"/>
	<c:set var = "j" value = "${j+ dividingNumber}"/>
	
	</c:forEach>	






</div>

</div>

<a href= "PlayerControllerServlet">Back to Player List</a>

</div>

</body>
</html>
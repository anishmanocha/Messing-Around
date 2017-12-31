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

	<h2>Add Round Pool</h2>

</div>


<div id="container">

<form action= "RoundRobinControllerServlet" method= "GET">


<input type= "hidden" name= "command" value= "addRoundRobinPool">


<input type= "hidden" name= "leagueNightId" value= "${leagueNightId}">

<table>
	<tbody>
		<tr>
			<td><label>How many round robin pools would you like to add?</label></td>
			<td><input type= "text" name= "numberOfRoundRobinPools"  ></td>
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

<a href= "LeagueNightControllerServlet">Back to League Night List</a>
</div>

</body>
</html>
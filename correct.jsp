<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page import="pkg.GameNumber"%>


<!DOCTYPE html>
<jsp:useBean id="game" class="pkg.GameNumber" scope="session"/>
<html>
	<head>
		<meta charset="UTF-8">
		<title> The Secret Number Game-Game Over </title>
		<link href="css/style.css" rel = "stylesheet"/>
	</head>
	<body>
		<h1> The Secret Number Game - Game Over, Well Played!</h1>
		<hr>
		<% if(request.getAttribute("winMsg") != null){ %>
		<p> <% out.println(request.getAttribute("winMsg"));%> </p> <br>
		<%}%>
		
		<% if(request.getAttribute("errorMessage") != null){ %>
		<p class="mesg"><% out.println(request.getAttribute("errorMessage"));%> </p></<br>
		<%}%>
		
		
		<% if(request.getAttribute("gameovermsg") != null){ %>		
			<p> <%	out.println(request.getAttribute("gameovermsg"));%></p> <br>
		<%}%>
		
		<p> <a href="home.jsp"> Play Again! </a> </p>
		<%session.invalidate();%> <!-- Remove session as game is over -->
	</body>
	<footer> 
		&copy; All rights reserved , Copyright University of NewCastle-Callaghan 2022 <br/>
		Akshata Dhuraji, SENG2050, University of NewCastle, <u>Email:c3309266@uon.edu.au</u>
	</footer>
</html>
		
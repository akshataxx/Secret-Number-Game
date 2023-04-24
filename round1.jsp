<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page import="pkg.GameNumber"%>

<% 
	int minimum = 1;
	int maximum = 11;
%>

<!DOCTYPE html>
<jsp:useBean id="game" class="pkg.GameNumber" scope="session"/>
<html>
	<head>
		<meta charset="UTF-8">
		<title> The Secret Number Game </title>
		<link href="css/style.css" rel = "stylesheet"/>
		<script type="text/javascript">    // disable user to go back to the game to change any case value.
            function disableBack() { window.history.forward(); }
            setTimeout("disableBack()", 0);
            window.onunload = function () { null };
			
			function chkGuess(){
				var choice1= document.forms["round"]["guess"].value;
				if (isNaN(choice1)) { alert("Must input numbers"); return false;}
				if(choice1 < 1){ alert("Must input greater than 0");return false;}
				if(choice1>11){alert("Must input smaller than 12"); return false;}
			}
			
        </script>
	</head>
	<body>
	<h1> Welcome <jsp:getProperty name="game" property="userName" /> to The Best Secret Number Game!!!</h1>
	<hr>
	
	<% if((request.getAttribute("msg") == null) || (request.getAttribute("outofrange")!= null)){ %>
		<!-- Save game button -->
		<p><% if(request.getAttribute("outofrange")!= null){out.println(request.getAttribute("outofrange"));}%></p> 
		<p> Secret number is generated </p><br>
		<p> 1<sup>st</sup> Round  - Please guess a number between <%= minimum %> and <%= maximum %> </p><br>
		<div id="right">
		<form action="${pageContext.request.contextPath}/GameServlet" onsubmit="alert('<%=request.getAttribute("saveMessage")%>');" method="get">
			<input type="submit" name="save" value="Save Game" /><br>
		</form>
		</div>
		<div class="form-style-2">
		<form name="round" id= "round" action="${pageContext.request.contextPath}/GameServlet"  method="Get">
		<br><br><br><br>
			<fieldset>
			<legend>Round-1</legend>	
			<label>	<span>Enter your number:<span class="required">*</span></span></label>
			<input type="text" class="input-field" name="guess" oninput="chkGuess()" /><br><br><br>
			<input type="submit" name="round1" value="Submit" /><br>
			</fieldset>
	<%}%>	
	
	
	<% if((request.getAttribute("msg") != null) && request.getAttribute("gameovermsg") == null){ %>
		<p> 1<sup>st</sup> Round -Your choosen number is : <% out.println(request.getAttribute("num"));%></p> <br>
		<p><%out.println(request.getAttribute("msg"));
			System.out.println("from JSP msg : "+ (String)request.getAttribute("msg"));%></p> 
		<form name="round" id= "round" action="${pageContext.request.contextPath}/GameServlet"  method="Get">
		<div class="form-style-2">
			<input type="submit" name="Accept" value="Accept" /> &emsp;
			<input type="submit" name="round1Continue" value="Continue" />
	<%}%>
	</form>
	</div>
	</body>
	<footer> 
		&copy; All rights reserved , Copyright University of NewCastle-Callaghan 2022 <br/>
		Akshata Dhuraji, SENG2050, University of NewCastle, <u>Email:c3309266@uon.edu.au</u>
	</footer>
</html>
	
	
		
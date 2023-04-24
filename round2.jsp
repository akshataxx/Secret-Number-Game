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
			
			function chkGuess1(){var x= document.forms["round"]["guess1"].value;
				if (isNaN(x)) {	alert("Must input numbers");return false;}
				if(x < 1){alert("Must input greater than 0");return false;}
				if(x>11){alert("Must input smaller than 12");return false;}
			}
			
			function chkGuess2(){var y= document.forms["round"]["guess2"].value;
				if (isNaN(y)) { alert("Must input numbers"); return false; }
				if(y < 1){ alert("Must input greater than 0");return false;}
				if(y > 11){ alert("Must input smaller than 12");return false;}
			}
			
			function validateForm() {var choice2 = document.forms["round"]["guess1"].value;
				var choice3 = document.forms["round"]["guess2"].value;
				if (choice2 == choice3){alert "You have entered duplicate numbers...pls. enter unique numbers here"); return false;}
			}
			
        </script>
	</head>
	<body>
	<h1> Congratulations <jsp:getProperty name="game" property="userName" /> You Entered Round 2 of the Best Secret Number Game   </h1>
	<hr>
		<% if((request.getAttribute("msg") == null) || (request.getAttribute("outofrange")!= null) || (request.getAttribute("note")!= null)){ %>
			<p><% if(request.getAttribute("outofrange")!= null){out.println(request.getAttribute("outofrange"));}%></p> <br>
			<p><% if(request.getAttribute("note")!= null){out.println(request.getAttribute("note"));}%></p> <br>
			<p> 2<sup>nd</sup> Round - Take 2 Guesses Between <%= minimum %> and <%= maximum %> </p>
			<div id="right">
			<form action="${pageContext.request.contextPath}/GameServlet" onsubmit="alert('<%=request.getAttribute("saveMessage")%>');" method="get">
				<input type="submit" name="save" value="Save Game" /><br>
			</form>
			</div>
			<div class="form-style-2">
			<form name="round" id= "round" action="${pageContext.request.contextPath}/GameServlet" onsubmit="return validateForm()" method="Get">
			<br><br><br><br>
			<fieldset>
			<legend>Round-2</legend>
				<label> Enter 1<sup>st</sup> number:</label> <input type="text" name="guess1" oninput="chkGuess1()" /> <br><br>
				
				<label> Enter 2<sup>nd</sup> number:</label> <input type="text" name="guess2" oninput="chkGuess2()" /> <br><br><br>
			
				<input type="submit" name="round2" value="Submit"  /><br>
			</fieldset>
		<%}%>	
		
		<% if((request.getAttribute("msg") != null)&& request.getAttribute("gameovermsg") == null){ %>
			<p> 2<sup>nd</sup> Round - Your choosen numbers are : <% out.println(request.getAttribute("num1"));%> , <%out.println(request.getAttribute("num2"));%></p> <br>
		    <p><%out.println(request.getAttribute("msg"));
			System.out.println("from JSP msg : "+ (String)request.getAttribute("msg"));%></p> 
			<div class="form-style-2">
			<form name="round" id= "round" action="${pageContext.request.contextPath}/GameServlet"  method="Get">
				<input type="submit" name="Accept" value="Accept" /> &emsp;
				<input type="submit" name="round2Continue" value="Continue" />
		<%}%>
	
	</form>
	</div>
	</body>
	<footer> 
		&copy; All rights reserved , Copyright University of NewCastle-Callaghan 2022 <br/>
		Akshata Dhuraji, SENG2050, University of NewCastle, <u>Email:c3309266@uon.edu.au</u>
	</footer>
</html>
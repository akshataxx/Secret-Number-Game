<!DOCTYPE html>
    <head>
        <title>GameStart</title>
        <link href="css/style.css" rel = "stylesheet"/>
		<script>
			function validateForm() {
				var userName = document.forms["home"]["userName"].value;
	
				if (userName == null || userName == "") { //checks user name is not null
					alert("Please fill out this field");
					return false;
				}
			}
		</script>
		
    </head>

    <body>
        <h1>Secret Number Game </h1>
		<hr>
		<div class="form-style-2">
		<div class="form-style-2-heading">Are you ready to play???</div>
        <form action="${pageContext.request.contextPath}/GameServlet" name= "home" onsubmit= "return validateForm()" method="Get">
            <fieldset>
			<legend>Start Game</legend>			
            <label for="username"><span>Name<span class="required">*</span></span>
            <input type="text" class="input-field" id="username" name="username" required  placeholder="UserName"><br><br></label>


            <input type="submit" name="newGame" value="New Game" /> &emsp;
            <input type="submit" name="loadGame" value="LoadGame" /> <br>
			</fieldset>
			
		<!-- Show messge -->
		<p class="mesg">
			<% if(request.getAttribute("errorMessage") != null){ 
			  	out.println(request.getAttribute("errorMessage"));
			}
			%>
		</p>
            
        </form>
        </div>

    </body>
	<footer> 
		&copy; All rights reserved , Copyright University of NewCastle-Callaghan 2022 <br/>
		Akshata Dhuraji, SENG2050, University of NewCastle, <u>Email:c3309266@uon.edu.au</u>
	</footer>
</html>
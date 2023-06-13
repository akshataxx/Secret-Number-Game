# Secret-Number-Game
Secret Number Game
• The application’s structure, i.e. relationships among objects etc. 
Game has following components:
1) Servlet - GameServlet: Controls the display of JSP and carrys out the processing via calling respective bean functions from GameNumber class.
2) SessionListener: Saves and Invalidates the session when the session is destroyed.
2) Bean - GameNumber: holds game structure as variables along with setters and getters and provides game functionalities.
3) JSP's:
	a) home.jsp: Home page to start the game, it will ask for user name. User name is used to identify game user.
	b) round1.jsp: Gets the number for round1 as per assignment specification.
	c) round2.jsp: Gets the numbers for round2 as per assignment specification.
	d) round3.jsp: Gets the numbers for round3 as per assignment specification.
	e) round4.jsp: Gets the numbers for round4 as per assignment specification.
	f) correct.jsp: This page is called when user accepts the offer or when the game has ended.
4) web.xml - defines mappings between URL paths and the servlets that handle requests with those paths
5) log.ser - saves game data

************************************************************************************************************************

• What is the purpose of each of your objects? 
1) GameServlet - is used to control the display of jsp based on the game stage e.g. during round1 it directs the user to round1 page. It also controls the processing of the game and ensures user is updated with the right messages.
2) GameNumber - Bean class, defines the game structure and processing functions such as computation of offer, comparision of numbers entered in the round with target number, saving data to file, searching and loading data from file.
3) JSP -
	a) home.jsp - as per the name takes user to home page of the game where user name is accepted.
	b) round1.jsp - shows a screen to capture the round1 number, validates the number entered by the user to ensure it meets assignment specs of between 1 to 11 and no repeatetion of previously entered numbers. It also provides means for accepting the offer and exiting the game or continuing with the game. Going back is disabled at this stage.
	c) round2.jsp, round3.jsp - Apart from all the controls of round1.jsp it also prevents change of game state incase browser refresh is clicked.
	d) round4.jsp - all the controls of above round pages are applied here. clicking on continue on this page will take the user to correct.jsp
4)log.ser - used to save the game data as game array and load the game data into game object.	

************************************************************************************************************************

• How did you implement session tracking? 
Session tracking is implemented via using session object. GameNumber object with details of players game state is stored in session object. This object is passed between the various JSP pages to display the relevant information.The session is invalidated upon game completion so that the session is ready for the next game.

************************************************************************************************************************

• How did you implement game saving?
The game is saved in a file called log.ser, a serialised file to save the game state of all the players.
The GameNumber class stores all the variables required to play a game. It is a serialised class , hence enables the secret number game application to save the game state of the game object to the log.ser file.

The player is asked to enter a userName in order to start a new game. Once the game is saved the player can use this userName to load their saved game. Only one game can be saved per player.

Inorder to save the game, player needs to click on the save game button during the game rounds,  save button is removed during the decision page where user needs to accept the offer or continue playing. Game can be saved during any of the rounds but not during the decision. Clicking on the save game button will ends the game. Loading the saved game (on the home page) removes the saved game record from the log.ser file.

************************************************************************************************************************

• The URL to start the application
The Secret number game can be run using URL  http://localhost:8080/c3309266_assignment2/home.jsp

************************************************************************************************************************
References used:
MyUon WebEngineering lecture notes
https://www.w3schools.com/html
https://www.tutorialspoint.com/servlets/index.htm
https://www.tutorialspoint.com/jsp/index.htm
https://www.tutorialspoint.com/java/java_files_io.htm

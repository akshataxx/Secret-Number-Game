/*
Name: Akshata Dhuraji
Student No:C3309266
Class: GameServlet 
Description: GameServlet accepts data from various rounds of game and calls beans functions to execute the game and displays results via jsp's
*/
package pkg;

import java.io.*;
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet{
	private static final long serialVersionID = 1L;
	
	private int minimum;										//minimum number 1 as specified in assignment
	private int maximum;										//maximum number 11 as specified in assignment
	private int offer;											//offer as specified in assignment
	private List<Integer> list = new ArrayList<Integer>();		//List to hold the values entered by user in the JSP and pass it to bean for storage
	
	public GameServlet(){										//constructor
		minimum =1;
		maximum =11;
		offer =0;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	
			HttpSession session = request.getSession();
			GameNumber game = (GameNumber)session.getAttribute("game"); 							//retrieve the session
			request.setAttribute("saveMessage", "Saving the game will end the current game");		//sets save message
			
			if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) { 	//checks if session has expired
				request.setAttribute("errorMessage", "Oops! Session expired, but the good part is your game is saved.");// msg if session expired
				load(request, response,"/correct.jsp");
	
			}else{		
				if (request.getParameter("newGame") != null) {	 									//New game button selected
					System.out.println("new game");													//Debug code
					newGame(request, response, session);											//callout to new game function
				}
				else if(request.getParameter("save") != null){ 			  							//save game button clicked
					if(game.saveGame(game, getServletContext(), true)) {  							//add new game to file
						list.clear();																//clear list if the game is saved
						request.getSession().invalidate(); 				 //clear session attributes - once game is saved the current session ends
						request.setAttribute("errorMessage", "Your game is saved"); 				//Add saved message
						load(request, response,"/correct.jsp");
					}
				}
				else if(request.getParameter("round1") != null) {	 								//Round1 submitted
					System.out.println("round1 submitted");											//Debug code
					list.add(Integer.parseInt(request.getParameter("guess")));						//add the number submitted to list 
					int len= list.size();
					len=len-1;
					if(list.get(len) >= minimum && list.get(len)<= maximum ){						//check if the number submitted by user is within the defined range
						game.setValues(list);
						System.out.println("guess 1 =" + game.getValues());							//Debug code
						request.setAttribute("num",list.get(len));									
						if (game.compare(game, game.getTarget()) == true){							//check if the number entere
							System.out.println("when condition is true");							//Debug code
							request.setAttribute("gameovermsg", "Oops! you entered the secret number.."+game.getTarget()+" in Round1, game over. Better luck next time");												//message displayed if the number entered and taget number matches
							game.setGameOver(true);													//game over flagged is marked as true
							list.clear();															//clear the list to prepare for the next game
							load(request, response,"/correct.jsp");									//show the correct.jsp page
						}else if(!game.compare(game,game.getTarget())){								//if the number entered by the user and target number doesn't match
							System.out.println("when condition is false");								//Debug code
							offer = game.offer(list);													//calculate the offer as per assignment specs
							System.out.println("offer =" + offer);										//Debug code
							request.setAttribute("msg","Great Going! this is not the secret number. Would you like to take the offer" +offer+"$ ? click on accept to take the offer and finish the game, else click on continue to move to next round of the game"); 
						}
					} else{
						request.setAttribute("outofrange","You seem to have missed the instruction at the top, pls. enter a number between 1 and 11");
					}
					System.out.println("from servlet" + request.getAttribute("msg"));					//Debug code
					load(request, response,"/round1.jsp");												//call the jsp page
				}	
				else if(request.getParameter("Accept") != null) {										//if user clicked on Accept
					game.setGameOver(true);																//sets gameover flag as true
					list.clear();																		//clears the list to prepare for next game
					load(request, response,"/correct.jsp");												//calls correct.jsp to show relevant message
				}
				else if(request.getParameter("round1Continue") != null){								// if user clicked on continue then move to round2 page.
					game.setRoundNo(2);
					load(request, response,"/round2.jsp");
				}
				else if(request.getParameter("round2") != null){										//if round2 numbers are submitted
					System.out.println("round2 submitted");												//Debug code
					int len=list.size();																//go to the end of the list and add numbers
					int len1=len+1;
					System.out.println("list len = " + len +"list len1="+ len1);						//Debug code
					if(list.contains(Integer.parseInt(request.getParameter("guess1"))) ||				//check if the numbers entered were not  
						list.contains(Integer.parseInt(request.getParameter("guess2")))){				//entered in previous rounds
						request.setAttribute("note", "Try different numbers, you already tried these numbers before"+ list); 
						load(request, response,"/round2.jsp");											
					}else if (Integer.parseInt(request.getParameter("guess1")) == Integer.parseInt(request.getParameter("guess2"))){//check if numbers are unique
						request.setAttribute("note", "You have entered duplicate numbers, pls. enter unqiue numbers");
						load(request, response,"/round2.jsp");
					}else{
						list.add(Integer.parseInt(request.getParameter("guess1")));						//if number meets above checks then add them
						list.add(Integer.parseInt(request.getParameter("guess2")));						//to the list
						System.out.println("list" + list);
						if(list.get(len) >= minimum && list.get(len)<= maximum && list.get(len1) >= minimum && list.get(len1)<= maximum){
							game.setValues(list);						//pass the numbers to bean class for storing in the respective game object.
							request.setAttribute("num1",list.get(len)); 
							request.setAttribute("num2",list.get(len1));
							System.out.println("Compare game returns =" + game.compare(game, game.getTarget()));//Debug code
							if (game.compare(game, game.getTarget()) == true){	//calls game function to compare values entered by user with target
								System.out.println("when condition is true");									//Debug code	
								request.setAttribute("gameovermsg", "Oops! you entered the secret number.."+game.getTarget()+" in Round2, game over. Better luck next time");
								game.setGameOver(true);															//sets game over flag as true
								list.clear();									//clear the list to prepare for next game
								load(request, response,"/correct.jsp");
							}
							else if(!game.compare(game,game.getTarget())){
								System.out.println("when condition is false");								//Debug code
								offer = game.offer(list);													//calculate the offer
								System.out.println("offer =" + offer);										//Debug code
								request.setAttribute("msg","Great Going! this is not the secret number. Would you like to take the offer" +offer+"$ ? press accept to finish the game, else press continue to move to next round of the game"); 
							}
						}else{
							list.remove(len);																//remove the number from the list
							list.remove(len1);																//remove the number from the list
							request.setAttribute("outofrange","You seem to have missed the instruction at the top, pls. enter a number between 1 and 11");																			
						}
						System.out.println("from servlet" + request.getAttribute("msg"));				//Debug code
						load(request, response,"/round2.jsp");											//load round2 page
					}
				}
				else if(request.getParameter("round2Continue") != null){								//if user clicks on continue on round2 page
					game.setRoundNo(3);																	//set game round as 3
					load(request, response,"/round3.jsp");												//load round 3 page
				}	
				else if(request.getParameter("round3") != null){										//if round 3 numbers are submitted		
					System.out.println("round3 submitted");												//Debug code
					int len=list.size();
					int len1=len+1;
					int len2 = len1+1;
					System.out.println("list len = " + len +"list len1="+ len1 + "list len2" + len2);	//Debug code
					if(list.contains(Integer.parseInt(request.getParameter("guess1"))) ||				//check if the numbers entered doesn't match
						list.contains(Integer.parseInt(request.getParameter("guess2")))|| 				//amy numbers from previous round
						list.contains(Integer.parseInt(request.getParameter("guess3")))){
						request.setAttribute("note", "Try different numbers, you already tried these numbers before"+ list);
						load(request, response,"/round3.jsp");
					}else if (Integer.parseInt(request.getParameter("guess1")) == Integer.parseInt(request.getParameter("guess2")) ||
							  Integer.parseInt(request.getParameter("guess1")) == Integer.parseInt(request.getParameter("guess3")) ||
							  Integer.parseInt(request.getParameter("guess2")) == Integer.parseInt(request.getParameter("guess3"))){
								request.setAttribute("note", "You have entered duplicate numbers, pls. enter unqiue numbers"); 
								load(request, response,"/round3.jsp");								
					}else{
						list.add(Integer.parseInt(request.getParameter("guess1")));
						list.add(Integer.parseInt(request.getParameter("guess2")));
						list.add(Integer.parseInt(request.getParameter("guess3")));
						System.out.println("list" + list);
						if(list.get(len) >= minimum && list.get(len)<= maximum && 
							list.get(len1) >= minimum && list.get(len1)<= maximum &&
							list.get(len2) >= minimum && list.get(len2)<= maximum){
							request.setAttribute("num1",list.get(len));
							request.setAttribute("num2",list.get(len1));
							request.setAttribute("num3",list.get(len2));
							game.setValues(list);
							if (game.compare(game, game.getTarget()) == true){
								System.out.println("when condition is true ");							//Debug code
								request.setAttribute("gameovermsg", "Oops! you entered the secret number.."+game.getTarget()+" in Round2, game over. Better luck next time ");
								game.setGameOver(true);
								list.clear();
								load(request, response,"/correct.jsp");
							}
							else if(!game.compare(game,game.getTarget())){
								System.out.println("when condition is false");							//Debug code
								offer = game.offer(list);
								System.out.println("offer =" + offer);									//Debug code
								request.setAttribute("msg","Great Going! this is not the secret number. Would you like to take the offer" +offer+"$ ? press accept to finish the game, else press continue to move to next round of the game"); 
							}
						}else{
							list.remove(len);
							list.remove(len1);
							list.remove(len2);
							request.setAttribute("outofrange","You seem to have missed the instruction at the top, pls. enter a number between 1 and 11");
						}
						System.out.println("from servlet" + request.getAttribute("msg"));				//Debug code
						load(request, response,"/round3.jsp");
					}
				}
				else if(request.getParameter("round3Continue") != null){
					game.setRoundNo(4);
					load(request, response,"/round4.jsp");
				}
				else if(request.getParameter("round4") != null){
					System.out.println("round4 submitted");												//Debug code
					int len=list.size();
					int len1=len+1;
					int len2 = len1+1;
					int len3 = len2+1;
					System.out.println("list len = " + len +"list len1="+ len1 + "list len2" + len2 +"list len3" + len3); //Debug code
					if(list.contains(Integer.parseInt(request.getParameter("guess1"))) ||
					   list.contains(Integer.parseInt(request.getParameter("guess2")))|| 
					   list.contains(Integer.parseInt(request.getParameter("guess3")))||
					   list.contains(Integer.parseInt(request.getParameter("guess4")))){
						request.setAttribute("note", "Try different numbers, you already tried these numbers before"+ list);
						load(request, response,"/round3.jsp");
					}else if(Integer.parseInt(request.getParameter("guess1")) == Integer.parseInt(request.getParameter("guess2"))||
							 Integer.parseInt(request.getParameter("guess1")) == Integer.parseInt(request.getParameter("guess3"))||
							 Integer.parseInt(request.getParameter("guess1")) == Integer.parseInt(request.getParameter("guess4"))||
							 Integer.parseInt(request.getParameter("guess2")) == Integer.parseInt(request.getParameter("guess3"))||
							 Integer.parseInt(request.getParameter("guess2")) == Integer.parseInt(request.getParameter("guess4"))||
							 Integer.parseInt(request.getParameter("guess3")) == Integer.parseInt(request.getParameter("guess4"))){
							 request.setAttribute("note", "You have entered duplicate numbers, pls. enter unqiue numbers");
							 load(request, response,"/round4.jsp");							 
					}else{
						list.add(Integer.parseInt(request.getParameter("guess1")));
						list.add(Integer.parseInt(request.getParameter("guess2")));
						list.add(Integer.parseInt(request.getParameter("guess3")));
						list.add(Integer.parseInt(request.getParameter("guess4")));
						System.out.println("list" + list);
						if(list.get(len) >= minimum && list.get(len)<= maximum && 
							list.get(len1) >= minimum && list.get(len1)<= maximum &&
							list.get(len2) >= minimum && list.get(len2)<= maximum &&
							list.get(len3) >= minimum && list.get(len3)<= maximum){
							request.setAttribute("num1",list.get(len));
							request.setAttribute("num2",list.get(len1));
							request.setAttribute("num3",list.get(len2));
							request.setAttribute("num4",list.get(len3));
							game.setValues(list);
							if (game.compare(game, game.getTarget()) == true){
								System.out.println("when condition is true");							//Debug code
								request.setAttribute("msg","Oops! you entered the secret number.. ");	
								request.setAttribute("gameovermsg", "Oops! you entered the secret number.."+game.getTarget()+" in Round2, game over");
								game.setGameOver(true);
								list.clear();
								load(request, response,"/correct.jsp");
							}
							else if(!game.compare(game,game.getTarget())){
								System.out.println("when condition is false");							//Debug code
								offer = game.offer(list);
								System.out.println("offer =" + offer);									//Debug code
								request.setAttribute("msg","Great Going! this is not the secret number. Would you like to take the offer" +offer+"$ ? press accept to finish the game, else press continue to move to next round of the game"); 
							}
						}else{
							list.remove(len);
							list.remove(len1);
							list.remove(len2);
							list.remove(len3);
							request.setAttribute("outofrange","You seem to have missed the instruction at the top, pls. enter a number between 1 and 11");
						}
						System.out.println("from servlet" + request.getAttribute("msg"));				//Debug code
						load(request, response,"/round4.jsp");
					}
				}
				else if(request.getParameter("round4Continue") != null){
					List<Integer> lastNums = new ArrayList<Integer>();
					lastNums = game.lastoffer(list);
					request.setAttribute("winMsg", "Unrevelead numbers : "+lastNums+ " " + game.getUserName()+ " you have won " + game.offer(game.getTarget())+"$" + "Congratulations! well played");
					list.clear();
					load(request, response,"/correct.jsp");
				}								
				else if(request.getParameter("loadGame") != null){ 										//load  game button selected
					System.out.println("Load game");													// Debug code
					GameNumber searchthisgame = new GameNumber();
					searchthisgame.setUserName(request.getParameter("username"));
					System.out.println("search game for user " + searchthisgame.getUserName());			//Debug code
					
					GameNumber existingGame = new GameNumber();
					existingGame = existingGame.searchGame(searchthisgame.getUserName(),getServletContext());
					System.out.println("loaded game for"+ existingGame.getUserName()+ "Game state " + existingGame.getGameOver()); //Debug code

					if(existingGame.getUserName().equals("")||existingGame.getUserName()== null|| existingGame.getGameOver()== true) {//game not found for this username or game found for the user but the game is already over 
						System.out.println("game not found");	//Debug code
						request.setAttribute("errorMessage", searchthisgame.getUserName() + ": You don't have a saved game " );
						load(request, response,"/home.jsp");
					}else{
						if((GameNumber)session.getAttribute("game") == null){
							existingGame.saveGame(existingGame,getServletContext(),false); 		// remove the game from log.txt after loading it
							session.setAttribute("game", existingGame);
							list.addAll(existingGame.getValues());
							int rNo = existingGame.getRoundNo();
							if (rNo==1){
								load(request, response,"/round1.jsp");
							} else if(rNo == 2){
								load(request, response,"/round2.jsp");
							} else if (rNo == 3){
								load(request, response,"/round3.jsp");
							} else if (rNo == 4) {
								load(request, response,"/round4.jsp");
							} else {
								load(request, response,"/correct.jsp");
							}
						}
					}			
				}	
			}				
		}catch (NullPointerException ex){			//handle exceptions
			request.setAttribute("errorMessage", "Game ended due to an internal error. Play again? " + ex ); 
			load(request, response,"/home.jsp");
		}catch(Exception ex) {
			request.setAttribute("errorMessage", "Error encountered: " + ex +"Play again");
			load(request, response,"/home.jsp");
		}
	}
	//function to receive jsp page name and load it
	private void load(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	//function to start new game
	private void newGame(HttpServletRequest  request, HttpServletResponse response,HttpSession  session) throws ServletException, IOException{	
		GameNumber game = new GameNumber();
		game.setUserName(request.getParameter("username"));			//sets game user name based on the value entered by the user on home page
		System.out.println(game.getUserName());						//Debug code
		game.setTarget(minimum, maximum);							//sets game target
		game.setRoundNo(1);											//set round num
		System.out.println("mini "+ minimum + "max " + maximum + "target "+ game.getTarget());		//Debug code
		if(list.size()>0){											//checks if the list to accept the numbers passed by JSP's is clear or not
			list.clear();											//if not clear then clears the list
		}
		if ((GameNumber)session.getAttribute("game") == null){		//sets the session with the game details in progress
			session.setAttribute("game", game);						
			load(request, response,"/round1.jsp");					//takes the player to round1
		}else {
			request.setAttribute("errorMessage", "The Game is already in progress in other browser window, pls. complete your game, before starting a new one");
			load(request, response,"/home.jsp");
		}
	}
}
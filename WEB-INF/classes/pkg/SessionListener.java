/*
Name: Akshata Dhuraji
Student No:C3309266
Class: Session Listener
Description: At exit save the session
*/
package pkg;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener{

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession(); //Retrieve the session
		if(session!=null){
			GameNumber game = (GameNumber)session.getAttribute("game");
			if(game!=null && !game.getGameOver() && game.getUserName()!=null){
					game.saveGame(game, session.getServletContext(), true); //callout to save function of GameNumber classs to save the game data
					session.setAttribute("game",null);
					session.invalidate(); 				 //clear session attributes - once game is saved the current session ends
			}
		}
	}
}
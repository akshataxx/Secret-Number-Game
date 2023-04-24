/*
Name: Akshata Dhuraji
Student No:C3309266
Class: GameNumber Bean Class 
Description: Bean class provides processing capabilities to the game
*/
package pkg;

import java.io.*;
import java.util.*;
import java.util.Random;
import javax.servlet.ServletContext;

public class GameNumber implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	// Initialise my game number variables
	private int target;											//stores game target
	private int roundNo;										//stores game round
	private String userName;									//stores user name
	private boolean gameOver = false;							//flag to store if game is completed
	private List<Integer> gnList = new ArrayList<Integer>();	//list with values from 1 to 11
	private List<Integer> values = new ArrayList<Integer>();	//list to store user entered values
	
	public GameNumber(){										//constructor to load 1 to 11 values in the gnList as per the assign specs
		for(int i=1; i<=11; i++){	
			gnList.add(i);
		}
	}

	public List<Integer> getValues(){							// returns values stored in the list
		return values;
	}

	public void setValues(List<Integer> mylist){				// sets the user entered values in the list
		if (values.size()>0){									//clears off the previous values for each instance of callout and stores it afresh
			values.clear();
		}
		for(int s=0; s<mylist.size(); s++){
			values.add(Integer.valueOf(mylist.get(s)));			// add values passed by user to the list, I could have used addAll function but wanted to avoid using collection functions for simplicity sake
		}
	}
	public void setTarget(int minimum, int maximum){			// sets game target	number		
		Random random = new Random();
		this.target = random.nextInt(maximum - minimum) + minimum;
	}
	public int getTarget(){										//returns game target
		return target;
	}
	public void setUserName(String userName){					//sets game userName 
		this.userName = userName;
	}
	public String getUserName(){								//returns game userName
		return userName;
	}
	public void setRoundNo(int roundNo){						//sets game roundNo
		this.roundNo = roundNo;
	}
	public int getRoundNo(){									//returns game roundNo
		return roundNo;
	}
	public void setGameOver(boolean isGameOver){				//sets gameover flag as true or false
		this.gameOver=isGameOver;
	}
	public boolean getGameOver(){								//sets game over
		return gameOver;
	}
	
	public boolean compare(GameNumber game, int target){		//compares numbers entered by user and target number and return boolean value as result
		List<Integer> complist = new ArrayList<Integer>();
		complist.addAll(game.getValues());
		boolean flag = false;
		for (int v=0;v < complist.size(); v++){
			if((Integer.valueOf(complist.get(v))) == target && (flag != true)){
				flag = true;
				System.out.println("From Game Number Complist = " + complist.get(v)+ " Flag = " + flag + " Target = "+ target);	// Debug code
			}
		}
		complist.clear();
		System.out.println("Flag =" +flag); 					//Debug code
		return flag;
	}
	
	// Inorder to calculate the offer a newlist is created with a list of all the numbers between 1 to 11,  except the numbers added by the user
	// from these numbers minimum value is obtained for offer calculation as per assignment specs
	public int offer(List<Integer> gslist){ 					// gslist holds the numbers added by the user
		for (int j=0; j < gslist.size(); j++){
			int usernum = gslist.get(j);					
			if(gnList.contains(Integer.valueOf(usernum)) == true){
				gnList.remove(Integer.valueOf(usernum));		//gnList now holds all the number except the numbers entered by the user
			}
		}
		if (gnList != null || gnList.size() != 0) {				//gets minimum value in the gnList
			int min = Collections.min(gnList);		
            min= min *100;
			return min;
        }else{
			return 0;
		}
	}
	
	public int offer(int target){								//calculates offer as per the assignment specs
		int prize = target *100;
		return prize;
	}
	
	public List<Integer> lastoffer(List<Integer> gslist){ 		// gslist holds the numbers added by the user
		for (int j=0; j < gslist.size(); j++){
			int usernum = gslist.get(j);					
			if(gnList.contains(Integer.valueOf(usernum)) == true){
				gnList.remove(Integer.valueOf(usernum));		//gnList now holds all the number except the numbers entered by the user
			}
		}
		return gnList;
	}
	//function to check if the game for the user already exist in the file, then delete it before storing this game in the file. This is as per the assignment specs. It returns boolean value to confirm if the data was saved successfully. 
	public boolean saveGame(GameNumber game, ServletContext context, boolean saveNewGame){	
		boolean success = false;
		List<GameNumber> gameList = new ArrayList<GameNumber>();
		FileOutputStream fileOut =	null;
		ObjectOutputStream objectOut = null;
		
		try {
				gameList = loadSavedGames(context); 										//load game from file
				for(int i = 0; i < gameList.size(); i ++) {				
					if(gameList.get(i).getUserName().equals(game.getUserName())) {			//checks if there are any existing games for this user
						gameList.remove(i);													//if it exist then remove existing game for this user
					}
				}
				if(saveNewGame) {				
					gameList.add(game); 													//add new game data to the List
				}
				GameNumber[] gameArray = gameList.toArray(new GameNumber[gameList.size()]); //Save in the file as array (ArrayList to Array)
				fileOut  = new FileOutputStream(context.getRealPath("/log.ser"));			//data file
				objectOut  = new ObjectOutputStream(fileOut );
					
				objectOut.writeObject(gameArray); 											//write game data to the file
				objectOut.close();
				fileOut.close();
				success = true;
			}catch (Exception e) {
				e.printStackTrace();
			} 
		return success;
	}
	//Internal function to load the saved games into a list and return this list to save game function
	private List<GameNumber> loadSavedGames(ServletContext context){
		GameNumber[] savedGames = null;
		List<GameNumber> savedList = new ArrayList<GameNumber>();

		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		try {
			fileIn = new FileInputStream(context.getRealPath("/log.ser"));
			objectIn = new ObjectInputStream(fileIn);

			savedGames = (GameNumber[])objectIn.readObject();
			savedList = new ArrayList<GameNumber>(Arrays.asList(savedGames)); 
			fileIn.close();
			objectIn.close();
		}catch (NullPointerException e){
			e.printStackTrace();
		}catch (EOFException e) { 
			return savedList;
		}catch(InvalidClassException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return savedList;
	}
	//Function to search game in the file. 
	public GameNumber searchGame(String userName, ServletContext context){
		GameNumber foundGame = new GameNumber();
		List<GameNumber> mySavedGames = loadSavedGames(context);
		for(int i=0; i<mySavedGames.size();i++){
			GameNumber isItMyGame = mySavedGames.get(i);
			if(isItMyGame.getUserName().equals(userName)==true){
				System.out.println("check if it is my game " + isItMyGame.getUserName() + "RoundNo " + isItMyGame.getRoundNo() + "target " + isItMyGame.getTarget() +"values "+ isItMyGame.getValues());				//Debug code
				foundGame=isItMyGame;
			}
		}
		return foundGame;
	}
}
	
//Author: Andrei Ghenoiu
//Fall 2011 Networks class
//if you have any questions contact me at andrei_stefang@yahoo.com

import java.io.*;
import java.net.*;
 public class TCPServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String clientSentence;
		String serverSentence;
		ServerSocket welcomeSocket;
		Socket serverSocket;
		
		String miss = "miss";
		String hit = "hit";
		String won = "won";
		boolean turn = true;
		
		
		//from command line
		BufferedReader inFromUser = new BufferedReader(
				new InputStreamReader(System.in));
		//create the board
		
		board gameBoard = new board();
		
		//player is asked to add the ships to the board
		System.out.println("Below you can input where you want to" +
				" place your battleships.\n Please enter them in integers" +
				" starting with the row followed by columns\n (for example" +
				" start with the head as 11 for row 1,\n column 1 and " +
				" tail as 51 for row 5 and column 1)\n. Please input the data\n " +
				"left to right and top to bottom\n" +
				"Type q when done.");
		while(true){
			System.out.println("Please enter head location:");
			String line1 = inFromUser.readLine();
			System.out.println("Please enter tail location:");
			String line2 = inFromUser.readLine();
			if(!line1.equals("q") || !line2.equals("q")){
				int head = Integer.parseInt(line1);
				int tail = Integer.parseInt(line2);
				//we call the testPos method to verify that we can place
				//the battleship at the inputed locations
				gameBoard.testPos(head, tail);
				if(gameBoard.boatBool == true){
					gameBoard.createBoat(head, tail);
					System.out.println("Creating boat at "+head+ " and "+tail);
				}
				else
					System.out.println("Sorry, can't place the battleship using these locations.");
			}
			else
				break;
		}
		
		gameBoard.printBoard();
		////////////////////////////////////
		//game starts
		////////////////////////////////////
		welcomeSocket = new ServerSocket(6789);
		serverSocket  = welcomeSocket.accept();
		//from client
		BufferedReader inFromClient = new BufferedReader(
				new InputStreamReader(serverSocket.getInputStream()));
		
		//out to client the hit or miss message
		DataOutputStream outToClient1 = 
			new DataOutputStream(serverSocket.getOutputStream());
		
		
		while(true){ 
			int hitRow;
			int hitCol;
			
			//the server receives a hit from the client 
			//and replies with a hit or miss
			clientSentence = inFromClient.readLine();
			System.out.println("Hit from client: " + clientSentence);
			int clientInt = Integer.parseInt(clientSentence);
			hitRow = Math.abs(clientInt/10)-1;
			hitCol = clientInt%10-1;
			
			if(gameBoard.testHit(hitRow, hitCol)){
				gameBoard.testLoss();
				if(gameBoard.testLoss() == false){
					hit = miss = "You won!";
					System.out.println("Sorry, you lost!");
				}
				outToClient1.writeBytes(hit+"\n");
				outToClient1.flush();
			}
			else{
				outToClient1.writeBytes(miss+"\n");
				outToClient1.flush();
			}
			
			//the server sends a hit
			String newS = inFromUser.readLine();
			outToClient1.writeBytes(newS+"\n");
			outToClient1.flush();
			
			clientSentence = inFromClient.readLine();
			System.out.println("Result from client: " + clientSentence);
			
		}
	}

}

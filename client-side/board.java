//Author: Andrei Ghenoiu
//Fall 2011 Networks class
//if you have any questions contact me at andrei_stefang@yahoo.com


public class board {
	public int[][] board;
	//this will hold info if the boat can be created
	public boolean boatBool;
	public boolean loss = true;
	public String  str;
	public board(){
		board = new int[10][10];
	}
	public void testPos(int a, int b){
		int row1 = Math.abs(a/10);
		int col1 = a%10;
		int row2 = Math.abs(b/10);
		int col2 = b%10;
		if(row1 == row2 || col1 == col2){
			boatBool = true;
		}
		else
			boatBool = false;
	}
	//we create a boat
	public void createBoat(int a, int b){
		//we also need to test for the existence of other boats
		
		//create the head and tail of the ship
		int row1 = Math.abs(a/10);
		int col1 = a%10;
		int row2 = Math.abs(b/10);
		int col2 = b%10;
		//testing to see if they are on the same column or row
		//this way we know how to put the ship in the array
		if(row1 == row2){
			//if the boat is on the same row we have to fill the 
			//columns out
			for(int i = col1; i<=col2; i++){
				board[row1-1][i-1] = 1;
			}
		}
		else if(col1 == col2){
			for(int i = row1; i<=row2; i++){
				board[i-1][col1-1] = 1;
			}
		}
		else{
			boatBool = false;
		}
	}
	//printing the board
	public void printBoard(){
		for(int i = 0; i<board.length; i++){
			for(int c = 0; c<board[i].length; c++){
				System.out.print(board[i][c]+" ");
			}
			System.out.println();
		}
	}
	public boolean testHit(int row, int col){
		if(board[row][col] == 1){
			board[row][col] = 5;
			printBoard();
			return true;
		}
		else 
			return false;
	}
	//test for loss, when all the board is 0's again
	public boolean testLoss(){
		int count = 0;
		boolean bool1 = true;
		for(int i = 0; i<board.length; i++){
			for(int c = 0; c<board[i].length; c++){
				if(board[i][c]==0 || board[i][c]==5){
					count++;
				}				
			}
		}
		if(count == 100)
			bool1 = false;
		return bool1;
	}
}

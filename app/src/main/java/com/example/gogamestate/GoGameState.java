package com.example.gogamestate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class GoGameState {


    //When true it is Player 1's turn
    //When false it is Player 2's turn
    private boolean isPlayer1;

    //Stones array for locations on the board
    private Stone[][] gameBoard;

    //Float values for the users click locations
    private float userXClick;
    private float userYClick;

    private int boardSize;

    private int player1Score;
    private int player2Score;

    public GoGameState() {

        //Initialize the board size and gameboard array
        boardSize = 9;
        gameBoard = new Stone[boardSize][boardSize];
        gameBoard = initializeArray();

        //Set isPlayer1 to true so that player 1 starts the game
        isPlayer1 = true;

        //Initialize the float values of the user's click
        userXClick = -1;
        userXClick = -1;

        //Initialize the players scores
        player1Score = 0;
        player2Score = 0;

    }



    public GoGameState(GoGameState gs){
        this.boardSize = gs.boardSize;
        this.userXClick = gs.userXClick;
        this.userYClick = gs.userYClick;
        this.gameBoard = new Stone[boardSize][boardSize];
        this.gameBoard = gs.gameBoard;
        this.player1Score = gs.player1Score;
        this.player2Score = gs.player2Score;
        this.isPlayer1 = gs.isPlayer1;
    }


    /**
     * This method initializes the array of stones
     *
     * @author Jude Gabriel
     *
     * NOTE: This method works as expected
     */
    public Stone[][] initializeArray() {
        //Create a temporary array of stones
        Stone[][] tempBoard = new Stone[boardSize][boardSize];

        //Initialize the stones to a certain color
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                tempBoard[i][j] = new Stone((j * 350) + 250,(i * 350) + 50);
            }
        }

        //Return the array of stones
        return tempBoard;
    }


    /**
     * Calculate the player move and then rechange the color of the stones
     *
     * @param x     x location of the user or AI click
     * @param y     y location of the user or AI click
     *
     * @author Jude Gabriel
     *
     * NOT FINISHED
     */
    public void playerMove(float x, float y) {
        //Find the liberty the user clicked on
        int[] indexVals = findStone(x, y);
        int iIndex = indexVals[0];
        int jIndex = indexVals[1];

        //Check if the move is valid
        boolean validMove = isValidLocation(iIndex, jIndex);


        if(isPlayer1){
            if(validMove){
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.BLACK);
                checkCaptureWhite();
            }
        }

        if(!isPlayer1){
            if(validMove){
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.WHITE);
                checkCaptureBlack();
            }
        }

        //Change the players turn to the next player
        isPlayer1 = !isPlayer1;
    }


    /**
     * findStone finds which index the user clicked on in the stones array
     *
     * @param x     the x location of the user click
     * @param y     the y location of the user click
     *
     * @return an integer array containing the indices
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing, should be finished
     */
    public int[] findStone(float x, float y){
        //Initialize index values as -1 so we can error check
        int iIndex = -1;
        int jIndex = -1;

        //Since the radius of the stone is 25 we wanna check double the surroundings
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if((x < gameBoard[i][j].getxRight()) && (x > gameBoard[i][j].getxLeft())){
                    if((y > gameBoard[i][j].getyTop()) && (y < gameBoard[i][j].getyBottom())){
                        iIndex = i;
                        jIndex = j;
                    }
                }
            }
        }



        //Create an array to store the index values
        int[] indexArray = new int[2];
        indexArray[0] = iIndex;
        indexArray[1] = jIndex;

        //Return the array with the index values of the stone
        return indexArray;
    }


    /**
     * This will check if the user places the stone in a valid position
     *
     * @return true if the user places a stone in a valid location
     *
     * @author Jude Gabriel
     *
     *
     * NOTE: NOT FINISHED
     */
    public boolean isValidLocation(int iIndex, int jIndex) {
        //Check if the user clicked on a liberty
        if(iIndex == -1 || jIndex == -1){
            return false;
        }

        //Check if a stone is already there
        if(gameBoard[iIndex][jIndex].getStoneColor() != Stone.StoneColor.NONE){
            return false;
        }

        //Check if the stone will capture itself
            //if so return false

        //Check if it is a repeated board position
            //if so return false

        //If all above cases do not return false then it is a valid move
        return true;
    }


    /**
     * Checks for captured white stones
     *
     * NOTE: NOT FINISHED
     */
    public void checkCaptureWhite(int i, int j){
        //Case 1: The stones are in the top left corner

        //Case 2: The stones are in the top right corner

        //Case 3: The stones are in the bottom left corner

        //Case 4: The stones are in the bottom right corner

        //Case 5: The stones are on the left hand side

        //Case 6: The stones are on the top row

        //Case 7: The stones are on the bottom row

        //Case 8: The stones are on the right hand side

        //Case 9: The stones have all four neighbors
    }

    /**
     * Checks for captured black stones
     * @param i
     * @param j
     *
     * NOTE: NOT FINISHED
     */
    public void checkCaptureBlack(int i, int j){
        //Case 1: The stones are in the top left corner

        //Case 2: The stones are in the top right corner

        //Case 3: The stones are in the bottom left corner

        //Case 4: The stones are in the bottom right corner

        //Case 5: The stones are on the left hand side

        //Case 6: The stones are on the top row

        //Case 7: The stones are on the bottom row

        //Case 8: The stones are on the right hand side

        //Case 9: The stones have all four neighbors
    }
}

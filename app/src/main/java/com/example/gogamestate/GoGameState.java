package com.example.gogamestate;

import android.view.View;

public class GoGameState{


    //When true it is Player 1's turn
    //When false it is Player 2's turn
    private boolean isPlayer1;

    //Stones array for locations on the board
    private Stone[][] gameBoard;

    //Float values for the users click locations
    private float userXClick;
    private float userYClick;

    private int boardSize;

    public GoGameState(){
        boardSize = 9;


        initializeArray();

    }

    public GoGameState(GoGameState gs){

    }


    /**
     * This method initializes the array of stones
     *
     * @author Jude Gabriel
     */
    public void initializeArray() {
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                gameBoard[i][j] = new Stone((i * 50), (i * 50));
            }
        }
    }


    /**
     * Calculate the player move and then rechange the color of the stones
     *
     * @param x     x location of the user or AI click
     * @param y     y location of the user or AI click
     *
     * @author Jude Gabriel
     */
    public void playerMove(float x, float y) {


        isValidLocation();


        if(isPlayer1){

        }
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
     */
    public int[] findStone(float x, float y){
        //Initialize index values as -1 so we can error check
        int iIndex = -1;
        int jIndex = -1;

        //Since the radius of the stone is 25 we wanna check double the surroundings
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if((x < gameBoard[i][j].getxLocation())
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
     */
    public boolean isValidLocation() {
        return true;
    }
}

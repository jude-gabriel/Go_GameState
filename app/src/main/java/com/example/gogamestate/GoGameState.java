package com.example.gogamestate;

public class GoGameState {

    public enum Color{
        WHITE,
        BLACK,
        NONE
    };

    //When true it is Player 1's turn
    //When false it is Player 2's turn
    private boolean userTurn;

    //int array for locations on the board
    private int[][] gameBoard;

    //Float values for the users click locations
    private float userXClick;
    private float userYClick;

}

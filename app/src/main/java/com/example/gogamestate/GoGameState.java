package com.example.gogamestate;

import android.view.View;

public class GoGameState implements View.OnClickListener{


    //When true it is Player 1's turn
    //When false it is Player 2's turn
    private boolean isPlayer1;

    //Stones array for locations on the board
    private Stones[][] gameBoard;

    //Float values for the users click locations
    private float userXClick;
    private float userYClick;

    public GoGameState(){

    }

    public GoGameState(GoGameState gs){

    }


    public boolean isValidLocation() {
        return true;
    }
}

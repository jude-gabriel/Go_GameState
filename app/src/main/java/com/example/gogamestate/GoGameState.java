package com.example.gogamestate;

public class GoGameState {

    public enum Color{
        BLACK,
        WHITE,
        NONE
    };

    //When true it is Player 1's turn
    //When false it is Player 2's turn
    private boolean userTurn;

    public GoGameState(){

    }

    public GoGameState(GoGameState gs){

    }

}

package com.example.gogamestate;

public class Stone {

    public enum StoneColor{
        WHITE,
        BLACK,
        NONE
    };


    private StoneColor stoneColor;
    private float xLocation;  //Create private variables for the x
    private float yLocation;  //and y locations of the of the stones
    private int radius;   //Create a private int for the radius of stones
    private float xLeft;  //stores the x value of the leftmost part of the stone
    private float xRight;  //stores the x value of the rightmost part of the stone
    private float yTop;  //stores the y value of the topmost part of the stone
    private float yBottom;  //stores the y value of bottommost part of the stone


    //constructor for Stone
    //@author Natalie Tashchuk
    public Stone(float x, float y){
        stoneColor = StoneColor.NONE;
        radius = 25;

        xLeft = xLocation - radius;
        xRight = xLocation + radius;
        yTop = yLocation - radius;
        yBottom = yLocation + radius;

    }

    public void setStoneColor(Color stoneColor) {
        this.stoneColor = stoneColor;
    }

    public StoneColor getStoneColor(){
        return stoneColor;
    }

    public float getxLocation(){

        return xLocation;
    }

    public float getyLocation(){

        return yLocation;
    }






}

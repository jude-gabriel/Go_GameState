package com.example.gogamestate;

public class Stone {


    /**
     * Enum's for the Stone's Color
     */
    public enum StoneColor{
        WHITE,
        BLACK,
        NONE
    };


    /**
     * Enum's for if the Stone was checked in the capture method
     */
    public enum CheckedStone{
        FALSE,
        TRUE
    };

    /* Instance Variables for the Stone Class */
    private StoneColor stoneColor;
    private float xLocation;  //Create private variables for the x
    private float yLocation;  //and y locations of the of the stones
    private int radius;   //Create a private int for the radius of stones
    private float xLeft;  //stores the x value of the leftmost part of the stone
    private float xRight;  //stores the x value of the rightmost part of the stone
    private float yTop;  //stores the y value of the topmost part of the stone
    private float yBottom;  //stores the y value of bottommost part of the stone
    private CheckedStone checkedStone;


    /**
     * Constructor for the Stone class
     *
     * @param x     The stone's x-coordinate on the board
     * @param y     The stone's y-coordinate on the board
     *
     * @author Natalie Tashchuk
     */
    public Stone(float x, float y){
        stoneColor = StoneColor.NONE;
        radius = 25;

        xLocation = x;
        yLocation = y;

        xLeft = xLocation - radius;
        xRight = xLocation + radius;
        yTop = yLocation - radius;
        yBottom = yLocation + radius;

        checkedStone = CheckedStone.FALSE;
    }

    public int getRadius(){
        return radius;
    }

    public void setRadius(int r){
        radius = r;
    }


    /**
     * Setter for the Stone's StoneColor enum
     *
     * @param stoneColor    The StoneColor to set the Stone to
     */
    public void setStoneColor(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }


    /**
     * Getter for the Stone's color
     *
     * @return The StoneColor the stone has
     */
    public StoneColor getStoneColor(){
        return stoneColor;
    }


    /**
     * Getter for the Stone's center x-coordinate
     *
     * @return a float representing the x value
     */
    public float getxLocation(){
        return xLocation;
    }

    public void setxLocation(float x){
        xLocation = x;
    }


    /**
     * Getter for the Stone's center y-coordinate
     *
     * @return a float representing the x value
     */
    public float getyLocation(){
        return yLocation;
    }


    public void setyLocation(float y){
        yLocation = y;
    }


    /**
     * Getter for the leftmost x value of the stone
     *
     * @return a float representing the x value
     */
    public float getxLeft(){
        return xLeft;
    }

    public void setxLeft(float x){
        xLeft = x;
    }


    /**
     * Getter for the rightmost x value of the stone
     *
     * @return a float representing the x value
     */
    public float getxRight(){
        return xRight;
    }


    public void setxRight(float x){
        xRight = x;
    }


    /**
     * Getter for the topmost y value of the stone
     *
     * @return a float representing the y value
     */
    public float getyTop(){
        return yTop;
    }

    public void setyTop(float y){
        yTop = y;
    }


    /**
     * Getter for the bottommost y value of the stone
     *
     * @return a float representing the y value
     */
    public float getyBottom(){
        return yBottom;
    }

    public void setyBottom(float y){
        yBottom = y;
    }


    /**
     * Getter for the stones checked value
     *
     * @return the CheckedStone value
     *
     * @author Jude Gabriel
     */
    public CheckedStone getCheckedStone(){
        return checkedStone;
    }


    /**
     * Setter for the stones checked value
     *
     * @param checkVal      The CheckedValue to set the stone as
     *
     * @author Jude Gabriel
     */
    public void setCheckedStone(CheckedStone checkVal){
        checkedStone = checkVal;
    }
}

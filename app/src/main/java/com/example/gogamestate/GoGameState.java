package com.example.gogamestate;


import android.os.CountDownTimer;

public class GoGameState {


    /* GoGameState private instance variables */
    private boolean isPlayer1;  //boolean value for which player's turn it is
    private Stone[][] gameBoard;    //Stones array for locations on the board
    private float userXClick;   //The x coordinate the user clicks
    private float userYClick;   //The y coordinate the user clicks
    private int boardSize;  //The dimensions of the board
    private int player1Score;   //Stores Player 1's score
    private int player2Score;   //Stores Player 2's score
    private int totalMoves;     //Total number of moves made in game
    private Stone[][] stoneCopiesFirst; //Stores the board from two moves ago
    private Stone[][] stoneCopiesSecond;    //Stores the board from one move ago
    private boolean hasEmptyNeighbor;   //Boolean value for if a stone has an empty neighbor
    private  int totalTime;
    private CountDownTimer countUpTimer;


    /**
     * Constructor for the GoGameStateClass
     */
    public GoGameState() {
        //Initialize the board size and gameBoard array
        boardSize = 9;
        gameBoard = new Stone[boardSize][boardSize];
        gameBoard = initializeArray();

        //Set isPlayer1 to true so that player 1 starts the game
        isPlayer1 = true;

        //Initialize the float values of the user's click
        userXClick = -1;
        userXClick = -1;

        //Initialize the players scores and total number of moves
        player1Score = 0;
        player2Score = 0;
        totalMoves = 0;

        //Initialize the arrays that store former board positions
        stoneCopiesFirst = new Stone[boardSize][boardSize];
        stoneCopiesSecond = new Stone[boardSize][boardSize];

        //Set hasEmptyNeighbor to false, used to check for captures
        hasEmptyNeighbor = false;

        countUpTimer = new CountDownTimer(30000, 1000){
            @Override
            public void onTick(long millisUntilFinish){
                totalTime++;

            }

            @Override
            public void onFinish(){
                countUpTimer.start();

            }
        };
        countUpTimer.start();

    }


    /**
     * Copy Constructor for the Go GameState
     * @param gs
     *
     * NOTE: Should be finished, requires testing
     */
    public GoGameState(GoGameState gs){
        this.boardSize = gs.boardSize;
        this.userXClick = gs.userXClick;
        this.userYClick = gs.userYClick;
        this.gameBoard = new Stone[boardSize][boardSize];
        this.gameBoard = gs.gameBoard;
        this.player1Score = gs.player1Score;
        this.player2Score = gs.player2Score;
        this.isPlayer1 = gs.isPlayer1;
        this.totalMoves = gs.totalMoves;

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
     * Calculate the player move and then re-change the color of the stones
     *
     * @param x     x location of the user or AI click
     * @param y     y location of the user or AI click
     * @return true if it is a valid player move, false otherwise
     *
     * @author Jude Gabriel
     *
     * NOT FINISHED
     */
    public boolean playerMove(float x, float y) {
        //Find the liberty the user clicked on
        int[] indexVals = findStone(x, y);
        int iIndex = indexVals[0];
        int jIndex = indexVals[1];

        boolean checkCap1 = true;
        boolean checkCap2 = true;
        boolean checkCap3 = true;
        boolean checkCap4 = true;
        boolean checkCap5 = true;
        boolean checkCap6 = true;
        boolean checkCap7 = true;
        boolean checkCap8 = true;

        if(totalMoves == 2){
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    stoneCopiesFirst[i][j] = gameBoard[i][j];
                }
            }
        }
        if (totalMoves == 3){
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++) {
                    stoneCopiesSecond[i][j] = gameBoard[i][j];
                }
            }
        }


        //Check if the move is valid
        boolean validMove = isValidLocation(iIndex, jIndex);


        if(isPlayer1) {
            if (validMove) {
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.BLACK);

                if (iIndex > 0) {
                    if (gameBoard[iIndex - 1][jIndex].getStoneColor() ==
                            Stone.StoneColor.WHITE) {
                        checkCap1 = checkCaptureWhite(iIndex - 1, jIndex);
                    }
                }
                if (iIndex < gameBoard.length - 1) {
                    if (gameBoard[iIndex + 1][jIndex].getStoneColor() ==
                            Stone.StoneColor.WHITE) {
                        checkCap2 = checkCaptureWhite(iIndex + 1, jIndex);
                    }
                }
                if (jIndex > 0) {
                    if (gameBoard[iIndex][jIndex - 1].getStoneColor() ==
                            Stone.StoneColor.WHITE) {
                        checkCap3 = checkCaptureWhite(iIndex, jIndex - 1);
                    }
                }
                if (jIndex < gameBoard.length - 1) {
                    if (gameBoard[iIndex][jIndex + 1].getStoneColor() ==
                            Stone.StoneColor.WHITE) {
                        checkCap4 = checkCaptureWhite(iIndex, jIndex);
                    }
                }
                if((iIndex > 0) && (jIndex > 0)){
                    if(gameBoard[iIndex - 1][jIndex - 1].getStoneColor() ==
                            Stone.StoneColor.WHITE){
                        checkCap5 = checkCaptureWhite(iIndex - 1, jIndex - 1);
                    }
                }
                if((iIndex < gameBoard.length - 1) && (jIndex < gameBoard.length - 1)){
                    if(gameBoard[iIndex + 1][jIndex + 1].getStoneColor() ==
                            Stone.StoneColor.WHITE){
                        checkCap6 = checkCaptureWhite(iIndex + 1, jIndex + 1);
                    }
                }
                if((iIndex < gameBoard.length - 1) && (jIndex > 0)){
                    if(gameBoard[iIndex + 1][jIndex - 1].getStoneColor() ==
                            Stone.StoneColor.WHITE){
                        checkCap7 = checkCaptureWhite(iIndex + 1, jIndex - 1);
                    }
                }
                if((iIndex > 0) && (jIndex < gameBoard.length - 1)){
                    if(gameBoard[iIndex - 1][jIndex + 1].getStoneColor() ==
                            Stone.StoneColor.WHITE){
                        checkCap8 = checkCaptureWhite(iIndex - 1, jIndex + 1);
                    }
                }

                if((checkCap1 == false) || (checkCap2 == false) || (checkCap3 == false) ||
                        (checkCap4 == false) || (checkCap5 == false) ||
                        (checkCap6 == false) || (checkCap7 == false) || (checkCap8 == false)) {
                    if(hasEmptyNeighbor == false){
                        commenceCapture();
                        player1Score = 0;
                        calculateBlackScore();
                        //Change the players turn to the next player
                        isPlayer1 = !isPlayer1;
                        totalMoves++;
                        hasEmptyNeighbor = false;
                        return true;
                    }
                    else{
                        resetCapture();
                        player1Score = 0;
                        calculateBlackScore();
                        hasEmptyNeighbor = false;
                        //Change the players turn to the next player
                        isPlayer1 = !isPlayer1;
                        return false;
                    }
                }
                else{
                    resetCapture();
                    player1Score = 0;
                    calculateBlackScore();
                    hasEmptyNeighbor = false;
                    //Change the players turn to the next player
                    isPlayer1 = !isPlayer1;
                    return false;
                }
            }
            else{
                //Change the players turn to the next player
                isPlayer1 = !isPlayer1;
                hasEmptyNeighbor = false;
                return false;
            }
        }


        else if(!isPlayer1){
            if(validMove){
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.WHITE);
                if (iIndex > 0) {
                    if (gameBoard[iIndex - 1][jIndex].getStoneColor() ==
                            Stone.StoneColor.BLACK) {
                        checkCap1 = checkCaptureBlack(iIndex - 1, jIndex);
                    }
                }
                if (iIndex < gameBoard.length - 1) {
                    if (gameBoard[iIndex + 1][jIndex].getStoneColor() ==
                            Stone.StoneColor.BLACK) {
                        checkCap2 = checkCaptureBlack(iIndex + 1, jIndex);
                    }
                }
                if (jIndex > 0) {
                    if (gameBoard[iIndex][jIndex - 1].getStoneColor() ==
                            Stone.StoneColor.BLACK) {
                        checkCap3 = checkCaptureBlack(iIndex, jIndex - 1);
                    }
                }
                if (jIndex < gameBoard.length - 1) {
                    if (gameBoard[iIndex][jIndex + 1].getStoneColor() ==
                            Stone.StoneColor.BLACK) {
                        checkCap4 = checkCaptureBlack(iIndex, jIndex);
                    }
                }
                if((iIndex > 0) && (jIndex > 0)){
                    if(gameBoard[iIndex - 1][jIndex - 1].getStoneColor() ==
                            Stone.StoneColor.BLACK){
                        checkCap5 = checkCaptureBlack(iIndex - 1, jIndex - 1);
                    }
                }
                if((iIndex < gameBoard.length - 1) && (jIndex < gameBoard.length - 1)){
                    if(gameBoard[iIndex + 1][jIndex + 1].getStoneColor() ==
                            Stone.StoneColor.BLACK){
                        checkCap6 = checkCaptureBlack(iIndex + 1, jIndex + 1);
                    }
                }
                if((iIndex < gameBoard.length - 1) && (jIndex > 0)){
                    if(gameBoard[iIndex + 1][jIndex - 1].getStoneColor() ==
                            Stone.StoneColor.BLACK){
                        checkCap7 = checkCaptureBlack(iIndex + 1, jIndex - 1);
                    }
                }
                if((iIndex > 0) && (jIndex < gameBoard.length - 1)){
                    if(gameBoard[iIndex - 1][jIndex + 1].getStoneColor() ==
                            Stone.StoneColor.BLACK){
                        checkCap8 = checkCaptureBlack(iIndex - 1, jIndex + 1);
                    }
                }

                if((checkCap1 == false) || (checkCap2 == false) || (checkCap3 == false) ||
                        (checkCap4 == false) || (checkCap5 == false) ||
                        (checkCap6 == false) || (checkCap7 == false) || (checkCap8 == false)){
                    if(hasEmptyNeighbor == false) {
                        commenceCapture();
                        player2Score = 0;
                        calculateWhiteScore();
                        //Change the players turn to the next player
                        isPlayer1 = !isPlayer1;
                        totalMoves++;
                        hasEmptyNeighbor = false;
                        return true;
                    }
                    else{
                        resetCapture();
                        player2Score = 0;
                        calculateWhiteScore();
                        hasEmptyNeighbor = false;
                        isPlayer1 = !isPlayer1;
                        return false;
                    }
                }
                else{
                    resetCapture();
                    player2Score = 0;
                    calculateWhiteScore();
                    hasEmptyNeighbor = false;
                    //Change the players turn to the next player
                    isPlayer1 = !isPlayer1;
                    return false;
                }
            }
            else{
                //Change the players turn to the next player
                hasEmptyNeighbor = false;
                isPlayer1 = !isPlayer1;
                return false;
            }
        }

        //This case shouldn't be hit
        else {
            //Change the players turn to the next player
            isPlayer1 = !isPlayer1;
            return false;
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
     * NOTE: NOT FINISHED. NEEDS TO CHECK FOR REPEATED MOVE
     */
    public boolean isValidLocation(int iIndex, int jIndex) {
        boolean canCapture;

        //Check if the user clicked on a liberty
        if(iIndex == -1 || jIndex == -1){
            return false;
        }

        //Check if a stone is already there
        if(gameBoard[iIndex][jIndex].getStoneColor() != Stone.StoneColor.NONE){
            return false;
        }

        //Check if the stone will capture itself
        if(isPlayer1) {
            gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.BLACK);
            if(checkCaptureBlack(iIndex, jIndex) == false){
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.NONE);
                resetCapture();
                hasEmptyNeighbor = false;
                return false;
            }
        }

        if(!isPlayer1){
            gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.WHITE);
            if(checkCaptureWhite(iIndex, jIndex) == false){
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.NONE);
                resetCapture();
                hasEmptyNeighbor = false;
                return false;
            }
        }


        if(checkRepeatedPosition(iIndex, jIndex) == true){
            return false;
        }

        //If all above cases do not return false then it is a valid move
        hasEmptyNeighbor = false;
        resetCapture();
        return true;
    }


    /**
     * Checks for captured white stones
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public boolean checkCaptureWhite(int i, int j){
        //Initially set the placed stone as checked
        gameBoard[i][j].setCheckedStone(Stone.CheckedStone.TRUE);


        /* Check if any neighbors are liberties, if they are then no capture.
         * if they aren't then check if they already have been checked.
         * if they have not then recursively call the method again
         */

        //Case 1: The stones are in the top left corner
        if((i == 0) && (j == 0)) {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }
        }


        //Case 2: The stones are in the top right corner
        else if((i == 0) && (j == gameBoard.length - 1)) {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }
        }



        //Case 3: The stones are in the bottom left corner
        else if((i == gameBoard.length - 1) && (j == 0)) {
            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }
        }



        //Case 4: The stones are in the bottom right corner
        else if((i == gameBoard.length - 1) && (j == gameBoard.length - 1)) {
            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }
        }



        //Case 5: The stones are on the left hand side
        else if((i != gameBoard.length - 1) && (i != 0 ) && (j == 0)) {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }
        }


        //Case 6: The stones are on the top row
        else if((i == 0) && (j != 0) && (j != gameBoard.length - 1)) {
            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }

            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }
        }



        //Case 7: The stones are on the bottom row
        else if((i == gameBoard.length - 1) && (j != 0) && (j != gameBoard.length - 1)) {
            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }
        }



        //Case 8: The stones are on the right hand side
        else if((i != 0) && (i != gameBoard.length - 1) && (j == gameBoard.length - 1)) {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }
        }



        //Case 9: The stones have all four neighbors
        else {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }
        }



        //Recursion has ended
        return false;
    }

    /**
     * Checks for captured black stones
     * @param i
     * @param j
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires Testing
     */
    public boolean checkCaptureBlack(int i, int j){
        //Initially set the placed stone as checked
        gameBoard[i][j].setCheckedStone(Stone.CheckedStone.TRUE);


        /* Check if any neighbors are liberties, if they are then no capture.
         * if they aren't then check if they already have been checked.
         * if they have not then recursively call the method again
         */

        //Case 1: The stones are in the top left corner
        if((i == 0) && (j == 0)) {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }
        }



        //Case 2: The stones are in the top right corner
        else if((i == 0) && (j == gameBoard.length - 1)) {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }
        }



        //Case 3: The stones are in the bottom left corner
        else if((i == gameBoard.length - 1) && (j == 0)) {
            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }
        }



        //Case 4: The stones are in the bottom right corner
        else if((i == gameBoard.length - 1) && (j == gameBoard.length - 1)) {
            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }
        }



        //Case 5: The stones are on the left hand side
        else if((i != gameBoard.length - 1) && (i != 0 ) && (j == 0)) {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }
        }


        //Case 6: The stones are on the top row
        else if((i == 0) && (j != 0) && (j != gameBoard.length - 1)) {
            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }

            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }
        }



        //Case 7: The stones are on the bottom row
        else if((i == gameBoard.length - 1) && (j != 0) && (j != gameBoard.length - 1)) {
            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }
        }



        //Case 8: The stones are on the right hand side
        else if((i != 0) && (i != gameBoard.length - 1) && (j == gameBoard.length - 1)) {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }
        }



        //Case 9: The stones have all four neighbors
        else {
            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                hasEmptyNeighbor = true;
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }
        }

        //Recursion has ended
        return false;
    }


    /**
     * This method finds which stones can be captured and captures them
     *
     * @author Jude Gabriel
     *
     * NOTE: Done but requires testing
     */
    public void commenceCapture(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.TRUE){
                    gameBoard[i][j].setStoneColor(Stone.StoneColor.NONE);
                    gameBoard[i][j].setCheckedStone(Stone.CheckedStone.FALSE);
                }
            }
        }
    }


    /**
     * If captures are not possible, reset checked stone values
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public void resetCapture(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                gameBoard[i][j].setCheckedStone(Stone.CheckedStone.FALSE);
            }
        }
    }


    /**
     * Checks if the user recreated a previous position on the board
     * @param x
     * @param y
     * @return true if the board position is repeated
     *
     * @author Jude Gabriel
     *
     * NOTE: Might be fnished.... needs testing
     */
    public boolean checkRepeatedPosition(int x, int y){
        //set a truth counter to zero
        int count = 0;

        //Place the new chip on the board for the given player
        if(isPlayer1){
            gameBoard[x][y].setStoneColor(Stone.StoneColor.BLACK);
        }
        else{
            gameBoard[x][y].setStoneColor(Stone.StoneColor.WHITE);
        }

        //Check if the boards are equal
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j] != stoneCopiesFirst[i][j]){
                    count++;
                }
            }
        }

        //If they are equal reset the board and return true
        if(count == 0){
            gameBoard[x][y].setStoneColor(Stone.StoneColor.NONE);
            return true;
        }

        //If they are not equal update the arrays
        else{
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++) {
                    stoneCopiesFirst[i][j] = stoneCopiesSecond[i][j];
                }
            }
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++) {
                    stoneCopiesSecond[i][j] = gameBoard[i][j];
                }
            }
            gameBoard[x][y].setStoneColor(Stone.StoneColor.NONE);
            return false;
        }
    }


    /**
     * Allows the user to skip a turn by changing the player turn value
     *
     * @return true once the player swap has happened
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public boolean skipTurn() {
        isPlayer1 = !isPlayer1;
        return true;
    }


    /**
     * Allows the user to forfeit the game
     *
     * @return true if player 1 forfeits and false if player 2 does
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public boolean forfeit(){
        if(isPlayer1){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Allows the user to end the game and play the dumb AI
     *
     * @return true when the player selects to play the dumb AI on
     *      their turn
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public boolean playDumbAI(){
        if(isPlayer1){
            forfeit();
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Allows the user to end the game and play the smart AI
     *
     * @return true when the player selects to play the smart AI on
     *          their turn
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public boolean playSmartAI(){
        if(isPlayer1){
            forfeit();
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Allows the user to end the current game and play a 2-player game
     *
     * @return true when the player selects to play a 2-player game on their turn
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public boolean play2Player(){
        if(isPlayer1){
            forfeit();
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Allows user to exit the application on their turn
     *
     * @return true when the player selects to quit the game on their turn
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public boolean quitGame(){
        if(isPlayer1){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Allows the user to start a network game on their turn
     *
     * @return true when the player selects to play a network game on their turn
     *
     * @author Jude Gabriel
     *
     * NOTE: Requires testing
     */
    public boolean networkPlay(){
        if(isPlayer1){
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public String toString(){
        String firstPlayerScore = "Player 1 Score: " + player1Score;
        String secondPlayerScore = "Player 2 Score: " + player2Score;

        int minutes = totalTime / 60;
        int seconds = totalTime - (minutes * 60);

        String timerString = "Time:" + minutes + ":" + seconds;

        String playerTurn;
        if(isPlayer1){
            playerTurn = "Player 1's turn.";
        }
        else{
            playerTurn = "Player 2's turn.";
        }

        String theBoard = "";
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                theBoard +=("    " + i + ", " + j + " is " + gameBoard[i][j].getStoneColor());
            }
        }

        String info = timerString + " " + playerTurn + " " + firstPlayerScore + " " + secondPlayerScore + " " + theBoard;

        return info;
    }

    public void countUpTimer(){

    }

    public void onTick(){

    }


    /**
     * Calculates the Score for player 1 (black stones).
     *
     * @author Jude Gabriel
     */
    public void calculateBlackScore(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.BLACK){
                    player1Score++;
                }
                if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE){
                    if(calculateBlackSurround(i, j)){
                        player1Score++;
                    }
                }
            }
        }
    }


    /**
     * Helper method for calculateBlackScore(). Takes the current location
     * of a liberty and travels left, right, up, and down until a
     * black square is hit
     *
     * @param i     The i index of the liberty
     * @param j     The j index of the liberty
     *
     * @return true if the liberty and neighboring liberties are surrounded
     *          by black stones
     *
     * @author Jude Gabriel
     */
    public boolean calculateBlackSurround(int i, int j){
        int x = i;
        int y = j;
        int count = 0;

        while(x >= 0) {
            if (gameBoard[x][y].getStoneColor() == Stone.StoneColor.BLACK) {
                count++;
                break;
            }
            x--;
        }

        x = i;
        while(x <= gameBoard.length - 1){
            if (gameBoard[x][y].getStoneColor() == Stone.StoneColor.BLACK) {
                count++;
                break;
            }
            x++;
        }

        x = i;
        while(y >= 0) {
            if (gameBoard[x][y].getStoneColor() == Stone.StoneColor.BLACK) {
                count++;
                break;
            }
            y--;
        }

        y = j;
        while(y <= gameBoard.length - 1){
            if (gameBoard[x][y].getStoneColor() == Stone.StoneColor.BLACK) {
                count++;
                break;
            }
            y++;
        }

        if(count == 4){
            return true;
        }
        else{
            return false;
        }
    }



    /**
     * Calculates the Score for player 2 (white stones).
     *
     * @author Jude Gabriel
     */
    public void calculateWhiteScore(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.WHITE){
                    player2Score++;
                }
                if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE){
                    if(calculateWhiteSurround(i, j)){
                        player2Score++;
                    }
                }
            }
        }
    }


    /**
     * Helper method for calculateWhiteScore(). Takes the current location
     * of a liberty and travels left, right, up, and down until a
     * black square is hit
     *
     * @param i     The i index of the liberty
     * @param j     The j index of the liberty
     *
     * @return true if the liberty and neighboring liberties are surrounded
     *          by white stones
     *
     * @author Jude Gabriel
     */
    public boolean calculateWhiteSurround(int i, int j){
        int x = i;
        int y = j;
        int count = 0;

        while(x >= 0) {
            if (gameBoard[x][y].getStoneColor() == Stone.StoneColor.WHITE) {
                count++;
                break;
            }
            x--;
        }

        x = i;
        while(x <= gameBoard.length - 1){
            if (gameBoard[x][y].getStoneColor() == Stone.StoneColor.WHITE) {
                count++;
                break;
            }
            x++;
        }

        x = i;
        while(y >= 0) {
            if (gameBoard[x][y].getStoneColor() == Stone.StoneColor.WHITE) {
                count++;
                break;
            }
            y--;
        }

        y = j;
        while(y <= gameBoard.length - 1){
            if (gameBoard[x][y].getStoneColor() == Stone.StoneColor.WHITE) {
                count++;
                break;
            }
            y++;
        }

        if(count == 4){
            return true;
        }
        else{
            return false;
        }
    }




















    /** HELPER METHODS FOR TESTING **/


    /**
     * Used to test if captures work
     *
     * Remove later
     *
     * @author Jude Gabriel
     */
    public void testCaptures(){
        gameBoard[0][0].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[0][1].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[0][2].setStoneColor(Stone.StoneColor.BLACK);

        gameBoard[1][0].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[1][3].setStoneColor(Stone.StoneColor.BLACK);

        gameBoard[2][0].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[2][4].setStoneColor(Stone.StoneColor.BLACK);

        gameBoard[3][0].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[3][2].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[3][3].setStoneColor(Stone.StoneColor.BLACK);







        gameBoard[1][1].setStoneColor(Stone.StoneColor.WHITE);
        gameBoard[1][2].setStoneColor(Stone.StoneColor.WHITE);

        gameBoard[2][1].setStoneColor(Stone.StoneColor.WHITE);
        gameBoard[2][2].setStoneColor(Stone.StoneColor.WHITE);
        gameBoard[2][3].setStoneColor(Stone.StoneColor.WHITE);

        gameBoard[3][1].setStoneColor(Stone.StoneColor.WHITE);
    }
}

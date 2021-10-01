package com.example.gogamestate;



public class GoGameState {


    //When true it is Player 1's turn
    //When false it is Player 2's turn
    private boolean isPlayer1;

    //Stones array for locations on the board
    private Stone[][] gameBoard;

    //Float values for the users click locations
    private float userXClick;
    private float userYClick;

    //Create the board size
    private int boardSize;

    //Create the players scores
    private int player1Score;
    private int player2Score;

    //Check how many moves have been made so far in the game
    private int totalMoves;

    Stone[][] stoneCopiesFirst;
    Stone[][] stoneCopiesSecond;

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

        //Initialize the players scores
        player1Score = 0;
        player2Score = 0;

        totalMoves = 0;

        stoneCopiesFirst = new Stone[boardSize][boardSize];
        stoneCopiesSecond = new Stone[boardSize][boardSize];
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
     * @author Jude Gabriel
     *
     * NOT FINISHED
     */
    public boolean playerMove(float x, float y) {
        //Find the liberty the user clicked on
        int[] indexVals = findStone(x, y);
        int iIndex = indexVals[0];
        int jIndex = indexVals[1];

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


        if(isPlayer1){
            if(validMove) {
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.BLACK);
                if(checkCaptureBlack(iIndex, jIndex) == false){
                    commenceCapture();
                    //Change the players turn to the next player
                    isPlayer1 = !isPlayer1;
                    totalMoves++;
                    return true;
                }
                else{
                    resetCapture();
                    //Change the players turn to the next player
                    isPlayer1 = !isPlayer1;
                    return false;
                }
            }
            else{
                //Change the players turn to the next player
                isPlayer1 = !isPlayer1;
                return false;
            }
        }

        else if(!isPlayer1){
            if(validMove){
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.WHITE);
                if(checkCaptureBlack(iIndex, jIndex) == false){
                    commenceCapture();
                    //Change the players turn to the next player
                    isPlayer1 = !isPlayer1;
                    totalMoves++;
                    return true;
                }
                else{
                    resetCapture();
                    //Change the players turn to the next player
                    isPlayer1 = !isPlayer1;
                    return false;
                }
            }
            else{
                //Change the players turn to the next player
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
            if (checkCaptureBlack(iIndex, jIndex) == false) {
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.NONE);
                return false;
            }
        }

        if(!isPlayer1){
            gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.WHITE);
            if(checkCaptureWhite(iIndex, jIndex) == false){
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.NONE);
                return false;
            }
        }


        if(checkRepeatedPosition(iIndex, jIndex) == true){
            return false;
        }

        //If all above cases do not return false then it is a valid move
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }

            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j + 1);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.WHITE)) {
                    checkCaptureWhite(i - 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }

            if (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i][j + 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j + 1);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
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
                return true;
            } else {
                if ((gameBoard[i + 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i + 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i + 1, j);
                }
            }

            if (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i][j - 1].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i][j - 1].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i, j - 1);
                }
            }

            if (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.NONE) {
                return true;
            } else {
                if ((gameBoard[i - 1][j].getCheckedStone() == Stone.CheckedStone.FALSE) &&
                        (gameBoard[i - 1][j].getStoneColor() == Stone.StoneColor.BLACK)) {
                    checkCaptureBlack(i - 1, j);
                }
            }

            if (gameBoard[i][j + 1].getStoneColor() == Stone.StoneColor.NONE) {
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
     * If ccaptures are not possible, reset checked stone values
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
     * @return
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

        //If they are equal reset the board and return false
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
}

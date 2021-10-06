package com.example.gogamestate;


import android.os.CountDownTimer;

public class GoGameState {
    /* GoGameState private instance variables */
    private boolean isPlayer1;           //boolean value for which player's turn it is
    private Stone[][] gameBoard;         //Stones array for locations on the board
    private final float userXClick;            //The x coordinate the user clicks
    private final float userYClick;            //The y coordinate the user clicks
    private final int boardSize;         //The dimensions of the board
    private int player1Score;            //Stores Player 1's score
    private int player2Score;            //Stores Player 2's score
    private boolean gameOver;            //Tracks whether the game is over
    private final CountDownTimer countUpTimer; //Timer for the game
    private int totalMoves;              //Total number of moves made in game
    private Stone[][] stoneCopiesFirst;  //Stores the board from two moves ago
    private Stone[][] stoneCopiesSecond; //Stores the board from one move ago
    private int totalTime;               //Total time elapsed in the game
    private int numSkips;                //Tracks whether two consecutive skips

    /** GoGameState
     * Constructor for the GoGameStateClass
     *
     * @author Jude Gabriel
     * @author Natalie Tashchuk
     * @author Mia Anderson
     * @author Brynn Harrington
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
        userYClick = -1;

        //Initialize the players scores and total number of moves
        player1Score = 0;
        player2Score = 0;
        totalMoves = 0;

        //Initialize game over to false and number of skips to zero
        gameOver = false;
        numSkips = 0;

        //Initialize the arrays that store former board positions
        stoneCopiesFirst = new Stone[boardSize][boardSize];
        stoneCopiesSecond = new Stone[boardSize][boardSize];
        totalTime = 0;

        //Use lambda function to redefine the timer for the game
        countUpTimer = new CountDownTimer(30000, 1000) {
            //override the onTick method
            @Override
            public void onTick(long millisUntilFinish){
                totalTime++;
            }

            //override the onFinish method
            @Override
            public void onFinish(){
                countUpTimer.start();
            }
        };
        //Initialize the timer
        countUpTimer.start();
    }


    /**
     * GoGameState
     * Copy Constructor for the Go GameState
     * Performs a deep copy of the Go GameState to counter
     * possible cheating in the game.
     *
     * @param gs - the game state to copy
     * @author Jude Gabriel
     * @author Natalie Tashchuk
     * @author Mia Anderson
     * @author Brynn Harrington
     * <p>
     * TODO: requires testing (verify with teammates)
     */
    public GoGameState(GoGameState gs) {
        //Initialize the instance variables to the current game state
        this.boardSize = gs.boardSize;
        this.userXClick = gs.userXClick;
        this.userYClick = gs.userYClick;
        this.gameBoard = new Stone[boardSize][boardSize];
        this.gameBoard = gs.gameBoard;
        this.player1Score = gs.player1Score;
        this.player2Score = gs.player2Score;
        this.gameOver = gs.gameOver;
        this.numSkips = gs.numSkips;
        this.isPlayer1 = gs.isPlayer1;
        this.totalMoves = gs.totalMoves;
        this.totalTime = gs.totalTime;

        //Initialize the timer for the deep copy of the game
        this.countUpTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totalTime++;
            }

            @Override
            public void onFinish() {
                countUpTimer.start();
            }
        };

        //Initialize the counter for the game
        countUpTimer.start();
    }


    /** initializeArray
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


    /** playerMove
     * Calculate the player move and then re-change the color of the stones
     *
     * @param x     x location of the user or AI click
     * @param y     y location of the user or AI click
     * @return true if it is a valid player move, false otherwise
     *
     * @author Jude Gabriel
     */
    public boolean playerMove(float x, float y) {
        //Find the liberty the user clicked on
        int[] indexVals = findStone(x, y);
        int iIndex = indexVals[0];
        int jIndex = indexVals[1];

        //Determine the current player's color
        Stone.StoneColor currStoneColor;
        if (isPlayer1) currStoneColor = Stone.StoneColor.BLACK;
        else currStoneColor = Stone.StoneColor.WHITE;

        //If total moves is 1, copy to track board from two moves ago
        if (totalMoves == 1) {
            stoneCopiesFirst = deepCopyArray(gameBoard);
        }

        //If total moves is 2, copy to track board from one move ago
        if (totalMoves == 2) {
            stoneCopiesSecond = deepCopyArray(gameBoard);
        }

        //Check if the move is valid
        boolean validMove = isValidLocation(iIndex, jIndex);

        //Place stone if valid
        if (validMove) {
            //Update the liberty's color based on the current player's move
            gameBoard[iIndex][jIndex].setStoneColor(currStoneColor);

            //Determine if capture is possible
            iterateAndCheckCapture(iIndex, jIndex);

            //Capture if possible
            commenceCapture(currStoneColor);

            //Reset the capture and player
            resetCapture();
            isPlayer1 = !isPlayer1;

            //Reset the score in case of captures and recalculate score
            player1Score = 0;
            player1Score += calculateScore(Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);

            player2Score = 0;
            player2Score += calculateScore(Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);

            //Increment number of moves
            totalMoves++;

            //Return true since valid move was made
            return true;
        }

     /*   //Player1's move
        if(isPlayer1) {
            placeStone(iIndex, jIndex);
*//*            if (validMove) {
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.BLACK);
                for(int i = 0; i < boardSize; i++){
                    for(int j = 0; j < boardSize; j++){
                        if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE){
                            if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE){
                                checkCapture(i, j, Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
                            }
                        }
                    }
                }*//*
                commenceCapture(Stone.StoneColor.WHITE);
                resetCapture();
                isPlayer1 = !isPlayer1;
                player1Score = 0;
                player2Score = 0;
                totalMoves++;
                player1Score += calculateScore(Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
                player2Score += calculateScore(Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);
                numSkips = 0;
                return true;
                //}
        }

        //Player2's move
        else{
            placeStone(iIndex, jIndex);
            *//*
            if(validMove){
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.WHITE);
                for(int i = 0; i < boardSize; i++){
                    for(int j = 0; j < boardSize; j++){
                        if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE){
                            if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE){
                                checkCapture(i, j, Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);
                            }
                        }
                    }
                }*//*
                commenceCapture(Stone.StoneColor.BLACK);
                resetCapture();
                isPlayer1 = !isPlayer1;
                player1Score = 0;
                player2Score = 0;
                totalMoves++;
                player1Score += calculateScore(Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
                player2Score += calculateScore(Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);
                numSkips = 0;
                return true;
            }*/
        //}

        // otherwise return false
        return false;
    }


    /** findStone
     * Finds which index the user clicked on in the stones array
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
     * iterateAndCheckCapture
     * A helper function that iterates through the board and checks
     * whether a capture is possible in the given place on the board.
     *
     * @param x the x-coordinate of where the user clicked
     * @param y the y-coordinate of where the user clicked
     * @author Brynn Harrington
     */
    public void iterateAndCheckCapture(int x, int y) {
        //Initialize a variable to store the stone color of current player
        //and the opponent's stone color
        Stone.StoneColor currStoneColor, oppStoneColor;
        if (isPlayer1) {
            currStoneColor = Stone.StoneColor.BLACK;
            oppStoneColor = Stone.StoneColor.WHITE;
        } else {
            currStoneColor = Stone.StoneColor.WHITE;
            oppStoneColor = Stone.StoneColor.BLACK;
        }

        //Iterate through the board and determine whether they player can capture
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                //Check if current position is empty
                if (gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE) {
                    //Verify this position has not been checked for a capture already
                    if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                        //Check if the player can capture from this position
                        checkCapture(i, j, currStoneColor, oppStoneColor);
                    }
                }
            }
        }
    }

    /**
     * placeStone
     * A helper function that updates the board to the stone based
     * on the current player and checks if the player is able to
     * capture based off that move. This function assumes the location
     * of the move is valid.
     *
     * @param x the x-coordinate of where the user clicked
     * @param y the y-coordinate of where the user clicked
     * @author Brynn Harrington
     * <p>
     * TODO: test whether this function works
     */
    public void placeStone(int x, int y) {
        //Initialize a variable to store the stone color of current player
        //and the opponent's stone color
        Stone.StoneColor currStoneColor, oppStoneColor;
        if (isPlayer1) {
            currStoneColor = Stone.StoneColor.BLACK;
            oppStoneColor = Stone.StoneColor.WHITE;
        } else {
            currStoneColor = Stone.StoneColor.WHITE;
            oppStoneColor = Stone.StoneColor.BLACK;
        }
        //Set the liberty to the current player's color
        gameBoard[x][y].setStoneColor(currStoneColor);

        //Iterate through the board and determine whether they player can capture
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                //Check if current position is empty
                if (gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE) {
                    //Verify this position has not been checked for a capture already
                    if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                        //Check if the player can capture from this position
                        checkCapture(i, j, currStoneColor, oppStoneColor);
                    }
                }
            }
        }
    }


    /** isValidLocation
     * This will check if the user places the stone in a valid position
     *
     * @return true if the user places a stone in a valid location
     *
     * @author Jude Gabriel
     *
     *
     */
    public boolean isValidLocation(int iIndex, int jIndex) {
        boolean capCheck = false;
        Stone[][] copyArr = deepCopyArray(gameBoard);

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
            capCheck = selfCapture(iIndex, jIndex, Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);
            gameBoard = deepCopyArray(copyArr);
            if(capCheck){
                return false;
            }
        }

        if(!isPlayer1) {
            gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.WHITE);
            capCheck = selfCapture(iIndex, jIndex, Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
            gameBoard = deepCopyArray(copyArr);
            if(capCheck){
                return false;
            }
        }

        if(totalMoves >= 2) {
            if (checkRepeatedPosition(iIndex, jIndex) == true) {
                return false;
            }
        }

        //If all above cases do not return false then it is a valid move
        resetCapture();
        return true;
    }

    /** selfCapture
     * Checks if a stone can capture itself
     * @param x     i index of stone
     * @param y     j index of stone
     * @param checkCol      Capturing stone color
     * @param capCol        Captured stone color
     *
     * @return true if the stone captures itself
     *
     * @author Jude Gabriel
     */
    public boolean selfCapture(int x, int y, Stone.StoneColor checkCol, Stone.StoneColor capCol){
        //Iterate through the board
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                //Determine if the liberty is empty
                if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE){
                    //Determine if stone was already checked
                    if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                        //Determine if capture possible
                        checkCapture(i, j, checkCol, capCol);
                    }
                }
            }
        }
        //If able to capture, capture the stone and reset it
        commenceCapture(capCol);
        resetCapture();

        //Return whether the stone liberty would capture itself
        return gameBoard[x][y].getStoneColor() != capCol;
    }


    /** checkCapture
     * Checks for captured stones
     *
     * @param i     i index of the stone
     * @param j     j index of the stone
     * @param checkCol  Color of capturing stones
     * @param capCol    Color of captured stones
     *
     * @author Jude Gabriel
     * @modified Brynn Harrington
     */
    public void checkCapture(int i, int j, Stone.StoneColor checkCol, Stone.StoneColor capCol) {
        //TODO: determine if this can be replaced with isValidLocation method

        //Verify the location is valid, the stone has not been checked. and the stone color
        //is not what is being checked
        if (i < 0 || j < 0 || i > gameBoard.length - 1 || j > gameBoard.length - 1
                || gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.TRUE
                || gameBoard[i][j].getStoneColor() == checkCol) {
            return;
        }

        //If the stone has not been checked yet, check if capture possible
        if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
            //Set the checked to true
            gameBoard[i][j].setCheckedStone(Stone.CheckedStone.TRUE);
            //Check around to determine if able to capture
            checkCapture(i + 1, j, checkCol, capCol);
            checkCapture(i - 1, j, checkCol, capCol);
            checkCapture(i, j + 1, checkCol, capCol);
            checkCapture(i, j - 1, checkCol, capCol);
        }
    }


    /** commerceCapture
     * This method finds which stones can be captured and captures them
     *
     * @author Jude Gabriel
     *
     * TODO: Testing
     */
    public void commenceCapture(Stone.StoneColor capCol){
        //Iterate through the board
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++) {
                //If the stone hasn't been checked and the color is the capture color,
                //capture the stone
                if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                    if (gameBoard[i][j].getStoneColor() == capCol) {
                        gameBoard[i][j].setStoneColor(Stone.StoneColor.NONE);
                    }
                }
            }
        }
    }


    /** resetCapture
     * If captures are not possible, reset checked stone values
     *
     * @author Jude Gabriel
     *
     * TODO: Testing
     */
    public void resetCapture(){
        //Iterate through the board and reset checked stone to false
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                gameBoard[i][j].setCheckedStone(Stone.CheckedStone.FALSE);
            }
        }
    }


    /** checkRepeatedPosition
     * Checks if the user recreated a previous position on the board
     *
     * @param x - the x-coordinate of where the user clicked
     * @param y - the y-coordinate of where the user clicked
     * @return true if the board position is repeated
     *
     * @author Jude Gabriel
     *
     * TODO: Testing
     */
    public boolean checkRepeatedPosition(int x, int y){
        //Set a truth counter to zero
        int count = 0;

        //Create a deep copy of the copy array
        Stone[][] copyArray = deepCopyArray(gameBoard);

        //Place the new chip on the board for the given player
        if(isPlayer1){
            gameBoard[x][y].setStoneColor(Stone.StoneColor.BLACK);

            //Iterate through the board
            for(int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    //Verify liberty is empty and the stone has not been checked
                    // TODO: create helper function since this line of code is used often
                    if (gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE) {
                        if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                            //Check if able to capture
                            checkCapture(i, j, Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
                        }
                    }
                }
            }
            //Capture the stone is able to
            commenceCapture(Stone.StoneColor.WHITE);
        }
        else{
            gameBoard[x][y].setStoneColor(Stone.StoneColor.WHITE);

            //Iterate through the board
            for(int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    //Verify liberty is empty and the stone has not been checked
                    if (gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE) {
                        if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                            //Check if able to capture
                            checkCapture(i, j, Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);
                        }
                    }
                }
            }
            //Capture stone if able to
            commenceCapture(Stone.StoneColor.BLACK);
        }
        resetCapture();

        //Check if the boards are equal
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard[i][j].getStoneColor() != stoneCopiesFirst[i][j].getStoneColor()) {
                    count++;
                }
            }
        }

        //If they are equal reset the board and return true
        if(count == 0){
            gameBoard = deepCopyArray(copyArray);
            return true;
        }

        //If they are not equal update the arrays
        else{
            stoneCopiesFirst = deepCopyArray(stoneCopiesSecond);
            stoneCopiesSecond = deepCopyArray(gameBoard);

            gameBoard = deepCopyArray(copyArray);
            return false;
        }
    }


    /** deepCopyArray
     * Creates a copy of the array
     *
     * @param firstArr The array to copy
     *
     * @return the copied array
     *
     * @author Jude Gabriel
     */
    public Stone[][] deepCopyArray(Stone[][] firstArr) {
        //Create a new copy array
        Stone[][] copyArr = new Stone[boardSize][boardSize];

        //Iterate through the current board size and create empty board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                copyArr[i][j] = new Stone(0, 0);
            }
        }

        //Iterate through the board and perform a deep copy of current board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (firstArr[i][j] != null) {
                    copyArr[i][j].setRadius(firstArr[i][j].getRadius());
                    copyArr[i][j].setxLeft(firstArr[i][j].getxLeft());
                    copyArr[i][j].setxLocation(firstArr[i][j].getxLocation());
                    copyArr[i][j].setxRight(firstArr[i][j].getxRight());
                    copyArr[i][j].setyBottom(firstArr[i][j].getyBottom());
                    copyArr[i][j].setyTop(firstArr[i][j].getyTop());
                    copyArr[i][j].setyLocation(firstArr[i][j].getyLocation());
                    copyArr[i][j].setStoneColor(firstArr[i][j].getStoneColor());
                    copyArr[i][j].setCheckedStone(firstArr[i][j].getCheckedStone());
                }
            }
        }

        //Return the new array
        return copyArr;
    }


    /** skipTurn
     * Allows the user to skip a turn by changing the player turn value
     *
     * @return true once the player swap has happened
     *
     * @author Jude Gabriel
     *
     * TODO: Requires testing - need to track if each player passed
     * TODO: add new variable that tracks if to consecutive passes
     */
    public boolean skipTurn() {
        //Reset the current player to the opponent
        isPlayer1 = !isPlayer1;
        //Increment number of skips
        numSkips++;
        //If number of skips is two, the game is over
        if (numSkips == 2) gameOver = true;
        return true;
    }


    /** forfeit
     * Allows the user to forfeit the game
     *
     * @return true if player 1 forfeits and false if player 2 does
     *
     * @author Jude Gabriel
     *
     * TODO: Testing - NEED TO VERIFY WORKS FOR HUMAN V. HUMAN
     *
     */
    public boolean forfeit(){
        //Check it is player 1's turn and sets gameOver to true
        if(isPlayer1){
            gameOver = true;
            return true;
        }
        return false;
    }


    /** playDumbAI
     * Allows the user to end the game and play the dumb AI
     *
     * @return true when the player selects to play the dumb AI on
     *      their turn
     *
     * @author Jude Gabriel
     *
     * TODO: Requires testing
     */
    public boolean playDumbAI(){
        //Checks if player 1 forfeits and plays the dumb AI
        if(isPlayer1){
            forfeit();
            return true;
        }
        return false;
    }


    /** playSmartAI
     * Allows the user to end the game and play the smart AI
     *
     * @return true when the player selects to play the smart AI on
     *          their turn
     *
     * @author Jude Gabriel
     *
     * TODO: Requires testing
     */
    public boolean playSmartAI(){
        //Checks if player 1 forfeits and plays the smart AI
        if(isPlayer1){
            forfeit();
            return true;
        }
        return false;
    }


    /** play2Player
     * Allows the user to end the current game and play a 2-player game
     *
     * @return true when the player selects to play a 2-player game on their turn
     *
     * @author Jude Gabriel
     *
     * TODO: Requires testing
     */
    public boolean play2Player(){
        //Checks if player 1 forfeits and plays another human
        if(isPlayer1){
            forfeit();
            return true;
        }
        return false;
    }


    /** quitGame
     * Allows user to exit the application on their turn
     *
     * @return true when the player selects to quit the game on their turn
     *
     * @author Jude Gabriel
     *
     * TODO: Requires testing
     */
    public boolean quitGame() {
        //Checks if player 1 quits the game
        return isPlayer1;
    }


    /** networkPlay
     * Allows the user to start a network game on their turn
     *
     * @return true when the player selects to play a network game on their turn
     *
     * @author Jude Gabriel
     *
     * TODO: Requires testing
     */
    public boolean networkPlay() {
        //Checks if player 1 is playing network play
        return isPlayer1;
    }

    /** toString
     * Turns all relevant game information into a string
     *
     * @return the string of game info
     *
     * @author Jude Gabriel
     * @author Mia Anderson
     */
    @Override
    public String toString(){
        //Convert the player's scores to a string
        String firstPlayerScore = "Player 1 Score: " + player1Score;
        String secondPlayerScore = "Player 2 Score: " + player2Score;

        //Calculate the time elapsed
        int minutes = totalTime / 60;
        int seconds = totalTime - (minutes * 60);

        //Convert calculations to a string
        String timerString = "Time:" + minutes + ":" + seconds;

        //Display which player's turn it is
        String playerTurn;
        if(isPlayer1){
            playerTurn = "Player 1's turn.";
        } else {
            playerTurn = "Player 2's turn.";
        }

        //Convert the board information to a string
        String theBoard = "";
        for (int i = 0; i < boardSize; i++) {
            theBoard += "\n";
            for (int j = 0; j < boardSize; j++) {
                theBoard += ("\t" + i + ", " + j + " is " + gameBoard[i][j].getStoneColor());
            }
        }

        //If game is over, only display that score
        if (gameOver) return timerString + " " + playerTurn + " " + firstPlayerScore + " " +
                secondPlayerScore + theBoard + " \nGAME OVER";

        //Convert all information and return that string
        return timerString + " " + playerTurn + " " + firstPlayerScore + " " +
                secondPlayerScore + " " + theBoard;
    }


    /**
     * calculateScore
     * Calculates the current score
     *
     * @return totalScore - the total score for the player
     * @author Jude Gabriel
     * @author Mia Anderson
     * @author Natalie Taschuck
     * @author Brynn Harrington
     */
    public int calculateScore(Stone.StoneColor colorToScore, Stone.StoneColor otherColor) {
        //Initialize the total score to zero
        int totalScore = 0;

        //Iterate through the board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                //See if there are empty liberties surrounding
                if (gameBoard[i][j].getStoneColor() == otherColor) {
                    checkCapture(i, j, colorToScore, otherColor);
                }
            }
        }

        // commented out for testing purposes
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                //If empty liberties, add to current score
                if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                    totalScore++;
                }
            }
        }

        //Reset the capture
        resetCapture();

        //Return the total Score
        return totalScore;
    }


    /*** HELPER METHODS FOR TESTING ***/
    //TODO: ADD MORE TESTS FOR SMART AND DUMB AI

    /**
     * testCaptures
     * Used to test if captures works
     *
     * @author Jude Gabriel
     */
    public void testCaptures() {
        gameBoard[0][3].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[2][0].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[1][3].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[2][3].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[2][2].setStoneColor(Stone.StoneColor.BLACK);
    }

    /**
     * testForfeit
     * Used to test if forfeit works
     *
     * @author Brynn Harrington
     */
    public void testForfeit() {
        // dummy values for the board
        gameBoard[0][3].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[2][0].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[1][3].setStoneColor(Stone.StoneColor.WHITE);
        gameBoard[0][0].setStoneColor(Stone.StoneColor.WHITE);

        // calculate the current score
        //this.calculateScore(Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
        //this.calculateScore(Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);

        // forfeit
        this.forfeit();
    }
}

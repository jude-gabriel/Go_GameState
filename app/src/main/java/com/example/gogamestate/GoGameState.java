package com.example.gogamestate;


import android.os.CountDownTimer;

public class GoGameState {


    /* GoGameState private instance variables */
    private boolean isPlayer1;  //boolean value for which player's turn it is
    private Stone[][] gameBoard;    //Stones array for locations on the board
    private float userXClick;   //The x coordinate the user clicks
    private float userYClick;   //The y coordinate the user clicks
    private final int boardSize;  //The dimensions of the board
    private int player1Score;   //Stores Player 1's score
    private int player2Score;   //Stores Player 2's score
    private int totalMoves;     //Total number of moves made in game
    private Stone[][] stoneCopiesFirst; //Stores the board from two moves ago
    private Stone[][] stoneCopiesSecond;    //Stores the board from one move ago
    private  int totalTime;
    private CountDownTimer countUpTimer;


    /**
     * Constructor for the GoGameStateClass
     *
     * @author Jude Gabriel
     * @author Natalie Tashchuk
     * @author Mia Anderson
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

        //Initialize the arrays that store former board positions
        stoneCopiesFirst = new Stone[boardSize][boardSize];
        stoneCopiesSecond = new Stone[boardSize][boardSize];
        totalTime = 0;

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
        this.totalTime = gs.totalTime;
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
        countUpTimer.start();

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

        if(totalMoves == 1){
            stoneCopiesFirst = deepCopyArray(gameBoard);
        }
        if (totalMoves == 2){
            stoneCopiesSecond = deepCopyArray(gameBoard);
        }

        //Check if the move is valid
        boolean validMove = isValidLocation(iIndex, jIndex);


        if(isPlayer1) {
            if (validMove) {
                gameBoard[iIndex][jIndex].setStoneColor(Stone.StoneColor.BLACK);
                for(int i = 0; i < boardSize; i++){
                    for(int j = 0; j < boardSize; j++){
                        if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE){
                            if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE){
                                checkCapture(i, j, Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
                            }
                        }
                    }
                }
                commenceCapture(Stone.StoneColor.WHITE);
                resetCapture();
                isPlayer1 = !isPlayer1;
                player1Score = 0;
                player2Score = 0;
                totalMoves++;
                player1Score += calculateScore(Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
                player2Score += calculateScore(Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);
                return true;
                }
            else{
                return false;
            }
        }

        else{
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
                }
                commenceCapture(Stone.StoneColor.BLACK);
                resetCapture();
                isPlayer1 = !isPlayer1;
                player1Score = 0;
                player2Score = 0;
                totalMoves++;
                player1Score += calculateScore(Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
                player2Score += calculateScore(Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);
                return true;
            }
            else{

                return false;
            }
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


    /**
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
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE){
                    if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE){
                        checkCapture(i, j, checkCol, capCol);
                    }
                }
            }
        }
        commenceCapture(capCol);
        resetCapture();

        if(gameBoard[x][y].getStoneColor() == capCol){
            return false;
        }
        else{
            return true;
        }
    }


    /**
     * Checks for captured stones
     *
     * @param i     i index of the stone
     * @param j     j index of the stone
     * @param checkCol  Color of capturing stones
     * @param capCol    Color of captured stones
     *
     * @author Jude Gabriel
     */
    public void checkCapture(int i, int j, Stone.StoneColor checkCol, Stone.StoneColor capCol){
        if(i < 0 || j < 0 || i > gameBoard.length - 1 || j > gameBoard.length - 1){
            return;
        }
        if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.TRUE){
            return;
        }
        if(gameBoard[i][j].getStoneColor() == checkCol){
            return;
        }
        else{
            if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE){
                gameBoard[i][j].setCheckedStone(Stone.CheckedStone.TRUE);
                checkCapture(i + 1, j, checkCol, capCol);
                checkCapture(i - 1, j, checkCol, capCol);
                checkCapture(i, j + 1, checkCol, capCol);
                checkCapture(i, j - 1, checkCol, capCol);
            }
            else{
                return;
            }
        }
    }


    /**
     * This method finds which stones can be captured and captures them
     *
     * @author Jude Gabriel
     *
     * NOTE: Done but requires testing
     */
    public void commenceCapture(Stone.StoneColor capCol){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE){
                    if(gameBoard[i][j].getStoneColor() == capCol){
                        gameBoard[i][j].setStoneColor(Stone.StoneColor.NONE);
                    }
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

        Stone[][] copyArray = deepCopyArray(gameBoard);

        //Place the new chip on the board for the given player
        if(isPlayer1){
            gameBoard[x][y].setStoneColor(Stone.StoneColor.BLACK);

            for(int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE) {
                        if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                            checkCapture(i, j, Stone.StoneColor.BLACK, Stone.StoneColor.WHITE);
                        }
                    }
                }
            }
            commenceCapture(Stone.StoneColor.WHITE);
            resetCapture();
        }
        else{
            gameBoard[x][y].setStoneColor(Stone.StoneColor.WHITE);

            for(int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (gameBoard[i][j].getStoneColor() == Stone.StoneColor.NONE) {
                        if (gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE) {
                            checkCapture(i, j, Stone.StoneColor.WHITE, Stone.StoneColor.BLACK);
                        }
                    }
                }
            }
            commenceCapture(Stone.StoneColor.BLACK);
            resetCapture();
        }

        //Check if the boards are equal
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j].getStoneColor() != stoneCopiesFirst[i][j].getStoneColor()){
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


    /**
     * Creates a copy of the array
     *
     * @param firstArr      The array to copy
     *
     * @return the copied array
     *
     * @author Jude Gabriel
     */
    public Stone[][] deepCopyArray(Stone[][] firstArr){
        Stone[][] copyArr = new Stone[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                copyArr[i][j] = new Stone(0, 0);
            }
        }

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(firstArr[i][j] != null) {
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

        return copyArr;
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

    /**
     * Turns all relevant game information into a string
     *
     * @return the string of game info
     *
     * @author Jude Gabriel
     * @author Mia Anderson
     */
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
        String info = timerString + " " + playerTurn + " " + firstPlayerScore + " " +
                secondPlayerScore + " " + theBoard;
        return info;
    }












    public int calculateScore(Stone.StoneColor colorToScore, Stone.StoneColor otherColor){
        int totalScore = 0;

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j].getStoneColor() == otherColor){
                    checkCapture(i, j, colorToScore, otherColor);
                }
            }
        }

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(gameBoard[i][j].getCheckedStone() == Stone.CheckedStone.FALSE){
                    totalScore++;
                }
            }
        }


        resetCapture();


        return totalScore;
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
        gameBoard[0][3].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[2][0].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[1][3].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[2][3].setStoneColor(Stone.StoneColor.BLACK);
        gameBoard[2][2].setStoneColor(Stone.StoneColor.BLACK);


    }
}

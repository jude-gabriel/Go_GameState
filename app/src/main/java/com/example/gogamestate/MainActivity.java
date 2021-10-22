/**
 * MainActivity.java
 *
 * @author Natalie Tashchuk
 * @author Mia Anderson
 * @author Brynn Harrington
 * @author Jude Gabriel
 */



package com.example.gogamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity{

    public int counter = 0;

    GoGameState firstInstance;
    GoGameState secondInstance;
    GoGameState thirdInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // initialize a new game state
        firstInstance = new GoGameState();
        secondInstance = new GoGameState(firstInstance);
        thirdInstance = new GoGameState();

        // display the information test
        EditText theText = findViewById(R.id.infoText);

        // initialize objects for the buttons to test
        Button runTest = findViewById(R.id.runTest);

        /** ClickOnListener
         * Override the run test for click using a lambda function
         * @author Jude Gabriel
         * @author Brynn Harrington
         * @author Mia Anderson
         */
        runTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstInstance != null && secondInstance != null && thirdInstance != null) {

                    //Modified to make a string resource
                    theText.setText(R.string.runTestsString);
                    //Utilize a switch on counter for tests
                    switch (counter) {

                        //Test capturing
                        case 0:
                            //Initialize the empty board
                            theText.append("Setup Phase");

                            theText.setText(firstInstance.toString());
                            theText.setText(secondInstance.toString());
                            theText.setText(thirdInstance.toString());
                            counter++;
                            break;

                        case 1:
                            //Test a handicap at beginning of game
                            theText.append("Handicap Phase: ");

                            firstInstance.setHandicap();
                            firstInstance.setHandicap();

                            theText.append(firstInstance.toString());
                            theText.append(secondInstance.toString());
                            theText.append(thirdInstance.toString());
                            counter++;
                            break;


                        //Set up the capture
                        case 2:
                            //Prepopulate the board to test a capture
                            firstInstance.resetStones();
                            firstInstance.testCapture();

                            theText.append("Capture Phase: ");

                            //Display on the screen
                            theText.append(firstInstance.toString());
                            theText.append(secondInstance.toString());
                            theText.append(thirdInstance.toString());

                            //Update the counter for the next test and break
                            counter++;
                            break;


                        //Place a stone to complete the capture
                        case 3:
                            //Place a black stone to complete a capture
                            boolean works = firstInstance.playerMove(2, 2);
                            works = firstInstance.playerMove(7, 7);

                            theText.append("Capture Phase: ");

                            //Display on the screen
                            theText.append(firstInstance.toString());
                            theText.append(secondInstance.toString());
                            theText.append(thirdInstance.toString());

                            //Increment the counter and break
                            counter++;
                            break;


                        //Set up the board and make the first capture for a repeated move
                        case 4:
                            //Reset the board to all blank to initialize for repeated test
                            firstInstance.resetStones();
                            firstInstance.testRepeatedPosition();

                            //Black makes the first capture
                            firstInstance.playerMove(2,1);

                            theText.append("Repeated Position Phase: ");

                            //Display on the screen
                            theText.append(firstInstance.toString());
                            theText.append(secondInstance.toString());
                            theText.append(thirdInstance.toString());

                            //Increment the counter and break
                            counter++;
                            break;


                        //Have white make a move such that the board will be repeated
                        case 5:
                            //White makes the next move
                            firstInstance.playerMove(1, 1);

                            theText.append("Repeated Position Phase: ");

                            //Display on screen. Is the same since move was invalid
                            theText.append(firstInstance.toString());
                            theText.append(secondInstance.toString());
                            theText.append(thirdInstance.toString());

                            //Increment the counter and break
                            counter++;
                            break;


                        //Test skipping twice
                        case 6:
                            //Attempt to skip twice
                            firstInstance.skipTurn();
                            firstInstance.skipTurn();

                            theText.append("Skip Turn Forfeit Phase: ");

                            theText.append(firstInstance.toString());
                            theText.append(secondInstance.toString());
                            theText.append(thirdInstance.toString());


                            //Increment the counter and break
                            counter++;
                            break;


                        //Test forfeiting
                        case 7:
                            //Attempt to forfeit on the first instance
                            firstInstance.testForfeit();

                            theText.append("Player Forfeits Phase: ");

                            //Display on screen game will be over
                            theText.append(firstInstance.toString());
                            theText.append(secondInstance.toString());
                            theText.append(thirdInstance.toString());

                            //Increment the counter and break
                            counter++;
                            break;

                        //All tests are complete. Let the user know
                        default:
                            theText.append("Restart Application to Run Tests Again");
                            break;
                    }
                }
            }
        });
    }
}

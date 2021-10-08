package com.example.gogamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/* ERRORS
 * TODO - when game finishes, needs to set the game state to null
 */
public class MainActivity extends AppCompatActivity{

    public int counter = 0;
    //public float x;
    //public float y;

    GoGameState firstInstance;
    GoGameState secondInstance;
    GoGameState thirdInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize a new main activity object
        MainActivity main = new MainActivity();

        // initialize a new game state
        firstInstance = new GoGameState();
        secondInstance = new GoGameState(firstInstance);
        thirdInstance = new GoGameState();

        // display the information test
        TextView theText = findViewById(R.id.infoText);

        // initialize objects for the buttons to test
        Button runTest = findViewById(R.id.runTest);

        /** ClickOnListener
         * Override the run test for click using a lambda function
         * @author Jude Gabriel
         * @author Brynn Harrington
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
                            theText.setText(firstInstance.toString());
                            CharSequence secondInst = secondInstance.toString();
                            theText.append(secondInst);
                            counter++;
                            break;

                        //Populate the board with one stone away from capturing
                        case 1:
                            firstInstance.testCapture();
                            CharSequence firstinst = firstInstance.toString();
                            theText.append(secondInstance.toString());
                            counter++;
                            break;

                        //MAKE MOVES
                        case 2:
                            counter++;
                            break;

                        //MAKE MOVES
                        case 3:
                            counter++;
                            break;

                        //MAKE MOVES
                        case 4:
                            //Update the text for each instance
                            theText.append(firstInstance.toString());
                            theText.append(secondInstance.toString());
                            theText.append(thirdInstance.toString());
                            counter++;
                            break;
                        //Test forfeiting
                        case 5:
                            firstInstance.testForfeit();
                            counter++;
                            break;
                    }
                }
            }
        });
    }
}







//Make a test for the capture
      //In the testing method initializes surrounded stones and all but one surrounding stone
      //Then on click place the last surrounding stones

//make a test for repeated board positions
      //in testing method initialize a set of positions
      //in on click play two consecutive moves so that it repeats the first position

//Make a test for forfeit
    //Show that this ends game

//Make a test for skip turn
       //show two consecutive skips ends the game
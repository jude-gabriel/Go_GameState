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
    public float x;
    public float y;

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
        Button forfeit = findViewById(R.id.forfeitTest);

        // override the run test for click
        // @author Jude Gabriel
        runTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstInstance != null && secondInstance != null && thirdInstance != null) {

                    theText.setText("Click to run tests");

                    //Initialize the empty board
                    if (counter == 0) {
                        theText.setText(firstInstance.toString());
                        CharSequence secondInst = secondInstance.toString();
                        theText.append(secondInst);
                    }

                    //Prepopulate with one stone missing from capture
                    if(counter == 1){
                        firstInstance.testCaptures();
                        CharSequence firstinst = firstInstance.toString();
                    }

                    if(counter == 2) {
                        //MAKE MOVES
                    }
                    if(counter == 3) {
                        //MAKE MOVES
                    }
                    if (counter == 4) {
                        //MAKE MOVES
                        theText.append((CharSequence) firstInstance.toString());
                        theText.append((CharSequence) secondInstance.toString());
                        theText.append((CharSequence) thirdInstance.toString());
                    }
                    counter++;
                }
            }
        });
    }
}
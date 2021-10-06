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
public class MainActivity extends AppCompatActivity {

    // initialize the counter for the number of clicks
    public int counter = 0;

    // initialize the x and y coordinates
    public float x;
    public float y;

    GoGameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize a new main activity object
        MainActivity main = new MainActivity();

        // initialize a new game state
        gameState = new GoGameState();

        // display the information test
        TextView theText = findViewById(R.id.infoText);

        // initialize objects for the buttons to test
        Button runTest = findViewById(R.id.runTest);
        Button forfeit = findViewById(R.id.forfeitTest);

        // override the run test for click
        // @author Jude Gabriel
        // @author Brynn Harrington
        runTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameState != null) {

                    //Initialize the empty board
                    if (counter == 0) {
                        theText.setText(gameState.toString());
                    }

                    //Prepopulate with one stone missing from capture
                    if(counter == 1){
                        gameState.testCaptures();
                        theText.setText(gameState.toString());
                    }

                    if (counter == 2) {
                        boolean move = gameState.playerMove(600, 750);
                        theText.setText(gameState.toString());
                    }
                    if (counter == 3) {
                        boolean move = gameState.playerMove(600, 400);
                        theText.setText(gameState.toString());
                    }
                    if (counter == 4) {
                        boolean move = gameState.playerMove(950, 400);
                        theText.setText(gameState.toString());
                    }
                    // run the forfeit test
                    if (counter == 5) {
                        gameState.testForfeit();
                        theText.setText(gameState.toString());
                    }

                    counter++;
                }
            }
        });

        // test forfeiting
        // @author Brynn Harrington
        forfeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call the forfeit test function
                gameState.testForfeit();
                theText.setText(gameState.toString());
            }
        });
    }
}
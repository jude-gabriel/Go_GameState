package com.example.gogamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/* ERRORS
 */
public class MainActivity extends AppCompatActivity{

    public int counter = 0;
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
        SeekBar tests = findViewById(R.id.testBar);

        // override the seekbar to perform each test
        // @author Brynn Harrington
        tests.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int prog = seekBar.getProgress();
                switch (prog) {
                    // Capture Test
                    // @author Jude Gabriel
                    case 0:
                        if (gameState != null) {
                            //Initialize the empty board
                            if (counter == 0) {
                                theText.setText(gameState.toString());
                            }

                            //Prepopulate with one stone missing from capture
                            if (counter == 1) {
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
                            counter++;
                        }
                        break;
                    // Forfeit Test
                    case 1:
                        //Call the forfeit test function
                        gameState.testForfeit();
                        theText.setText(gameState.toString());
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // override the run test
        // @author Jude Gabriel
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

                    if(counter == 2) {
                        boolean move = gameState.playerMove(600, 750);
                        theText.setText(gameState.toString());
                    }
                    if(counter == 3) {
                        boolean move = gameState.playerMove(600, 400);
                        theText.setText(gameState.toString());
                    }
                    if (counter == 4) {
                        boolean move = gameState.playerMove(950, 400);
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
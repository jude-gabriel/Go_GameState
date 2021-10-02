package com.example.gogamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public int counter = 0;
    public float x;
    public float y;

    GoGameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity main = new MainActivity();

        gameState = new GoGameState();

        TextView theText = (TextView) findViewById(R.id.infoText);

        Button runTest = (Button) findViewById(R.id.runTest);
        runTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameState != null) {

                    //Initialize the empty board
                    if(counter == 0){
                        theText.setText(gameState.toString());
                    }

                    //Prepopulate with one stone missing from capture
                    if(counter == 1){
                        gameState.testCaptures();
                        theText.setText(gameState.toString());
                    }

                    //Place a stone to create a capture
                    if(counter == 2) {
                        boolean move = gameState.playerMove(600, 1450);
                        theText.setText(gameState.toString());
                    }
                    counter++;
                }
            }
        });
    }






    /**
     * runs tests based on what time the Run Test button was pressed
     *
     * @param  view
     * @return void
     *
     * @author Mia Anderson
    *
    **/

    @Override
    public void onClick(View view) {

//        if(gameState != null) {
//            boolean move = gameState.playerMove(600, 400);
//        }







//        //restart the test runs if clicked more than 3 times
//        if(counter == 3){
//            counter = 0;
//        }
//        //increment counter each time run test is clicked so we
//        //know what test to run
//        counter++;

    }


}
package com.example.gogamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public int counter = 0;
    public float x;
    public float y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity main = new MainActivity();

        GoGameState gameState = new GoGameState();

        Button runTest = (Button) findViewById(R.id.runTest);
        runTest.setOnClickListener(main);
    }
    /**
     * runs tests based on what time the Run Test button was pressed
     *
     * @param View view
     * @return void
     *
     * @author Mia Anderson
    *
    **/

    @Override
    public void onClick(View view) {
        //restart the test runs if clicked more than 3 times
        if(counter == 3){
            counter = 0;
        }
        //increment counter each time run test is clicked so we
        //know what test to run
        counter++;

    }


}
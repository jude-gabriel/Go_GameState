package com.example.gogamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity main = new MainActivity();

        GoGameState gameState = new GoGameState();

        Button runTest = (Button) findViewById(R.id.runTest);
        runTest.setOnClickListener(main);

    }

    @Override
    public void onClick(View view) {

    }


}
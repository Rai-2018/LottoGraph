package com.example.lottograph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button GraphView;
        GraphView = (Button) findViewById(R.id.GraphView) ;

        GraphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGraphActivity();
            }
        });

        Button Number;
        Number = (Button) findViewById(R.id.View) ;

        Number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNumber();
            }
        });
    }

    private void openGraphActivity() {
        Intent intent = new Intent(this, Graph.class);
        startActivity(intent);
    }

    private void openNumber() {
        Intent intent = new Intent(this, Number.class);
        startActivity(intent);
    }

}











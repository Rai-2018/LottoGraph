package com.example.lottograph;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class AllNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_number);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
        LinearLayout Vertical = (LinearLayout) findViewById(R.id.Main);

        try {
            File cacheFile = new File(this.getCacheDir(), "lotto.txt");
            FileInputStream fis = new FileInputStream(cacheFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));

            String lines;
            int row = 1;
            int[] results = new int[7];
            while((lines = bufferedReader.readLine())!=null){
                String[] intarray = lines.split("\t");
                for(int i = 0; i < intarray.length ; i++) {
                    results[i] = Integer.parseInt(intarray[i]);
                }

                LinearLayout row_detail = new LinearLayout(this);
                row_detail.setOrientation(LinearLayout.HORIZONTAL);
                Vertical.addView(row_detail);

                TextView weeknum = new TextView(this);
                weeknum.setText(String.valueOf(row));
                weeknum.setGravity(Gravity.CENTER);
                weeknum.setLayoutParams(params);
                row_detail.addView(weeknum);

                for(int i:results){
                    TextView current_number = new TextView(this);
                    current_number.setText(String.valueOf(i));
                    current_number.setGravity(Gravity.CENTER);
                    current_number.setLayoutParams(params);
                    row_detail.addView(current_number);
                }
                row++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
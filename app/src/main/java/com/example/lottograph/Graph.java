package com.example.lottograph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class  Graph extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart( ) {
        super.onStart();
        setContentView(R.layout.activity_graph);
        TextView currentnum = (TextView) findViewById(R.id.currentNum);

        Button Next = (Button) findViewById(R.id.Next) ;
        Button Prev = (Button) findViewById(R.id.Prev) ;
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
                LinearLayout Vertical = (LinearLayout) findViewById(R.id.VerticalGraph);
                Vertical.removeAllViewsInLayout();
                Vertical.invalidate();
                display();
            }
        });

        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev();
                LinearLayout Vertical = (LinearLayout) findViewById(R.id.VerticalGraph);
                Vertical.removeAllViewsInLayout();
                Vertical.invalidate();
                display();
            }
        });

        display();
    }

    private void display(){
        List<ArrayList<Integer>> rowList = new ArrayList<>();
        TextView currentnum = (TextView) findViewById(R.id.currentNum);
        String input = currentnum.getText().toString();
        int nextnum = Integer.parseInt(input);

        try{
            File file = new File(this.getCacheDir(), "lottoGraph.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            String lines;
            for (int i = 0; i < 49 ; i++ ){
                rowList.add(new ArrayList<Integer>());
            }

            // Reading last line of the text file
            while ((lines = bufferedReader.readLine()) != null) {
                // Parse the result of the last line and decrement
                String[] intarray = lines.split("\t");
                for (int i = 0; i < 49; i++)
                    rowList.get(i).add(Integer.valueOf(intarray[i]));
            }

            LinearLayout Vertical = (LinearLayout) findViewById(R.id.VerticalGraph);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 600,1.0f);
            LinearLayout Horizontal = new LinearLayout(this);
            for (int i = 0; i < 49 ; i++ ){
                if((i+1)% 10 == nextnum){
                    ArrayList<Integer> temp = rowList.get(i);

                    Horizontal = new LinearLayout(this);
                    Horizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    Horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    Vertical.addView(Horizontal);

                    GraphView graph_num = new GraphView(this);
                    NumberFormat nf = NumberFormat.getInstance();
                    graph_num.setLayoutParams(layoutParams);


                    DataPoint[] dp = new DataPoint[temp.size()];
                    for (int j = 0; j< temp.size(); j++) {
                        dp[j] = new DataPoint(j, temp.get(j));
                    }
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                    series.setColor(Color.argb(255, 255, 60, 60));
                    series.setDrawDataPoints(true);

                    series.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            Toast toast =  Toast.makeText(Graph.this, "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                    String Title = "Number: " + String.valueOf(i+1);
                    graph_num.getViewport().setXAxisBoundsManual(true);
                    graph_num.getViewport().setMaxX(temp.size()-1);
                    graph_num.setTitle(Title);
                    graph_num.addSeries(series);
                    graph_num.refreshDrawableState();
                    Horizontal.addView(graph_num);
                }
            }
        } catch (Exception e){
            Intent intent = new Intent(this, Number.class);
            Toast toast =  Toast.makeText(Graph.this, "No value in database", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(intent);
            e.printStackTrace();
        }


    }

    private void next(){
        TextView currentnum = (TextView) findViewById(R.id.currentNum);
        String input = currentnum.getText().toString();
        int nextnum = Integer.parseInt(input);
        if(nextnum == 9){return;}
        nextnum = nextnum + 1;
        currentnum.setText(String.valueOf(nextnum));
    }

    private void prev(){
        TextView currentnum = (TextView) findViewById(R.id.currentNum);
        String input = currentnum.getText().toString();
        int nextnum = Integer.parseInt(input);
        if(nextnum == 0){return;}
        nextnum = nextnum - 1;
        currentnum.setText(String.valueOf(nextnum));
    }

}
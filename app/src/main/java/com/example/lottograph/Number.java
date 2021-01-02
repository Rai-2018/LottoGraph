package com.example.lottograph;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Number extends AppCompatActivity {
    int number1,number2,number3,number4,number5,number6,number7;
    EditText input1, input2, input3, input4, input5, input6, input7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        input1 = (EditText) findViewById(R.id.No1);
        input2 = (EditText) findViewById(R.id.No2);
        input3 = (EditText) findViewById(R.id.No3);
        input4 = (EditText) findViewById(R.id.No4);
        input5 = (EditText) findViewById(R.id.No5);
        input6 = (EditText) findViewById(R.id.No6);
        input7 = (EditText) findViewById(R.id.No7);

        Button Submit = (Button) findViewById(R.id.Submit) ;

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    number1 = Integer.parseInt(input1.getText().toString());
                    number2 = Integer.parseInt(input2.getText().toString());
                    number3 = Integer.parseInt(input3.getText().toString());
                    number4 = Integer.parseInt(input4.getText().toString());
                    number5 = Integer.parseInt(input5.getText().toString());
                    number6 = Integer.parseInt(input6.getText().toString());
                    number7 = Integer.parseInt(input7.getText().toString());
                    final int[] numbers = {number1,number2,number3,number4,number5,number6,number7};

                    for(int i = 0; i < numbers.length; i++) {
                        for(int j = i + 1; j < numbers.length; j++) {
                            if(numbers[i] == numbers[j])
                                throw new Exception();
                        }
                    }

                    for(int i = 0;i<numbers.length;i++){
                        if(numbers[i] > 49 || numbers[i] < 1) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Number.this);
                            builder.setMessage(
                                    "The number " + numbers[i] + " is not within 1 to 49")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                                    finish();
                                                    startActivity(getIntent());
                                                }
                                            });
                            final AlertDialog alert = builder.create();
                            alert.show();
                            return;
                        }
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(Number.this);
                    builder.setCancelable(true);
                    builder.setTitle("Confirm");
                    String msg = "";
                    for(int i = 0; i<numbers.length;i++){
                        msg = msg + "\t\t\t" + String.valueOf(numbers[i]);
                    }
                    builder.setMessage("Are these the numbers you want to enter: \n" + msg);
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    openSubmitActivity(numbers);
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Number.this, Number.class);
                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }catch (Exception e){
                    //Catching Errors
                    new AlertDialog.Builder(Number.this)
                            .setTitle("Not Valid")
                            .setCancelable(true)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    })
                            .setMessage("You did not input 7 numbers or there is duplicate, Retry")
                            .create().show();
                }
            }
        });

    }

    private void openSubmitActivity(int[] Array) {
        Intent intent = new Intent(this, Graph.class);
        try{
            int linecount = 1;
            // Preparing txt file for numbers in the graph
            File file = new File(this.getCacheDir(), "lottoGraph.txt");
            FileOutputStream fos = null;
            int[] results = new int[49];

            // Preparing txt file for Viewing all number
            File cacheFile = new File(this.getCacheDir(), "lotto.txt");
            FileOutputStream cachefos = null;
            int[] cache_results = new int[7];

            if(file.length() == 0) {
                // Prepare a line of zeros at very beginning
                fos = new FileOutputStream(file);
                for (int i = 0; i < 49; i++){
                    String toWrite = String.valueOf(0) + "\t";
                    fos.write(toWrite.getBytes());
                }
                fos.write("\n".getBytes());

                // Creating the txt file for Viewing all number
                cachefos = new FileOutputStream(cacheFile);
                for (int i = 0; i < 7; i++) {
                    String toWrite = String.valueOf(0) + "\t";
                    cachefos.write(toWrite.getBytes());
                }
                cachefos.write("\n".getBytes());

            } else {
                String last = null,lines = null;
                FileInputStream fis = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));

                // Reading last line of the text file
                while ((lines = bufferedReader.readLine()) != null) {
                    last = lines;
                    linecount += 1;
                }
                // Parse the result of the last line and decrement
                String[] intarray = last.split("\t");
                for (int i = 0; i < intarray.length; i++)
                    results[i] = Integer.parseInt(intarray[i]);
            }
            fos = new FileOutputStream(file, true);

            if(linecount > 31) {
                fos = new FileOutputStream(file);
                for(int i : results){
                    String toWrite = String.valueOf(i) + "\t";
                    fos.write(toWrite.getBytes());
                }
                fos.write("\n".getBytes());
                fos = new FileOutputStream(file, true);

                // Reading of cache view all
                String last = null,lines = null;
                FileInputStream cache_fis = new FileInputStream(cacheFile);
                BufferedReader cache_bufferedReader = new BufferedReader(new InputStreamReader(cache_fis));
                while ((lines = cache_bufferedReader.readLine()) != null) {
                    last = lines;
                }
                String[] intarray = last.split("\t");
                cachefos = new FileOutputStream(cacheFile);
                for (int i = 0; i < intarray.length; i++) {
                    cache_results[i] = Integer.parseInt(intarray[i]);
                    String toWrite = String.valueOf(cache_results[i]) + "\t";
                    cachefos.write(toWrite.getBytes());
                }
                cachefos.write("\n".getBytes());
            }

            for(int i = 0; i <49;i++){
                for(int j = 0; j < Array.length;j++){
                    if( i == Array[j]-1){
                        results[i] += 2;
                    }
                }
                results[i] -= 1;
                String toWrite = String.valueOf(results[i]) + "\t";
                fos.write(toWrite.getBytes());
            }
            fos.write("\n".getBytes());
            fos.close();

            cachefos = new FileOutputStream(cacheFile, true);
            for(int i :Array){
                String toWrite = String.valueOf(i) + "\t";
                cachefos.write(toWrite.getBytes());
            }
            cachefos.write("\n".getBytes());
            cachefos.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startActivity(intent);
    }
}













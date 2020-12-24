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
                    int[] numbers = {number1,number2,number3,number4,number5,number6,number7};

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

                    openSubmitActivity(numbers);

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
            File file = new File(this.getCacheDir(), "lottoGraph.txt");
            FileOutputStream fos = null;
            int[] results = new int[49];

            if(file.length() == 0){
                fos = new FileOutputStream(file);
                for (int i = 0; i < 49; i++){
                    results[i] = 0;
                }
                for(int i : results){
                    String toWrite = String.valueOf(i) + "\t";
                    fos.write(toWrite.getBytes());
                }
                fos.write("\n".getBytes());
                fos = new FileOutputStream(file, true);

            } else {
                fos = new FileOutputStream(file, true);
                FileInputStream fis = new FileInputStream(file);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
                String lines;
                String lastLine = "";
                // Reading last line of the text file
                while ((lines = bufferedReader.readLine()) != null) {
                    lastLine = lines;
                    linecount += 1;
                }
                // Parse the result of the last line and decrement
                String[] intarray = lastLine.split("\t");
                for (int i = 0; i < intarray.length; i++)
                    results[i] = Integer.parseInt(intarray[i]);
            }

            if(linecount > 30+1){
                fos = new FileOutputStream(file);
                for(int i : results){
                    String toWrite = String.valueOf(i) + "\t";
                    fos.write(toWrite.getBytes());
                }
                fos.write("\n".getBytes());
                fos = new FileOutputStream(file, true);
            }

            for(int i = 0; i <49;i++){
                for(int j = 0; j < Array.length;j++){
                    if( i == Array[j]-1){
                        results[i] += 2;
                    }
                }
                results[i] -= 1;
            }
            for(int i : results){
                String toWrite = String.valueOf(i) + "\t";
                fos.write(toWrite.getBytes());
            }
            fos.write("\n".getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startActivity(intent);
    }
}













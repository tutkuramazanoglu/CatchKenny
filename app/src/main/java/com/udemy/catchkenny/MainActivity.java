package com.udemy.catchkenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView textView2;
    TextView textview;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView[] imageArray;
    int counter;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        counter=0;
        textView2=findViewById(R.id.textView2);
        textview=findViewById(R.id.textView);
        textView2.setText("Score: "+counter);

        //call to methods
        callImageView();
        hideImage();
        startGame();

    }

    public void increaseScore(View view){
        counter++;   //update score
        textView2.setText("Score: "+counter);
    }

    public void callImageView(){

        //define all imageView
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);
        imageView10=findViewById(R.id.imageView10);

        //assign imageViews to array
        imageArray=new ImageView[]{imageView2,imageView3,imageView4,imageView5,imageView6,
                imageView7,imageView8,imageView9,imageView10};
    }

    public void hideImage(){
        //handler repeats same things periodically.
        //Every one second one image shows up randomly
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView image:imageArray){
                    image.setVisibility(View.INVISIBLE); //all images are invisible
                }

                Random rnd=new Random();
                int random=rnd.nextInt(imageArray.length-1);
                imageArray[random].setVisibility(View.VISIBLE);  //random image is visible
                //this refers to who is above
                handler.postDelayed(this,1000); // this refers to run method.
            }
        };
        handler.post(runnable); 
    }

    public void startGame(){
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                //millisUntilFinished gives as a milliseconds
                textview.setText("Time: " + millisUntilFinished/1000);
            }

            public void onFinish() {
                textview.setText("Time is up!");
                handler.removeCallbacks(runnable);
                for(ImageView image:imageArray){
                    image.setVisibility(View.INVISIBLE);
                }
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Do you want to restart game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Game Over",
                                Toast.LENGTH_LONG).show();
                    }
                });

                alert.show();
            }
        }.start();   //start to timer
    }
}
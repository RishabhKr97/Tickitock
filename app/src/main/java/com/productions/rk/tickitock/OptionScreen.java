package com.productions.rk.tickitock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class OptionScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_screen);

        //Defining buttons

        Button singleplayerbutton = (Button)findViewById(R.id.singleplayerbutton);
        Button twoplayerbutton = (Button)findViewById(R.id.twoplayerbutton);
        Button statsbutton = (Button)findViewById(R.id.statsbutton);

        //Setting on click listener for the buttons

        singleplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplaySinglePlayerAlert();
            }
        });

        twoplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayTwoPlayerAlert();
            }
        });

        statsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display Stats Activity
            }
        });
    }

    // Alert Box for button clicks

    public void DisplaySinglePlayerAlert(){
        LayoutInflater inflater = getLayoutInflater();
        View SinglePlayerLayout = inflater.inflate(R.layout.layout_single_player_options, null);
        AlertDialog.Builder alertD = new AlertDialog.Builder(this);
        alertD.setView(SinglePlayerLayout);
        alertD.setCancelable(true);
        final AlertDialog dialog = alertD.create();

        // setting buttons for single player options
        Button supereasy = (Button) SinglePlayerLayout.findViewById(R.id.supereasybutton);
        Button easy = (Button) SinglePlayerLayout.findViewById(R.id.easybutton);

        // setting on click listener for buttons

        supereasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change to game activity
                startActivity(new Intent(OptionScreen.this, SinglePlayerSuperEasy.class));
                dialog.cancel();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change to game activity
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void DisplayTwoPlayerAlert(){
        LayoutInflater inflater = getLayoutInflater();
        View TwoPlayerLayout = inflater.inflate(R.layout.layout_two_player_options, null);
        AlertDialog.Builder alertD = new AlertDialog.Builder(this);
        alertD.setView(TwoPlayerLayout);
        alertD.setCancelable(true);
        final AlertDialog dialog = alertD.create();

        // setting up buttons

        Button wifibutton = (Button) TwoPlayerLayout.findViewById(R.id.wifibutton);
        Button samedevicebutton = (Button) TwoPlayerLayout.findViewById(R.id.samedevicebutton);

        // setting on click listener

        wifibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch wifi game
                dialog.cancel();
            }
        });

        samedevicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to game activity
                startActivity(new Intent(OptionScreen.this, TwoPlayerSameDevice.class));
                dialog.cancel();
            }
        });
        dialog.show();
    }
}

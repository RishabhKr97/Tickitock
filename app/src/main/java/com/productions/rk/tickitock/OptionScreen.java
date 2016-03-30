package com.productions.rk.tickitock;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
                DisplayStasAlert();
            }
        });
    }

    private void DisplayStasAlert() {

        // **** REMOVE ALERT DIALOG FOR STATS *****

        LayoutInflater inflater=getLayoutInflater();
        View statsAlertDialog=inflater.inflate(R.layout.stats_select_layout, null);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        AlertDialog dialog=alertDialog.create();
        dialog.setView(statsAlertDialog);
        Button sps = (Button) statsAlertDialog.findViewById(R.id.singlePlayerStats);
        Button dps = (Button) statsAlertDialog.findViewById(R.id.doublePlayerStats);
        sps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionScreen.this,StatsActivity.class));
                OptionScreen.this.finish();
            }
        });
        dps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionScreen.this,StatsActivity2.class));
                OptionScreen.this.finish();
            }
        });
        dialog.show();
    }

    // Alert Box for button clicks

    public void DisplaySinglePlayerAlert(){
        LayoutInflater inflater = getLayoutInflater();
        View SinglePlayerLayout = inflater.inflate(R.layout.layout_single_player_options, null);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(SinglePlayerLayout);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.alertbackground));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        // setting buttons for single player options
        Button supereasy = (Button) SinglePlayerLayout.findViewById(R.id.supereasybutton);
        Button easy = (Button) SinglePlayerLayout.findViewById(R.id.easybutton);

        // setting on click listener for buttons

        supereasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change to game activity
                startActivity(new Intent(OptionScreen.this, SinglePlayerSuperEasy.class));
                //CLose Menu
                OptionScreen.this.finish();
                dialog.cancel();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change to game activity
                startActivity(new Intent(OptionScreen.this, SinglePlayerEasy.class));
                //CLose Menu
                OptionScreen.this.finish();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void DisplayTwoPlayerAlert(){
        LayoutInflater inflater = getLayoutInflater();
        View TwoPlayerLayout = inflater.inflate(R.layout.layout_two_player_options, null);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(TwoPlayerLayout);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.alertbackground));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        // Alert Dialog if device does not support bluetooth
        final android.app.AlertDialog.Builder noBluetoothAlertBuilder = new android.app.AlertDialog.Builder(this);

        // setting up buttons

        Button bluetoothbutton = (Button) TwoPlayerLayout.findViewById(R.id.wifibutton);
        Button samedevicebutton = (Button) TwoPlayerLayout.findViewById(R.id.samedevicebutton);

        // setting on click listener

        bluetoothbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch wifi game
                dialog.cancel();
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if(mBluetoothAdapter==null){
                    noBluetoothAlertBuilder.setMessage("Device does not support bluetooth");
                    noBluetoothAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    android.app.AlertDialog noBluetoothAlert = noBluetoothAlertBuilder.create();
                    noBluetoothAlert.show();
                }
                else{

                    startActivity(new Intent(OptionScreen.this, BluetoothProcessHandling.class));
                    OptionScreen.this.finish();
                }
            }
        });

        samedevicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to game activity
                startActivity(new Intent(OptionScreen.this, TwoPlayerSameDevice.class));
                //Close Menu when opening 2p game
                OptionScreen.this.finish();
                dialog.cancel();
            }
        });

        dialog.show();
    }
}

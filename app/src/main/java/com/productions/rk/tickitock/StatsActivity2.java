package com.productions.rk.tickitock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class StatsActivity2 extends AppCompatActivity {
    TextView player1,player2,draws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        player1=(TextView)findViewById(R.id.SDWin);
        player2=(TextView)findViewById(R.id.SDLoses);
        draws=(TextView)findViewById(R.id.SDDraws);

        SharedPreferences SDprefs=getSharedPreferences("SAMEDEVICE",2);
        String str1="PLAYER1\n"+SDprefs.getString("WINS","0");
        String str2="PLAYER2\n"+SDprefs.getString("LOSES","0");
        String str3="DRAW\n"+SDprefs.getString("DRAWS","0");
        player1.setText(str1);
        player2.setText(str2);
        draws.setText(str3);


    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(StatsActivity2.this,OptionScreen.class));
        StatsActivity2.this.finish();
    }
}

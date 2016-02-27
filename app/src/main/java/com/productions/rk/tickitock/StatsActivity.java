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

import org.w3c.dom.Text;

public class StatsActivity extends AppCompatActivity {
    TextView SEwin,SElose,SEdraw,Ewin,Elose,Edraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SEwin=(TextView)findViewById(R.id.SEWin);
        Ewin=(TextView)findViewById(R.id.EWin);
        SElose=(TextView)findViewById(R.id.SELoses);
        Elose=(TextView)findViewById(R.id.ELoses);
        SEdraw=(TextView)findViewById(R.id.SEDraws);
        Edraw=(TextView)findViewById(R.id.EDraws);

        SharedPreferences SEprefs=getSharedPreferences("EASY",1);
        String str1="WINS\n"+SEprefs.getString("WINS","0");
        String str2="LOSES\n"+SEprefs.getString("LOSES","0");
        String str3="DRAWS\n"+SEprefs.getString("DRAWS","0");
        Ewin.setText(str1);
        Elose.setText(str2);
        Edraw.setText(str3);
        SharedPreferences Eprefs=getSharedPreferences("SUPEREASY",0);
        str1="WINS\n"+Eprefs.getString("WINS","0");
        str2="LOSES\n"+Eprefs.getString("LOSES","0");
        str3="DRAWS\n"+Eprefs.getString("DRAWS","0");
        SEwin.setText(str1);
        SElose.setText(str2);
        SEdraw.setText(str3);

    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(StatsActivity.this,OptionScreen.class));
        StatsActivity.this.finish();
    }

}

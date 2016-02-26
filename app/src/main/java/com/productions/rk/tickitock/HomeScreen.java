package com.productions.rk.tickitock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Setting custom font for welcome text
        TextView WelcomeText = (TextView)findViewById(R.id.welcomeId);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/3Dumb.ttf");
        WelcomeText.setTypeface(custom_font);

        // Make HomeScreen clickable
        RelativeLayout HomeScreen = (RelativeLayout)findViewById(R.id.HomeScreen);
        HomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, OptionScreen.class));
            }
        });
    }
}

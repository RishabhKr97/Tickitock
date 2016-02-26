package com.productions.rk.tickitock;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TwoPlayerSameDevice extends Activity implements View.OnClickListener {

    ImageView img1,img2,img3;
    TextView player1score,player2score;
    String winner="Game Drawn";
    AnimationDrawable anim,win1,win2,win3;
    Button player1moveindicator,player2moveindicator;
    int activeplayer = 2;
    int filled;
    int playerOnewin=0,playerTwowin=0;
    int state[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    boolean gameActive=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_same_device);
        player1moveindicator = (Button) findViewById(R.id.player1moveindicator);
        player2moveindicator = (Button) findViewById(R.id.player2moveindicator);
        player1score = (TextView) findViewById(R.id.player1score);
        player2score = (TextView) findViewById(R.id.player2score);
        ImageView position0 = (ImageView) findViewById(R.id.position0);
        position0.setOnClickListener(this);
        ImageView position1 = (ImageView) findViewById(R.id.position1);
        position1.setOnClickListener(this);
        ImageView position2 = (ImageView) findViewById(R.id.position2);
        position2.setOnClickListener(this);
        ImageView position3 = (ImageView) findViewById(R.id.position3);
        position3.setOnClickListener(this);
        ImageView position4 = (ImageView) findViewById(R.id.position4);
        position4.setOnClickListener(this);
        ImageView position5 = (ImageView) findViewById(R.id.position5);
        position5.setOnClickListener(this);
        ImageView position6 = (ImageView) findViewById(R.id.position6);
        position6.setOnClickListener(this);
        ImageView position7 = (ImageView) findViewById(R.id.position7);
        position7.setOnClickListener(this);
        ImageView position8 = (ImageView) findViewById(R.id.position8);
        position8.setOnClickListener(this);
        initialiseBoard();
    }

    public void initialiseBoard()
    {
        if(activeplayer==1) {
            activeplayer = 2;
            player2moveindicator.setBackgroundColor(Color.argb(255,54,145,32));
            player1moveindicator.setBackgroundColor(Color.argb(255,222,237,222));
        }
        else {
            activeplayer = 1;
            player1moveindicator.setBackgroundColor(Color.argb(255,54,145,32));
            player2moveindicator.setBackgroundColor(Color.argb(255,222,237,222));
        }

        filled=0;

        for(int i : state)
        {
            state[i]= 0;
        }
    }

    public void onClick(View view) {
        ImageView counter = (ImageView) view;
        int check=Integer.parseInt(counter.getTag().toString());

        if(state[check]==0&& gameActive) {
            if (activeplayer == 1) {
                counter.setBackgroundResource(R.drawable.circle_anim);
                anim = (AnimationDrawable) counter.getBackground();
                anim.start();
                activeplayer = 2;
                state[check] = 1;
                filled++;
                player2moveindicator.setBackgroundColor(Color.argb(255,54,145,32));
                player1moveindicator.setBackgroundColor(Color.argb(255,222,237,222));

            } else {
                counter.setBackgroundResource(R.drawable.cross_anim);
                anim = (AnimationDrawable) counter.getBackground();
                anim.start();
                activeplayer = 1;
                state[check] = 2;
                filled++;
                player1moveindicator.setBackgroundColor(Color.argb(255,54,145,32));
                player2moveindicator.setBackgroundColor(Color.argb(255,222,237,222));
            }
        }
        if(state[0]==state[1] && state[1]==state[2] &&state[0]!=0){
            win(0,1,2);
        }
        else  if(state[3]==state[4] && state[4]==state[5]&&state[3]!=0){
            win(3,4,5);
        }
        else  if(state[6]==state[7] && state[7]==state[8]&&state[6]!=0){
            win(6,7,8);
        }
        else  if(state[0]==state[3] && state[3]==state[6]&&state[0]!=0){
            win(0,3,6);

        }
        else  if(state[1]==state[4] && state[4]==state[7]&&state[1]!=0){
            win(1,4,7);
        }
        else  if(state[2]==state[5] && state[5]==state[8]&&state[2]!=0){
            win(2,5,8);
        }
        else  if(state[0]==state[4] && state[4]==state[8]&&state[0]!=0){
            win(0,4,8);
        }
        else  if(state[2]==state[4] && state[4]==state[6]&&state[2]!=0){
            win(2,4,6);
        }

        if(gameActive&&filled==9){
            gameActive=false;
        }
        if(!gameActive){

            // make Alert Dialog for Restart and Exit
            Toast.makeText(getApplicationContext(), winner, Toast.LENGTH_LONG).show();
        }

    }

    private void win(int a,int b,int c){
        String ida="position"+a;
        String idb="position"+b;
        String idc="position"+c;
        int id1=getResources().getIdentifier(ida, "id", getPackageName());
        int id2=getResources().getIdentifier(idb, "id", getPackageName());
        int id3=getResources().getIdentifier(idc, "id", getPackageName());
        if(state[a]==1){
            img1=(ImageView) findViewById(id1);
            img2=(ImageView) findViewById(id2);
            img3=(ImageView) findViewById(id3);
            img1.setBackgroundResource(R.drawable.circlewin_anim);
            img2.setBackgroundResource(R.drawable.circlewin_anim);
            img3.setBackgroundResource(R.drawable.circlewin_anim);
            win1=(AnimationDrawable)img1.getBackground();
            win2=(AnimationDrawable)img2.getBackground();
            win3=(AnimationDrawable)img3.getBackground();
            win1.start();
            win2.start();
            win3.start();
            gameActive=false;
            winner="Player 1 wins!";
            playerOnewin++;
            player1score.setText(String.valueOf(playerOnewin));
        }
        else{
            img1=(ImageView) findViewById(id1);
            img2=(ImageView) findViewById(id2);
            img3=(ImageView) findViewById(id3);
            img1.setBackgroundResource(R.drawable.crosswin_anim);
            img2.setBackgroundResource(R.drawable.crosswin_anim);
            img3.setBackgroundResource(R.drawable.crosswin_anim);
            win1=(AnimationDrawable)img1.getBackground();
            win2=(AnimationDrawable)img2.getBackground();
            win3=(AnimationDrawable)img3.getBackground();
            win1.start();
            win2.start();
            win3.start();
            gameActive=false;
            winner="Player 2 wins!";
            playerTwowin++;
            player2score.setText(String.valueOf(playerTwowin));
        }

    }

}

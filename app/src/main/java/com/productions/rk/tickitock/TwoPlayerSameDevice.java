package com.productions.rk.tickitock;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TwoPlayerSameDevice extends Activity implements View.OnClickListener {

    private ImageView img1,img2,img3;
    int activeplayer = 1;
    int state[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private AnimationDrawable anim,win1,win2,win3;
    boolean gameActive=true;
    int filled=0;
    String winner="Game Drawn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_same_device);
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

            } else {
                counter.setBackgroundResource(R.drawable.cross_anim);
                anim = (AnimationDrawable) counter.getBackground();
                anim.start();
                activeplayer = 1;
                state[check] = 2;
                filled++;
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
            Toast.makeText(getApplicationContext(), winner, Toast.LENGTH_LONG).show();
        }

    }

    private void win(int a,int b,int c){
        String ida="position"+a;
        String idb="position"+b;
        String idc="position"+c;
        int id1=getResources().getIdentifier(ida,"id",getPackageName());
        int id2=getResources().getIdentifier(idb,"id",getPackageName());
        int id3=getResources().getIdentifier(idc,"id",getPackageName());
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
        }

    }

}

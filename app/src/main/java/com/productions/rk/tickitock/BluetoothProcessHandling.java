package com.productions.rk.tickitock;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

public class BluetoothProcessHandling extends Activity implements View.OnClickListener{

    /**   ************************************************
       VARIABLES AND BROADCAST FOR BLUETOOTH HANDLING
      *************************************************
    */

    String DeviceMACAddress;
    UUID uuid = UUID.fromString("c59332d0-4055-432a-a5bd-2684fc9fb5d5");
    BluetoothAdapter mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
    private ArrayAdapter<String> madapter;

    AcceptThread BluetoothServer;
    ConnectThread BluetoothClient;
    ConnectedThread BluetoothDataTransfer;
    Thread BluetoothServerThread,BluetoothClientThread,BluetoothDataTransferThread;
    Handler BluetoothDataHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
               if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                madapter.add(device.getName() + "\n" + device.getAddress());
                madapter.notifyDataSetChanged();
            }
        }
    };

    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    /**   *****************************************************
          END OF VARIABLES AND BROADCAST FOR BLUETOOTH HANDLING
          *****************************************************
     */



    /**   ************************************************
                  VARIABLES FOR BLUETOOTH GAME
          *************************************************
     */
    ImageView img1,img2,img3,iv0,iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8;
    ImageView position[] = {iv0,iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8};
    TextView player1score,player2score;
    String winner="Game Drawn";
    AnimationDrawable anim,win1,win2,win3;
    Button player1moveindicator,player2moveindicator,ReplayButton,exitButton;
    int activeplayer = 2; // Player 1 is user and Player 2 is bot
    int filled;
    int playerOnewin=0,playerTwowin=0,draws;
    int state[]= {0,0,0,0,0,0,0,0,0};
    boolean gameActive,playermovedfirst=false;

        /**  ************************************************
                  END OF VARIABLES FOR BLUETOOTH GAME
         *************************************************
        */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_process_handling);

        /**   ************************************************
                CODE FOR BLUETOOTH HANDLING
             *************************************************
        */
        ListView deviceList = (ListView) findViewById(R.id.listView);
        madapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,
                android.R.id.text1);
        deviceList.setAdapter(madapter);
        registerReceiver(mReceiver, filter);

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivityForResult(discoverableIntent, 0);
        // To make sure bluetooth is enabled before starting discovery
        while(!mBluetoothAdapter.isEnabled());


        BluetoothServer = new AcceptThread();
        Runnable BluetoothServerRunnable = new Runnable() {
            @Override
            public void run() {
                BluetoothServer.run();
            }
        };
        BluetoothServerThread = new Thread(BluetoothServerRunnable);
        BluetoothServerThread.start();

        mBluetoothAdapter.startDiscovery();

        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBluetoothAdapter.cancelDiscovery();
                String info = ((TextView) view).getText().toString();
                DeviceMACAddress = info.substring(info.length() - 17);
                BluetoothDevice ConnectToDevice = mBluetoothAdapter.getRemoteDevice(DeviceMACAddress);
                BluetoothClient = new ConnectThread(ConnectToDevice);
                Runnable BluetoothClientRunnable = new Runnable() {
                    @Override
                    public void run() {
                        BluetoothClient.run();
                    }
                };
                BluetoothClientThread = new Thread(BluetoothClientRunnable);
                BluetoothClientThread.start();
            }
        });
        /**   ************************************************
                    END OF CODE FOR BLUETOOTH HANDLING
              *************************************************
        */


        /**   **********************************************
                     CODE FOR BLUETOOTH GAME
              ***********************************************
         */
        player1moveindicator = (Button) findViewById(R.id.player1moveindicator);
        player2moveindicator = (Button) findViewById(R.id.player2moveindicator);
        player1score = (TextView) findViewById(R.id.player1score);
        player2score = (TextView) findViewById(R.id.player2score);

        position[0] = (ImageView) findViewById(R.id.position0);
        position[0].setOnClickListener(this);
        position[1] = (ImageView) findViewById(R.id.position1);
        position[1].setOnClickListener(this);
        position[2] = (ImageView) findViewById(R.id.position2);
        position[2].setOnClickListener(this);
        position[3] = (ImageView) findViewById(R.id.position3);
        position[3].setOnClickListener(this);
        position[4] = (ImageView) findViewById(R.id.position4);
        position[4].setOnClickListener(this);
        position[5] = (ImageView) findViewById(R.id.position5);
        position[5].setOnClickListener(this);
        position[6] = (ImageView) findViewById(R.id.position6);
        position[6].setOnClickListener(this);
        position[7] = (ImageView) findViewById(R.id.position7);
        position[7].setOnClickListener(this);
        position[8] = (ImageView) findViewById(R.id.position8);
        position[8].setOnClickListener(this);

        /**   ************************************************
                      END OF CODE FOR BLUETOOTH GAME
              *************************************************
         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            startActivity(new Intent(BluetoothProcessHandling.this, OptionScreen.class));
            BluetoothProcessHandling.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(BluetoothServerThread.isAlive()){
            BluetoothServer.cancel();
        }
        if(BluetoothClientThread.isAlive()){
            BluetoothClient.cancel();
        }
        if(BluetoothDataTransferThread.isAlive()){
            BluetoothDataTransfer.cancel();
        }
        if(mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
        }

        unregisterReceiver(mReceiver);
        saveStats(playerOnewin, playerTwowin, draws);
        startActivity(new Intent(BluetoothProcessHandling.this, OptionScreen.class));
        BluetoothProcessHandling.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(BluetoothServerThread.isAlive()){
            BluetoothServer.cancel();
        }
        if(BluetoothClientThread.isAlive()){
            BluetoothClient.cancel();
        }
        if(BluetoothDataTransferThread.isAlive()){
            BluetoothDataTransfer.cancel();
        }
        if(mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
        }
        unregisterReceiver(mReceiver);
    }


    /**   ************************************************
                 FUNCTIONS FOR BLUETOOTH GAME
         *************************************************
     */
    /**   ************************************************
               ON CLICK FUNCTION FOR BLUETOOTH GAME
         *************************************************
     */
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
                player2moveindicator.setBackgroundColor(Color.argb(255, 54, 145, 32));
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
            draws++;
        }

        if(!gameActive){
            LayoutInflater inflater= getLayoutInflater();
            View ResultAlertBox = inflater.inflate(R.layout.layout_game_result_2p_same_device, null);

            final Dialog alertDialog = new Dialog(this);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(ResultAlertBox);
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.alertbackground));
            alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

            ReplayButton=(Button)ResultAlertBox.findViewById(R.id.ReplayButton);
            exitButton=(Button)ResultAlertBox.findViewById(R.id.ExitButton);

            ReplayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beginNewGame();
                    alertDialog.cancel();

                }
            });
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveStats(playerOnewin,playerTwowin,draws);
                    startActivity(new Intent(BluetoothProcessHandling.this, OptionScreen.class));
                    BluetoothProcessHandling.this.finish();
                }
            });

            //0.2s delay before showing dialog
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.show();
                }
            }, 200);
        }

        else if(activeplayer==2){
            // Make the move for bot after delay of 200ms
            if(gameActive) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random botMove = new Random();
                        int mybotMove = botMove.nextInt(9);
                        while (state[mybotMove] != 0) mybotMove = botMove.nextInt(9);
                        position[mybotMove].performClick();
                    }
                }, 200);
            }
        }

    }
    /**   ************************************************
           END OF ON CLICK FUNCTION FOR BLUETOOTH GAME
         *************************************************
     */


    /**   ************************************************
           BEGIN NEW GAME FUNCTION FOR BLUETOOTH GAME
         *************************************************
     */
    private void beginNewGame() {
        gameActive=true;
        for(int i=0;i<9;i++){
            state[i]=0;
        }
        filled=0;
        position[0].setBackgroundResource(android.R.color.white);
        position[1].setBackgroundResource(android.R.color.white);
        position[2].setBackgroundResource(android.R.color.white);
        position[3].setBackgroundResource(android.R.color.white);
        position[4].setBackgroundResource(android.R.color.white);
        position[5].setBackgroundResource(android.R.color.white);
        position[6].setBackgroundResource(android.R.color.white);
        position[7].setBackgroundResource(android.R.color.white);
        position[8].setBackgroundResource(android.R.color.white);
        if(playermovedfirst) {
            player2moveindicator.setBackgroundColor(Color.argb(255,54,145,32));
            player1moveindicator.setBackgroundColor(Color.argb(255, 222, 237, 222));
            activeplayer=2;
            // Make the move for bot after delay of 200ms
            if(gameActive) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random botMove = new Random();
                        int mybotMove = botMove.nextInt(9);
                        while (state[mybotMove] != 0) mybotMove = botMove.nextInt(9);
                        position[mybotMove].performClick();
                    }
                }, 200);
            }
            playermovedfirst = false;
        }
        else {
            activeplayer = 1;
            playermovedfirst = true;
            player1moveindicator.setBackgroundColor(Color.argb(255,54,145,32));
            player2moveindicator.setBackgroundColor(Color.argb(255,222,237,222));
        }

    }
    /**   ************************************************
         END OF BEGIN NEW GAME FUNCTION FOR BLUETOOTH GAME
         *************************************************
     */


    /**   ************************************************
                WIN FUNCTION FOR BLUETOOTH GAME
         *************************************************
     */
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
    /**   ************************************************
     *       END OF WIN FUNCTION FOR BLUETOOTH GAME
          *************************************************
     */

    /**   ************************************************
           SAVE STATS FUNCTION FOR BLUETOOTH GAME
          *************************************************
     */
    private void saveStats(int wins,int loses,int draws){
        SharedPreferences SEPrefs=getSharedPreferences("SUPEREASY", 0);
        SharedPreferences.Editor SEprefEditor=SEPrefs.edit();
        if(!SEPrefs.contains("WINS")) {
            SEprefEditor.putString("WINS", String.valueOf(wins));
            SEprefEditor.putString("LOSES", String.valueOf(loses));
            SEprefEditor.putString("DRAWS", String.valueOf(draws));
            SEprefEditor.apply();
        }
        else{
            wins+=Integer.parseInt(SEPrefs.getString("WINS","0"));
            loses+=Integer.parseInt(SEPrefs.getString("LOSES","0"));
            draws+=Integer.parseInt(SEPrefs.getString("DRAWS","0"));
            SEprefEditor.putString("WINS", String.valueOf(wins));
            SEprefEditor.putString("LOSES", String.valueOf(loses));
            SEprefEditor.putString("DRAWS", String.valueOf(draws));
            SEprefEditor.apply();
        }
    }
    /**   ************************************************
           END OF SAVE STATS FUNCTION FOR BLUETOOTH GAME
         *************************************************
     */
    /**   ************************************************
              END OF FUNCTIONS FOR BLUETOOTH GAME
         *************************************************
     */



    /**   ************************************************
             CLASSES FOR BLUETOOTH HANDLING
      *************************************************
    */
    /**   ************************************************
                   ACCEPT THREAD CLASS
      *************************************************
    */
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            try{
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("tickitock",uuid);
            }catch (Exception e){
             }

            mmServerSocket = tmp;
        }

        @Override
        public void run(){
            BluetoothSocket socket;
            while (true){
                try{
                    socket = mmServerSocket.accept();
                }catch (Exception e){
                    break;
                }

                if(socket != null){

                    ManageConnectedSocket(socket);
                    try{
                        mmServerSocket.close();
                    } catch (Exception e){}

                    break;
                }
            }
        }

        public void cancel(){
            try{
                mmServerSocket.close();
            }catch (Exception e){}
        }
    }
    /**   ************************************************
                  END OF ACCEPT THREAD CLASS
     *************************************************
     */


    /**   ************************************************
                      CONNECT THREAD CLASS
         *************************************************
    */
    private class ConnectThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device){
            BluetoothSocket tmp =null;
            mmDevice = device;

            try{
                tmp= device.createRfcommSocketToServiceRecord(uuid);
            }catch (Exception e){}

            mmSocket = tmp;
        }

        @Override
        public void run(){
            mBluetoothAdapter.cancelDiscovery();

            try{
                mmSocket.connect();
            } catch (Exception connectException){
                try {
                    mmSocket.close();
                } catch (Exception closeException) {}
            }
            ManageConnectedSocket(mmSocket);
        }

        public void cancel(){
            try {
                mmSocket.close();
            } catch (Exception e){}
        }
    }

    /**   ************************************************
                  END OF CONNECT THREAD CLASS
         *************************************************
     */


    /**   ************************************************
                    CONNECTED THREAD CLASS
         *************************************************
     */
    private class ConnectedThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket){
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try{
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            }catch (Exception e){}

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true){
                try{
                    bytes = mmInStream.read(buffer);

                }catch (Exception e){
                    break;
                }
            }
        }

        public void write(byte[] bytes){
            try{
                mmOutStream.write(bytes);
            }catch (Exception e){}
        }

        public void cancel(){
            try{
                mmSocket.close();
            }catch (Exception e){}
        }
    }
    /**   ************************************************
                  END OF CONNECTED THREAD CLASS
         *************************************************
     */


    /**   ************************************************
                MANAGE CONNECTED SOCKET FUNCTION
          *************************************************
     */
    public void ManageConnectedSocket(BluetoothSocket socket){
        BluetoothDataTransfer = new ConnectedThread(socket);
        Runnable BluetoothDataTransferRunnable = new Runnable() {
            @Override
            public void run() {
                BluetoothDataTransfer.run();
            }
        };
        BluetoothDataTransferThread = new Thread(BluetoothDataTransferRunnable);
        BluetoothDataTransferThread.start();

        Toast.makeText(getApplicationContext(),"Successful connection!",Toast.LENGTH_LONG).show();
    }
    /**   ************************************************
              END OF MANAGE CONNECTED SOCKET FUNCTION
          *************************************************
     */
    /**   ************************************************
               END OF CLASSES FOR BLUETOOTH HANDLING
          *************************************************
     */
}

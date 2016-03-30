package com.productions.rk.tickitock;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class BluetoothProcessHandling extends Activity {

    String DeviceMACAddress;
    UUID uuid = UUID.fromString("c59332d0-4055-432a-a5bd-2684fc9fb5d5");
    BluetoothAdapter mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
    Thread BluetoothServerRun;
    private ArrayAdapter<String> madapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_process_handling);

        ListView deviceList = (ListView) findViewById(R.id.listView);
        madapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,
                android.R.id.text1);
        deviceList.setAdapter(madapter);
        registerReceiver(mReceiver, filter);
        Toast.makeText(getApplicationContext(),"above is enabled",Toast.LENGTH_LONG).show();

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivityForResult(discoverableIntent, 0);
        // To make sure bluetooth is enabled before starting discovery
        while(!mBluetoothAdapter.isEnabled());


        final AcceptThread BluetoothServer = new AcceptThread();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BluetoothServer.run();
            }
        };
        BluetoothServerRun = new Thread(runnable);
        BluetoothServerRun.start();

        Toast.makeText(getApplicationContext(),"above start des",Toast.LENGTH_LONG).show();
        mBluetoothAdapter.startDiscovery();

        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBluetoothAdapter.cancelDiscovery();
                String info = ((TextView) view).getText().toString();
                DeviceMACAddress = info.substring(info.length() - 17);
                BluetoothDevice ConnectToDevice = mBluetoothAdapter.getRemoteDevice(DeviceMACAddress);
                ConnectThread BluetoothClient = new ConnectThread(ConnectToDevice);
                BluetoothClient.run();
            }
        });
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
        startActivity(new Intent(BluetoothProcessHandling.this, OptionScreen.class));

       if(BluetoothServerRun.isAlive()){
           BluetoothServerRun.interrupt();
       }
       if(mBluetoothAdapter.isEnabled()){
           mBluetoothAdapter.disable();
       }
       BluetoothProcessHandling.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBluetoothAdapter!=null){
            mBluetoothAdapter.cancelDiscovery();
        }
        unregisterReceiver(mReceiver);
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            try{
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("tickitock",uuid);
                 Toast.makeText(getApplicationContext(), "accept thread constructor bingo!", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                 Toast.makeText(getApplicationContext(),"accept thread constructor exception",Toast.LENGTH_LONG).show();
            }

            mmServerSocket = tmp;
        }

        public void run(){
            BluetoothSocket socket;
            while (true){
                try{
                    socket = mmServerSocket.accept();
                }catch (Exception e){
                    break;
                }

                if(socket != null){

                    //manageconnectedsocket(mmServerSocket)
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

        public void run(){
            mBluetoothAdapter.cancelDiscovery();

            try{
                mmSocket.connect();
            } catch (Exception connectException){
                try {
                    mmSocket.close();
                } catch (Exception closeException) {}
            }
            //manageconnectedsocket(mmSocket)
        }

        public void cancel(){
            try {
                mmSocket.close();
            } catch (Exception e){}
        }
    }

}

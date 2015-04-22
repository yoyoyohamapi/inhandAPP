package com.inhand.milk.fragment.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class Bluetooth {

    private BluetoothAdapter  bluetoothAdapter;
    private static final int  REQUEST_ENABLE_BT = 1;
    private static final int MESSAGE_READ = 88;
    private static BluetoothDevice paired = null;
    private boolean isService = false;
    private Activity activity;
    private static UUID uuid = new UUID( 511024l,19910808l );
    private ArrayAdapter<String> mArrayAdapter;
    private IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    private IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    private IntentFilter filter3 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
    private List<BluetoothDevice> listdevice = new ArrayList<BluetoothDevice>();
    private ConnectedThread connectedThread;
    private AcceptThread acceptThread;
    private ConnectThread connectThread;
    private static final String ACTION_DISCOVERY_FINISHED = BluetoothAdapter.ACTION_DISCOVERY_FINISHED;
    private static final byte[] sayBye = {(byte) 255,(byte) 255};
    private final Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == MESSAGE_READ){
                Toast.makeText(activity.getApplicationContext(), new String( (byte[])(msg.obj)).subSequence(0, msg.arg1) ,
                        Toast.LENGTH_LONG).show();
                Log.i("消息",    new String(  (byte[])(msg.obj) ))  ;
                //( (byte[])msg.obj ).
            }
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //Toast.makeText(activity.getApplicationContext(),"aa", Toast.LENGTH_SHORT).show();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                listdevice.add(device);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                Toast.makeText(activity.getApplicationContext(),device.getName(), Toast.LENGTH_LONG).show();
            }
            else if ( ACTION_DISCOVERY_FINISHED.equals(action) ){
                Toast.makeText(activity.getApplicationContext(), "finish_discover", Toast.LENGTH_SHORT).show();
                activity.unregisterReceiver(mReceiver);
            }
            else if (bluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                Toast.makeText(activity, "discover start", Toast.LENGTH_SHORT).show();
            }
        }
    };





    public Bluetooth(Activity act) {
        // TODO Auto-generated constructor stub
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        activity = act;
        if(bluetoothAdapter == null){
            Toast.makeText(activity.getApplicationContext(), "你没有蓝牙设备,无法传输数据", Toast.LENGTH_LONG).show();
        }
        paired = selectFromBonded();
        mArrayAdapter =  new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1);
    }

    public void openBlue(){
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBluetooth,REQUEST_ENABLE_BT);
        }
    }

    public void discoverable(){
        Intent discoverableIntent = new  Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        activity.startActivityForResult(discoverableIntent,REQUEST_ENABLE_BT );
    }

    public boolean startSearch(){
        boolean result;
        mArrayAdapter.clear();
        listdevice.clear();
        activity.registerReceiver(mReceiver, filter1); // Don't forget to unregister during onDestroyactivity.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        activity.registerReceiver(mReceiver, filter2); // Don't forget to unregister during onDestroy
        activity.registerReceiver(mReceiver, filter3);
        Toast.makeText(activity, "准备发现模块", Toast.LENGTH_SHORT).show();
        result = bluetoothAdapter.startDiscovery();
        if (result == false){
            Toast.makeText(activity, "蓝牙没有开启", Toast.LENGTH_SHORT).show();
        }
        return result;

    }

    public ArrayAdapter<String> getListViewAdapter(){
        return mArrayAdapter;
    }

    public void setPaired(int pos){
        paired = listdevice.get(pos);
        bluetoothAdapter.cancelDiscovery();
        Log.i("device_name", paired.getName());
        Toast.makeText(activity.getApplicationContext(), "选择了"+paired.getName(), Toast.LENGTH_SHORT).show();
    }
    private BluetoothDevice selectFromBonded(){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                if (device.getName().equals( "HS-E917")){
                    Log.i(" find device",device.getName());
                    return device;
                }
            }
        }
        Log.i("no find device","null");
        return null;
    }


    public void asClient(){
        if(paired == null){
            Log.i("paired", "null");
            Toast.makeText(activity.getApplicationContext(), "没有配对对象",
                    Toast.LENGTH_LONG).show();
            return ;
        }
        Toast.makeText(activity.getApplicationContext(), "准备连接入"+paired.getName(),
                Toast.LENGTH_LONG).show();
        if(connectThread == null){
            connectThread = new ConnectThread(paired);
            connectThread.start();
            isService = false;
        }
    }


    private boolean  connect(BluetoothSocket socket ){
        if(socket !=null)
            connectedThread = new ConnectedThread(socket);
        connectedThread.start();
        Log.i(  "连入", String.valueOf(  socket.isConnected() )  );
        return socket.isConnected();
    }

    public void asServer(){
        isService = true;
        if(acceptThread == null ){
            acceptThread = new AcceptThread();
            acceptThread.start();
        }
        else {
            ShutConnect();
            acceptThread = new AcceptThread();
            acceptThread.start();
        }
    }

    public void ShutConnect(){
        if(connectedThread == null)
            if(isService){

                if(acceptThread != null)
                    acceptThread.cancel();
            }
            else {
                if(connectThread !=null)
                    connectThread.cancel();
            }
        else {
            connectedThread.cancel();
        }

    }



    public void sendStream(byte[] bytes){
        if( connectedThread !=null){
            connectedThread.write(bytes);
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;
        BluetoothSocket socket = null;
        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) { }
            socket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            bluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                socket.connect();

            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                Log.i("bluetooth", "连入"+paired.getName()+"失败");
                try {
                    socket.close();
                } catch (IOException closeException) { }
                return;
            }
            Log.i("bluetooth", "连入"+paired.getName()+":成功创建了socket");
            connect(socket);
            // Do work to manage the connection (in a separate thread)

        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) { }
        }
    }



    private class ConnectedThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket mmsocket) {
            mmSocket = mmsocket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = mmsocket.getInputStream();
                tmpOut = mmsocket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
	        	/*
	        	if(mmSocket.isConnected() == false){
	        		Log.i("bluetooth","关闭了连接");
	        		ShutConnect();
	        		break;
	        	}
	        	*/
                Log.i(  "连入", String.valueOf(  mmSocket.isConnected() )  );
                try {
                    Log.i("bluetooth", "连入成功—等待数据");
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    if (bytes == 2 &&buffer[0] == sayBye[0] && buffer[1] == sayBye[1]){
                        ShutConnect();
                    }
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                    Log.i("len", String.valueOf(bytes));
                    Log.i("run ",(String) new String(  buffer  ).subSequence(0 , bytes)  )  ;
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
                Log.i("发送",bytes.toString());
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                this.write(sayBye);
                mmSocket.close();
            } catch (IOException e) { }
        }
    }


    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("milk", uuid);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {

            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {

                try {
                    Log.i("bluetooth","listening ......");
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    Log.i("bluetooth","accpted");
                    try {
                        mmServerSocket.close();
                        connect(socket);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }

}



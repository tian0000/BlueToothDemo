//package com.example.tian.bluetoothdemo;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import java.io.InputStream;
//import java.util.UUID;
//
///**
// * @name yanantian
// * @motto 莫羡他人谢语花, 腹有诗书气自华
// * @E-mail 1173568715@qq.com
// * @WX 15978622391
// */
//
//public class Activity extends AppCompatActivity implements View.OnClickListener {
//    private Button but;
//    private ListView listview;
//    private BluetoothAdapter bluetoothAdapter;
//    private AcceptThread acceptThread;
//    private final UUID MY_UUID = UUID
//            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//和客户端相同的UUID
//    private final String NAME = "Bluetooth_Socket";
//    private BluetoothServerSocket serverSocket;
//    private InputStream is;//输入流
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Toast.makeText(Activity.this, String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
//            super.handleMessage(msg);
//        }
//    };
//    private BluetoothSocket socket;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        acceptThread = new AcceptThread();
//        acceptThread.start();
//        initView();
//    }
//
//    private void initView() {
//        but = (Button) findViewById(R.id.but);
//        listview = (ListView) findViewById(R.id.listview);
//        but.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.but) {
//
//
//        }
//    }
//
//    private class AcceptThread extends Thread {
//        public AcceptThread() {
//            try {
//                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void run() {
//            try {
//                socket = serverSocket.accept();
//                is = socket.getInputStream();
//                while (true) {
//                    byte[] bytes = new byte[1024];
//                    int read = is.read(bytes);
//                    Message message = new Message();
//                    message.obj = new String(bytes, 0, read, "UTF-8");
//                    handler.sendMessage(message);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

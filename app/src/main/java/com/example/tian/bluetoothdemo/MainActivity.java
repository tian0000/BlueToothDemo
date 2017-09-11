package com.example.tian.bluetoothdemo;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button kaiguan;
    private Button sousuo;
    private Button peidui;
    private Button fasong;
    private Button guanbi;
    private BluetoothAdapter mBluetoothAdapter;
    private ListView listview;
    private MyAdapter ma;
    private ArrayList<Bean> list = new ArrayList<>();
    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;
    private String names;
    private String contents;
    private final UUID MY_UUID = UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//随便定义一个
    private BluetoothSocket clientSocket;
    private BluetoothDevice device;
    private OutputStream os;//输出流
    private void requestBluetoothPermission() {
        //判断系统版本
        if (Build.VERSION.SDK_INT >= 23) {
            //检测当前app是否拥有某个权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            //判断这个权限是否已经授权过
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要 向用户解释，为什么要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION))
                    Toast.makeText(this, "Need bluetooth permission.",
                            Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_BLUETOOTH_PERMISSION);
                return;
            } else {
            }
        } else {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        kaiguan = (Button) findViewById(R.id.kaiguan);
        sousuo = (Button) findViewById(R.id.sousuo);
        peidui = (Button) findViewById(R.id.peidui);
        fasong = (Button) findViewById(R.id.fasong);
        guanbi = (Button) findViewById(R.id.guanbi);

        kaiguan.setOnClickListener(this);
        sousuo.setOnClickListener(this);
        peidui.setOnClickListener(this);
        fasong.setOnClickListener(this);
        guanbi.setOnClickListener(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listview = (ListView) findViewById(R.id.listview);
        ma = new MyAdapter(list, this);
        listview.setAdapter(ma);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                names = list.get(i).getName();
                contents = list.get(i).getContent();
                String trim = contents.substring(contents.indexOf(":") + 1).trim();
//                连接蓝牙服务器
                try {
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    if (device == null) {
                        device = mBluetoothAdapter.getRemoteDevice(trim);
                    }

                    try {
                        if (clientSocket == null) {
                            clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                            clientSocket.connect();
                            os = clientSocket.getOutputStream();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (os != null) {

                        os.write("蓝牙信息来了".getBytes("UTF-8"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kaiguan:
//                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
                mBluetoothAdapter.enable(); //开启
                break;
            case R.id.sousuo:
                initHuoQu();
                break;
            case R.id.peidui:


                break;
            case R.id.fasong:
                break;
            case R.id.guanbi:
                mBluetoothAdapter.disable(); //关闭
                break;
        }
    }

    private void initHuoQu() {
//        获取已经配对的蓝牙设备
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                Bean bean = new Bean();
                bean.setName(device.getName());
                bean.setContent(device.getAddress());
                list.add(bean);
            }
            ma.notifyDataSetChanged();
        }

        // 设置广播信息过滤
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//每搜索到一个设备就会发送一个该广播
        this.registerReceiver(receiver, filter);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
//        filter.setPriority(Integer.MAX_VALUE);//设置优先级
//        注册蓝牙搜索广播接收者，接收并处理搜索结果
        this.registerReceiver(receiver, filter);
//        如果当前在搜索，就先取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
//        开启搜索
        mBluetoothAdapter.startDiscovery();

    }

    /**
     * 定义广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    Bean bean = new Bean();
                    bean.setName(device.getName());
                    bean.setContent(device.getAddress());
                    list.add(bean);
                }
                ma.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //已搜素完成
                Log.e("tian", "搜索完成");
            }
        }
    };
}

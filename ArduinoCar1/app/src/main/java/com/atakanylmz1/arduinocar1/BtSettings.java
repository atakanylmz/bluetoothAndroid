package com.atakanylmz1.arduinocar1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BtSettings extends AppCompatActivity {
    private TextView txtBtState;
    private Button btn_enable;
    private Button btn_view_paired;
    private Button btn_scanPair;
    private ProgressDialog mProgressDlg;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();/*Bluetooth deviceları için arraylist oluşturuyoruz.*/
    private BluetoothAdapter mBluetoothAdapter;/*Bluetooth adapter oluşturuyoruz.*/
    private Button btn_aboutArduino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_settings);
        txtBtState = (TextView) findViewById(R.id.txt_bt_state);
        btn_enable = (Button) findViewById(R.id.btn_enable);
        btn_view_paired = (Button) findViewById(R.id.btn_viewConnect_paired);
        btn_scanPair = (Button) findViewById(R.id.btn_scanPair);
        btn_aboutArduino=(Button)findViewById(R.id.btn_aboutArduino);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();/*Adapterımıza cihazdaki default olan adapteri atyoruz.*/
        mProgressDlg = new ProgressDialog(this);
        mProgressDlg.setMessage("Scanning...");
        mProgressDlg.setCancelable(false);
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mBluetoothAdapter.cancelDiscovery();
            }
        });

        btn_aboutArduino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),AboutArduinoCode.class);
                startActivity(intent);
            }
        });


        if (mBluetoothAdapter == null) {
            showUnsupported();/*Adapter boş ise desteklenmiyor diye TOAST basıyoruz.*/
        } else {
            btn_view_paired.setOnClickListener(new View.OnClickListener() {/*Eşleşmiş cihazları telefonumuzda arıyor.*/
                @Override
                public void onClick(View v) {
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                    if (pairedDevices == null || pairedDevices.size() == 0) {
                        showToast("There is not a paired device!");
                    } else {
                        /*Eşleşmiş cihazları listeleye ekliyoruz.*/
                        ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
                        list.addAll(pairedDevices);
                        Intent intent = new Intent(BtSettings.this, DeviceList.class);
                        intent.putParcelableArrayListExtra("device.list", list);
                        startActivity(intent);
                    }
                }
            });
            btn_scanPair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    /*Eşleştirme işlemi için cihaz arıyoruz.*/
                    mBluetoothAdapter.startDiscovery();
                }
            });
            btn_enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Bluetooth açıp kapatıyoruz.*/
                    if (mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.disable();

                    } else {
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, 1000);
                    }
                    if (mBluetoothAdapter.isEnabled()) {
                        showEnabled();
                    }else {
                        showDisabled();
                    }
                }
            });

        }
        //endregion

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);

    }
    /*****************************
     * onCreate END
     *********************/
    @Override
    public void onPause() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void showEnabled() {
        txtBtState.setText("The Bluetooth is opened");
        txtBtState.setTextColor(Color.BLUE);
        btn_enable.setText("Close");
        btn_enable.setEnabled(true);
        btn_view_paired.setEnabled(true);
        btn_scanPair.setEnabled(true);
        btn_view_paired.setBackgroundColor(Color.parseColor("#f7efa646"));
        btn_scanPair.setBackgroundColor(Color.parseColor("#282BB6"));
    }

    private void showDisabled() {
        txtBtState.setText("The Bluetooth is closed");
        //f77f0202
        txtBtState.setTextColor(Color.rgb(128, 0, 0));
        btn_enable.setText("Open");
        btn_enable.setEnabled(true);
        btn_view_paired.setEnabled(false);
        btn_scanPair.setEnabled(false);
        btn_view_paired.setBackgroundColor(Color.parseColor("#c9ac71"));
        btn_scanPair.setBackgroundColor(Color.parseColor("#a2dfcc"));
    }

    private void showUnsupported() {
        txtBtState.setText("Your bluetooth device is not supported");
        btn_enable.setText("Açık");
        btn_enable.setEnabled(false);
        btn_view_paired.setEnabled(false);
        btn_scanPair.setEnabled(false);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                if (state == BluetoothAdapter.STATE_ON) {
                    showToast("Opened");
                    showEnabled();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();
                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                /*Tarama işlemi bittiyse listeye eklediğimiz cihazları göstermek için DeviceListActivity sınıfına gönderiyoruz.*/
                mProgressDlg.dismiss();
                Intent newIntent = new Intent(BtSettings.this, DeviceList.class);
                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);
                startActivity(newIntent);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(device);
                showToast("Finding devices " + device.getName());
            }
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*Geri tuşuna bastığımızda bu class ta finish ediyoruz ve main classına gidiyoruz.*/
            startActivity(new Intent(BtSettings.this, MainActivity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



}

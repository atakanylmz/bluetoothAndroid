package com.atakanylmz1.arduinocar1;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;

    volatile boolean stopWorker;
    boolean connectionstate = false;
    byte BtAdapterSayac = 0;
    Button btnHorn, btnLight1, btnAyarlar, btnLight2;
    boolean bisChecked = false;
    Button ileri_btn,geri_btn,sag_ileri_btn,sol_ileri_btn,sag_geri_btn,sol_geri_btn,sag_btn,sol_btn;
    boolean farDurum1=false,farDurum2=false,kornaDurum=false;
    TextView txtBilgi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle data=null;
        String address=null;
        btnHorn = (Button) findViewById(R.id.btnHorn);
        btnLight1 = (Button) findViewById(R.id.btnLight1);
        btnAyarlar = (Button) findViewById(R.id.btnSettings);
        btnLight2 = (Button) findViewById(R.id.btnLight2);
        txtBilgi=(TextView)findViewById(R.id.txtBilgi);
        ileri_btn=(Button)findViewById(R.id.ileri_btn);
        geri_btn= (Button)findViewById(R.id.geri_btn);
        sag_ileri_btn=(Button)findViewById(R.id.sag_ileri_btn);
        sol_ileri_btn=(Button)findViewById(R.id.sol_ileri_btn);
        sag_geri_btn=(Button)findViewById(R.id.sag_geri_btn);
        sol_geri_btn=(Button)findViewById(R.id.sol_geri_btn);
        sag_btn=(Button)findViewById(R.id.sag_btn);
        sol_btn=(Button)findViewById(R.id.sol_btn);

        data=getIntent().getExtras();
        if(data!=null)
        address=data.getString("btDeviceAddress");
        if(data!=null&&address!=null){
            try {
                openBT();
                findBT(address);
                openBT();
                findBT(address);

            }
            catch (IOException e){

            }
        }
        else{
            txtBilgi.setText("Not Connected");

        }

        ileri_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    try
                    {
                        sendData("F");
                        ileri_btn.setBackgroundResource(R.mipmap.kileri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){

                    try
                    {
                        sendData("S");
                        ileri_btn.setBackgroundResource(R.mipmap.ileri);

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });
        geri_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    try
                    {
                        sendData("B");
                        geri_btn.setBackgroundResource(R.mipmap.kgeri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){

                    try
                    {
                        sendData("S");
                        geri_btn.setBackgroundResource(R.mipmap.geri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });

        sag_ileri_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    try
                    {
                        sendData("I");
                        sag_ileri_btn.setBackgroundResource(R.mipmap.ksag_ileri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){

                    try
                    {
                        sendData("S");
                        sag_ileri_btn.setBackgroundResource(R.mipmap.sag_ileri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });

        sol_ileri_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    try
                    {
                        sendData("G");
                        sol_ileri_btn.setBackgroundResource(R.mipmap.ksol_ileri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){

                    try
                    {
                        sendData("S");
                        sol_ileri_btn.setBackgroundResource(R.mipmap.sol_ileri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });

        sag_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    try
                    {
                        sendData("R");
                        sag_btn.setBackgroundResource(R.mipmap.ksag);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){

                    try
                    {
                        sendData("S");
                        sag_btn.setBackgroundResource(R.mipmap.sag);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });
        sol_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    try
                    {
                        sendData("L");
                        sol_btn.setBackgroundResource(R.mipmap.ksol);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){

                    try
                    {
                        sendData("S");
                        sol_btn.setBackgroundResource(R.mipmap.sol);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });


        sag_geri_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    try
                    {
                        sendData("J");
                        sag_geri_btn.setBackgroundResource(R.mipmap.ksag_geri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){

                    try
                    {
                        sendData("S");
                        sag_geri_btn.setBackgroundResource(R.mipmap.sag_geri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });

        sol_geri_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    try
                    {
                        sendData("H");
                        sol_geri_btn.setBackgroundResource(R.mipmap.ksol_geri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){

                    try
                    {
                        sendData("S");
                        sol_geri_btn.setBackgroundResource(R.mipmap.sol_geri);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });

        btnAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BtSettings.class));
                finish();
            }
        });

        btnLight1.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("EmptyCatchBlock")
            @Override
            public void onClick(View view) {

                try {
                /*Bluetooth bağlantımız aktifse veri gönderiyoruz.*/
                        if(farDurum1==false)
                        {
                            sendData("W");
                            farDurum1=true;
                            btnLight1.setBackgroundResource(R.mipmap.far1);
                        }
                        else
                        {
                            sendData("w");
                            farDurum1=false;
                            btnLight1.setBackgroundResource(R.mipmap.far0);

                        }

                } catch (Exception ignored) {
                }
            }
        });

        btnLight2.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("EmptyCatchBlock")
            @Override
            public void onClick(View view) {

                try {
                /*Bluetooth bağlantımız aktifse veri gönderiyoruz.*/
                        if(farDurum2==false)
                        {
                            sendData("U");
                            farDurum2=true;
                            btnLight2.setBackgroundResource(R.mipmap.far3);
                        }
                        else
                        {
                            sendData("u");
                            farDurum2=false;
                            btnLight2.setBackgroundResource(R.mipmap.far2);
                        }
                    }

                catch (Exception ignored)
                {
                }
            }
        });
        btnHorn.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("EmptyCatchBlock")
            @Override
            public void onClick(View view) {

                try {
                /*Bluetooth bağlantımız aktifse veri gönderiyoruz.*/
                        if (kornaDurum == false) {
                            sendData("V");
                            kornaDurum = true;
                            btnHorn.setBackgroundResource(R.mipmap.horn1);
                        } else {
                            sendData("v");
                            kornaDurum = false;
                            btnHorn.setBackgroundResource(R.mipmap.horn0);
                        }

                } catch (Exception ignored) {
                }
            }
        });

    }


    void openBT() throws IOException {

        /*Bluetooth u açıyoruz.*/
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService I
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
        }
        catch (Exception ignored) {
        }

    }

    void findBT(String btAddress) {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                txtBilgi.setText("There is not a Bluetooth Adapter");
            }
            if (BtAdapterSayac == 0) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBluetooth, 0);
                    BtAdapterSayac = 1;
                }
            }
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (btAddress.equals(device.getAddress().toString())) {
                        mmDevice = device;
                        txtBilgi.setText("Connected");
                        connectionstate = true;
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    void closeBT() throws IOException {
        try {
            /*Aktif olan bluetooth bağlantımızı kapatıyoruz.*/
            if (mBluetoothAdapter.isEnabled()) {
                stopWorker = true;
                mBluetoothAdapter.disable();
                mmOutputStream.close();
                mmInputStream.close();
                mmSocket.close();
            } else {
            }
        } catch (Exception ignored) {
        }
    }

    void sendData(String data) throws IOException {
            try {
            if (connectionstate) {
                /*Bluetooth bağlantımız aktifse veri gönderiyoruz.*/
                mmOutputStream.write(data.getBytes());
            }
            else {
                txtBilgi.setText("not connected");
            }
        } catch (Exception ignored) {
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", null).show();
    }
}

package com.atakanylmz1.arduinocar1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutArduinoCode extends AppCompatActivity {
    private Button btn_commands;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_arduino_code);
        btn_commands=(Button)findViewById(R.id.btn_commands);
        btn_commands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Commands.class);
                startActivity(intent);
            }
        });
    }
}

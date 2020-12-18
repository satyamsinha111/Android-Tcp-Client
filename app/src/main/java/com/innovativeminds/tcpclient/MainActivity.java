package com.innovativeminds.tcpclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Client client;
    private Thread thread;
    private Button btnSend;
    private EditText message,host,port;
    public static TextView status;
    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public  boolean isApOn(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(getApplicationContext().WIFI_SERVICE);
        try {
            Method method = wifimanager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (Boolean) method.invoke(wifimanager);
        }
        catch (Throwable ignored) {}

        return false;
    }

    public void connect(View view){
        host=(EditText)findViewById(R.id.editText);
        port=(EditText)findViewById(R.id.editText2);

        if(!host.getText().toString().equals("") && !port.getText().toString().equals(""))
        {
            int portInt= Integer.parseInt(port.getText().toString());
            status=(TextView)findViewById(R.id.status);
            client=new Client(host.getText().toString(),portInt);
            thread=new Thread(client);
            thread.start();
            status.setText("Connecting");
            status.setTextColor(ContextCompat.getColor(this, R.color.connecting));
            Toast.makeText(this, "Connecting", Toast.LENGTH_SHORT).show();
        }
        else if(host.getText().toString().equals("") && port.getText().toString().equals("")){
            Toast.makeText(this, "Please enter the host address and port address", Toast.LENGTH_SHORT).show();
        }

    }
    public static void update(final String str,final String color) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // This gets executed on the UI thread so it can safely modify Views
                status.setText(str);
                status.setTextColor(Color.parseColor(color));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client=null;
        thread.destroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void  send(View view){
        message=(EditText)findViewById(R.id.editText3);
        String cmd = message.getText().toString();
        if(!cmd.equals("")) {
            if (null != client) {

                client.sendMessage(cmd);
            } else {
                Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
            }
        }
        else if(cmd.equals("")){
            Toast.makeText(this, "Please enter the message", Toast.LENGTH_SHORT).show();
        }
    }

}

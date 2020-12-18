package com.innovativeminds.tcpclient;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.net.*;
import java.io.*;

import java.io.IOException;

public class Client implements Runnable {
    public static Socket socket=null;
    private String hostIp=null;
    private int port=0;
    Handler handler = new Handler();
    Thread runOnUiThread=new Thread();

//    public Client(Socket socket, String hostIp, int port) {
//        this.socket = socket;
//        this.hostIp = hostIp;
//        this.port = port;
//    }

    public Client() {

    }

    public Client(String hostIp, int port) {
        this.hostIp = hostIp;
        this.port = port;
    }

    public Client(int port) {
        this.port = port;
    }

    public Client(String hostIp) {
        this.hostIp = hostIp;
    }

//    public void setSocket(Socket socket) {
//        this.socket = socket;
//    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getHostIp() {
        return hostIp;
    }

    public int getPort() {
        return port;
    }






    @Override
    public void run() {
        try{
            System.out.println("Connecting to 192.168.43.24 at port 143");
            socket=new Socket("192.168.43.24",143);
            System.out.println("Connected");


            System.out.println("Sending message to the server");
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            MainActivity.update("Connected","#218F76");
            bw.write("Hi you are connected");
            bw.flush();

            System.out.println("Message sent to the server");

        }
        catch (IOException e){
            MainActivity.update("Connection failed","#B83227");
        }
        catch (Exception e){
            MainActivity.update("Connection failed","#B83227");
        }

    }
    public void sendMessage(final String message)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    if(null!=socket){
                        System.out.println("Sending message to the server");
                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        BufferedWriter bw = new BufferedWriter(osw);
                        bw.write(message);
                        bw.flush();
                        System.out.println("Message sent to the server");
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

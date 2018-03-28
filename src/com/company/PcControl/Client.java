package com.company.PcControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.43.1",5000);
            if(socket != null){
                System.out.println("Success");
                BufferedReader inp = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
                String string = inp.readLine() ;
                System.out.println(string);
                if(string != null)
                {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("Message Received");
                    out.flush();
                    System.out.println("Report Sent");


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

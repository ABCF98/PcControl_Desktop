package com.company.PcControl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000) ;

        System.out.println("Server is Waiting") ;

        Socket socket = serverSocket.accept() ;
        System.out.println("Socket Accepted");

        /*InputStream inputStream = socket.getInputStream() ;
        BufferedReader inpFromClient = new BufferedReader(new InputStreamReader(inputStream));
        String fileName = inpFromClient.readLine() ;

        System.out.println(fileName) ;


        String presentDir = System.getProperty("user.dir") ;
        System.out.println(presentDir + "\\" + fileName) ;
        BufferedReader fileReader = new BufferedReader(new FileReader(presentDir + "\\" + fileName)) ;

        OutputStream outputStream = socket.getOutputStream() ;
        PrintWriter outToClient = new PrintWriter(outputStream, true) ;

        String line = null ;

        while((line = fileReader.readLine()) != null) {
            outToClient.println(line) ;
            System.out.println(line) ;
        }

        outToClient.close() ;
        fileReader.close() ;
        inpFromClient.close() ;
        */
        socket.close() ;
        serverSocket.close() ;
    }
}

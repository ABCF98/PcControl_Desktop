/*
package com.company.PcControl;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;


public class PCtoMobile {
    // Method to connect to the android server using ip address and port
    public void connect(InetAddress inetAddress, int port) {
        new Service<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        Thread.sleep(3000);
                        connectToAndroid(inetAddress, port);
                        return null;
                    }

                };
            }

        }.start();
    }
    // Method to establish socket connection between server (Mobile) and client(PC) and also gets the input and output srteams
    private void connectToAndroid(InetAddress inetAddress, int port) {
        try {
            SocketAddress socketAddress
                    = new InetSocketAddress(inetAddress, port);
            clientSocket = new Socket();

            // 3s timeout
            clientSocket.connect(socketAddress, 3000);
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectInputStream = new ObjectInputStream(inputStream);

            // Request Android to fetch files list for root directory
fetchDirectory("/");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    // Closes all the started streams and sockets and thus terminates the given connection
    public static void closeConnectionToAndroid() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//*
/ Allows one to send device to Server(android) from CLient(PC)
    public static void sendMessageToAndroid(String message) {
        if (clientSocket != null) {
            try {
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    // Get the list of files on the android device and display it on the main screen
    public static void fetchDirectory(String path) {
        try {
            sendMessageToAndroid("FILE_DOWNLOAD_LIST_FILES");
            sendMessageToAndroid(path);
            ArrayList<AvatarFile> filesInFolder
                    = (ArrayList<AvatarFile>) objectInputStream.readObject(); // Receives list of files in android
            if (filesInFolder == null || filesInFolder.isEmpty()) {
            } else {
                MainScreenMaster.showFiles(filesInFolder); // Shows all the files in the android folder on the mainScreen Controller
                MainScreenMaster.displayPath(path); // Displays the path of the directory which contain all the files
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
*/

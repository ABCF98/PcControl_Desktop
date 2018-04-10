package com.company.PcControl;

import com.company.PcControl.file.FileHead;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;


public class Server1 {

    private int port;
    private Label messageLabel, statusLabel;
    private Button connectButton;


    public void connect(Button connectButton, javafx.scene.control.Button resetButton, Label statusLabel, Label msgLabel, int port) {
        this.messageLabel = msgLabel;
        this.statusLabel = statusLabel;
        this.connectButton = connectButton;
        String message;
        MouseKeyboardControl mouseControl = new MouseKeyboardControl(); // An Object of the mouse keyboard control class - That allows the control over the input device
        PowerOff  powerOff = new PowerOff(); // NEEDS TO BE TESTED

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // This allows to make a full screen pane
        int screenWidth = (int) screenSize.getWidth(); // Setting the width of the window to the size of screen
        int screenHeight = (int) screenSize.getHeight(); // Setting the height of the window to the size of the scree


        try {
            System.out.println("Port Number:" + port);
            Main.serverSocket = new ServerSocket(9999);


            System.out.println("Server is Waiting");
            //Main.socket = Main.serrverSocket.accept() ;
            try {
                Main.socket =
                        Main.serverSocket.accept();
                System.out.println("Socket Accepted");

                //showMessage("Socket Accepted");
                InetAddress remoteInetAddress =
                        Main.socket.getInetAddress();
                String connectedMessage = "Connected to: " + remoteInetAddress;
                Platform.runLater(() -> {
                    statusLabel.setText(connectedMessage);
                });
                showMessage("Connection Established");
            } catch (Exception e) {
                System.out.println(e);
            }

            Main.inputStream = Main.socket.getInputStream();
            System.out.println("input stream obtained");

            Main.outputStream = Main.socket.getOutputStream();
            System.out.println("output stream obtained");

            Main.objectOutputStream = new ObjectOutputStream(Main.outputStream);
            Main.objectInputStream = new ObjectInputStream(Main.inputStream);

            Boolean cond = true;
            while (cond) {
                {
                    try {
                        message = (String) Main.objectInputStream.readObject(); // Receives the message from the android device, ehich determines which function to perform
                        int keyCode;
                        if (message != null) {
                            switch (message) {
                                case "LEFT_CLICK": // 1
                                    mouseControl.leftClick(); // Performs the left click operation on the pc
                                    break;
                                case "RIGHT_CLICK": //2
                                    mouseControl.rightClick(); // Performs the right click operation on the pc
                                    break;
                                case "DOUBLE_CLICK": //3
                                    mouseControl.doubleClick();// Performs the double click operation on the pc
                                    break;
                                case "MOUSE_WHEEL": //4
                                    int scrollAmount = (int) Main.objectInputStream.readObject(); // Receives the amount that needs to be scrolled
                                    mouseControl.mouseWheel(scrollAmount);// Performs the scroll wheel operation of the mouse
                                    break;
                                case "MOUSE_MOVE": //5
                                    int x = (int) Main.objectInputStream.readObject(); // Gets the x co-ordinate
                                    int y = (int) Main.objectInputStream.readObject(); // Gets the y co-ordinate
                                    Point point = MouseInfo.getPointerInfo().getLocation(); // Gets the current pointer location on the screen
                                    // Get current mouse position
                                    float nowx = point.x; // gets the x co-ordinate on screen
                                    float nowy = point.y; // gets the y co-ordinate on screen
                                    mouseControl.mouseMove((int) (nowx + x), (int) (nowy + y)); // Moves the mouse to the new co-ordinates
                                    break;
                                case "MOUSE_MOVE_LIVE": //6:
                                    // need to adjust coordinates
                                    float xCord = (float) Main.objectInputStream.readObject(); // gets the proportion of movement on x
                                    float yCord = (float) Main.objectInputStream.readObject(); // gets the proportion of movement on y
                                    xCord = xCord * screenWidth; // Gets corresponding value of movement on screen
                                    yCord = yCord * screenHeight; // Gets corresponding value of movement on y
                                    mouseControl.mouseMove((int) xCord, (int) yCord); //move the mouse pointer to the given x and y co-ordinates
                                    break;
                                case "KEY_PRESS": //7
                                    keyCode = (int) Main.objectInputStream.readObject();
                                    mouseControl.keyPress(keyCode); //Presses the given key according to the key-press operation
                                    break;
                                case "KEY_RELEASE": //8
                                    keyCode = (int) Main.objectInputStream.readObject();
                                    mouseControl.keyRelease(keyCode); //Presses the given key according to the key-press operation
                                    break;
                                case "CTRL_ALT_T": //9
                                    mouseControl.ctrlAltT(); // This combination of keys is executed
                                    break;
                                case "CTRL_SHIFT_Z": //10
                                    mouseControl.ctrlShiftZ(); // This combination of keys is executed
                                    break;
                                case "ALT_F4": //11
                                    mouseControl.altF4(); // This combination of keys is executed
                                    break;
                                case "TYPE_CHARACTER": //12
                                    //handle StringIndexOutOfBoundsException here when pressing soft enter key
                                    char ch = ((String) Main.objectInputStream.readObject()).charAt(0);
                                    mouseControl.typeCharacter(ch);
                                    break;
                                case "TYPE_KEY": //13
                                    keyCode = (int) Main.objectInputStream.readObject();
                                    mouseControl.typeCharacter(keyCode);
                                    break;
                                case "LEFT_ARROW_KEY": //14
                                    mouseControl.pressLeftArrowKey(); // The left arrow key is pressed
                                    break;
                                case "DOWN_ARROW_KEY": //15
                                    mouseControl.pressDownArrowKey(); // The down arrow key is pressed
                                    break;
                                case "RIGHT_ARROW_KEY"://16
                                    mouseControl.pressRightArrowKey(); // The right arrow key is pressed
                                    break;
                                case "UP_ARROW_KEY": // 17
                                    mouseControl.pressUpArrowKey(); // The up arrow key is pressed
                                    break;
                                case "F5_KEY": //18
                                    mouseControl.pressF5Key(); // The left arrow key is pressed
                                    break;
                                case "SHUTDOWN_PC": //22
                                    powerOff.shutdown(); // Turns the PC off
                                    break;
                                case "RESTART_PC": //23
                                    powerOff.restart(); // Restarts the PC
                                    break;
                                case "SLEEP_PC": //24
                                    powerOff.suspend(); // Suspends the PC
                                    break;
                                case "LOCK_PC": //25
                                    powerOff.lock(); // Locks the PC
                                    break;
                                case "DISCONNECT":
                                    disconnect();
                                    cond = false;
                                    break;
                                case "FILE_GET":
                                    FileHead tempHead = FileHead.readFileHead(Main.objectInputStream) ;  // FileHead clicked by the user
                                    System.out.println(tempHead.getPath()) ;
                                    if(tempHead.isDir()) {                          // Checks if it is a directory
                                        sendFilesList(Main.objectOutputStream, Main.objectInputStream, tempHead.getPath()) ;
                                    }
                                    else {
                                        sendBufFile(Main.outputStream, tempHead.getPath()) ;
                                    }
                                    break;
                                case "GET_FILE_LIST":
                                    showMessage("Sending list of file");
                                    System.out.println("Sending file list");
                                    sendFilesList(Main.objectOutputStream,Main.objectInputStream,"~/");
                                    System.out.println("Data Sent");
                                    break;

                            }
                        } else
                            break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Main.socket.close();
                        Main.serverSocket.close();
                        //PCtoMobile.closeConnectionToAndroid();

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Main.socket.close();
            Main.serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /************************** TO SEND THE FILE
     *
     * @param outputStream
     * @param filePath
     * @throws IOException
     *
     * Uses Data streams to transfer
     * Creates a byte array and fills it with the file's bytes
     * Writes the full data to output stream
     */
    public static void sendBufFile(OutputStream outputStream, String filePath) throws IOException {
        File file = new File(filePath) ;
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file))) ;
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream) ;

        byte[] bytes = new byte[(int) file.length()] ;
        System.out.println(bytes.length) ;
        dataInputStream.readFully(bytes, 0, bytes.length) ;

        dataOutputStream.write(bytes, 0, bytes.length) ;
        dataOutputStream.flush() ;

        dataOutputStream.close() ;
    }



    /******************************* TO SEND THE FILE LIST
     *
     * @param objectOutputStream
     * @param objectInputStream
     * @param parentDir
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * Receives the file list at the given path
     * Checks if it is null
     * Sends each element of the list
     *
     */
    public static void sendFilesList(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String parentDir) throws IOException, ClassNotFoundException {
        ArrayList<FileHead> fileHeadArrayList = FileList.getFileHeadArrayList(parentDir) ;
        if(fileHeadArrayList != null) {
            objectOutputStream.writeObject("Not Null") ;
            objectOutputStream.writeInt(fileHeadArrayList.size());

            for(int i = 0; i < fileHeadArrayList.size(); i++) {
                fileHeadArrayList.get(i).sendFileHead(objectOutputStream) ;
                System.out.println(fileHeadArrayList.size());
            }
            FileList.print(fileHeadArrayList) ;

        } else {
            objectOutputStream.writeObject("Null") ;
        }
    }

    private void disconnect() {
        try {
            Main.objectOutputStream.close();
            Main.objectInputStream.close();
            Main.inputStream.close();
            Main.outputStream.close();
            Main.socket.close();
            Main.serverSocket.close();
            connectButton.setDisable(false);
            showStatus("Not connected");
            showMessage("No connection");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setPort(int port) {
        this.port = port;
    }

    private void showMessage(String message) {
        Platform.runLater(() -> {
            messageLabel.setText(message);
        });
    }

    private void showStatus(String message) {
        Platform.runLater(() -> {
            statusLabel.setText(message);
        });
    }
}

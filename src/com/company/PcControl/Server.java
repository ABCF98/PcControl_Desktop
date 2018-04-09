package com.company.PcControl;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;




public class Server extends Thread {

    private int port;

    @Override
    public void run() {
        String message;
        MouseKeyboardControl mouseControl = new MouseKeyboardControl(); // An Object of the mouse keyboard control class - That allows the control over the input device
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // This allows to make a full screen pane
        int screenWidth = (int) screenSize.getWidth(); // Setting the width of the window to the size of screen
        int screenHeight = (int) screenSize.getHeight(); // Setting the height of the window to the size of the scree


        try {
            System.out.println("Port Number:" + port);
            Main.serverSocket = new ServerSocket(9999) ;


        System.out.println("Server is Waiting") ;
        //Main.socket = Main.serrverSocket.accept() ;
        try {
            Main.socket =
                    Main.serverSocket.accept();
            System.out.println("Socket Accepted");

            //showMessage("Socket Accepted");
            InetAddress remoteInetAddress =
                    Main.socket.getInetAddress();
            String connectedMessage = "Connected to: " + remoteInetAddress;
            /*Main.msgLabel.setText(connectedMessage);
            Platform.runLater(() -> {
                statusLabel.setText(connectedMessage);
            });
            showMessage(connectedMessage);*/
        }catch (Exception e){
            System.out.println(e);
        }

        Main.inputStream = Main.socket.getInputStream() ;
        System.out.println("input stream obtained");

        Main.outputStream = Main.socket.getOutputStream() ;
        System.out.println("output stream obtained");

        Main.objectOutputStream = new ObjectOutputStream(Main.outputStream);
        Main.objectInputStream = new ObjectInputStream(Main.inputStream);


        while (true){
            {
                try {
                    message = (String) Main.objectInputStream.readObject(); // Receives the message from the android device, ehich determines which function to perform
                    int keyCode;
                    if (message != null) {
                        switch (message)
                        {
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

                        }
                    }
                    else
                        break;
                } catch (Exception e) {
                    e.printStackTrace();
                    //PCtoMobile.closeConnectionToAndroid();

                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Main.socket.close() ;
            Main.serverSocket.close() ;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setPort(int port) {
        this.port = port;
    }


}
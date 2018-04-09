package com.company.PcControl;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class Main implements Initializable {
    public Label portNum;
    public Label ipAdd;
    public AnchorPane label;
    public Label statusLabel;
    public Label msgLabel;
    public Button ConnectBtn;

    public Main() {
        System.out.println("Hello");
    }

    public static Main mainScreenController;
    public static Socket socket;

  
    @FXML
    public static Button ResetBtn;
    public static ServerSocket serverSocket;
    public static InputStream inputStream;
    public static OutputStream outputStream;
    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;


    public void start(ActionEvent actionEvent) {
        
        setConnectionDetails();
        ConnectBtn.setDisable(true);

    }

    private void setConnectionDetails() {
        String ipAddresses[] = new MyIPAddressGather().ipAddress();
        String connectionStatus = "Not Connected";
        String ipAddress = ipAddresses[0];
        int port = 9000;
        if (ipAddresses[1] != null) {
            ipAddress = ipAddress + " | " + ipAddresses[1];
            System.out.println("Printing ip Address" + ipAddress);

        }
        ipAdd.setText("IP Address is :" + ipAddress);

        if(portNum != null) {
            portNum.setText("Port Number : 9999");
        }
        statusLabel.setText(connectionStatus);
        if (ipAddresses[0].equals("127.0.0.1")) {
            showMessage("Connect your PC to Android phone hotspot or" +
                    " connect both devices to a local network.");
        } else {
            try {
                ConnectBtn.setDisable(true);
                startServer(port);
            } catch(Exception e) {
                showMessage("Error in initializing server");
                e.printStackTrace();
            }
        }
    }// Setting up connection : Getting both the PC and the android device on the same Network


    private void startServer(int port) throws Exception {
        new Service<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        new Server1().connect(ConnectBtn,ResetBtn,statusLabel,msgLabel,9999);
                        System.out.println("Starting server");
                        return null;
                    }

                };
            }

        }.start();
    } // Calls the server.connect() method to run as a service thereby starting the PC Server


  /*  public static void showMessage(String message) {
        Platform.runLater(() -> {
            msgLabel.setText(message);
        });
    }*/ // Displays the message in the message label on MainScreen.fxml

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void setMainScreenController(Main mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public static Main getMainScreenController() {
        return mainScreenController;
    }
    public void showMessage(String msg) {
        Platform.runLater(() -> {
            msgLabel.setText(msg);
        });
    } // Displays the message in the message label on MainScreen.fxml

    public void stop(ActionEvent actionEvent) {

        try {

            objectOutputStream.close();
            inputStream.close();
            objectInputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
            ResetBtn.setDisable(true);
            ConnectBtn.setDisable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.company.PcControl;

import java.net.ServerSocket;

public class TakeFreePorts {

    private static boolean isPortAvailable(int port) {
        boolean portAvailable = true;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch(Exception e) {
            portAvailable = false;
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch(Exception e) {
                    e.printStackTrace();
                };
            }
        }
        return portAvailable;
    }

    public static int getFreePort() {
        int port = 9000;
        while (true) {
            if (isPortAvailable(port) == true) {
                break;
            } else {
                port++;
            }
        }
        return ++port;
    }
}

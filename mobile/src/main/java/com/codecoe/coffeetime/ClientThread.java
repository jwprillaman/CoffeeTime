package com.codecoe.coffeetime;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * This class creates a thread that connects to Coffee Time
 */


public class ClientThread implements Runnable {

    private static final int SERVERPORT = 5432;
    private static final String SERVER_IP = "192.168.1.250";

    @Override
    public void run() {
        try {
            SocketAddress socketAdd = new InetSocketAddress(SERVER_IP, SERVERPORT);
            Globals.socket = new Socket();
            Globals.socket.connect(socketAdd);

            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(Globals.socket.getOutputStream())),
                        true);
                out.println("coffeeTime");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

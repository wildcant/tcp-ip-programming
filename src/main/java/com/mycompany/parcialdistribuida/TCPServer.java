/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parcialdistribuida;

/**
 *
 * @author will
 */
import java.net.*;
import java.io.*;

public class TCPServer {

    Boolean Listening = true;
    
    public TCPServer() {
        final int PORT = 9090;
        int nConnections = 1;
        try {

            ServerSocket server = new ServerSocket(PORT);

            while (Listening) {
                System.out.println("Waiting for client to connect");
                Socket newClientSocket = server.accept();
                System.out.println("New connection. Socket number" + nConnections); nConnections++;
                ConnectionHandler newConnection = new ConnectionHandler(newClientSocket);
                newConnection.start();
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("File error: " + fnfe);
        } catch (IOException ioe) {
            System.err.println("I/O Exception: " + ioe);
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }

    class ConnectionHandler extends Thread {

        Socket newClient;
        String filePath = "/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/com/mycompany/parcialdistribuida/GatewayDest/";

        public ConnectionHandler(Socket _newClient) {
            newClient = _newClient;
        }

        public void run() {
            try {
                InputStream clientIs = newClient.getInputStream();
                ObjectInputStream fileData = new ObjectInputStream(clientIs);
                String[] data = (String[]) fileData.readObject();

                String fileName = data[0];
                Long fileSize = Long.parseLong(data[1]);

                FileOutputStream fr = new FileOutputStream(filePath + fileName);
                Long packetSize = 1000l;
                Long Ntransfer = Math.floorDiv(fileSize, packetSize) + 1;
                System.out.println("Number of tranfers " + Ntransfer);

                byte[] b = new byte[Math.toIntExact(packetSize)];
                for (int i = 0; i < Ntransfer; i++) {
                    System.out.println("Packet " + i + " received from " + Thread.currentThread());
                    if (i == Ntransfer-1) {
                        b = new byte[Math.toIntExact(fileSize - (Ntransfer-1) * packetSize)];
                    }
                    clientIs.read(b, 0, b.length);
                    fr.write(b, 0, b.length);
                }
            } catch (IOException ioe) {
                System.err.println("I/O Error: " + ioe);
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Error on readObject: " + cnfe);
            }
        }
    }

    public static void main(String[] args) {
        new TCPServer();
    }
}

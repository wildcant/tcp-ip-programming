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

    public static void main(String[] args) {
        try {
            String filePath = "/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/com/mycompany/parcialdistribuida/GatewayDest/";
            File file = new File(filePath);
            System.out.println("File description");
            //System.out.println("Name: " + file.getName());
            System.out.println("Can read: " + file.canRead());

            ServerSocket server = new ServerSocket(9090);
            System.out.println("Waiting for client to connect");
            Socket clientSocket = server.accept();
            InputStream clientIs = clientSocket.getInputStream();
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
                System.out.println("Pack received");
                if (i == Ntransfer) {
                    b = new byte[Math.toIntExact(fileSize - Ntransfer * packetSize)];
                }
                clientIs.read(b, 0, b.length);
                fr.write(b, 0, b.length);
            }

        } catch (FileNotFoundException fnfe) {
            System.err.println("File error: " + fnfe);
        } catch (IOException ioe) {
            System.err.println("I/O Exception: " + ioe);
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }
}

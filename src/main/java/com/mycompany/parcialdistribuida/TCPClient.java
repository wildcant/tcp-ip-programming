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
import java.io.*;
import java.net.*;

public class TCPClient {

    public static void main(String[] args) {
        try {
            String filePath = "/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/com/mycompany/parcialdistribuida/ClientSource/random.txt";
            File file = new File(filePath);
            FileInputStream fileStream = new FileInputStream(filePath);

            Socket socket = new Socket("localhost", 9090);

            OutputStream socketOs = socket.getOutputStream();
            String fileName = file.getName();
            Long fileSize = file.length();
            String[] data = {fileName, Long.toString(fileSize)};
            System.out.println("File description");

            System.out.println("Filename: " + data[0] + "\nFiles size: " + data[1]);

            ObjectOutputStream fileInfo = new ObjectOutputStream(socketOs);
            fileInfo.writeObject(data);

            Long packetSize = 1000l;
            Long Ntransfer = Math.floorDiv(fileSize, packetSize) + 1;
            System.out.println("Number of packets to send: " + Ntransfer);

            byte[] b = new byte[Math.toIntExact(packetSize)];
            for (int i = 0; i < Ntransfer; i++) {
                System.out.println("Pack send");
                // Define byte array and copy file on it
                fileStream.read(b, 0, b.length);
                // Get socket pipe to write to client
                socketOs.write(b, 0, b.length);
            }

        } catch (Exception e) {
            System.err.println("Error" + e);
        }
    }
}

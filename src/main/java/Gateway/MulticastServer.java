/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gateway;

import java.io.*;
import java.net.*;
import java.util.*;
import java.math.*;

/**
 *
 * @author will
 */
public class MulticastServer {

    MulticastSocket multicastSocket;
    InetAddress ipAddress;
    int port;
    boolean isEnable;

    public MulticastServer() {
        try {
            this.ipAddress = InetAddress.getByName("224.0.0.2");
            this.port = 9090;
            this.isEnable = InitializeMulticastSocket();
            if (isEnable) {
                System.out.println("Multicast group started at" + ipAddress);
            }
            String fileTest = "/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/Gateway/Destination/LTspiceXVII.exe";
            new SendFile(fileTest);

        } catch (UnknownHostException uhe) {
            System.err.println("Unknown host: " + uhe);
        }
    }

    public boolean InitializeMulticastSocket() {
        try {
            this.multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(ipAddress);
            return true;
        } catch (IOException ioe) {
            System.err.println("I/O Exception " + ioe);
            return false;
        }
    }

    public class SendFile extends Thread {

        String filePath;

        public SendFile(String fileNameFromTCP) {
            filePath = fileNameFromTCP;
            this.start();
        }

        @Override
        public void run() {
            if (isEnable) {
                try {
                    File file = new File(filePath);
                    FileInputStream fileStream = new FileInputStream(filePath);

                    String fileName = file.getName();
                    Long fileSize = file.length();

                    String fileData = fileName + " " + Long.toString(fileSize);
                    System.out.println("File description");
                    System.out.println("Filename: " + fileName + "\nFiles size: " + fileSize);

                    DatagramPacket packetFileSize = new DatagramPacket(fileData.getBytes(), fileData.length(), ipAddress, port);
                    multicastSocket.send(packetFileSize);

                    Long packetSize = 1300l;
                    Long Ntransfer = Math.floorDiv(fileSize, packetSize) + 1;
                    System.out.println("Number of packets to send: " + Ntransfer);

                    byte[] b = new byte[Math.toIntExact(packetSize)];
                    for (int i = 0; i < Ntransfer; i++) {
                        if (i == Ntransfer - 1) {
                            b = new byte[Math.toIntExact(fileSize - (Ntransfer - 1) * packetSize)];
                        }
                        
                        fileStream.read(b, 0, b.length);
                        DatagramPacket packet = new DatagramPacket(b, b.length, ipAddress, port);
                        multicastSocket.send(packet);
                        Thread.sleep(10);
                        System.out.println(i + " packet send from multicast");
                    }
                    fileStream.close();

                } catch (Exception   e) {
                    System.err.println("Error " + e);
                }
            }
        }
    }
    public static void main(String[] args) {
        MulticastServer multicastServer = new MulticastServer();
    }
}

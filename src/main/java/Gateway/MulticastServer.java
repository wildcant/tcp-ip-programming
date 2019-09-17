/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gateway;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author will
 */
public class MulticastServer extends Thread {

    MulticastSocket multicastSocket;
    InetAddress ipAddress;
    int port;
    boolean isEnable = true;

    public MulticastServer() {
        try {
            System.out.println("Multicast Server instance created");
            this.ipAddress = InetAddress.getByName("224.0.0.2");
            this.port = 9090;
            this.start();
        } catch (Exception e) {
        }
    }

    public boolean InitializeMulticastSocket() {
        try {
            this.multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(ipAddress);

            return true;
        } catch (Exception e) {
            System.err.println("Error: " + e);
            return false;
        }
    }

    public void SendFile() {
        try {
            String filePath = "/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/Gateway/Destination/SomeText.txt";
            File file = new File(filePath);
            FileInputStream fileStream = new FileInputStream(filePath);

            String fileName = file.getName();
            String fileNameToSend = "N " + fileName;
            
            Long fileSize = file.length();
            String flSize = "S " + Long.toString(fileSize);
            
            System.out.println("File description");

            System.out.println("Filename: " + fileName + "\nFiles size: " + fileSize);
            
            DatagramPacket packetFileSize = new DatagramPacket(flSize.getBytes(), flSize.length(), ipAddress, port);
            DatagramPacket packetFileName = new DatagramPacket(fileNameToSend.getBytes(), fileNameToSend.length(), ipAddress, port);
            
            multicastSocket.send(packetFileSize);
            multicastSocket.send(packetFileName);
            

            Long packetSize = 5000l;
            Long Ntransfer = Math.floorDiv(fileSize, packetSize) + 1;
            System.out.println("Number of packets to send: " + Ntransfer);

            byte[] b = new byte[Math.toIntExact(packetSize)];
            
            for (int i = 0; i < Ntransfer; i++) {
                fileStream.read(b, 0, b.length);
                DatagramPacket packet = new DatagramPacket(b, b.length, ipAddress, port);
                multicastSocket.send(packet);
            }
            fileStream.close();
             
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }

    @Override
    public void run() {
        if (InitializeMulticastSocket()) {
            System.out.println("Able to initialize multicast group!");
            try {
                System.out.println("Sending File");
                SendFile();
            } catch (Exception e) {
                System.err.println("Error: " + e);
            }
        }
    }

    public static void main(String[] args) {
        MulticastServer multicastServer = new MulticastServer();
    }
}

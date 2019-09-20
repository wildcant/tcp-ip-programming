/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Storage;

import java.io.*;
import java.net.*;
import java.math.*;

/**
 *
 * @author will
 */
public class MulticastReceptor {

    public static void main(String[] args) {
        boolean Listening = true;
        int port = 9090;
        try {
            MulticastSocket multicastSocket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName("224.0.0.2");
            multicastSocket.joinGroup(address);

            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, 1024);
            System.out.println("Waiting for file data");

            multicastSocket.receive(packet);
            String res = new String(packet.getData(), 0, packet.getLength());
            String[] fileData = res.split("\\s");
            String fileName = fileData[0];
            String fileSize = fileData[1];
            System.out.println("File size; " + fileSize + "\nFile name: " + fileName);

            String filePath = "/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/Storage/Destination/";
            System.out.println(filePath + fileName);
            FileOutputStream fr = new FileOutputStream(filePath + fileName);

            Long packetSize = 1300L;
            Long Ntransfer = Math.floorDiv(Long.parseLong(fileSize), packetSize) + 1;
            System.out.println("Number of packets to receive: " + Ntransfer);

            byte[] b = new byte[Math.toIntExact(packetSize)];
            for (int i = 0; i < Ntransfer; i++) {
                multicastSocket.receive(packet);
                fr.write(packet.getData(), 0, packet.getLength());
                System.out.println(i + " packt Recieve");
            }
            System.out.println(fileName + " finished! from multicast server");
            fr.close();

            multicastSocket.close();
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }
}

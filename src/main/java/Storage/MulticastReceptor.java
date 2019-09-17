/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Storage;

import java.io.*;
import java.net.*;

/**
 *
 * @author will
 */
public class MulticastReceptor {

    public static void main(String[] args) {
        int port = 9090;
        try {
            String fileSize = null;
            String fileName = null;
            MulticastSocket multicastSocket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName("224.0.0.2");
            multicastSocket.joinGroup(address);

            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, 1024);
            System.out.println("Waiting for file data");

            multicastSocket.receive(packet);
            String res = new String(packet.getData(), 0, packet.getLength());
            String[] words = res.split("\\s");
            char st = res.charAt(0);
            if (st == 'S') {
                fileSize = words[1].toString();
            }
            multicastSocket.receive(packet);
            res = new String(packet.getData(), 0, packet.getLength());

            words = res.split("\\s");
            st = res.charAt(0);
            if (st == 'N') {
                fileName = words[1].toString();
            }
            System.out.println("File size; " + fileSize + "\nFile name: " + fileName);

            String filePath = "/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/Storage/Destination/";
            FileOutputStream fr = new FileOutputStream(filePath + fileName);

            Long packetSize = 5000l;
            Long Ntransfer = Math.floorDiv(Long.parseLong(fileSize), Long.parseLong(fileSize)) + 1;
            System.out.println("Number of packets to send: " + Ntransfer);
            
            byte[] b = new byte[Math.toIntExact(packetSize)];
            for (int i = 0; i < Ntransfer; i++) {
                multicastSocket.receive(packet);
                fr.write(packet.getData(), 0, packet.getLength());
            }
            
            fr.close();
            multicastSocket.close();
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }
}

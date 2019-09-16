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

    public static void SendFile() {
        try {
            String filePath = "/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/Gateway/Destination/SomeText.txt";
            File file = new File(filePath);
            FileInputStream fileStream = new FileInputStream(filePath);

            String fileName = file.getName();
            Long fileSize = file.length();
            String[] data = {fileName, Long.toString(fileSize)};
            System.out.println("File description");

            System.out.println("Filename: " + data[0] + "\nFiles size: " + data[1]);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            new ObjectOutputStream(out).writeObject(data);
            
            String yourString = new String(Hex.encodeHex(out.toByteArray()));
            System.out.println(yourString);
            // deserialize
            ByteArrayInputStream in = new ByteArrayInputStream(Hex.decodeHex(yourString.toCharArray()));
            System.out.println(Arrays.toString((String[]) new ObjectInputStream(in).readObject()));
/*
            Long packetSize = 5000l;
            Long Ntransfer = Math.floorDiv(fileSize, packetSize) + 1;
            System.out.println("Number of packets to send: " + Ntransfer);

            byte[] b = new byte[Math.toIntExact(packetSize)];
            for (int i = 0; i < Ntransfer; i++) {
                fileStream.read(b, 0, b.length);
                InetAddress group = InetAddress.getByName("10.20.58.143");
                DatagramPacket packet = new DatagramPacket(str.getBytes(), str.length(), group, port);
                multicastSocket.send(packet);
            }
            fileStream.close();

            System.out.println(out);
             */
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Storage;

import java.net.*;

/**
 *
 * @author will
 */
public class MulticastReceptor {

    public static void main(String[] args) {
        int port = 9090;
        try {
            MulticastSocket multicastSocket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName("224.0.0.2");
            multicastSocket.joinGroup(address);

            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, 1024);
            
            multicastSocket.receive(packet);
            String str = new String(packet.getData(), 0, packet.getLength());
            System.out.println(str);
            multicastSocket.close();
        } catch (Exception e) {
            System.err.println("Error " + e);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientdemo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author will
 */
public class WebClient {
      
  public String listFiles() {
    try {
      String endpoint = "http://localhost:8080/GatewayDemo/resources/gateway";
      String methodType = "GET";
      URL url = new URL(endpoint);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod(methodType);
      urlConnection.setDoInput(true);
      int httpResponseCode = urlConnection.getResponseCode();
      InputStream urlConIs = urlConnection.getInputStream();
      InputStreamReader isrUrl = new InputStreamReader(urlConIs);
      BufferedReader bisUrl = new BufferedReader(isrUrl);
      String list = null;
      if (httpResponseCode == 200) {
        list = bisUrl.readLine();
      } else {
        return "Response code: " + httpResponseCode;
      }
      return list;
    } catch (Exception ex) {
      System.out.println("error");
      System.err.println(ex);
      return "error";
    }
  }

  public String downloadFile(String fileName, Long fileSize) throws FileNotFoundException {
    try {
      String endpoint = "http://localhost:8080/WebServerDemo/resources/server/files/" + fileName;
      Client client = ClientBuilder.newClient();
      WebTarget resource = client.target(endpoint);
      Response response = resource.request().get();

      OutputStream os = new FileOutputStream("/home/will/Escritorio/Distribuida/ParcialDistribuida/Web/ClientDemo/src/main/java/com/mycompany/clientdemo/files/" + fileName);
      if (response.getStatus() == 200) {
        InputStream io = (InputStream) response.readEntity(InputStream.class);

        int packetSize = 1400;
        int currentPacket = 0;
        Long NumberOfPackets =  Math.floorDiv(fileSize, packetSize) + 1;
        int count;
        
        System.out.println("Packets size: " + packetSize);
        System.out.println("File size: " + fileSize);
        System.out.println("Number of packets: " + NumberOfPackets);
        Long currentAmount = 0l;
        byte[] buff = new byte[packetSize];
        while ((count = io.read(buff, 0, buff.length)) != -1) {
          currentPacket++;
          currentAmount = currentAmount + count ;
          float filePorcentage = (float) currentAmount/fileSize * 100;
          // System.out.println(currentPacket + ". Packet received of size " + count + ". Current amount " + currentAmount);
          System.out.println(filePorcentage);
          os.write(buff, 0, count);
        }
        os.close();
        io.close();
      } else {
        System.out.println("Response code :" + response.getStatus());
      }
      return fileName + " finished!";
    } catch (FileNotFoundException fnfe) {
      return "error FileNotFoundException " + fnfe;
    } catch (Exception e) {
      return "error " + e;
    }
  }
}

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

  public String downloadFile(String fileName) throws FileNotFoundException {
    try {
      String endpoint = "http://localhost:8080/WebServerDemo/resources/server/files/" + fileName;
      Client client = ClientBuilder.newClient();
      WebTarget resource = client.target(endpoint);
      Response response = resource.request().get();

      OutputStream os = new FileOutputStream("/home/will/Escritorio/Distribuida/ParcialDistribuida/Web/ClientDemo/src/main/java/com/mycompany/clientdemo/files/" + fileName);
      System.out.println(new Date() + " : Reached point A");

      if (response.getStatus() == 200) {
        System.out.println(new Date() + " : Reached point B");
        InputStream io = (InputStream) response.readEntity(InputStream.class);

        byte[] buff = new byte[1400];
        int count;
        while ((count = io.read(buff, 0, buff.length)) != -1) {
          System.out.println("buffer to send" + count);
          System.out.println(count);
          os.write(buff, 0, count);
        }
        os.close();
        io.close();
      } else {
        System.out.println("Response code :" + response.getStatus());
      }
      return "here";
    } catch(FileNotFoundException fnfe){
      return "error FileNotFoundException " + fnfe;
    } 
    catch (Exception e) {
      return "error " + e;
    }
  }
}

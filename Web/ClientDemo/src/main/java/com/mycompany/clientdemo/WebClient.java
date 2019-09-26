/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientdemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author will
 */
public class WebClient {

  public boolean testConnection(String ip) {
    String endpoint = "http://" + ip + ":8080/GatewayDemo";
    String methodType = "GET";
    System.out.println(endpoint);
    try {
      URL url = new URL(endpoint);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod(methodType);
      int httpCode = urlConnection.getResponseCode();
      
      System.out.println(httpCode);
      if (httpCode == 200) {
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      System.err.println("testConnection error " + e);
      return false;
    }
  }

  public String listFiles(String endpoint) {
    try {
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
}

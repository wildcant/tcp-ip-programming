/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gatewaydemo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mycompany.gatewaydemo.GatewayAdmin;

/**
 *
 * @author will
 */
@RequestScoped
@Path("gateway")
public class Middlewares {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String test() {
    System.out.println(GatewayAdmin.getServerHostnames().size());
    if (GatewayAdmin.getServerHostnames().size() > 0) {
      try {
        String endpoint = GatewayAdmin.getServerHostnames().get(0) + "resources/server/";
        System.out.println(endpoint);
        String methodType = "GET";
        URL url = new URL(endpoint);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(methodType);
        urlConnection.setDoInput(true);
        int httpResponseCode = urlConnection.getResponseCode();
        InputStream urlConIs = urlConnection.getInputStream();
        InputStreamReader isrUrl = new InputStreamReader(urlConIs);
        BufferedReader bisUrl = new BufferedReader(isrUrl);
        String list;
        if (httpResponseCode == 200) {
          list = bisUrl.readLine();
          System.out.println(list);
        } else {
          return "Response code: " + httpResponseCode;
        }
        return list;
      } catch (Exception ex) {
        System.out.println("error");
        System.err.println(ex);
        return "Error";
      }
    } else {
      return "No server connected";
    }

  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("files/{filename}")
  public Response sendFile(@PathParam("filename") String fileName) {
    String endpoint =  GatewayAdmin.getServerHostnames().get(0)  + "/resources/server/files/" + fileName;
    Client client = ClientBuilder.newClient();
    WebTarget resource = client.target(endpoint);
    Response response = resource.request().get();
    GatewayAdmin.incI();
    System.out.println(GatewayAdmin.getI());
    return response;
  }

  @POST
  @Path("newserver")
  public int AddNewServer(@FormParam("serverURI") String serverURI) {
    for (String serverHostname : GatewayAdmin.getServerHostnames()) {
      System.out.println("Compare " + serverHostname + " and " + serverURI);
      if (serverHostname.equals(serverURI)) {
        System.out.println("Are the same");
        return GatewayAdmin.getServerHostnames().indexOf(serverURI) + 1;
      }
    }
    System.out.println("New server at " + serverURI);
    GatewayAdmin.addServer(serverURI);
    return GatewayAdmin.serverHostnames.size();
  }
}

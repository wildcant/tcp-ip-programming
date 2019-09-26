/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webserverdemo;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.mycompany.webserverdemo.Utilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author will
 */
@RequestScoped
@Path("server")
public class ServerResource {

  private final String gateWayIp = "127.0.0.1";
  private Utilities ut = new Utilities();

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getHtml() {
    System.out.println("Restful server");
    List<String> listFiles = ut.getList();
    List<Long> filesSize = ut.getFilesSize();
    String list = "";
    for (String fileName : listFiles) {
      list = list + fileName + ",";
    }
    list = list + ";";
    for (Long fileSize : filesSize) {
      list = list + Long.toString(fileSize) + ",";
    }
    return (list);
  }

  @GET
  @Path("files/{filename}")
  @Produces("text/plain")
  public Response sendFile(@PathParam("filename") String filename) {
    System.out.println("sendFile on server");
    try {
      System.out.println(ut.getFilePath());
      ResponseBuilder response = Response.ok(new FeedReturnStreamingOutput(ut.getFilePath() + filename));
      return response.build();

    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  public static class FeedReturnStreamingOutput implements StreamingOutput {

    String filePath;

    private FeedReturnStreamingOutput(String filename) {
      System.out.println(filename);
      filePath = filename;
    }

    @Override
    public void write(OutputStream output) throws IOException, WebApplicationException {
      try {
        File file = new File(filePath);
        Long packetSize = 1400l;
        Long fileSize = file.length();
        Long Ntransfer = Math.floorDiv(fileSize, packetSize) + 1;

        FileInputStream fileStream = new FileInputStream(filePath);

        byte[] b = new byte[Math.toIntExact(packetSize)];
        for (int i = 0; i < Ntransfer; i++) {
          if (i == Ntransfer - 1) {
            b = new byte[Math.toIntExact(fileSize - (Ntransfer - 1) * packetSize)];
          }
          int packetRec = fileStream.read(b, 0, b.length);

          System.out.println("buffer recieved:" + packetRec);
          output.write(b, 0, b.length);
          output.flush();
          TimeUnit.MILLISECONDS.sleep(1);

        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

  }

  @GET
  @Path("test")
  @Produces("text/plain")
  public String Testing() {
    System.out.println("Testing");
    String currentServerURI = "http://" + ut.getIp() + ":8080/WebServerDemo/";

    try {
      URL urlGateway = new URL("http://" + gateWayIp + ":8080/GatewayDemo/resources/gateway/newserver");
      HttpURLConnection urlConnection = (HttpURLConnection) urlGateway.openConnection();
      urlConnection.setRequestMethod("POST");
      urlConnection.setDoInput(true);
      urlConnection.setDoOutput(true);

      StringBuffer queryParam = new StringBuffer();
      queryParam.append("serverURI=");
      queryParam.append(currentServerURI);

      OutputStream output = urlConnection.getOutputStream();
      output.write(queryParam.toString().getBytes());
      output.flush();

      int httpResponseCode = urlConnection.getResponseCode();
      System.out.println(urlGateway + "  " + httpResponseCode);
      if (httpResponseCode == 200) {
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        return "Conexion al gateway exitosa, eres el servidor numero " + in.readLine();
      } else {
        return "No fue posible conectar al gateway \nResponse code: " + httpResponseCode;
      }
    } catch (Exception ex) {
      System.err.println("Testing() error" + ex);
      return "Error: " + ex;
    }
  }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webserverdemo;

/**
 *
 * @author will
 */
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import sun.security.x509.IPAddressName;

public class Utilities {

  public List<String> getList() {
    try ( Stream<Path> walk = Files.walk(Paths.get("/home/will/Escritorio/Distribuida/ParcialDistribuida/Web/WebServerDemo/src/main/java/com/mycompany/webserverdemo/files/"))) {
      List<String> result = walk.filter(Files::isRegularFile).map(x -> x.getFileName().toString()).collect(Collectors.toList());
      return (result);
    } catch (Exception e) {
      e.printStackTrace();
      return (new ArrayList<String>());
    }
  }

  public List<Long> getFilesSize() {
    try ( Stream<Path> walk = Files.walk(Paths.get("/home/will/Escritorio/Distribuida/ParcialDistribuida/Web/WebServerDemo/src/main/java/com/mycompany/webserverdemo/files/"))) {
      List<Long> filesSize = walk.filter(Files::isRegularFile).map(x -> x.toFile().length()).collect(Collectors.toList());
      System.out.println(filesSize);
      return (filesSize);
    } catch (Exception e) {
      e.printStackTrace();
      return (new ArrayList<Long>());
    }
  }

  public String getIp() {
    try {
      InetAddress ip = InetAddress.getLocalHost();
      return ip.getHostAddress();
    } catch (Exception e) {
      System.out.println("Get ip error" + e);
      return "not able to get ip";
    }
  }

  /*
  public String getDir() {
    Properties p = System.getProperties();
    System.out.println(p.getProperty("java.class.path"));
    Enumeration keys = p.keys();
    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      String value = (String) p.get(key);
      System.out.println(key + ": " + value);
    }
    return p.getProperty("user.dir");
  }
   */
}

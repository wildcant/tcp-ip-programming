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
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class Utilities {

  private final URL filePath = Utilities.class.getResource("/files/");

  public String getFilePath() {
    try {
      return Paths.get(filePath.toURI()).toString() + "/";
    } catch (Exception e) {
      System.err.println(e);
      return "error";
    }
  }

  public List<String> getList() {
    try ( Stream<Path> walk = Files.walk(Paths.get(filePath.toURI()))) {
      List<String> result = walk.filter(Files::isRegularFile).map(x -> x.getFileName().toString()).collect(Collectors.toList());
      return (result);
    } catch (Exception e) {
      e.printStackTrace();
      return (new ArrayList<String>());
    }
  }

  public List<Long> getFilesSize() {
    try ( Stream<Path> walk = Files.walk(Paths.get(filePath.toURI()))) {
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
}

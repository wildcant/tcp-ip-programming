/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gatewaydemo;

import java.util.ArrayList;

/**
 *
 * @author will
 */
public class GatewayAdmin {

  public static ArrayList<String> serverHostnames = new ArrayList<String>() ;
  public static  int i;

  //Getters
  public static ArrayList<String> getServerHostnames() {
    return serverHostnames;
  }

  public static int getI() {
    return i;
  }

  // "Setter"
  public static void addServer(String server) {
    serverHostnames.add(server);
  }

  public static void incI() {
    i = i + 1;
  }

}

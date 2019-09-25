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

  public ArrayList<String> serverHostnames;
  public int i;

  public GatewayAdmin() {
    this.serverHostnames = new ArrayList<>();
    this.i = 0;
    System.out.println("GatewayAdmin iniciado");
  }

  //Getters
  public ArrayList<String> getServerHostnames() {
    return this.serverHostnames;
  }

  public int getI() {
    return this.i;
  }

  // "Setter"
  public void addServer(String server) {
    this.serverHostnames.add(server);
  }

  public void incI() {
    this.i = this.i + 1;
  }

}

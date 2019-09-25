/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.nio.file.*;
import java.util.List;
import java.util.stream.*;

/**
 *
 * @author will
 */
public class Test {

  public static void main(String[] args) {
    try ( Stream<Path> walk = Files.walk(Paths.get("/home/will/Escritorio/Distribuida/ParcialDistribuida/src/main/java/Client/Source/"))) {
      List<String> result = walk.filter(Files::isRegularFile).map(x -> x.getFileName().toString()).collect(Collectors.toList());
      result.forEach(n -> System.out.println(n));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

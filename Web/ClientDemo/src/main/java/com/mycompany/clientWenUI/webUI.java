package com.mycompany.clientWenUI;

import com.mycompany.clientdemo.WebClient;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.SwingWorker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.omg.CORBA.Any;

/**
 *
 * @author will
 */
public class webUI extends javax.swing.JFrame {

  WebClient client = new WebClient();
  String[] lista;
  String[] sizes;

  public webUI() {
    initComponents();
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jButton1 = new javax.swing.JButton();
    refreshBtn = new javax.swing.JButton();
    title = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    fileList = new javax.swing.JList<>();
    jButton2 = new javax.swing.JButton();
    pbar = new javax.swing.JProgressBar();
    title1 = new javax.swing.JLabel();
    etiqueta = new javax.swing.JLabel();

    jButton1.setText("jButton1");

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Web Client UI");

    refreshBtn.setText("Actualizar lista");
    refreshBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        refreshBtnActionPerformed(evt);
      }
    });

    title.setText("Lista de archivos");

    fileList.setModel(new javax.swing.AbstractListModel<String>() {
      String[] lista = getList();
      public int getSize() { return lista.length; }
      public String getElementAt(int i) { return lista[i]; }
    });
    jScrollPane1.setViewportView(fileList);

    jButton2.setText("Descargar");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });

    title1.setText("Archivo");

    etiqueta.setText("\"");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(42, 42, 42)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(title)
          .addComponent(refreshBtn))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(pbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jButton2)
          .addComponent(title1)
          .addComponent(etiqueta))
        .addContainerGap(111, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(30, 30, 30)
            .addComponent(title)
            .addGap(23, 23, 23)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addGap(64, 64, 64)
            .addComponent(title1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(pbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(etiqueta)
            .addGap(11, 11, 11)
            .addComponent(jButton2)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(refreshBtn)
        .addContainerGap(100, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
    getList();
  }//GEN-LAST:event_refreshBtnActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    System.out.println("Inicio thread");
    SwingWorker worker = new Worker1();
    worker.execute();
    /*
    try {
      int i = fileList.getSelectedIndex();
      System.out.println(lista[i]);
      String response = client.downloadFile(lista[i], Long.parseLong(sizes[i]));
      System.out.println(response);
    } catch (Exception e) {
      System.out.println("error here");
    }
     */
  }//GEN-LAST:event_jButton2ActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(webUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(webUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(webUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(webUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new webUI().setVisible(true);
      }
    });
  }

  public String[] getList() {
    System.out.println("Getting list");
    String lstResponse = client.listFiles();
    String[] listas = lstResponse.split(";");
    lista = listas[0].split(",");
    sizes = listas[1].split(",");
    fileList.setModel(new javax.swing.AbstractListModel<String>() {
      public int getSize() {
        return lista.length;
      }

      public String getElementAt(int i) {
        return lista[i];
      }
    });
    return lista;

  }

  class Worker1 extends SwingWorker<Double, Integer> {

    @Override
    protected Double doInBackground() throws Exception {
      // Mostramos el nombre del hilo, para ver que efectivamente
      // esto NO se ejecuta en el hilo de eventos.
      System.out.println("Iniciando hilo de descarga");
      int i = fileList.getSelectedIndex();
      String fileName = lista[i];
      Long fileSize = Long.parseLong(sizes[i]);
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
          Long NumberOfPackets = Math.floorDiv(fileSize, packetSize) + 1;
          int count;
          System.out.println("File: " + fileName);
          System.out.println("File size: " + fileSize);
          System.out.println("Packets size: " + packetSize);
          System.out.println("Number of packets: " + NumberOfPackets);
          Long currentAmount = 0l;
          float filePorcentage = 0;
          byte[] buff = new byte[packetSize];
          while ((count = io.read(buff, 0, buff.length)) != -1) {
            currentPacket++;
            currentAmount = currentAmount + count;
            filePorcentage = (float) currentAmount / fileSize * 100;
            publish(Math.round(filePorcentage));
            // System.out.println(currentPacket + ". Packet received of size " + count + ". Current amount " + currentAmount);
            os.write(buff, 0, count);
          }
          System.out.println(filePorcentage);
          response.close();
          os.close();
        } else {
          System.out.println("Response code :" + response.getStatus());
        }
      } catch (FileNotFoundException fnfe) {
        System.err.println(fnfe);
      } catch (IOException e) {
        System.out.println("HEre");
        System.err.println(e);
      }
      return 100.0;
    }

    @Override
    protected void done() {
      // Mostramos el nombre del hilo para ver que efectivamente esto
      // se ejecuta en el hilo de eventos.
      System.out.println("Termino descarga");
      pbar.setValue(100);

      pbar.setValue(0);

      etiqueta.setText("hecho");
    }

    @Override
    protected void process(List<Integer> chunks) {
      //System.out.println("process() esta en el hilo "+ Thread.currentThread().getName());
      pbar.setValue(chunks.get(0));
    }
  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel etiqueta;
  private javax.swing.JList<String> fileList;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JProgressBar pbar;
  private javax.swing.JButton refreshBtn;
  private javax.swing.JLabel title;
  private javax.swing.JLabel title1;
  // End of variables declaration//GEN-END:variables
}

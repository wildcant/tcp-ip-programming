package com.mycompany.clientWenUI;

import com.mycompany.clientdemo.WebClient;

/**
 *
 * @author will
 */
public class webUI extends javax.swing.JFrame {
  
  WebClient client = new WebClient();
  String[] lista;
  
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
        .addGap(45, 45, 45)
        .addComponent(jButton2)
        .addContainerGap(121, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(30, 30, 30)
        .addComponent(title)
        .addGap(23, 23, 23)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jButton2)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    try {
      int i = fileList.getSelectedIndex();
      System.out.println(lista[i]);
      String response = client.downloadFile(lista[i]);
      System.out.println(response);
    } catch (Exception e) {
      System.out.println("error here");
    }
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
    lista = lstResponse.split(",");
    fileList.setModel(new javax.swing.AbstractListModel<String>() {
      public int getSize() {return lista.length;}
      public String getElementAt(int i) {return lista[i];}
    });
    return lista;
    
  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JList<String> fileList;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JButton refreshBtn;
  private javax.swing.JLabel title;
  // End of variables declaration//GEN-END:variables
}

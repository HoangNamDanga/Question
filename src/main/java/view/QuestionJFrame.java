/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import controller.MongoDB;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.ListModel;
import model.Question;
import org.bson.Document;

/**
 *
 * @author ADMIN
 */
public class QuestionJFrame extends javax.swing.JFrame {

    public String subjectCode = "EPC";
    List<Question> list = new ArrayList<Question>();
    List<Question> listTotal = new ArrayList<Question>();//TTổng số câu
    int Limit = 20;//Max 20 câu hỏi
    Question q;
    private static int pos = 0;
    public QuestionJFrame() {
        initComponents();
        initStaticData();
        MongoDB mongoDB = new MongoDB();
        MongoCursor<Document> cursor = null;
        DefaultListModel model = new DefaultListModel();
        cursor = selectByCode(cursor,mongoDB);
        getData(cursor);
        View();
        ViewList();
    }
    
        public void initStaticData(){
            this.setTitle("Question");
            this.setSize(1600,900);
            this.setLocationRelativeTo(null);
            this.jPanel3.setBackground(Color.lightGray);
            this.jPanel2.setBackground(Color.lightGray);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.jPanel1.setBackground(Color.lightGray);
        }
    
        public MongoCursor<Document> selectByCode(MongoCursor<Document> cursor,MongoDB mongoDB){
            if(this.subjectCode == "EPC"){
                return cursor = mongoDB.epcCollection.find().iterator(); 
            }else if (this.subjectCode == "JAVA"){
                return cursor = mongoDB.javaCollection.find().iterator(); 
            }
            return null;
        }
    
        public void getData(MongoCursor<Document> cursor){
            while (cursor.hasNext()) {
                var obj = cursor.next();
                int qid = (int) obj.get("qid");
                String title = (String) obj.get("title");
                List<String> options = (List<String>) obj.get("options");
                String answer = (String) obj.get("answer");
                Question data = new Question(qid, title, options, answer);
                listTotal.add(data);
            }
            Collections.shuffle(listTotal);
            for (int i = 0; i < Limit; i++) {
                var item = listTotal.get(i);
                list.add(item);
            }
        }
    
        public void View(){
        q = list.get(pos);
        this.jLabelTitle.setText("Câu số " + (pos + 1) + " :  " + q.getTitle());
        this.jRadioButtonA.setText("1 . " + q.getAnswerA());
        this.jRadioButtonB.setText("2 . " + q.getAnswerB());
        this.jRadioButtonC.setText("3 . " + q.getAnswerC());
        this.jRadioButtonD.setText("4 . " + q.getAnswerD());
        int status = q.getStatus();
        switch (status) {// kiểm tra trạng thái của câu trả lời
            case 1:
                OnOff(status); 
                break;
            case 2:
                OnOff(status);
                break;
            case 3:
                OnOff(status);
                break;
            case 4:
                OnOff(status);
                break;
            default:
                this.buttonGroup1.clearSelection(); // khi ko chọn cái nào
        }
        Setcolor();
        
    }
    public void OnOff(int status){ 
        this.q.setStatus(status);
        list.set(pos, q);// Gán hết quả vừa đc chọn
        switch(status){
            case 1:
                this.jRadioButtonA.setSelected(true);
                break;
            case 2:
                this.jRadioButtonB.setSelected(true);
                break;
            case 3:
                this.jRadioButtonC.setSelected(true);
                break;
            case 4:
                this.jRadioButtonD.setSelected(true);
                break;
            
        }
    }
    public int Choice(){ // hàm xác định chọn đáp án a hay b hay c hay d
        int n = 0;
        return n = q.getStatus();
    }
    
    public void OnAnswer(int n){ // n là đáp án đã chọn
        switch (n) {
            case 1:
                this.jRadioButtonA.setBackground(Color.red);
                break;
            case 2:
                this.jRadioButtonB.setBackground(Color.red);
                break;
            case 3:
                this.jRadioButtonC.setBackground(Color.red);
                break;
            case 4:
                this.jRadioButtonD.setBackground(Color.red);
                break;
        }
    }
    public void ViewList(){
        DefaultListModel model = new DefaultListModel();
        this.jList1.setModel(model); // quản lý bởi model
        int n = 1 ;
        for (Question x : list) {
            model.addElement("Câu " + n++);
        }
    }
    public void Setcolor(){
        this.jRadioButtonA.setBackground(Color.lightGray);
        this.jRadioButtonB.setBackground(Color.lightGray);
        this.jRadioButtonC.setBackground(Color.lightGray);
        this.jRadioButtonD.setBackground(Color.lightGray);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jRadioButtonA = new javax.swing.JRadioButton();
        jRadioButtonB = new javax.swing.JRadioButton();
        jRadioButtonC = new javax.swing.JRadioButton();
        jRadioButtonD = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setText("Nộp bài");

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton2.setText("Thoát");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jButton1)
                .addGap(62, 62, 62)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setText("Câu hỏi trắc nghiệm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(405, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jList1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jList1ComponentHidden(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelTitle.setText("Câu hỏi ");
        jPanel3.add(jLabelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 26, 754, -1));

        buttonGroup1.add(jRadioButtonA);
        jRadioButtonA.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jRadioButtonA.setText("A");
        jRadioButtonA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonA, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 760, -1));

        buttonGroup1.add(jRadioButtonB);
        jRadioButtonB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jRadioButtonB.setText("B");
        jRadioButtonB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonBActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonB, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 760, -1));

        buttonGroup1.add(jRadioButtonC);
        jRadioButtonC.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jRadioButtonC.setText("C");
        jRadioButtonC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonC, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 766, -1));

        buttonGroup1.add(jRadioButtonD);
        jRadioButtonD.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jRadioButtonD.setText("D");
        jRadioButtonD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonD, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 760, -1));

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setText("Nộp bài");

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.setText("Thoát");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jButton3)
                .addGap(210, 210, 210)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAActionPerformed
        // TODO add your handling code here:
        OnOff(1);
    }//GEN-LAST:event_jRadioButtonAActionPerformed

    private void jRadioButtonBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonBActionPerformed
        // TODO add your handling code here:
        OnOff(2);
    }//GEN-LAST:event_jRadioButtonBActionPerformed

    private void jList1ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jList1ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_jList1ComponentHidden

    private void jRadioButtonCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCActionPerformed
        // TODO add your handling code here:
        OnOff(3);
    }//GEN-LAST:event_jRadioButtonCActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        list.set(pos, q);// Gán hết quả vừa đc chọn
        pos = this.jList1.getSelectedIndex();
        View();
    }//GEN-LAST:event_jList1ValueChanged

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jList1MouseClicked

    private void jRadioButtonDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonDActionPerformed
        // TODO add your handling code here:
        OnOff(4);
    }//GEN-LAST:event_jRadioButtonDActionPerformed

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
            java.util.logging.Logger.getLogger(QuestionJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuestionJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuestionJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuestionJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuestionJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButtonA;
    private javax.swing.JRadioButton jRadioButtonB;
    private javax.swing.JRadioButton jRadioButtonC;
    private javax.swing.JRadioButton jRadioButtonD;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.Timer;
import model.Question;
import org.bson.Document;

/**
 *
 * @author ADMIN
 */
public class QuestionJFrame extends javax.swing.JFrame {

    public String subjectCode;
    List<Question> list = new ArrayList<Question>();
    List<Question> listTotal = new ArrayList<Question>();//TTổng số câu
    int Limit = 20;//Max 20 câu hỏi
    Question q;
    private static int pos = 0;
    int timeLeft = 300;
    Date dateTimeLeft = new Date(0,0,0,0,5,0); 
    private Timer timer;
    
    public QuestionJFrame(String subject) {
        initComponents();
        initStaticData();
        
        this.subjectCode = subject;
        MongoDB mongoDB = new MongoDB();
        MongoCursor<Document> cursor = null;
        DefaultListModel model = new DefaultListModel();
        cursor = selectByCode(cursor,mongoDB);
        getData(cursor);
        Timer();
        View();
        ViewList();
        this.jList1.setSelectedIndex(pos);
    }

    private QuestionJFrame() {
    }
    
        public void initStaticData(){
            this.setTitle("Question");
            this.setSize(1600,900);
            this.setLocationRelativeTo(null);
            this.jPanel3.setBackground(Color.lightGray);
            this.jPanel2.setBackground(Color.lightGray);
            
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            
            // xử lý phím tắt Prex
            this.jList1.addKeyListener(new CustomKeyLinstener());
            this.jPanel3.addKeyListener(new CustomKeyLinstener());
            this.jButton5.addKeyListener(new CustomKeyLinstener());
            this.jButton6.addKeyListener(new CustomKeyLinstener());
            this.jButton3.addKeyListener(new CustomKeyLinstener());
            this.jButton4.addKeyListener(new CustomKeyLinstener());
            this.jRadioButtonA.addKeyListener(new CustomKeyLinstener());
            this.jRadioButtonB.addKeyListener(new CustomKeyLinstener());
            this.jRadioButtonC.addKeyListener(new CustomKeyLinstener());
            this.jRadioButtonD.addKeyListener(new CustomKeyLinstener());
            InputMap imA = jRadioButtonA.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            imA.put(KeyStroke.getKeyStroke("RIGHT"), "none");
            imA.put(KeyStroke.getKeyStroke("LEFT"), "none");
            InputMap imB = jRadioButtonB.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            imB.put(KeyStroke.getKeyStroke("RIGHT"), "none");
            imB.put(KeyStroke.getKeyStroke("LEFT"), "none");
            InputMap imC = jRadioButtonC.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            imC.put(KeyStroke.getKeyStroke("RIGHT"), "none");
            imC.put(KeyStroke.getKeyStroke("LEFT"), "none");
            InputMap imD = jRadioButtonD.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            imD.put(KeyStroke.getKeyStroke("RIGHT"), "none");
            imD.put(KeyStroke.getKeyStroke("LEFT"), "none");
        }
        
        class CustomKeyLinstener implements KeyListener {
        
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    accept();
                    break;
                case KeyEvent.VK_RIGHT:
                    nextPage(); 
                    break;
                case KeyEvent.VK_LEFT:
                    backPage();
                    break;
                case KeyEvent.VK_F4:
                    exit();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
            
        }
        
        public void Timer(){
            timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { //anonymous inner class
            timeLeft--;
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String textTime = df.format(dateTimeLeft);
            Calendar cal = Calendar.getInstance(); 
            cal.setTime(dateTimeLeft);
            cal.add(Calendar.SECOND, -1);
            dateTimeLeft = cal.getTime();
            jLabel2.setText(textTime);
            if(timeLeft <= 0){
                timer.stop();
                accept1();
            }
        }
        });
        timer.start();
        }
    
        public MongoCursor<Document> selectByCode(MongoCursor<Document> cursor,MongoDB mongoDB){
            if("EPC".equals(this.subjectCode)){
                return cursor = mongoDB.epcCollection.find().iterator();
            }else if ("JAVA".equals(this.subjectCode)){
                return cursor = mongoDB.javaCollection.find().iterator(); 
            }else if("HTML5".equals(this.subjectCode)){
                return cursor = mongoDB.htmlCollection.find().iterator(); 
            }
            return null;
        }
    
        public void getData(MongoCursor<Document> cursor){
            while (cursor.hasNext()) {
                var obj = cursor.next();
                int qid = (int) obj.get("qid");
                String title = (String) obj.get("title");
                List<String> options = (List<String>) obj.get("options");
                int answer = (int) obj.get("answer");
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
        this.jLabelTitle.setText("<html><p>" + "Câu số " + (pos + 1) + " :  " + q.getTitle() +  "</p></html>");
        this.jRadioButtonA.setText("A " + q.getAnswerA());
        this.jRadioButtonB.setText("B " + q.getAnswerB());
        this.jRadioButtonC.setText("C " + q.getAnswerC());
        this.jRadioButtonD.setText("D " + q.getAnswerD());
        int status = q.getStatus();
        switch (status) {// kiểm tra trạng thái của câu trả lời
            case 1:
                OnOff(status); 
                break;
            case 2:
            
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
    public void accept(){
        int dialogButton = JOptionPane.OK_CANCEL_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn tiếp tục không?", "Warning", dialogButton,HEIGHT);
            if (result == JOptionPane.OK_OPTION) {
            int point = 0;
       
            for (int i = 0; i < this.list.size(); i++) {
                var item = this.list.get(i);
                if(item.getStatus() == item.getAnswer()){
                    point++;
                }
            }
            ResultViewJFrame rvj = new ResultViewJFrame(point);
            rvj.show();
            this.hide();
            } else if (result == JOptionPane.NO_OPTION) {
                
            }
    }
    public void accept1(){
        
            int point = 0;
       
            for (int i = 0; i < this.list.size(); i++) {
                var item = this.list.get(i);
                if(item.getStatus() == item.getAnswer()){
                    point++;
                }
            }
            ResultViewJFrame rvj = new ResultViewJFrame(point);
            rvj.show();
            this.hide();
            
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
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

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

        jLabel1.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 48)); // NOI18N
        jLabel1.setText("Aptech Education");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(26, 26, 26))
        );

        jList1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jList1.setForeground(getForeground());
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
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
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelTitle.setText("Câu hỏi");
        jLabelTitle.setPreferredSize(new java.awt.Dimension(80, 35));
        jPanel3.add(jLabelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 80));

        buttonGroup1.add(jRadioButtonA);
        jRadioButtonA.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jRadioButtonA.setText("A");
        jRadioButtonA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonA, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 780, -1));

        buttonGroup1.add(jRadioButtonB);
        jRadioButtonB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jRadioButtonB.setText("B");
        jRadioButtonB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonBActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonB, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 790, -1));

        buttonGroup1.add(jRadioButtonC);
        jRadioButtonC.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jRadioButtonC.setText("C");
        jRadioButtonC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonC, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 790, -1));

        buttonGroup1.add(jRadioButtonD);
        jRadioButtonD.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jRadioButtonD.setText(" ");
        jRadioButtonD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonD, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 790, -1));

        jLabel2.setText("00:45:00");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 50, -1));

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton5.setText("Prev");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton6.setText("Next");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton3)
                .addGap(47, 47, 47)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
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
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        OnOff(1); //
    }//GEN-LAST:event_jRadioButtonAActionPerformed

    private void jRadioButtonBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonBActionPerformed
        // TODO add your handling code here:
        OnOff(2);
    }//GEN-LAST:event_jRadioButtonBActionPerformed

    private void jRadioButtonCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCActionPerformed
        // TODO add your handling code here:
        OnOff(3);
    }//GEN-LAST:event_jRadioButtonCActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        pos = this.jList1.getSelectedIndex();
        View();
    }//GEN-LAST:event_jList1ValueChanged

    private void jRadioButtonDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonDActionPerformed
        // TODO add your handling code here:
        OnOff(4);
    }//GEN-LAST:event_jRadioButtonDActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        accept();
        
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        nextPage();
    }//GEN-LAST:event_jButton6ActionPerformed
    
    public void nextPage(){
        if(pos == 19){
            return;  
        }
        pos++;
        this.jList1.setSelectedIndex(pos);
        this.jList1.ensureIndexIsVisible(pos);
        View();
    };
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       backPage();
    }//GEN-LAST:event_jButton5ActionPerformed
    
    public void backPage(){
        if(pos == 0){
            return;
        }
        pos--;
        this.jList1.setSelectedIndex(pos);
        this.jList1.ensureIndexIsVisible(pos);
        View();
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        exit();
    }//GEN-LAST:event_jButton4ActionPerformed
    public void exit(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn thoát chương trình không?", "Warning", dialogButton,HEIGHT);
        if (result == JOptionPane.YES_OPTION) {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            QuestionJFrame questionJFrame = new QuestionJFrame();
            questionJFrame.hide();
        }else{
            return;
        }
    }
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
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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

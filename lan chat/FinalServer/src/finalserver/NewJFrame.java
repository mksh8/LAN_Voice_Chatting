/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalserver;
import java.io.*;
import java.net.*;
import javax.sound.midi.Receiver;
import javax.sound.sampled.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import static javafx.scene.input.KeyCode.K;
import javax.sound.sampled.DataLine.Info;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;


import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mukeshkumar
 */

public class NewJFrame extends javax.swing.JFrame implements ActionListener{
    ServerSocket MyService;
    Socket clientSocket = null;
    BufferedInputStream input;
     AudioFormat audioFormat;			//the class which specify the particular arrangemnt of data
    TargetDataLine targetDataLine;		//type of data line from which data can be read.
    BufferedOutputStream out;
    ByteArrayOutputStream byteArrayOutputStream;
    SourceDataLine sourceDataLine;
    int count=0;
    byte tempBuffer[] = new byte[10000];
    NewJFrame() throws LineUnavailableException
    {       super("Conference_Server");
        initComponents();
        //jTextArea2.setText("startho gya ");
            try
        {
            audioFormat = getAudioFormat();
            // DataLine.Info provides additional information specific to data lines. 
            //This information includes:
           //   the audio formats supported by the data line.
           //     the minimum and maximum sizes of its internal buffer.
            DataLine.Info dataLineInfo =  new DataLine.Info(SourceDataLine.class,audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();
           // jList1.add(jLabel1);
          jButton1.addActionListener(this);
          this.setVisible(true);
        }
        catch (Exception e)
        {
            //System.out.println("constructer exp");
            e.printStackTrace();
        }
            
    }
      public  void start(){
        try{  MyService = new ServerSocket(1987);
             
        while(true)
               {
                   try{
                       //System.out.println("\n in start");
                       waitForClient();
                       
                       whileChatting();
                       
                   }catch( EOFException e)
                   {
                       System.out.println("\n Server ended the connection!");
                   }finally{
                       closeCrap();
                       count=0;
                   }
                   
                   
               }
                 
             } catch(IOException io)
            {
               // System.out.println("start exxp");
                io.printStackTrace();
            }
    }
      public void waitForClient() throws IOException{
            // System.out.println("hello");
            
             System.out.println("Waiting for client");
             if(count==0)
             {
                  clientSocket = MyService.accept();
                  count=1;
             }
             else
             {
                 jTextArea1.append("\n New client wait ");
                 PrintStream out = new PrintStream(clientSocket.getOutputStream());
             }
          
             System.out.println("Waiting for client2");
           try{
         jTextArea2.setText((String)(clientSocket.getInetAddress().getHostName()));
             jTextArea1.append("\nAccepted connection to "+clientSocket.getInetAddress());
            System.out.println("\nAccepted"+clientSocket.getInetAddress().getHostName());
            
           // jList1.add(clientSocket.getInetAddress().getHostName(), rootPane);
            
            input = new BufferedInputStream(clientSocket.getInputStream());    
            out = new BufferedOutputStream(clientSocket.getOutputStream());
           }catch(Exception e)
           {
              // System.out.println("wait exp");
           }
       
        
      }
       public void whileChatting(){
             try{
             ScaptureAudio();
              while(input.read(tempBuffer)!=-1)
            {            
                sourceDataLine.write(tempBuffer,0,10000);
            }
             }catch(IOException e)
             {
                 //System.out.println("WHILECHATTING EXCEp");
                 e.printStackTrace();
             }
                 
         }
        private AudioFormat getAudioFormat ()throws IOException
            
    {
        {
        float sampleRate = 8000.0F;          
        int sampleSizeInBits = 16;           
        int channels = 1;            
        boolean signed = true;            
        boolean bigEndian = false;         
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        }
    }

   
    
    private void ScaptureAudio()
    {
        try
        {
           // System.out.println("\n in scapaud!");
             //The Mixer.Info class represents information about an audio mixer, 
            //including the product's name, version, and vendor, along with a textual description;
            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            audioFormat = getAudioFormat();
            // DataLine.Info provides additional information specific to data lines. 
            //This information includes:
           //   the audio formats supported by the data line
           //     the minimum and maximum sizes of its internal buffer
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            //A mixer is an audio device with one or more lines.
            // A mixer that actually mixes audio has multiple input (source) lines and at least one output (target) line.
            Mixer mixer = AudioSystem.getMixer(mixerInfo[3]);
            targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            Thread ScaptureThread = new SCaptureThread();
            ScaptureThread.start();        
        }
        catch (Exception e)
        {
            System.out.println(e+"scapture exp");
           // System.exit(0);
        }
    }

    class SCaptureThread extends Thread
    {
        byte tempBuffer[] = new byte[10000];
        @Override
        public void run()
        {            
            try
            {
                while (true)
                {
                    int cnt = targetDataLine.read(tempBuffer, 0, tempBuffer.length);
                    out.write(tempBuffer);
                     out.flush();
                }
               
            }
            catch (Exception e)
            {
                //System.out.println("scap exp");
                System.out.println(e);
                //System.exit(0);
            }
        }
    }



    /**
     * Creates new form NewJFrame
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Name of the Client");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton2.setText("Disconnect");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Connect");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane1.setViewportView(jTextArea2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
         if(ae.getSource()==this.jButton1)
         {
             //start();
               // System.out.println(" in jb1 ");
             try{
                  this.jTextArea1.append("waiting for client\n");
                    start();
            }catch(Exception e){
               // System.out.println("jb1 exp");
            }
             
         }
        
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
            closeCrap();
        }catch (Exception e)
        {
            
            e.printStackTrace();
        } 
           
    }//GEN-LAST:event_jButton2ActionPerformed

     public void closeCrap(){
         jTextArea1.setText(jTextArea1.getText()+"\nClosing streams\n");
     
        try
        {
          
            out.close();
        byteArrayOutputStream.close();
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables

//class KK{
 public static void main(String args[]) throws LineUnavailableException {
       
       NewJFrame newJFrame = new NewJFrame();
        newJFrame.start();
        
    }
}
//}
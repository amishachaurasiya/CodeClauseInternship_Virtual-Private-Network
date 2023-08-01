import java.io.*;
import java.net.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.BorderLayout;


public class Client extends JFrame {
     Socket socket;
    BufferedReader br;
     PrintWriter out;

private JLabel heading = new JLabel("Client Area");
private JTextArea messageArea=new JTextArea();
private JTextField messageInput = new JTextField();
private Font font=new Font("Roboto",Font.PLAIN,20);
 
public Client()
  {
    try{
        System.out.println("Sending request to server...");
           socket=new Socket("127.0.0.1",7778);
           System.out.println("Connection Done");


           br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
          out=new PrintWriter(socket.getOutputStream());
          
          createGUI();
          handleEvents();

          startReading();
         startWriting();
    } catch(Exception e)
    {
        //TODO: Handle exception
// System.out.println("Connection closed");  
 
    }



 }
 private void handleEvents()
 {
    messageInput.addKeyListener((KeyListener) new KeyListener()
    {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
             }
             

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
           if(e.getKeyCode()==10)
           {
            String contentToSend= messageInput.getText();
            messageArea.append("Me :"+contentToSend+"\n");
            out.println(contentToSend);
            out.flush();
            messageInput.setText("");
            messageInput.requestFocus();
           }
        }

    });
 }
 private void createGUI()
 {
    this.setTitle("Client Messager[END]");
    this.setSize(600,600);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    //coding for component
    heading.setFont(font);
    messageArea.setFont(font);
    messageInput.setFont(font);
    heading.setIcon(new ImageIcon("clogo.png"));
    heading.setHorizontalAlignment(SwingConstants.CENTER);
    heading.setVerticalTextPosition(SwingConstants.BOTTOM);
    heading.setHorizontalAlignment(SwingConstants.CENTER);
    heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    messageInput.setHorizontalAlignment(SwingConstants.CENTER);
    
    // set frame layout
    this.setLayout(new BorderLayout());
    //adding components to the frame
    this.add(heading,BorderLayout.NORTH);
    this.add(messageArea,BorderLayout.CENTER);
    this.add(messageInput,BorderLayout.SOUTH);

 }




 public void startReading()
{
//thread-read and give data
Runnable r1=()->{
 System.out.println("Reader started...");
 
 try{
 while(true)
 {
    
   String msg=br.readLine(); 
   if(msg.equals("exit"))
   {
    System.out.println("Server terminated the chat");
    JOptionPane.showMessageDialog(this,"Server Terminated the chat");
    messageInput.setEnabled(false);
    socket.close();
    break;
   }
//    System.out.println("Server:"+msg);
   messageArea.append("Server : " + msg +"\n");
 }
  } catch (Exception e)
    {
// e.printStackTrace();
System.out.println("Connection closed");  
    }
};
new Thread(r1).start();
}
public void startWriting()
{
 // thread- takes data frm user and send data to the client.
Runnable r2=()->{
     System.out.println("Writer started...");
try{
     while(true && !socket.isClosed())
{
    
       BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
       String content=br1.readLine(); 
       out.println(content);
       out.flush();

       if(content.equals("exit"))
       {
        socket.close();
        break;
       }

   
}
System.out.println("Connection is closed");  
 } catch (Exception e)
    {
       e.printStackTrace();
    }
};
new Thread(r2).start();

}

    public static void main(String ar[])
    {
        System.out.println("This is client...");
        new Client();
    
    }
}

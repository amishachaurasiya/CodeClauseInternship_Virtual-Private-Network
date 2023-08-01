import java.net.*;
import java.io.*;


public class Server
{
    ServerSocket server;
    Socket socket;
     BufferedReader br;
     PrintWriter out;

    public Server()
    {
        try{
             server= new ServerSocket(7778);
         System.out.println("Server is ready to accept connection.");
         System.out.println("Waiting...");
         socket= server.accept();
          br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
          out=new PrintWriter(socket.getOutputStream());
          startReading();
          startWriting();

    
        } catch (Exception e)
    {
        e.printStackTrace();
    }
        
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
    System.out.println("Client terminated the chat");
    socket.close();
    break;
   }
   System.out.println("Client:"+msg);
   
 }
  } catch (Exception e)
    {
e.printStackTrace();
// System.out.println("Connection is closed");
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
} catch (Exception e)
    {
    //    e.printStackTrace();
System.out.println("Connection is closed");
}
};
new Thread(r2).start();

}

    public static void main(String[] args)
    {
        
        System.out.println("This is server... going to start server");
    new Server();
    }

}
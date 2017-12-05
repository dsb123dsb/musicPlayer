import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
	ArrayList clientOutputStreams;
	// 接收信息线程
	public class ClientHandler implements Runnable{
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket){
			try{
				sock = clientSocket;
				// 接收信息
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		public void run(){
			String message;
			try{
				while ((message=reader.readLine())!=null){
					System.out.println("reader "+message);
					tellEveryone(message);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	public static void main (String[] args){
		new SimpleChatServer().go();
	}
	public void go(){
		clientOutputStreams = new ArrayList();
		try{
			// 建立等待用户请求的ServerSocket
			ServerSocket serverSock = new ServerSocket(5000);
			
			while(true){
				// 建立与用户通信的Socket
				Socket clientSocket = serverSock.accept();
				// 建立输出信息对象
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("got a connection");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	// 遍历接收消息列表，并且输出
	public void tellEveryone (String message){
		Iterator it = clientOutputStreams.iterator();
		while(it.hasNext()){
			try{
				PrintWriter writer = (PrintWriter)it.next();
				writer.println(message);
				writer.flush();			
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}

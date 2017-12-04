import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
	ArrayList clientOutputStreams;
	// 接收信息线程
	public class ClientHander implements Runnable{
		BufferedReader reader;
		Socket sock;
		
		public ClientHander(Socket clientSocket){
			
		}
		public void run(){
			
		}
	}
	public static void main (String[] args){
		new SimpleChatServer().go();
	}
	public void go(){
		clientOutputStreams = new ArrayList();
	}
	// 遍历接收消息列表，并且输出
	public void tellEveryone (String message){
		
	}
}

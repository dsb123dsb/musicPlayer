import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
	ArrayList clientOutputStreams;
	// ������Ϣ�߳�
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
	// ����������Ϣ�б��������
	public void tellEveryone (String message){
		
	}
}

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {
	JTextArea incoming;
	JTextField outgoing;
	BufferedReader reder;
	PrintWriter writer;
	Socket sock;
	
	public static void main(String[] args){
		SimpleChatClient client = new SimpleChatClient();
		client.go();
	} 
	public void go(){
		
	}
	// ����socket���ӷ����
	private void setUpNetworking(){
		
	}
	// �����ť���������ڷ����
	public class SendbuttonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			
		}
	}
	// �����߳�ͬʱ��ȡ�������Ϣͬʱ�ӵ��ɹ����ı�������
	public class IncomingReader implements Runnable{
		public void run(){
			
		}
	}
}

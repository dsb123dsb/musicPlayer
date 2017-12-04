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
	// 启动socket链接服务端
	private void setUpNetworking(){
		
	}
	// 点击按钮传送内容内服务端
	public class SendbuttonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			
		}
	}
	// 开启线程同时读取服务端信息同时加到可滚动文本区域内
	public class IncomingReader implements Runnable{
		public void run(){
			
		}
	}
}

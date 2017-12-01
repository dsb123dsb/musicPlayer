import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer3{
	static JFrame f = new JFrame("My First Music Video");
	static MyDrawPanel ml;
	
	public static void main (String[] args){
		MiniMusicPlayer3 mini = new MiniMusicPlayer3();
		mini.go();
	}
//	Gui主方法
	public void setUpGui(){
		ml = new MyDrawPanel();
		f.setContentPane(ml);
		f.setBounds(30,30,300,300);
		f.setVisible(true);
	}
//	程序执行主方法
	public void go(){
		setUpGui();
		try{
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			
//			int[] eventsIWant = {127};//向sequencer注册事件，为其请求更改通知的 MIDI 控件号0-127
			sequencer.addControllerEventListener(ml, new int[] {127});//第一个参数事件源
			
			Sequence seq = new Sequence(Sequence.PPQ,4);
			Track track = seq.createTrack();
			
			int r = 0;
			for(int i =0; i<60; i+=4){
				r=(int)((Math.random()*50)+1);//随机数1-50
//				144表示打开，128表示关闭，第二个表示频道何种乐器，第三个表示音符0-127可对MIDI进行编号，代表不同音高，第四个表示音道，多大声音
				track.add(makeEvent(144,1,r,100,i));
				track.add(makeEvent(176,1,127,0,i));//监听此事件127不会做任何事情，只是打印
				track.add(makeEvent(128,1,r,100,i+2));
			}
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(120);
			sequencer.start();
		}catch(Exception ex){ex.printStackTrace();}
	}

//	静态实用方法来制作信息并返回MidiEvent
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();// 做什么
			a.setMessage(comd,chan, one ,two); 
			event = new MidiEvent(a, tick);// 何时做，tick事件时间
		}catch(Exception e){}
		return event;		
	}
//	画板继承事件接口
	class MyDrawPanel extends JPanel implements ControllerEventListener{
		boolean msg = false;//获知事件时才会为真
		
		//		事件接口抽象方法重写
		public void controlChange(ShortMessage event){
			// 获知事件为true并调用重绘程序
			msg = true;
			repaint();
		}
		public void paintComponent(Graphics g){
			if(msg){//需要判断，因为可能其它原因引发重绘，要判断是否为ControllerEvent引发
				Graphics2D g2 = (Graphics2D) g;
				
				int r = (int)(Math.random()*250);
				int gr = (int)(Math.random()*250);
				int b = (int)(Math.random()*250);
				
				g.setColor(new Color(r,gr,b));
				
				int ht = (int)((Math.random()*120)+10);
				int width = (int)((Math.random()*120)+10);
				
				int x = (int)((Math.random()*40)+10);
				int y = (int)((Math.random()*40)+10);
				
				g.fillRect(x, y, ht, width);
				msg = false;
			}
		}//关闭方法
	}//关闭内部类
	
}//关闭类

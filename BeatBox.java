import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class BeatBox {
	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame theFrame;
	String[]instrumentNames={"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare",
			"Crash Cymbal", "Hand Clap", "Hight Tom", "Hi Bongo", "Maracass", "Whistle","LowConga",
			"Cowbell", "Vibrasslap", "Low-mid Tom", "High Agogo","Open Hi Conga"
	};
	int []instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	
	public static void main(String[] args){
		new BeatBox().buildGUI();
	}
	
	public  void buildGUI(){
		theFrame=new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		checkboxList = new ArrayList<JCheckBox>();
		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		
		JButton start = new JButton("Start");
		start.addActionListener(new MyStartListener());
		buttonBox.add(start);
		
		JButton stop = new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);
		
		JButton upTempo = new JButton("Tempo Up");
		upTempo.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTempo);
		
		JButton downTempo = new JButton("Tempo Down");
		downTempo.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);
		
		JButton store = new JButton("store");
		store.addActionListener(new MySendListener());
		buttonBox.add(store);
		
		JButton readStore = new JButton("readStore");
		readStore.addActionListener(new MyReadListener());
		buttonBox.add(readStore);
		
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for(int i =0; i<16; i++){
			nameBox.add(new Label(instrumentNames[i]));
		}
		
		background.add(BorderLayout.EAST,buttonBox);
		background.add(BorderLayout.WEST,nameBox);
		
		theFrame.getContentPane().add(background);
		
		GridLayout grid = new GridLayout(16,16);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER,mainPanel);
		
		for(int i =0 ;i<256; i++){
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}
		
		setUpMidi();
		
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}
	public void setUpMidi(){
		try{
			sequencer=MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}catch(Exception e){e.printStackTrace();}
	}
	public void buildTrackAndStart(){
		int[] trackList = null;
		
		sequence.deleteTrack(track);// 清除掉旧的track做一个新的
		track = sequence.createTrack();
		
		for(int i=0;i<16;i++){
			trackList = new int[16];
			int key = instruments[i];
			
			for(int j= 0; j<16;j++){
				JCheckBox jc = (JCheckBox)checkboxList.get(j+16*i);
				if(jc.isSelected()){
					trackList[j]=key;
				}else{
					trackList[j]=0;
				}
			}
			makeTracks(trackList);
//			track.add(makeEvent(176,1,127,0,16));
		}
		
//		track.add(makeEvent(192,9,1,0,15));
		try{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}catch(Exception e){e.printStackTrace();}
	}
	public class MyStartListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			buildTrackAndStart();
		}
	}
	public class MyStopListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			sequencer.stop();
		}
	}
	public class MyUpTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			System.out.println(tempoFactor);
			sequencer.setTempoFactor((float)(tempoFactor*1.03));
		}
	}
	public class MyDownTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor*.97));
		}
	}
	// 存储复选框状态
	public class MySendListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState = new boolean[256];
			for(int i =0;i<256;i++){
				JCheckBox check = (JCheckBox) checkboxList.get(i);
				if(check.isSelected()){
					checkboxState[i]=true;
				}
			}
			try{// 将boolean数组序列化
				FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));
				ObjectOutputStream os = new ObjectOutputStream(fileStream);
				os.writeObject(checkboxState);
			}catch(Exception ex) {ex.printStackTrace();}
		}
	}
	// 读取复选框状态
	public class MyReadListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState = null;
			try{// 读取文件对象并且转化为boolean数组
				FileInputStream fileIn = new FileInputStream(new File("Checkbox.ser"));
				ObjectInputStream is = new ObjectInputStream(fileIn);
				checkboxState=(boolean[]) is.readObject();
			}catch(Exception ex) {ex.printStackTrace();}
			
			for(int i =0;i<256;i++){
				JCheckBox check = (JCheckBox) checkboxList.get(i);
				if(checkboxState[i]){
					check.setSelected(true);
				}else{
					check.setSelected(false);
				}
			}
			
			sequencer.stop();
			buildTrackAndStart();
		}
	}
	public void makeTracks(int[] list){
		for(int i =0; i<16; i++){
			int key = list[i];
			
			if(key!=0){
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100,i+1));
			}
		}
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
}

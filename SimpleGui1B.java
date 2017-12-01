import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SimpleGui1B {
	JFrame frame;
	JLabel label;
	JButton colorButton;
	JButton labelButton;
	int x = 70;
	int y = 70;
	public static void main (String[] args){
		SimpleGui1B gui = new SimpleGui1B();
		gui.go();
	}
	public void go(){
		frame = new JFrame();// window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		colorButton = new JButton("Change color!!"); //按钮组件1
		colorButton.addActionListener(new ColorListener());
		
		labelButton = new JButton("Change label!!"); //按钮组件2
		labelButton.addActionListener(new LabelListener());
		
		label = new JLabel("I am a label");// label组件
		MyDrawPanel draw = new MyDrawPanel(); // 画布组件
		
		
		
		frame.getContentPane().add(BorderLayout.SOUTH,colorButton);
		frame.getContentPane().add(BorderLayout.CENTER,draw);
		frame.getContentPane().add(BorderLayout.EAST,labelButton);
		frame.getContentPane().add(BorderLayout.WEST,label);	
		frame.setSize(500, 500);
		frame.setVisible(true);
		
		// panel动画
		for(int i = 0; i<130; i++){
			x++;
			y++;
			draw.repaint();
			try{
				Thread.sleep(50);
			}catch(Exception ex){}
		}
	}
	// 内部类
	class ColorListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			colorButton.setText("I have been clicked!!");
			frame.repaint();
		}	
	}
	class LabelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			labelButton.setText("I have been clicked!!");
			label.setText("Ouch!");
		}	
	}
	class MyDrawPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g){
			// 填充背景色，擦去动画时原来痕迹
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			int red = (int)(Math.random()*250);
			int green = (int)(Math.random()*250);
			int blue = (int)(Math.random()*250);
			
			Color randomColor = new Color(red,green,blue);
			g.setColor(randomColor);
			g.fillOval(x,y,40,40);
		}
	}
}


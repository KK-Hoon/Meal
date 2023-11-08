package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Management extends JFrame{

	private JButton btn[] = new JButton[5]; 
	
	class NorthPanel extends JPanel{
		String s[] = {"메뉴 등록", "메뉴 관리", "결제 조회", "종류별 차트", "종료"};
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			for(int i=0; i<btn.length; i++) {
				btn[i] = new JButton(s[i]);
				btn[i].addActionListener(new MyActionListener());
				add(btn[i]);
			}
		}
	}
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			
			switch(s) {
				case "메뉴 등록":
					new Newmenu();
					break;
				case "메뉴 관리":
					new MenuManage();
					break;
				case "결제 조회":
					new Paycheck();
					break;
				case "종류별 차트":
					new Chart();
					break;
				case "종료":
					dispose();
					break;
			}
		}
	}
	class CenterPanel extends JPanel{
		JLabel label = new JLabel(new ImageIcon("C:\\Users\\rlaru\\OneDrive\\바탕 화면\\meal\\Meal\\DataFiles\\main.jpg"));
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			add(label);
		}
	}
	
	public Management() {
		// TODO Auto-generated constructor stub
		setTitle("관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		c.add(new NorthPanel(), BorderLayout.NORTH);
		c.add(new CenterPanel(), BorderLayout.CENTER);
		
		setSize(600, 470);
		setVisible(true);
	}
}
package UI;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main extends JFrame{
	
	private JButton btn[] = new JButton[4];
	
	public Main() {
		// TODO Auto-generated constructor stub
		setTitle("메인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = getContentPane();
		
		ct.setLayout(new GridLayout(4, 1));
		
		String s[] = {"사용자", "관리자", "사원등록", "종료"};
		for(int i=0; i<btn.length; i++) {
			btn[i] = new JButton(s[i]);
			btn[i].addActionListener(new MyActionListener());
			ct.add(btn[i]);
		} 
		
		setVisible(true);
		setSize(250, 250);
	}
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			
			switch(s) {
				case "사용자":
					dispose();
					new Ticket();
					break;
				case "관리자":
					dispose();
					new Mpass();
					break;
				case "사원등록":
					new Register();
					break;
				case "종료":
					dispose();
					break;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
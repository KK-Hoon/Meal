package UI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
public class Mpass extends JFrame{
	
	private JPasswordField jt = new JPasswordField(4);
	
	class Mainpanel extends JPanel{
		
		public Mainpanel() {
			// TODO Auto-generated constructor stub
			setLayout(new BorderLayout());
			
			jt.setEnabled(false);
			jt.setEchoChar('●');
			
			add(jt, BorderLayout.NORTH);
			
			add(new CenterPanel(), BorderLayout.CENTER);
			
			add(new SouthPanel(), BorderLayout.SOUTH);
		}
	}
	
	class CenterPanel extends JPanel{
		
		JButton btn[] = new JButton[9];
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(3, 3));
			
			for(int i=1; i<=btn.length; i++) {
				btn[i-1] = new JButton(Integer.toString(i));
				btn[i-1].addActionListener(new MyBtnListener());
				add(btn[i-1]);
			}
		}
	}
	
	class SouthPanel extends JPanel{
		
		JButton btn = new JButton("0");
		
		public SouthPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(1,1));
			btn.addActionListener(new MyBtnListener());
			add(btn);
		}
	}
	
	class MyBtnListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			char c[] = jt.getPassword();
			String s1 = new String(c);
			
			if(s1.length()<4) {
				jt.setText(s1.concat(s));
			}
		}
	}
	public Mpass() {
		// TODO Auto-generated constructor stub
		int n = JOptionPane.showConfirmDialog(null, new Mainpanel(), "관리자 패스워드 입력", JOptionPane.OK_CANCEL_OPTION);
		if(n == JOptionPane.OK_OPTION) {
			char c[] = jt.getPassword();
			String s = new String(c);
			if(s.equals("0000")) {
				new Management();
			}
			else {
				JOptionPane.showMessageDialog(null, "관리자 패스워드를 확인하십시오", "Message", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			dispose();
		}
	}
}
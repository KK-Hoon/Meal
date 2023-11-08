package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DB.Connect;

public class Register extends JFrame{

	private JLabel label[] = new JLabel[3];
	private JTextField jt[] = new JTextField[2];
	private JButton btn[] = new JButton[2];
	private Connection con = Connect.makeConnection("meal");
	private PreparedStatement psmt = null;
	private Statement st = null;
	private JPasswordField pass[] = new JPasswordField[2];
	private JLabel trfa = new JLabel("");
	
	public Register() {
		// TODO Auto-generated constructor stub
		setTitle("사원등록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new GridLayout(5, 2));
		
		//라벨 초기화
		String detail[]= {"사원번호:", "*사원명:", "*패스워드:"};
		for(int i=0; i<label.length; i++) {
			label[i] = new JLabel(detail[i]);			
		}
		
		//텍스트필드 초기화
		for(int i=0; i<jt.length; i++) {
			jt[i] = new JTextField();
			if(i == 0) {
				jt[i].setEnabled(false);
				try {
					st = con.createStatement();
					ResultSet rs = st.executeQuery("select max(memberNo) from member");
					while(rs.next()) {
						jt[i].setText(Integer.toString(rs.getInt(1)+1));
					}
					
				}
				catch(Exception e) {
					
				}
			}
		}
		//패스워드필드 초기화
		for(int i=0; i<pass.length; i++) {
			pass[i] = new JPasswordField(4);
			pass[i].setFocusTraversalKeysEnabled(false);
			pass[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					String tr = "일치";
					String fa = "불일치";
					if(e.getKeyCode() == KeyEvent.VK_TAB) {
						if(e.getSource() == pass[0]) {
							pass[1].requestFocus();
						}
						else {
							btn[0].requestFocus();
						}
						
						String p1 = new String(pass[0].getPassword());
						String p2 = new String(pass[1].getPassword());
						if(!p1.equals("") && !p2.equals("")) {
							if(p1.equals(p2)) {
								trfa.setText(tr);
								trfa.setForeground(Color.blue);
							}
							else {
								trfa.setText(fa);
								trfa.setForeground(Color.red);
							}
						}
					}
				}
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					JPasswordField src = (JPasswordField) e.getSource();
					char c[] = src.getPassword();
					String s = new String(c);
					if(s.length() > 3) {
						e.consume();
					}
				}
			});
		}
		
		//삽입
		for(int i=0; i<2; i++) {
			add(label[i]);
			add(jt[i]);
		}
		add(label[2]);
		add(pass[0]);
		add(new TF());
		add(pass[1]);
		
		//버튼 초기화
		String submit[] = {"등록", "닫기"};
		for(int i=0; i<btn.length; i++) {
			btn[i] = new JButton(submit[i]);
			btn[i].addActionListener(new MyActionListener());
			add(btn[i]);
		}
		
		setSize(400, 300);
		setVisible(true);
		
		c.setFocusable(true);
		c.requestFocus();
	}
	
	class TF extends JPanel{
		JLabel la = new JLabel("*패스워드 재입력:");
		
		public TF() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(0, 2));
			add(la);
			add(trfa);
		}
	}
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			switch(s){
				case "등록":
					Regist();
					break;
				case "닫기":
					dispose();
					break;
			}
		}
	}
	public void Regist(){
		String s[] = new String[3];
		s[0] = jt[1].getText();
		for(int i=0; i<pass.length; i++) {
			String ss = new String(pass[i].getPassword());
			s[i+1] = ss;
		}
		if(s[0].equals("") || s[1].equals("") || s[2].equals("")) {
			JOptionPane.showMessageDialog(null, "필수 항목(*)누락", "message", JOptionPane.ERROR_MESSAGE);
		}
		else if(!s[1].equals(s[2])) {
			JOptionPane.showMessageDialog(null, "패스워드 확인 요망", "message", JOptionPane.ERROR_MESSAGE);
		}
		else {
			try {
				psmt = con.prepareStatement("insert into member values(?, ?, ?)");
				psmt.setInt(1, Integer.parseInt(jt[0].getText()));
				psmt.setString(2, s[0]);
				psmt.setString(3, s[0]);
				psmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "사원이 등록되었습니다.", "message", JOptionPane.INFORMATION_MESSAGE);
			}
			catch(Exception e) {
				
			}
		}
	}
}
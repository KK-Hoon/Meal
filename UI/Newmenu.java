package UI;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DB.Connect;

public class Newmenu extends JFrame{
	
	private JLabel label[] = new JLabel[4];
	private JButton btn[] = new JButton[2];
	private JTextField jt = new JTextField();
	private JComboBox combo[] = new JComboBox[3]; 
	private Connection con = Connect.makeConnection("meal");
	private PreparedStatement psmt = null;
	
	public Newmenu() {
		// TODO Auto-generated constructor stub
		setTitle("신규 메뉴 등록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		c.setLayout(new GridLayout(5, 2));
		
		//라벨 초기화
		String detail[]= {"종류", "*메뉴명", "가격", "조리가능수량"};
		for(int i=0; i<label.length; i++) {
			label[i] = new JLabel(detail[i]);			
		}
		
		//콤보0번 초기화
		String menu[] = {"한식", "중식", "일식", "양식"};
		combo[0] = new JComboBox<String>(menu);
		
		//콤보1번 초기화
		Vector<String> price = new Vector<String>();
		for(int i=1000; i<=12000; i+=500) {
			price.add(Integer.toString(i));
		}
		combo[1] = new JComboBox<String>(price);
		
		//콤보2번 초기화
		Vector<String> cookable = new Vector<String>();
		for(int i=0; i<=50; i++) {
			cookable.add(Integer.toString(i));
		}
		combo[2] = new JComboBox<String>(cookable);
		
		int j=0;
		for(int i=0; i<4; i++) {
			add(label[i]);
			if(i == 1) {
				add(jt);
				continue;
			}
			else {
				add(combo[j]);
				j++;
			}
		}
		//버튼 초기화
		String submit[] = {"등록", "닫기"};
		for(int i=0; i<btn.length; i++) {
			btn[i] = new JButton(submit[i]);
			btn[i].addActionListener(new MyActionListener());
			add(btn[i]);
		}
		
		setSize(400, 300);
		setVisible(true);
	}

	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			
			switch(s) {
				case "등록":
					String type = combo[0].getSelectedItem().toString();
					String name = jt.getText();
					String price = combo[1].getSelectedItem().toString();
					String cookable = combo[2].getSelectedItem().toString();
					String typeNum = null;
					
					switch(type) {
					case "한식":
						typeNum = "1";
						break;
					case "중식":
						typeNum = "2";
						break;
					case "일식":
						typeNum = "3";
						break;
					case "양식":
						typeNum = "4";
						break;
					}
					//메뉴명 없을때
					if(name.equals("")) {
						JOptionPane.showMessageDialog(null, "메뉴명을 입력해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
					}
					
					//메뉴명 있을때
					else {
						try {
							psmt = con.prepareStatement("insert into meal(cuisineNo, mealName, price, maxCount, todayMeal) values(?,?,?,?,?)");
							
							String ss[] = {typeNum, name, price, cookable, "0"}; 
							for(int i=0; i<5; i++) {
								psmt.setString(i+1, ss[i]);
							}
							psmt.executeUpdate();
							JOptionPane.showMessageDialog(null, "메뉴가 정상적으로 등록되었습니다." , "Message", JOptionPane.INFORMATION_MESSAGE);
						}
						catch(Exception ee) {
							
						}
					}
					break;
				case "닫기":
					dispose();
					break;
			}
		}
	}
}
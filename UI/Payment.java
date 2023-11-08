package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DB.Connect;

public class Payment extends JFrame{
	
	private String cuisineNo;
	private JLabel type = new JLabel();
	private JLabel total = new JLabel();
	private int money = 0;
	DecimalFormat df = new DecimalFormat("###,###");
	private String m = df.format(money);
	private String menu;
	private Vector<String> mealNo = new Vector<String>();
	private Vector<String> mealName= new Vector<String>();
	private Vector<String> price= new Vector<String>();
	private Vector<String> maxCount= new Vector<String>();
	private Vector<JButton> menubtn = new Vector<JButton>();
	private Vector<String> colData = new Vector<String>();
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	private DefaultTableModel model;
	private JTable table;
	private JTextField jt[] = new JTextField[2];
	private JButton calbtn[] = new JButton[10];
	private JButton insert = new JButton();
	private JButton cal0 = new JButton();
	private JButton reset = new JButton();
	private JButton pay[] = new JButton[2];
	private int index;
	private Connection con = Connect.makeConnection("meal");
	private Statement st = null;
	private PreparedStatement psmt = null;
	private JComboBox<String> login;
	private JPasswordField pass = new JPasswordField(4);	
	private JLabel num = new JLabel("사원번호");
	private JLabel pw = new JLabel("패스워드");
	
	class NorthPanel extends JPanel{
		
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(2, 1));
			type.setHorizontalAlignment(JLabel.CENTER);
			type.setFont(new Font("고딕", Font.PLAIN, 30));
			add(type);
			add(new Money());
		}
	}
	class Money extends JPanel{
		private JLabel none = new JLabel("");
		
		public Money() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(1, 2));
			add(none);
			add(new Money_1());
		}
	}
	class Money_1 extends JPanel{
		private JLabel label = new JLabel("총 결제금액:");
		
		public Money_1() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(1, 2));
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setFont(new Font("고딕", Font.PLAIN, 25));
			add(label);
			total.setText(m + "원");
			total.setHorizontalAlignment(JLabel.RIGHT);
			total.setFont(new Font("고딕", Font.PLAIN, 25));
			add(total);
		}
	}
	
	class CenterPanel extends JPanel{
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new BorderLayout());
			add(new LCenterPanel(), BorderLayout.CENTER); //메뉴버튼 추가
			add(new RCenterPanel(), BorderLayout.EAST); //결제란 추가
		}
	}
	class RCenterPanel extends JPanel{ //우측 패널
		
		public RCenterPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(2, 1));
			add(new R12());
			add(new R34());
			
		}
	}
	class R12 extends JPanel{// 우측 위 패널
		
		public R12() {
			// TODO Auto-generated constructor stub
			setLayout(new BorderLayout(0, 10));
			add(new R1(), BorderLayout.CENTER);
			add(new R2(), BorderLayout.SOUTH);
		}
	}
	
	class R1 extends JPanel{// 우측 테이블
		
		String s[] = {"상품번호", "품명", "수량", "금액"};
		
		public R1() {
			// TODO Auto-generated constructor stub
			for(int i=0; i<s.length; i++) {
				colData.add(s[i]);
			}
			model = new DefaultTableModel(rowData, colData);
			table = new JTable(model);
			table.addMouseListener(new MyTableListener());
			add(new JScrollPane(table));
		}
	}
	
	class MyTableListener extends MouseAdapter{//테이블 클릭리스너
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getClickCount() == 2) {
				int i = table.getSelectedRow();
				Vector<String> v = rowData.get(i);
				index = Integer.parseInt(v.get(0)) - 1;
				menubtn.get(index).setEnabled(true);
				model = (DefaultTableModel)table.getModel();
				money -= Integer.parseInt(v.get(3));
				m = df.format(money);
				total.setText(m + "원");
				model.removeRow(i);
			}
		}
	}
	
	class R2 extends JPanel{//우측 텍스트필드
		
		String s[] = {"선택품명:", "수량:"};
		JLabel lb[] = new JLabel[2];
		
		public R2() {
			// TODO Auto-generated constructor stub
			for(int i=0; i<s.length; i++) {
				lb[i] = new JLabel(s[i]);
				lb[i].setFont(new Font("고딕", Font.PLAIN, 20));
				add(lb[i]);
				if(i == 0) {
					jt[i] = new JTextField(20);
					add(jt[i]);
				}
				else {
					jt[i] = new JTextField(3);
					add(jt[i]);
				}
				jt[i].setEnabled(false);
			}
		}
	}
	
	class R34 extends JPanel{//우측 아래 텍스트필드
		
		public R34() {
			// TODO Auto-generated constructor stub
			setLayout(new BorderLayout(0, 10));
			
			add(new R3(), BorderLayout.CENTER);
			add(new R4(), BorderLayout.SOUTH);
		}
	}
	
	class R3 extends JPanel{//우측 계산기
		
		public R3() {
			// TODO Auto-generated constructor stub
			setLayout(new BorderLayout());
			add(new R3_12(), BorderLayout.CENTER);
			add(new R3_34(), BorderLayout.SOUTH);
		}
	}
	
	class R3_12 extends JPanel{
		
		public R3_12() {
			// TODO Auto-generated constructor stub
			setLayout(new BorderLayout());
			add(new R3_1(), BorderLayout.CENTER);
			
			insert = new JButton("입    력");
			insert.addActionListener(new MyActionListener());
			add(insert, BorderLayout.EAST);
		}
	}
	class R3_1 extends JPanel{
		
		public R3_1() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(3, 3));
			for(int i=1; i<10; i++) {
				calbtn[i] = new JButton(Integer.toString(i));
				calbtn[i].addActionListener(new MyCalListener());
				add(calbtn[i]);
			}
		}
	}
	class MyCalListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			if(jt[1].getText().length() < 3) {
				jt[1].setText(jt[1].getText().concat(s));
			}
		}
	}
	class R3_34 extends JPanel{
		
		public R3_34() {
			// TODO Auto-generated constructor stub
			setLayout(new BorderLayout());
			
			cal0 = new JButton("0");
			cal0.addActionListener(new MyCalListener());
			add(cal0, BorderLayout.CENTER);
			
			reset = new JButton("초기화");
			reset.addActionListener(new MyActionListener());
			add(reset, BorderLayout.EAST);
			
		}
	}
	class R4 extends JPanel{//우측 결제/취소란
		String s[] = {"결제", "취소"};
		
		public R4() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(1, 2));
			for(int i=0; i<pay.length; i++) {
				pay[i] = new JButton(s[i]);
				pay[i].addActionListener(new MyActionListener());
				add(pay[i]);
			}
		}
	}
	
	
	class LCenterPanel extends JPanel{//왼쪽 메뉴버튼
		
		String sql;
		int n;
		
		public LCenterPanel() {
			// TODO Auto-generated constructor stub
			switch(menu) {
			case "한식":
				cuisineNo = "1";
				break;
			case "중식":
				cuisineNo = "2";
				break;
			case "일식":
				cuisineNo = "3";
				break;
			case "양식":
				cuisineNo = "4";
				break;
			}
			try {
				sql = "select * from meal where cuisineNo = '" + cuisineNo + "' and  todayMeal = '1'";
				st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while(rs.next()) {
					mealNo.add(rs.getString("mealNo"));
					mealName.add(rs.getString("mealName"));
					price.add(rs.getString("price"));
					maxCount.add(rs.getString("maxCount"));
				}
				if(mealNo.size()%5 == 0) {
					n = mealNo.size()/5;
				}
				else {
					n = mealNo.size()/5 + 1;
				}
				setLayout(new GridLayout(n, 5));
				for(int i=0; i<mealNo.size(); i++) {
					menubtn.add(new btn(mealName.get(i), price.get(i), maxCount.get(i)));
					menubtn.get(i).addActionListener(new MyMenuListener(i));
					add(menubtn.get(i));
				}
			}
			catch(Exception e) {
				
			}
		}
	}
	
	class MyMenuListener implements ActionListener{
		
		int i;
		
		public MyMenuListener(int i) {
			// TODO Auto-generated constructor stub
			this.i = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			index = i;
			jt[0].setText(mealName.get(index));
		}
	}
	
	class btn extends JButton{
		
		public btn(String mealName, String price, String maxCount) {
			// TODO Auto-generated constructor stub
			this.setText("<html><center>" + mealName + "</br></br><center>" + price + "원</html>");
			if(maxCount.equals("0")) {
				this.setEnabled(false);
			}
		}
	}
	
	class MyActionListener implements ActionListener{//버튼 클릭리스너
		
		Vector<String> id = new Vector<String>();
		Vector<String> p = new Vector<String>();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			switch(s) {
				case "초기화":
				jt[1].setText("");
				break;
				case "입    력":
					if(jt[0].getText().equals("")) {
						JOptionPane.showMessageDialog(null, "품명을 선택해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
					}
					else if(!jt[0].getText().equals("") && jt[1].getText().equals("")) {
						JOptionPane.showMessageDialog(null, "수량을 지정해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
					}
					else if(!jt[0].getText().equals("") && !jt[1].getText().equals("") && Integer.parseInt(jt[1].getText()) > Integer.parseInt(maxCount.get(index))) {
						JOptionPane.showMessageDialog(null, "조리가능수량을 초과하였습니다.", "Message", JOptionPane.ERROR_MESSAGE);
					}
					else {
						int mon = Integer.parseInt(price.get(index)) * Integer.parseInt(jt[1].getText());
						money = money += mon;
						m = df.format(money);
						total.setText(m + "원");
						Vector<String> v = new Vector<String>();
						v.add(mealNo.get(index));
						v.add(mealName.get(index));
						v.add(jt[1].getText());
						v.add(Integer.toString(mon));
						rowData.add(v);
						menubtn.get(index).setEnabled(false);
						table.updateUI();
					}
					jt[0].setText("");
					jt[1].setText("");
					break;
				case "결제":
					member mb = new member();
					int result = JOptionPane.showConfirmDialog(null, mb, "결제자 인증", JOptionPane.OK_CANCEL_OPTION);
					if(result == JOptionPane.OK_OPTION) {
						int i = login.getSelectedIndex();
						String pw = p.get(i);
						char[] pwChar = pass.getPassword();
						String pp = new String(pwChar);
						if(pw.equals(pp)) {
							pass.setText("");
							try {
								String ss = null;
								psmt = con.prepareStatement("insert into orderlist(cuisineNo, mealNo, memberNo, orderCount, amount, orderDate)"
										+ " values(?,?,?,?,?,?)");
								int rowCount = table.getRowCount();
								
								for(int ii=0; ii<rowCount; ii++) {
									Date nowdate = new Date();
									SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
									SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
									ss = date.format(nowdate) + " " + time.format(nowdate);
									Vector<String> v = rowData.get(ii);
									String order[] = {cuisineNo, v.get(0), id.get(i), v.get(2), v.get(3), ss};
									
									for(int i1=0; i1<order.length; i1++) {
										psmt.setString(i1+1, order[i1]);
									}
									psmt.executeUpdate();
									
									index = Integer.parseInt(v.get(0)) - 1;
									int count = Integer.parseInt(maxCount.get(index)) - Integer.parseInt(v.get(2));
									st = con.createStatement();
									st.executeUpdate("update meal set maxCount = '" + Integer.toString(count) + "' where mealNo = '" + v.get(0) + "'");
								}
								
								JOptionPane.showMessageDialog(null, new message(), "Message", JOptionPane.INFORMATION_MESSAGE);
//								dispose();
								new MealTicket(rowData, id.get(i), ss, cuisineNo);
							}
							catch(Exception e1) {
								
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "패스워드가 일치하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
							dispose();
							new Ticket();
							pass.setText("");
						}
					}
					else {
						pass.setText("");
					}
					break;
				case "취소":
					dispose();
					break;
			}
		}
		
		class message extends JPanel{
			JLabel lb[] = new JLabel[2];
			String s[] = {"결제가 완료되었습니다.", "식권을 출력합니다."};
			
			public message() {
				// TODO Auto-generated constructor stub
				setLayout(new GridLayout(2,1));
				for(int i=0; i<lb.length; i++) {
					lb[i] = new JLabel(s[i]);
					add(lb[i]);
				}
			}
		}
		
		class member extends JPanel{
			
			public member() {
				// TODO Auto-generated constructor stub
				try {
					setLayout(new GridLayout(2,2));
					st = con.createStatement();
					ResultSet rs = st.executeQuery("select memberNo, passwd from member");
					while(rs.next()) {
						id.add(rs.getString(1));
						p.add(rs.getString(2));
					}
					login = new JComboBox<String>(id);
					add(num); add(login);
					add(pw); add(pass);
				}
				catch(Exception e){
					
				}
			}
		}
	}
	
	public Payment(String s) {
		// TODO Auto-generated constructor stub
		setTitle("결제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		this.menu = s;
		this.type = new JLabel(menu);

		c.add(new NorthPanel(), BorderLayout.NORTH);
		c.add(new CenterPanel(), BorderLayout.CENTER);
		
		setSize(1000, 600);
		setVisible(true);
	}
}
package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DB.Connect;

public class Paycheck extends JFrame{

	private JTextField jt = new JTextField(10);
	private JButton btn[] = new JButton[4];
	private Vector<String> colData = new Vector<String>();
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	private DefaultTableModel model;
	private JTable table;
	private Connection con = Connect.makeConnection("meal");
	private Statement st = null;
	private PreparedStatement psmt = null;
	private String sql = "select orderlist.cuisineNo, mealName, memberName, orderCount, amount, orderDate from orderlist"
						+" join member on orderlist.memberNo = member.memberNo"
						+" join meal on orderlist.mealNo = meal.mealNo";
	
	class NorthPanel extends JPanel{
		
		JLabel label = new JLabel("메뉴명: ");
		String s[] = {"조회", "새로고침", "인쇄", "닫기"};
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			add(label);
			add(jt);
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
				case "조회":
					Check();
					break;
				case "새로고침":
					Settable(sql);
					jt.setText("");
					break;
				case "인쇄":
					Print();
					break;
				case "닫기":
					dispose();
					break;
			}
		}
	}
	public void Check(){
		String s = jt.getText();
		String sqll = sql.concat(" where mealName like '%" + s + "%'");
		Settable(sqll);
	}
	public void Print() {
		try {
			table.print(JTable.PrintMode.FIT_WIDTH,new MessageFormat("Header"),new MessageFormat("Footer")); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	class SouthPanel extends JPanel{
		
		String col[] = {"종류", "메뉴명", "사원명", "결제수량", "총결제금액", "결제일"};
		
		public SouthPanel() {
			// TODO Auto-generated constructor stub
			for(int i=0; i<col.length; i++) {
				colData.add(col[i]);
			}
			
			try {
				st = con.createStatement();
				Settable(sql);
				
			}
			catch(Exception e) {
				
			}
		}
	}
	public void Settable(String sql) {
			try {
				rowData.clear();
				ResultSet rs = st.executeQuery(sql);
				while(rs.next()) {
					Vector<String> v = new Vector<String>();
					for(int i=0; i<6; i++) {
						if(i == 0) {
							String s = rs.getString(i+1);
							switch(s) {
							case "1":
								s = "한식";
								break;
							case "2":
								s = "중식";
								break;
							case "3":
								s = "일식";
								break;
							case "4":
								s = "양식";
								break;
							}
							v.add(s);
						}
						else {
							v.add(rs.getString(i+1));
						}
					}
					rowData.add(v);
				}
				table.updateUI();
			}
			catch(Exception e) {
				
			}
	}
	
	public Paycheck() {
		// TODO Auto-generated constructor stub
		setTitle("결제 조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		c.add(new NorthPanel(), BorderLayout.NORTH);
		
		new SouthPanel();
		
		model = new DefaultTableModel(rowData, colData);
		table = new JTable(model);
		//테이블 가운데 정렬
		DefaultTableCellRenderer x = new DefaultTableCellRenderer();
		x.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(x);
		}
		c.add(new JScrollPane(table), BorderLayout.CENTER);
		
		setSize(600, 600);
		setVisible(true);
	}
}
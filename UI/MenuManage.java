package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DB.Connect;

public class MenuManage extends JFrame{
	
	private JComboBox<String> combo = new JComboBox<String>();
	private JButton btn[] = new JButton[5];
	private Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
	private Vector<String> colData = new Vector<String>();
	private DefaultTableModel model;
	private JTable table;
	private Connection con = Connect.makeConnection("meal");
	private Statement st = null;
	private PreparedStatement psmt = null;
	
	class NorthPanel extends JPanel{
		
		JLabel label = new JLabel("종류: ");
		String s[] = {"검색", "수정", "삭제", "오늘의메뉴 선정", "닫기"};
		String type[] = {"한식", "중식", "일식", "양식"};
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			add(label);
			combo = new JComboBox<String>(type);
			add(combo);
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
			case "검색":
				Search();
				break;
			case "수정":
				Update();
				Search();
				break;
			case "삭제":
				Delete();
				Search();
				break;
			case "오늘의메뉴 선정":
				Todaymeal();
				Search();
				break;
			case "닫기":
				dispose();
				break;
			}
		}
		
		//검색
		public void Search() {
			
			String s = combo.getSelectedItem().toString();
			String num = "0";
			
			switch(s) {
			case "한식":
				num = "1";
				break;
			case "중식":
				num = "2";
				break;
			case "일식":
				num = "3";
				break;
			case "양식":
				num = "4";
				break;
			}
			try {
				rowData.clear();
				st = con.createStatement();
				ResultSet rs = st.executeQuery("select mealName, price, maxCount, todayMeal from meal where cuisineNo = '" + num + "'");
				while(rs.next()) {
					Vector<Object> v = new Vector<Object>();
					v.add(false);
					for(int i=1; i<=4; i++) {
						if(i == 4) {
							//System.out.println(rs.getString(i));
							int a = rs.getInt(i);
							if(a == 1){
								v.add("Y");
							}
							else {
								v.add("N");
							}
						}
						else {
							v.add(rs.getString(i));
						}
					}
					rowData.add(v);
				}
				table.updateUI();
			}
			catch(Exception e) {
				
			}
		}
		//체크된 rowData의 인덱스
		public Vector<Integer> checkVector() {
			
			Vector<Integer> index = new Vector<Integer>();
			
			for(int i=0; i<rowData.size(); i++) {
				Vector<Object> v = rowData.get(i);
				if((boolean)v.get(0)) {
					index.add(i);
				}
			}
			
			return index;
		}
		//체크된 갯수
		public Integer checkIndex() {
			
			int num = 0;

			for(int i=0; i<rowData.size(); i++) {
				Vector<Object> v = rowData.get(i);
				if((boolean)v.get(0)) {
					num++;
				}
			}
			return num;
		}
		
		//수정
		public void Update() {
			
			int num = checkIndex();
			Vector<Integer> index = checkVector();
		
			if(num > 1) {
				JOptionPane.showMessageDialog(null, "하나씩 수정가능합니다.", "Message", JOptionPane.ERROR_MESSAGE);
			}
			
			else if(num == 0) {
				JOptionPane.showMessageDialog(null, "수정할 메뉴를 선택해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
			}
			else {
				int n = combo.getSelectedIndex();
				Vector<Object> v = rowData.get(index.get(0));
				new UpdateMenu(v, n);
			}		
		}
		
		//수정 눌렀을때 나오는 화면
		class UpdateMenu extends JFrame{
			
			private JLabel label[] = new JLabel[4];
			private JButton btn[] = new JButton[2];
			private JTextField jt = new JTextField();
			private JComboBox combo[] = new JComboBox[3]; 
			private Connection con = Connect.makeConnection("meal");
			private PreparedStatement psmt = null;
			private Vector<Object> menuName = new Vector<Object>();
			private int index = 0;
			
			public UpdateMenu(Vector<Object> v, int index) {
				// TODO Auto-generated constructor stub
				this.menuName = v;
				this.index = index;
				
				setTitle("메뉴 수정");
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
				combo[0].setSelectedIndex(index);
				//콤보1번 초기화
				Vector<String> price = new Vector<String>();
				for(int i=1000; i<=12000; i+=500) {
					price.add(Integer.toString(i));
				}
				combo[1] = new JComboBox<String>(price);
				combo[1].setSelectedItem(menuName.get(2));
				//콤보2번 초기화
				Vector<String> cookable = new Vector<String>();
				for(int i=0; i<=50; i++) {
					cookable.add(Integer.toString(i));
				}
				combo[2] = new JComboBox<String>(cookable);
				combo[2].setSelectedItem(menuName.get(3));
				int j=0;
				for(int i=0; i<4; i++) {
					add(label[i]);
					if(i == 1) {
						jt.setText((String)menuName.get(1));
						add(jt);
						continue;
					}
					else {
						add(combo[j]);
						j++;
					}
					
				}
				
				//버튼 초기화
				String submit[] = {"수정", "닫기"};
				for(int i=0; i<btn.length; i++) {
					btn[i] = new JButton(submit[i]);
					btn[i].addActionListener(new MyUpdateListener());
					add(btn[i]);
				}
				
				setSize(400, 300);
				setVisible(true);
			}

			class MyUpdateListener implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String s = e.getActionCommand();
					
					switch(s) {
						case "수정":
							String type = combo[0].getSelectedItem().toString();
							String new_type = null;
							String new_name = jt.getText();
							String new_price = combo[1].getSelectedItem().toString();
							String new_count = combo[2].getSelectedItem().toString();
							String old_name = (String)menuName.get(1);
							switch(type) {
							case "한식":
								new_type = "1";
								break;
							case "중식":
								new_type = "2";
								break;
							case "일식":
								new_type = "3";
								break;
							case "양식":
								new_type = "4";
								break;
							}
							
							//메뉴명 없을때
							if(new_name.equals("")) {
								JOptionPane.showMessageDialog(null, "메뉴명을 입력해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
							}
							
							//메뉴명 있을때
							else {
								try {
									String sql = "update meal set cuisineNo = '" + new_type + "', mealName = '" + new_name + "', price = '" + new_price + "', maxCount = '" + new_count + "' where mealName = '" + old_name + "'";
									st = con.createStatement();
									st.executeUpdate(sql);
									JOptionPane.showMessageDialog(null, "메뉴가 정상적으로 수정되었습니다." , "Message", JOptionPane.INFORMATION_MESSAGE);
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
		
		//삭제
		public void Delete() {
			
			int num = checkIndex();
			Vector<Integer> index = checkVector();
			
			try {
				for(int i=0; i<index.size(); i++) {
					Vector<Object> v = rowData.get(index.get(i));
					String name = (String)v.get(1);
					st = con.createStatement();
					String sql = "delete from meal where mealName = '" + name + "'";
					st.executeUpdate(sql);
				}
				table.updateUI();
			}
			catch(Exception e) {
				
			}
		}
		//오늘의 메뉴
		public void Todaymeal() {
			
			Vector<Integer> index = checkVector();
			
			if(index.size() > 25) {
				JOptionPane.showMessageDialog(null, "25개를 초과할 수 없습니다." , "Message", JOptionPane.ERROR_MESSAGE);
			}
			else {
				for(int i=0; i<rowData.size(); i++) {
					Vector<Object> v = rowData.get(i);
					String menu = (String)v.get(1);
					if(index.contains(i)) {//체크O
						try {
							st = con.createStatement();
							String sql = "update meal set todayMeal = '1' where mealName = '" + menu + "'";
							st.executeUpdate(sql);
						}
						catch(Exception e) {
							
						}
					}
					else {
						try {//체크x
							st = con.createStatement();
							String sql = "update meal set todayMeal = '0' where mealName = '" + menu + "'";
							st.executeUpdate(sql);
						}
						catch(Exception e) {
							
						}
					}
				}
			}
		}
	}

	class SouthPanel extends JPanel{
		
		String s[] = {"□", "menuName", "price", "maxCount", "todayMeal"};
		public SouthPanel() {
			// TODO Auto-generated constructor stub
				for(int i=0; i<s.length; i++) {
					colData.add(s[i]);
				}
				model = new DefaultTableModel(rowData, colData) {
					public java.lang.Class<?> getColumnClass(int columnIndex) {
						switch(columnIndex) {
							case 0:
								return Boolean.class;
							default:
								return String.class;
						}
					};
				};
				table = new JTable(model);
				//테이블 가운데 정렬
				DefaultTableCellRenderer x = new DefaultTableCellRenderer();
				x.setHorizontalAlignment(SwingConstants.CENTER);
				TableColumnModel tcmSchedule = table.getColumnModel();
				for (int i = 1; i < tcmSchedule.getColumnCount(); i++) {
					tcmSchedule.getColumn(i).setCellRenderer(x);
				}
			}		
	}
	public MenuManage() {
		// TODO Auto-generated constructor stub
		setTitle("메뉴 관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		c.add(new NorthPanel(), BorderLayout.NORTH);
		new SouthPanel();
		c.add(new JScrollPane(table), BorderLayout.CENTER);
		
		setSize(600, 600);
		setVisible(true);
	}
}
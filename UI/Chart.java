package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DB.Connect;

public class Chart extends JFrame{

	private JButton btn[] = new JButton[2];
	private JLabel label = new JLabel("종류별 결제건수 통계차트");
	private Connection con = Connect.makeConnection("meal");
	private Statement st = null;
	private JLabel type_label[] = new JLabel[4];
	private double type_num[] = new double[4];
	private Color color[] = new Color[4];
	private int type_num_index = 0;
	
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = e.getActionCommand();
			
			switch(s) {
				case "차트이미지저장":
					saveImage();
					break;
				case "닫기":
					dispose();
					break;
			}
		}
	}
	
	public void saveImage() {//JFrame 이미지로 저장
		// TODO Auto-generated constructor stub
		Date nowdate = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat time = new SimpleDateFormat("HHmmss");
		String name = date.format(nowdate)+ time.format(nowdate) + ".jpg";
		BufferedImage image = new BufferedImage(650, 500, BufferedImage.TYPE_INT_RGB);
		Graphics g2 = image.createGraphics();
		paint(g2);
		try {
			ImageIO.write(image, "jpg", new File(name));
		}
		catch(Exception e){
			
		}
	}
	class Panel extends JPanel{
		
		public Panel() {
			// TODO Auto-generated constructor stub
			setLayout(null);
			//버튼 초기화
			String btn_s[] = {"차트이미지저장", "닫기"};
			for(int i=0; i<btn.length; i++) {
				btn[i] = new JButton(btn_s[i]);
				btn[i].addActionListener(new MyActionListener());
			}
			btn[0].setBounds(400, 20, 150, 30);
			btn[1].setBounds(560, 20, 60, 30);
			add(btn[0]); add(btn[1]);
			
			
			label.setBounds(150, 40, 150, 30);
			add(label);
			
			//오른쪽 라벨 초기화
			String type[] = {"한식", "중식", "일식", "양식"};
			int y = 180;
			for(int i=0; i<type_label.length; i++) {
				String num = Integer.toString(i+1);
				type_label[i] = new JLabel(type[i] + " (" + Gettype(num) + ")");
				type_label[i].setBounds(500, y, 100, 30);
				add(type_label[i]);
				y += 20;
			}
		}
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			int y = 190;
			
			double num = 0;
			double percent_100[] = new double[4];
			double percent_360[] = new double[4];
			
			for(int i=0; i<type_num.length; i++) {
				num += type_num[i];
				System.out.println(type_num[i]);
			}
			
			for(int i=0; i<percent_100.length; i++) {
				percent_100[i] = Math.ceil((type_num[i]/num) * 100);
			}
			
			for(int i=0; i<percent_360.length; i++) {
				percent_360[i] = Math.ceil((360*percent_100[i])/100);
			}
			int x = 0;
			for(int i=0; i<color.length; i++) {
				int r = (int)(Math.random()*256);
				int g1 = (int)(Math.random()*256);
				int b = (int)(Math.random()*256);
				color[i] = new Color(r, g1, b);
				g.setColor(color[i]);
				g.fillRect(480, y, 10, 10);
				g.fillArc(60, 100, 300, 300, x, (int)percent_360[i]);
				x += (int)percent_360[i];
				y += 20;
			}

		}
	}
	
	public String Gettype(String typenum) {
		String num = "0";
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select count(*) from orderlist where cuisineNo = '" + typenum + "'");
			int i = 0;
			while(rs.next()) {
				num = rs.getString(1);
				type_num[type_num_index] = Integer.parseInt(num);
				type_num_index++;
			}
		}
		catch(Exception e){
			
		}
		return num;
	}
	
	public Chart() {
		// TODO Auto-generated constructor stub
		setTitle("종류별 결제현황차트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new Panel());
		
		setSize(650, 500);
		setVisible(true);
	}
}
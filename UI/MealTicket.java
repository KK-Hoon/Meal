package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class MealTicket extends JFrame{
	
	private Vector<Vector<String>> Data = new Vector<Vector<String>>();//테이블 정보
	private String id;//사용자 id
	private String date;//날짜
	private String cuisineNo;
	private int n = 0;
	private Vector<Ticket> tv = new Vector<Ticket>();
	
	class Ticket extends JPanel{
		
		int index, amount;
		String color;
		String num;
		Vector<String> meal = new Vector<String>();
		
		public Ticket(String color, int index, int amount, Vector<String> meal, String num) {
			// TODO Auto-generated constructor stub
			this.color = color;//색상
			this.index = index;//인덱스
			this.amount = amount;//총량
			this.meal = meal;//벡터
			this.num = num;//일련번호
			Border blackline = BorderFactory.createLineBorder(Color.black);
			
			DecimalFormat df = new DecimalFormat("###,###");
			String money = df.format(Integer.parseInt(meal.get(3))/amount);
			
			String menu = meal.get(1);
			
			setLayout(new BorderLayout());
			setBorder(blackline);
			
			JLabel numlabel = new JLabel(num);
			numlabel.setFont(new Font("고딕", Font.BOLD, 15));
			add(numlabel, BorderLayout.NORTH);
			
			if(color == "blue") {
				setBackground(Color.cyan);
				add(new Ticket_Center(money, "blue"), BorderLayout.CENTER);
			}
			else {
				setBackground(Color.pink);
				add(new Ticket_Center(money, "red"), BorderLayout.CENTER);
			}
			
			add(new Ticket_South(color, menu, index, amount), BorderLayout.SOUTH);
		}
	}
	class Ticket_South extends JPanel{
		
		String color;
		String menu; 
		int index; 
		int amount;
		
		public Ticket_South(String color, String menu, int index, int amount) {
			// TODO Auto-generated constructor stub
			this.color = color;
			this.menu = menu;
			this.index = index;
			this.amount = amount;
			
			setLayout(new GridLayout(1, 2));
			if(color == "blue") {
				setBackground(Color.cyan);
			}
			else {
				setBackground(Color.pink);
			}
			
			JLabel menulabel = new JLabel("메뉴: " + menu);
			menulabel.setHorizontalAlignment(JLabel.LEFT);
			menulabel.setFont(new Font("고딕", Font.BOLD, 15));
			add(menulabel, BorderLayout.SOUTH);
			
			JLabel n = new JLabel(index + "/" + amount);
			n.setHorizontalAlignment(JLabel.RIGHT);
			n.setFont(new Font("고딕", Font.BOLD, 15));
			add(n, BorderLayout.SOUTH);
		}
	}
	class Ticket_Center extends JPanel{
		
		String menu;
		String color;
		
		public Ticket_Center(String money, String color) {
			// TODO Auto-generated constructor stub
			this.menu = menu;
			this.color = color;
			setLayout(new GridLayout(2, 1));
			
			if(color == "blue") {
				setBackground(Color.cyan);
			}
			else {
				setBackground(Color.pink);
			}
			JLabel label = new JLabel("식 권");
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setFont(new Font("고딕", Font.BOLD, 40));
			add(label);
			
			JLabel m = new JLabel(money + "원");
			m.setHorizontalAlignment(JLabel.CENTER);
			m.setFont(new Font("고딕", Font.PLAIN, 40));
			add(m);
		}
	}
	class SubPanel extends JPanel{
		
		public SubPanel() {
			// TODO Auto-generated constructor stub
			setBackground(Color.white);
			
			for(int ii=0; ii<tv.size(); ii++) {
				add(tv.get(ii));
			}
		}
	}
	
	public void saveImage(String name, int width, int height) {//JFrame 이미지로 저장
		// TODO Auto-generated constructor stub
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g2 = image.createGraphics();
		paint(g2);
		try {
			ImageIO.write(image, "jpg", new File(name+"-ticket.jpg"));
		}
		catch(Exception e){
			
		}
	}
	
	public MealTicket(Vector<Vector<String>> Data, String id, String ss, String cuisineNo) {
		// TODO Auto-generated constructor stub
		this.Data = Data;
		this.id = id;
		this.date = ss.replace(" ", "").replace("-", "").replace(":", "");
		this.cuisineNo = cuisineNo;
		
		setTitle("식권");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		for(int i=0; i<Data.size(); i++) {
			Vector<String> v = Data.get(i);
			n += Integer.parseInt(v.get(2));
		}
		
		String num = date + "-" + id + "-" + cuisineNo;
		
		for(int i=1; i<=Data.size(); i++) {
			Vector<String> v = Data.get(i-1);//상품번호, 품명, 수량 , 금액
			int amount = Integer.parseInt(v.get(2));
			
			for(int ii=1; ii<=amount; ii++) {
				if(i%2 == 1) {
					tv.add(new Ticket("blue", ii, amount, v, num));// 색상/ 인덱스 / 주문량 / 벡터 / 일련번호
					
				}
				else {
					tv.add(new Ticket("red", ii, amount, v, num));
				}
				
			}
		}
		
		JPanel panel = new JPanel();
		SubPanel sub = new SubPanel();
		
		sub.setLayout(new GridLayout(n, 1, 20, 20));
		
		panel.setBackground(Color.white);
		panel.add(sub);
		
		c.add(new JScrollPane(panel));
		
		setSize(300, n*150);
		setVisible(true);
		
		saveImage(num, this.getWidth(), this.getHeight());
	}
}
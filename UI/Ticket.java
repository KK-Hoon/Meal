package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Ticket extends JFrame{
	
	private JButton btn[] = new JButton[4];
	private JLabel label = new JLabel("식권 발매 프로그램");
	private JTabbedPane pane  = new JTabbedPane();
	private ImageIcon icon[] = {new ImageIcon("C:\\Users\\rlaru\\OneDrive\\바탕 화면\\meal\\Meal\\DataFiles\\menu_1.png"),
								new ImageIcon("C:\\Users\\rlaru\\OneDrive\\바탕 화면\\meal\\Meal\\DataFiles\\menu_2.png"),
								new ImageIcon("C:\\Users\\rlaru\\OneDrive\\바탕 화면\\meal\\Meal\\DataFiles\\menu_3.png"),
								new ImageIcon("C:\\Users\\rlaru\\OneDrive\\바탕 화면\\meal\\Meal\\DataFiles\\menu_4.png")};
	private JLabel time = new JLabel();
	
	class NorthPanel extends JPanel{
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			label.setFont(new Font("고딕", Font.PLAIN, 20));
			add(label);
		}
	}

	class CenterPanel extends JPanel{
		
		String s[] = {"한식", "중식", "일식", "양식"};
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(2,2));
			
			for(int i=0; i<btn.length; i++) {
				btn[i] = new JButton(icon[i]);
				btn[i].setToolTipText(s[i]);
				btn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JButton b = (JButton)e.getSource();
						String s = b.getToolTipText();
						new Payment(s);
					}
				});
				add(btn[i]);
			}
		}
	}
	class Time extends Thread{
		
		private JLabel timelabel = new JLabel();
		
		public Time(JLabel timelabel) {
			// TODO Auto-generated constructor stub
			this.timelabel = timelabel;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				Date nowdate = new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일");
				SimpleDateFormat time = new SimpleDateFormat("HH시 mm분 ss초");
				String ss ="현재시간 : " + date.format(nowdate) + " " + time.format(nowdate);
				
				try {
					timelabel.setText(ss);
					Thread.sleep(1000);					
				}
				catch(InterruptedException e) {
					return;
				}
			}
		}
	}
	class SouthPanel extends JPanel{
		public SouthPanel() {
			// TODO Auto-generated constructor stub
			setBackground(Color.black);
			time.setForeground(Color.cyan);
			time.setFont(new Font("고딕", Font.PLAIN, 20));
			add(time);
			Time timer = new Time(time);
			timer.start();
		}
	}
	public Ticket() {
		// TODO Auto-generated constructor stub
		setTitle("식권 발매 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = getContentPane();
		
		ct.add(new NorthPanel(), BorderLayout.NORTH);
		
		pane.addTab("메뉴", new CenterPanel());
		ct.add(pane, BorderLayout.CENTER);
		
		ct.add(new SouthPanel(), BorderLayout.SOUTH);
		
		setVisible(true);
		setSize(600, 900);
		
		
	}
}
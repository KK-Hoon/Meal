package DB;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InsertTable {

	private Connection con = Connect.makeConnection("meal");
	private PreparedStatement psmt = null;
	
	String insert[] = {"Insert into member values(?,?,?)",
					   "Insert into cuisine values(?,?)",
					   "Insert into meal values(?,?,?,?,?,?)",
					   "Insert into orderlist values(?,?,?,?,?,?,?)"};
	
	String s[] = {"member", "cuisine", "meal", "orderlist"};
	
	public InsertTable() {
		// TODO Auto-generated constructor stub
		try {
			for(int i=0; i<s.length; i++) {
				psmt = con.prepareStatement(insert[i]);
				Scanner fscanner = new Scanner(new FileInputStream("DataFiles\\" + s[i] + ".txt"));
				fscanner.nextLine();
				while(fscanner.hasNext()) {
					StringTokenizer st = new StringTokenizer(fscanner.nextLine(), "\t");
					int n = 1;
					
					while(st.hasMoreTokens()) {
						psmt.setString(n++, st.nextToken());
					}
					psmt.executeUpdate();
					n = 0;
				}
				
			}
			System.out.println("테이블 추가 완료");
		}
		catch(Exception e) {
			
		}
	}
	
}
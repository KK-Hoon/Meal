package DB;
import java.sql.*;

public class Connect {

	public static Connection makeConnection(String s){
		String id = "root";
		String pass = "root";
		String url = null;
		if(s == null) {
			url = "jdbc:mysql://localhost";
		}
		else {
			url = "jdbc:mysql://localhost/" + s;
		}
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url,id,pass);
			System.out.println("데이터베이스 연결 성공");
		}
		catch(Exception e) {
			System.out.println("데이터베이스 연결 실패!");
		}
		return con;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = makeConnection(null);
	}

}
package DB;
import java.sql.*;

public class CreateMeal {
	
	private Connection con = null;
	private Statement st = null;
	
	public CreateMeal() {
		// TODO Auto-generated constructor stub
		new mealDB();
		new createTable();
		new InsertTable();
	}
	
	class mealDB{
		String create = "create database if not exists meal";
		String drop = "drop database if exists meal";
		
		public mealDB() {
			// TODO Auto-generated constructor stub
			con = Connect.makeConnection(null);
			try {
				st = con.createStatement();
				st.executeUpdate(drop);
				st.executeUpdate(create);
			}
			catch(Exception e) {
				System.out.println("DB 생성 실패!");
			}
		}
	}
	
	class createTable{
		String memberNo = "create table member("
				+ "memberNo int primary key not null auto_increment,"
				+ "memberName varchar(20),"
				+ "passwd varchar(4))";
		
		String cuisine = "create table cuisine("
				+ "cuisineNo int primary key not null auto_increment,"
				+ "cuisineName varchar(10))";
		
		String meal = "create table meal("
				+ "mealNo int primary key not null auto_increment,"
				+ "cuisineNo int,"
				+ "mealName varchar(20),"
				+ "price int,"
				+ "maxCount int ,"
				+ "todayMeal tinyint(1))";
		
		String orderlist = "create table orderlist("
				+ "orderNo int primary key not null auto_increment,"
				+ "cuisineNo int,"
				+ "mealNo int,"
				+ "memberNo int,"
				+ "orderCount int,"
				+ "amount int,"
				+ "orderDate datetime)";
		
		public createTable() {
			// TODO Auto-generated constructor stub
			con = Connect.makeConnection("meal");
			try {
				st = con.createStatement();
				st.executeUpdate(memberNo);
				st.executeUpdate(cuisine);
				st.executeUpdate(meal);
				st.executeUpdate(orderlist);
				System.out.println("테이블 생성 완료");
			}
			catch(Exception e) {
				
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CreateMeal();

	}

}
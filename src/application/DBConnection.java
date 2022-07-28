package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public Connection dbConn;
	
	public Connection getConnection() {
		
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String userid="SUNGIL";
		String password="sungil";
		
		try {
			Class.forName(driver);
			dbConn = DriverManager.getConnection(url, userid, password);
			System.out.println("디비 접속 성공했어요");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("디비 접속 실패했어요");
		}
		
		return dbConn;
		
	}
	
	
}

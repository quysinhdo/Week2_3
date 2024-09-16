package vn.iotstar.configs;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectSQL {
	public static final String serverName = "DESKTOP-IJ7RR4F\\QUYSINH";
	public static String dbName = "ltwebct4";
	public static String portNumber = "1433";
	public static String instance = "";
	public static String userID = "sa";
	public static String password = "123456";

	// Kết nối SQL Server với SQL Authentication
	public Connection getConnection() throws Exception {
		String url = "jdbc:sqlserver://" + serverName + "\\" + instance + ":" + portNumber + ";databaseName=" + dbName;
		if (instance == null || instance.trim().isEmpty())
			url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url, userID, password);
	}

	public static void main(String[] args) {
		try {
			System.out.println(new DBConnectSQL().getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
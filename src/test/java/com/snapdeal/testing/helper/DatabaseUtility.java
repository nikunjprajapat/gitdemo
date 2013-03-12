package com.snapdeal.testing.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.snapdeal.testing.config.ShippingConfig;

public class DatabaseUtility {

	public static String executeQuery(String query) throws Exception
	{
		return executeQuery(query, "dev");
	}



	public static String executeQuery(String query, String environment) throws Exception
	{
		ShippingConfig config = ShippingConfig.getInstance();
		String userName= config.getConfig(environment+".DataBase.UserName");
		String passWord=config.getConfig(environment+".DataBase.Password");
		String dbEndpoint=config.getConfig(environment+".DataBase.EndPoint");
		String dbName=config.getConfig(environment+".DataBase.DBName");

		System.out.println("username= "+userName);
		System.out.println("pass= "+passWord);
		System.out.println("end= "+dbEndpoint);
		System.out.println("db= "+dbName);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySql JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return null;
		}

		Connection connection = null;
		try {


			String connString= "jdbc:mysql://"+dbEndpoint+"/"+dbName;
			System.out.println("connection string..."+connString);
			connection = DriverManager.getConnection(connString, userName, passWord);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		String result=null;
		if(rs.next())
			result= rs.getString(1);

		rs.close();
		st.close() ;
		connection.close();
		return result;
	}



	public static ArrayList<String> getList(String query, String environment) throws Exception
	{
		ShippingConfig config = ShippingConfig.getInstance();
		String userName= config.getConfig(environment+".DataBase.UserName");
		String passWord=config.getConfig(environment+".DataBase.Password");
		String dbEndpoint=config.getConfig(environment+".DataBase.EndPoint");
		String dbName=config.getConfig(environment+".DataBase.DBName");

		System.out.println("\n----------DATABASE ENDPOINTS-------------");
		System.out.println("username= "+userName);
		System.out.println("pass= "+passWord);
		System.out.println("end= "+dbEndpoint);
		System.out.println("db= "+dbName);

		System.out.println("-------------------------------------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySql JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return null;
		}

		Connection connection = null;
		try {


			String connString= "jdbc:mysql://"+dbEndpoint+"/"+dbName;
			System.out.println("connstring...."+connString);
			connection = DriverManager.getConnection(connString, userName, passWord);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		ArrayList<String> arrayList = new ArrayList<String>();
		while(rs.next( )){  
			arrayList.add(rs.getString(1));  
		}  

		rs.close();
		st.close() ;
		connection.close();
		return arrayList;
	}



	public static ArrayList<ArrayList<String>> getRecords(String query, String environment) throws Exception
	{
		ShippingConfig config = ShippingConfig.getInstance();
		String userName= config.getConfig(environment+".DataBase.UserName");
		String passWord=config.getConfig(environment+".DataBase.Password");
		String dbEndpoint=config.getConfig(environment+".DataBase.EndPoint");
		String dbName=config.getConfig(environment+".DataBase.DBName");
		//String userName="chhavi.suri";
		//String passWord="shipping";

		System.out.println("\n----------DATABASE ENDPOINTS-------------");
		System.out.println("username= "+userName);
		System.out.println("pass= "+passWord);
		System.out.println("end= "+dbEndpoint);
		System.out.println("db= "+dbName);

		System.out.println("-------------------------------------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySql JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return null;
		}

		Connection connection = null;
		try {


			String connString= "jdbc:mysql://"+dbEndpoint+"/"+dbName;
			//String connString= "jdbc:mysql://"+"10.130.27.194:3308"+"/"+"shipping";
			//String connString= "jdbc:mysql://"+"127.0.0.1:3307"+"/"+"snapdeal";
			System.out.println("The connstring..."+connString);
			connection= DriverManager.getConnection(connString, userName, passWord);
			

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);




		ArrayList<ArrayList<String>> listMatrix = new ArrayList<ArrayList<String>>();



		while(rs.next( )){  

			ArrayList<String> arrayList = new ArrayList<String>();	
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				arrayList.add(rs.getString(i));
			}
			listMatrix.add(arrayList);
		}  



		rs.close();
		st.close() ;
		connection.close();
		return listMatrix;
	}




	public static void main(String[] args) throws Exception {

		
		ArrayList<ArrayList<String>> rs = getRecords("select id,code,name from shipping_group where enabled=1", "dev");
		
		for (int i = 0; i < rs.size(); i++) {
			ArrayList<String> list= rs.get(i);
			for (int j = 0; j < list.size(); j++) {
				System.out.println(list.get(j));
			}
		}
		
	}


}

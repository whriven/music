package com.java2.BaseDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BaseDao {
	public static final String DRIVER="com.mysql.jdbc.Driver";
	  public static final String URL="jdbc:mysql://127.0.0.1:3306/music";
	  public static Connection getConnection(){
		  Connection conn=null;
		  try {
			Class.forName(DRIVER);
			  conn=DriverManager.getConnection(URL,"root","root");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return conn;
	  }
	  public static void release(Connection conn,PreparedStatement pst,ResultSet rs){
		  try {
			if(conn!=null){
				  conn.close();
				  conn=null;
			  }
			  if(pst!=null){
				  pst.close();
				  pst=null;
			  }
			  if(rs!=null){
				  rs.close();
				  rs=null;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}

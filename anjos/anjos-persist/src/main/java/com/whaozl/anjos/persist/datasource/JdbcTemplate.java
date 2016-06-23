package com.whaozl.anjos.persist.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class JdbcTemplate {
	private static ComboPooledDataSource dataSource;
	
	static{
		dataSource = new ComboPooledDataSource();
	}
	
	public static ComboPooledDataSource getDataSource() {
		return dataSource;
	}
	
	public static Connection getMySqlConnection() throws SQLException{
		return  dataSource.getConnection();
	}
	
	public static void close(Connection conn) throws SQLException{
		if(conn!=null){
			conn.close();
		}
	}
	
	public static void close(PreparedStatement pstmt) throws SQLException {
		if(pstmt!=null){
			pstmt.close();
		}
	}
	
	public static void close(ResultSet rs) throws SQLException {
		if(rs!=null){
			rs.close();
		}
	}
	
	public static  Boolean isEmpty(String str){
		Boolean flag=false;
		if(str==null || str.equals("")||str.length()<=0) {
			flag=true;
		}
		return flag;
	}
	
	public static boolean isLowerCase(String word){
	  for(int i = 0; i < word.length(); i++){
	   char c = word.charAt(i);
	   if (!Character.isLowerCase(c)) return false;
	  }
	  return true;
	 }
}




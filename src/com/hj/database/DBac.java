package com.hj.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBac {
	private Connection dbConn = null;
	private PreparedStatement perstmt=null;
	private ResultSet rs=null;
	private String driverName=null;
	private String dbURL=null;
	private String userName=null;
	private String userPwd=null;
	public DBac(String dbURL, String userName, String userPwd) {
		this.dbURL = dbURL;
		this.userName = userName;
		this.userPwd = userPwd;
		if(this.dbURL.contains("sqlserver"))
			this.driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		else if(this.dbURL.contains("oracle"))
			this.driverName = "oracle.jdbc.driver.OracleDriver";
		else if(this.dbURL.contains("mysql"))
			this.driverName = "org.gjt.mm.mysql.Driver";
		else if(this.dbURL.contains("sqlite"))
			this.driverName = "org.sqlite.JDBC";
	}
	public boolean excute(String sqlbody,String[] parameter) {
		// TODO Auto-generated constructor stub
		try {
			Class.forName(this.driverName);
			System.out.println("Connection started");
			if(this.dbURL.contains("sqlite"))
				this.dbConn=DriverManager.getConnection(this.dbURL);
			else
				this.dbConn=DriverManager.getConnection(this.dbURL,this.userName,this.userPwd);
		    System.out.println("Connection succeeded");
		    this.perstmt = dbConn.prepareStatement(sqlbody);
		    for(int i=0;parameter!=null&&i<parameter.length;i++)
		    	this.perstmt.setString(i+1, parameter[i].trim());
		    if(sqlbody.split(" ")[0].equals("select"))
		    	this.rs = perstmt.executeQuery();
		    else
		    {
		    	perstmt.executeUpdate();
		    	if(!this.dbConn.getAutoCommit())
		    		this.dbConn.commit();
			    this.closeAll();
		    }
		    return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Connection failed");
			e.printStackTrace();
		}
		return false;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void closeAll(){
		try {
			if(this.rs!=null)
				this.rs.close();
			if(this.perstmt!=null)
				this.perstmt.close();
			if(this.dbConn!=null)
				this.dbConn.close();
			System.out.println("Connection ended");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection ending failed");
			e.printStackTrace();
		}
	}
}

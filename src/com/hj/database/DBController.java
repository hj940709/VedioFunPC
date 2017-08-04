package com.hj.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBController {
	private DBac accessor = null;
//	private String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=VSMS";
//	private String dbURL="jdbc:mysql://localhost:3306/";
//	private String dbURL="jdbc:oracle:thin:@localhost:1521:XE";
//	private String dbURL="jdbc:sqlite:" + 
//			this.getClass()
//			.getClassLoader()
//			.getResource("")
//			.getFile()
//			.substring(1) + "SqliteDatabase.db";
	//%project%/bin/SqliteDatabase.db
	//jdbc:sqlite::memory: in memory
	//jdbc:sqlite://home//jxhou//VedioStorage.db
	private String dbURL="jdbc:sqlite://C://Users//hj940//OneDrive//VedioStorage.db";
	private String userName="VSMSDBA";
	private String userPwd="vsms";
	public DBController() {
		// TODO Auto-generated constructor stub
		this.accessor = new DBac(this.dbURL,this.userName,this.userPwd);
	}
	
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public boolean insert(String table,String[] item,String[] parameter){
		if(item.length!=parameter.length)
			return false;
		String sqlbody = "insert into "+table+" ";
		if(item!=null)
		{
			sqlbody += "(";
			sqlbody = this.assemble(sqlbody, item.length,false, ",",item);
			sqlbody += ")";
		}
		sqlbody +=" values(";
		sqlbody = this.assemble(sqlbody, parameter.length,false, ",",null);
		sqlbody += ");";
		return this.accessor.excute(sqlbody, parameter);
	}
	public boolean update(String table,String[] item,
							String[] parameter,String[] target){
		if(item.length+target.length!=parameter.length)
			return false;
		String sqlbody = "update "+table+" set ";
		sqlbody = this.assemble(sqlbody, item.length,true, ",",item);
		if(target!=null)
		{
			sqlbody +=" where ";
			sqlbody = this.assemble(sqlbody, target.length,true, " and ", target);
		}
		sqlbody += ";";
		return this.accessor.excute(sqlbody, parameter);
	}
	public boolean delete(String table,String[] target,String[] parameter){
		if(target.length!=parameter.length)
			return false;
		String sqlbody="delete from "+table;
		if(target!=null)
		{
			sqlbody +=" where ";
			sqlbody = this.assemble(sqlbody, target.length,true, " and ", target);
			sqlbody +=";";
		}
		return this.accessor.excute(sqlbody, parameter);
	}
	public ArrayList<Map<String, String>> select(String table,String[] item,
						String[] target,String[] parameter){
		if(null!=target&&null!=parameter&&target.length!=parameter.length)
			return null;
		ArrayList<Map<String,String>> list = null;
		String sqlbody = "select ";
		if(item!=null)
			sqlbody = this.assemble(sqlbody, item.length,false, ",", item);
		else
			sqlbody += "* ";
		sqlbody += " from "+table;
		if(target!=null)
		{
			sqlbody += " where ";
			sqlbody = this.assemble(sqlbody, target.length,true, " and ", target);
		}
		sqlbody +=";";
		this.accessor.excute(sqlbody, parameter);
		ResultSet rs = this.accessor.getRs();
		try {
			list = new ArrayList<Map<String,String>>();
			ResultSetMetaData rsmd =rs.getMetaData();			
			while(rs.next())
			{
				Map<String,String> map = new HashMap<String,String>();
				if(item!=null)
					for(String str:item)
						map.put(str, rs.getString(str));
				else
				{
					for(int i=0;i<rsmd.getColumnCount();i++)
					{
						String label = rsmd.getColumnLabel(i+1);
						map.put(label, rs.getString(label));
					}
				}
				list.add(map);
			}
			if(list.isEmpty()) list = null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.accessor.closeAll();
		}
		return list;
	}
	public ArrayList<Map<String,String>> selectRaw(String sqlbody,String[] item){
		ArrayList<Map<String,String>> list=null;
		this.accessor.excute(sqlbody, null);
		ResultSet rs = this.accessor.getRs();
		try {
		list = new ArrayList<Map<String,String>>();
		ResultSetMetaData rsmd =rs.getMetaData();			
		while(rs.next())
		{
			Map<String,String> map = new HashMap<String,String>();
			if(item!=null)
				for(String str:item)
					map.put(str, rs.getString(str));
			else
			{
				for(int i=0;i<rsmd.getColumnCount();i++)
				{
					String label = rsmd.getColumnLabel(i+1);
					map.put(label, rs.getString(label));
				}
			}
			list.add(map);
		}
		if(list.isEmpty()) list = null;
		
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		finally{
		this.accessor.closeAll();
		}
		return list;
	}
	
	
	private String assemble(String body,int times,boolean isTarget,String connector,String[] item){
		String result = body;
		for(int i=0;i<times;i++)
		{
			if(item!=null)
				if(item[i]!=null&&!item[i].isEmpty())
					result += item[i];
			else
				result += "?";
			if(isTarget)
				result +="=?";
			if(i!=times-1)
				result += connector;
		}
		return result;
	}
}

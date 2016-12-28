package com.hj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.hj.database.DBController;

public class VedioController {
	public static void update(ArrayList<Vedio> list){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Map<String,String>> old_list = new DBController()
				.select("vediolist", new String[]{"id","version"}, null, null);
		try{
			for(Vedio vedio: list)
			{
				Date new_date = df.parse(vedio.getVersion());
				Iterator<Map<String, String>> i = old_list.iterator();
				String old_date_str="";
				while(i.hasNext())
				{
					Map<String,String> map = (Map<String, String>) i.next();
					if(map.get("id").equals(vedio.getId()))
					{
						old_date_str = map.get("version");
						break;
					}
				}
				Date old_date = df.parse(old_date_str);
				if(old_date.getTime()<new_date.getTime())
					new DBController().update("vediolist", Vedio.getItemList(), 
		        			vedio.getParameterList(),new String[]{"id"});
			}
		}catch(Exception e){
			return;
		}
	}
}

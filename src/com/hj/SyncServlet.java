package com.hj;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hj.database.DBController;

/**
 * Servlet implementation class SyncServlet
 */
@WebServlet("/SyncServlet")
public class SyncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SyncServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Vedio> list = new ArrayList<Vedio>();
		for(Map<String, String> map: new DBController().selectRaw("select * from vediolist where freq>=0", Vedio.getFullItemList()))
        	list.add(new Vedio(map));
		String reply = new Gson().toJson(list);
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json; charset=utf-8");
        reply = URLEncoder.encode(reply, "UTF-8");
        writer.print(reply);
        writer.flush();
        writer.close();
        System.out.println("Sended");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Received");
		Gson gson = new Gson();
		String jstring = request.getParameter("vediolist");
		ArrayList<Vedio> list;
		if(null!=jstring&&!"".equals(jstring))
		{
			jstring = URLDecoder.decode(jstring,"UTF-8");
			list = new ArrayList<Vedio>();
			JsonParser parser = new JsonParser();
	        JsonArray jarray = parser.parse(jstring).getAsJsonArray();
	        for(JsonElement json : jarray)
	        	list.add(gson.fromJson(json, Vedio.class));
	        VedioController.update(list);
		}
        list = new ArrayList<Vedio>();
        for(Map<String, String> map: new DBController().selectRaw("select * from vediolist where freq>=0", Vedio.getFullItemList()))
        	list.add(new Vedio(map));
        String reply = gson.toJson(list);
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json; charset=utf-8");
        reply = URLEncoder.encode(reply, "UTF-8");
        writer.print(reply);
        writer.flush();
        writer.close();
        System.out.println("Sended");
	}

	
}

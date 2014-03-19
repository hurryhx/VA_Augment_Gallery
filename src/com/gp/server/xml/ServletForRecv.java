/*
 * name: ServletForRecv.java
 * To receive the comments and add to database.
 */
package com.gp.server.xml;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.server.domain.Info;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForRecv")
public class ServletForRecv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	public ServletForRecv(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		List<Info> infor= new ArrayList<Info>();
		String title = "comment";//request.getParameter("title");
		String comment = request.getParameter("comment");
		String userid = request.getParameter("userid");
		String picid = request.getParameter("picid");
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/3-8.db");
			Statement stat = conn.createStatement();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stat.executeUpdate("insert into comments values(NULL, '" + title + "','" + comment + "','"+df.format(new Date())+"',0,"+picid+","+userid+","+x+","+y+");");
			stat.executeUpdate("insert into commentasso values(NULL, "+userid+","+picid+");");
			System.out.println("Recv comments");
			stat.close();
			conn.close(); //结束数据库的连接 
			}
		catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
			
		}
		request.setAttribute("infor", infor);
		request.getRequestDispatcher("/WEB-INF/page/info.jsp").
		forward (request,response);
	}
}
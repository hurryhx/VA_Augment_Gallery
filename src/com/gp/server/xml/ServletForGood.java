/*
 * name: ServletForGood.java
 * To response the "Great!" request and report the users' inclination.
 */
package com.gp.server.xml;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.server.domain.Info;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForGood")
public class ServletForGood extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	public ServletForGood(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("picid");
		String userid = request.getParameter("userid");
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/3-8.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from pictures where id="+ id +";");
			int tmp = rs.getInt("good");
			rs = stat.executeQuery("select * from association where picid="+id+" and userid="+userid+";");
			if (!rs.next()){
			String rss = Integer.toString(tmp+1);
			stat.executeUpdate("update pictures SET good="+ rss + " where id=" + id + ";");
			//rs = stat.executeQuery("select * from users where id="+userid+";");
			stat.executeUpdate("insert into association values(NULL, "+userid+","+id+");");
			}stat.close();
			rs.close();
			conn.close();
		}
		catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
			
		}
		request.getRequestDispatcher("/WEB-INF/page/info.jsp").
		forward (request,response);
	}
}
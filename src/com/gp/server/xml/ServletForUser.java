/*
 * name: ServletForUser.java
 * Registration for new users.
 */
package com.gp.server.xml;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.server.domain.Info;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForUser")
public class ServletForUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	public ServletForUser(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		List<Info> infor= new ArrayList<Info>();
		String name = request.getParameter("name");
		String passwd = request.getParameter("passwd");
		
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/3-8.db");
			Statement stat = conn.createStatement();
			stat.executeUpdate("insert into users values(NULL, '" + name + "','" + passwd + ",'normal" + "');");
			System.out.println("reg success!");
			
			//2013-3-15
			ResultSet rs = stat.executeQuery("select * from users where name ='"+name+"';");
			//2013-3-15
			
			infor.add(new Info("id="+rs.getInt("id"), 0));
			rs.close();
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
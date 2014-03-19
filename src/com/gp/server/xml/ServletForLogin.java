/*
 * name: ServletForLogin.java
 * To validate whether the users are in the database and sign in.
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
import com.gp.server.domain.Login;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForLogin")
public class ServletForLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private XMLServiceBean service=new XMLServiceBean();
	public ServletForLogin(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Login> infor= new ArrayList<Login>();
		
		String name = request.getParameter("name");
		String passwd = request.getParameter("passwd");
		System.out.println("log");
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/3-8.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from users;");
			while(rs.next()){
				if (rs.getString("name").equals(name) && rs.getString("passwd").equals(passwd)){
					//2013-3-15
					infor.add(new Login(rs.getString("id"), rs.getString("name"), rs.getString("type")));
					//2013-3-15
					System.out.println("login success!");
				}
			}
			System.out.println("login finished.");
			rs.close();
			stat.close();
			conn.close();
			}
		catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
			
		}
		request.setAttribute("infor", infor);
		request.getRequestDispatcher("/WEB-INF/page/login.jsp").
		forward (request,response);
	}
}
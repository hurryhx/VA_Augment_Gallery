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
import com.gp.server.domain.Listpic;
import com.gp.server.domain.Login;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForChannel")
public class ServletForChannel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String c1, c2, c3, c4, c5, c6;
//	private XMLServiceBean service=new XMLServiceBean();
	public ServletForChannel(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Listpic> infor= new ArrayList<Listpic>();
		
		c1 = request.getParameter("c_classical");
		c2 = request.getParameter("c_modern");
		c3 = request.getParameter("c_abstract");
		c4 = request.getParameter("c_expression");
		c5 = request.getParameter("c_scene");
		c6 = request.getParameter("c_face");
		System.out.println("log");
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/4-4.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from pictures;");
			while(rs.next()){
				if ((!c1.equals("-1") && rs.getString("f_classical").equals(c1)) || (!c2.equals("-1") && rs.getString("f_modern").equals(c2)) || (!c3.equals("-1") &&rs.getString("f_abstrace").equals(c3)) || (!c4.equals("-1") &&rs.getString("f_expression").equals(c4)) || (!c5.equals("-1") &&rs.getString("f_scene").equals(c5)) || (!c6.equals("-1") &&rs.getString("f_face").equals(c6))){
					//2013-3-15
					infor.add(new Listpic(rs.getString("id"), rs.getString("name"), rs.getString("content"), rs.getString("good")));
					//2013-3-15
					System.out.println("Channel!");
				}
			}
			System.out.println("Channel finished.");
			rs.close();
			stat.close();
			conn.close();
			}
		catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
			
		}
		request.setAttribute("infor", infor);
		request.getRequestDispatcher("/WEB-INF/page/listpic.jsp").
		forward (request,response);
	}
}
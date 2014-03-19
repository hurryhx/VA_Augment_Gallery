/*
 * name: ServletForDisplay.java
 * To display the comments for specific picture's id, ordered by good.
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

import com.gp.server.domain.Disp;
import com.gp.server.domain.Info;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForDisplay")
public class ServletForDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	public ServletForDisplay(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Disp> infor= new ArrayList<Disp>();
		
		String id = request.getParameter("picid");
		
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/3-8.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from comments where picid="+id+" order by good desc;");
			ResultSet rs2 = stat.executeQuery("select name from users where id="+rs.getString("userid")+";");
			while(rs.next()){
				infor.add(new Disp(rs.getString("userid"), rs.getString("title"), rs.getString("content"), rs.getString("posttime"), Integer.toString(rs.getInt("good")), rs2.getString("name"), rs.getString("x"), rs.getString("y"))); //评论用户的ID
				System.out.println("display success!");
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
		request.getRequestDispatcher("/WEB-INF/page/display.jsp").
		forward (request,response);
	}
}
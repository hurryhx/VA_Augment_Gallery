/* 2013-3-15
 * name: ServletForListpic.java
 * To display the pictures, ordered by good.
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
import com.gp.server.domain.Listpic;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForListpic")
public class ServletForListpic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	public ServletForListpic(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Listpic> infor= new ArrayList<Listpic>();
		
		//String id = request.getParameter("picid");
		
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/4-4.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from pictures order by good desc;");
			while(rs.next()){
				infor.add(new Listpic(rs.getString("id"), rs.getString("name"), rs.getString("content"), rs.getString("good"))); //作品id
//				infor.add(new Info(rs.getString("name"), 1)); //作品名称
//				infor.add(new Info(rs.getString("postdate"),2)); //投放日期
//				infor.add(new Info(rs.getString("good"), 3)); //喜爱人数
			}
			System.out.println("listpic finished.");
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
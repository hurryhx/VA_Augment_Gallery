/* 2013-3-15
 * name: ServletForAssociate.java
 * To figure the map between userid and picid.
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

import com.gp.server.domain.Asso;
import com.gp.server.domain.Info;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForAssociate")
public class ServletForAssociate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	private void recommend(){}
	public ServletForAssociate(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Asso> infor=new ArrayList<Asso>();
		String userid = request.getParameter("userid");
		
		
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/3-8.db");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from association where userid="+userid+";");
			while(rs.next()){
				String picid = rs.getString("picid");
				ResultSet rstmp = stat.executeQuery("select * from pictures where id="+picid+";");
				infor.add(new Asso(picid, rstmp.getString("name"), rstmp.getString("author")));
				rstmp.close();
			}
			ResultSet rs2 = stat.executeQuery("select * from commentasso where userid="+userid+";");
			while(rs2.next()){
				String picid = rs2.getString("picid");
				ResultSet rstmp = stat.executeQuery("select * from pictures where id="+picid+";");
				infor.add(new Asso(picid, rstmp.getString("name"), rstmp.getString("author")));
				rstmp.close();
			}
			System.out.println("Associate");
			rs.close();rs2.close();
			stat.close();
			conn.close(); //结束数据库的连接 
			}
		catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
			
		}
		request.setAttribute("infor", infor);
		request.getRequestDispatcher("/WEB-INF/page/asso.jsp").
		forward (request,response);
	}
}
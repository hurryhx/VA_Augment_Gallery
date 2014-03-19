/*
 * name: ServletForInterest.java
 * To figure out the common interests(pictures) between two people.
 */
package com.gp.server.xml;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.server.domain.Listpic;
import com.gp.server.domain.Info;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForRecom")
public class ServletForRecom extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	private double product(Vector <Integer> v1, Vector <Integer> v2){
		int sum = 0, sum2 = 0;
		for (int i = 0; i < v1.size() - 1; i++ ){
			sum += ((int)v1.elementAt(i) * (int)v2.elementAt(i));
		}
		for (int i = 0; i < v2.size() - 1; i++){
			if (v2.elementAt(i) == 1) sum2++;
		}
		return (double)sum/Math.log10(sum2);
	}
	public Vector<Integer> vec1 = new Vector<Integer>();
	public Vector<Integer> vec2 = new Vector<Integer>(), vec3 = new Vector<Integer>();
	public ServletForRecom(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		List<Listpic> infor= new ArrayList<Listpic>();
		
		String id1 = request.getParameter("id");
		vec1.clear(); vec2.clear(); vec3.clear();
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/3-8.db");
			Statement stat =conn.createStatement();
			Statement stat2 =conn.createStatement();
			Statement stat3 =conn.createStatement();
			Statement stat4 =conn.createStatement();
			ResultSet rs = stat.executeQuery("select id from pictures;");
			ResultSet rs3 = stat3.executeQuery("select id from users;");
			double t, max = 0; 
			while (rs.next()){
				int tmp = rs.getInt("id");
				ResultSet rs2 = stat2.executeQuery("select picid from association where picid="+Integer.toString(tmp)+" and userid ="+id1+";");
				if (rs2.next()) {vec1.addElement(1);vec2.addElement(0);}
				else {vec1.addElement(0);vec2.addElement(0);}
				rs2.close();
			}
			
			while (rs3.next()){ 
				if (rs3.getInt("id") == Integer.parseInt(id1)) continue;
				for (int i = 0; i < vec2.size()-1; i++)
					vec2.setElementAt(0, i);
				ResultSet rs4 = stat4.executeQuery("select picid from association where userid ="+Integer.toString(rs3.getInt("id"))+" order by picid asc;");
				if (rs4.getInt("picid") >= vec1.size()) continue;
				while (rs4.next()) vec2.setElementAt(1, rs4.getInt("picid")-1);
				t = product(vec1, vec2); System.out.println(t);
				if (max < t) {max = t;
					vec3 = (Vector<Integer>) vec2.clone();
				}
				rs4.close();
			}
			
			for (int i = 0; i < vec1.size() - 1; i++){
				if ((vec1.elementAt(i) == 0 && vec3.elementAt(i) == 1) == true){
					rs3 = stat.executeQuery("select * from pictures where id="+Integer.toString(i+1) +";");
					infor.add(new Listpic(Integer.toString(i+1), rs3.getString("name"), rs3.getString("content"), rs3.getString("good")));
				}
			}
			stat.close(); stat2.close();stat3.close();stat4.close();
			rs.close(); rs3.close();
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
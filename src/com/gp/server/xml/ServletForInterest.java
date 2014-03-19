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

import com.gp.server.domain.Asso;
import com.gp.server.domain.Info;
import com.gp.server.service.XMLServiceBean;

@WebServlet("/ServletForInterest")
public class ServletForInterest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	private Vector<Integer> id = new Vector<Integer>(), num = new Vector<Integer>();
	private Vector<String> name = new Vector<String>();
	public void sort(){
		for (int i = 0; i < id.size(); i++){
			for (int j = i+1; j < id.size(); j++){
				if (num.elementAt(i) < num.elementAt(j)) {
					int t = num.elementAt(i); num.setElementAt(num.elementAt(j), i); num.setElementAt(t, j);
					t = id.elementAt(i); id.setElementAt(id.elementAt(j), i); id.setElementAt(t, j);
					String tmp = name.elementAt(i); name.setElementAt(name.elementAt(j), i); name.setElementAt(tmp, j);
				}
			}
		}
	}
	public ServletForInterest(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Asso> infor= new ArrayList<Asso>();
		id.clear(); num.clear();
		String id1 = request.getParameter("id");
		int amount;
		Connection conn;
		Statement stat = null, stat2 = null, stat3 = null;
		ResultSet rs = null, rs2 = null, rs3 = null;
		try{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:f:/sqlite/3-8.db");
			stat = conn.createStatement();
			stat2 =conn.createStatement();
			stat3 =conn.createStatement();
			rs3 = stat3.executeQuery("select * from users order by id desc");
			rs3.next(); 
			amount = rs3.getInt("id"); System.out.println(amount);
			for (int i = 1; i <= amount; i++){
			if (i == Integer.parseInt(id1)) continue;
			System.out.println("rs test");
			rs = stat.executeQuery("select * from association where userid="+id1+" order by picid;");
			rs2 = stat2.executeQuery("select * from association where userid="+i+" order by picid;");
			
			int ComInt = 0, cnt = 0, cnt1 = 0, cnt2 = 0, cnt3 = 0;
			rs.next(); rs2.next();
			while(true){
				cnt++;
				if (rs.isAfterLast() || rs2.isAfterLast()){
					break;
				}
				
				if (rs.getInt("picid") == rs2.getInt("picid")){
					System.out.println("getIntrs1:"+rs.getInt("id")+" "+rs2.getInt("id"));
					rs.next(); rs2.next();
					cnt1++;
				//	if (!rs.next() || !rs2.next()) break;
					ComInt++;
				}
				else if (rs.getInt("picid") < rs2.getInt("picid")){
					cnt2++;
					rs.next();
				}
				else if (rs.getInt("picid") > rs2.getInt("picid")) {
					cnt3++;
					rs2.next();
				}
			}
			System.out.println("common interest:"+Integer.toString(ComInt));
			System.out.println("cnt:"+cnt+" "+cnt1+" "+cnt2+" "+cnt3);
			rs2 = stat2.executeQuery("select name from users where id = "+i+";");
			id.addElement(i); num.addElement(ComInt); name.addElement(rs2.getString("name"));
			
			}
			sort();
			System.out.println("information important");
			for (int i = 0; i < id.size(); i++){
				System.out.println(id.elementAt(i));
				infor.add(new Asso(Integer.toString(id.elementAt(i)), Integer.toString(num.elementAt(i)), name.elementAt(i)));
			}
			stat.close(); stat2.close(); stat3.close();
			rs.close(); rs2.close(); rs3.close();
			conn.close();
			}
		catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.toString());
		}
		request.setAttribute("infor", infor);
		request.getRequestDispatcher("/WEB-INF/page/Interest.jsp").
		forward (request,response);
	}
}
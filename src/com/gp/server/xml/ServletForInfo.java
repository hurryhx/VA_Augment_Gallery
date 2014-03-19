/*
 * name: ServletForInfo.java
 * For test.
 */
package com.gp.server.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.server.domain.Info;
import com.gp.server.service.XMLServiceBean;

/**
 * Servlet implementation class ServletForInfo
 */
@WebServlet("/ServletForInfo")
public class ServletForInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private XMLServiceBean service=new XMLServiceBean();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletForInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Info> infor=service.getLastInfo();
		request.setAttribute("infor", infor);
        try {

            Class.forName("org.sqlite.JDBC");         
            //建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
            Connection conn = DriverManager.getConnection("jdbc:sqlite:c:/zieckey.db");         
            Statement stat = conn.createStatement();
   
//            stat.executeUpdate( "create table tbl1(name varchar(20), salary int);" );//创建一个表，两列
//            stat.executeUpdate("create table images(img blob)");
//            stat.executeUpdate( "insert into tbl1 values('张三',8000);" ); //插入数据
//            stat.executeUpdate( "insert into tbl1 values('LiSi',7800);" );
//            stat.executeUpdate( "insert into tbl1 values('WangWu',5800);" );
//            stat.executeUpdate( "insert into tbl1 values('ZhaoLiu',9100);" ); 
            File fi = new File("F:/serverforpicture/WebContent/a.jpg");
            InputStream in = new FileInputStream(fi);
            byte[] bytes = new byte[(int)fi.length()];
            in.read(bytes);
//            stat = conn.prepareStatement("insert into image values(?)");
            PreparedStatement ps = conn.prepareStatement("insert into images values(?)");
//            ps.setBytes(1, bytes);
//            ps.executeUpdate();
            
            ResultSet rs = stat.executeQuery("select * from tbl1;"); //查询数据 
            
            while (rs.next()) { //将查询到的数据打印出来
            	infor.add(new Info(rs.getString("name") + " ", 10));
                System.out.print("name = " + rs.getString("name") + " "); //列属性一
                System.out.println("salary = " + rs.getString("salary")); //列属性二
            }
            rs = stat.executeQuery("select * from images;");
            byte[] bt = rs.getBytes(1);
            infor.add(new Info(bt.toString(), 0));
            rs.close();
            conn.close(); //结束数据库的连接 

        }
        catch (Exception e) {
            // Print some generic debug info
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }
		
		request.getRequestDispatcher("/WEB-INF/page/info.jsp").
		forward (request,response);
	}

}


package es.mad.onco.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Configuracion {
	private Properties prop;
	private String dbUser="";
	private String dbPass="";
	private String jdbcStr="";
	
	public Configuracion()
	{
	    prop = new Properties();
		InputStream input = null;
		try {
			input=new FileInputStream(System.getProperty("catalina.home")+"/conf/app.properties");
			prop.load(input);
			jdbcStr=prop.getProperty("jdbc");
			dbUser=prop.getProperty("usr");
			dbPass=prop.getProperty("pass");
 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			prop=null;
			e.printStackTrace();
		}
	}
	public ResultSet ejecutarSQL(String sentencia,boolean insert)
	{
		ResultSet rs=null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				Connection conexion =  (Connection) DriverManager.getConnection("jdbc:"+prop.getProperty("jdbc")+"?user="+prop.getProperty("usr")+"&password="+prop.getProperty("pass"));
						

				Statement s = (Statement) conexion.createStatement(); 
				if (!insert)
				{
				rs = s.executeQuery (sentencia);
				
				return rs;
				}
				else
				{
					s.executeUpdate(sentencia);
					return null;
				} 
			} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
	}
	
}

package es.mad.onco;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.mad.onco.utils.Configuracion;

/**
 * Servlet implementation class InicioSesion
 */
@WebServlet("/InicioSesion")
public class InicioSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InicioSesion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		Configuracion cfg=new Configuracion();
		ResultSet rs=cfg.ejecutarSQL("SELECT json FROM evaluaciones.iniciosesion WHERE clave=1", false);
		if (rs!=null) 
		{  
			try {
				rs.next();
				response.getWriter().append(rs.getString("json"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				response.getWriter().append(e.getMessage());
			} 
		}
		else
		{
			response.getWriter().append("failure");
		}
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json=request.getParameter("json"); 
  
		Configuracion cfg=new Configuracion();
		cfg.ejecutarSQL("UPDATE evaluaciones.iniciosesion SET json='"+json+"' WHERE clave=1",true);
		ResultSet rs=cfg.ejecutarSQL("SELECT * FROM evaluaciones.iniciosesion WHERE clave=1", false);
		if (rs!=null) 
		{
			response.getWriter().append("success");
		} 
		else
		{
			response.getWriter().append("failure");
		} 

	}
	}

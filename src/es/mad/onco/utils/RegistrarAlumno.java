package es.mad.onco.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrarAlumno
 */
@WebServlet("/RegistrarAlumno") 
public class RegistrarAlumno extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarAlumno() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String apellidos=request.getParameter("apellidos");
		String curso=request.getParameter("curso"); 
		Configuracion cfg=new Configuracion();
		cfg.ejecutarSQL("INSERT INTO evaluaciones.alumnos VALUES ('"+id+"','"+nombre+"','"+apellidos+"','"+curso+"')",true);
		ResultSet rs=cfg.ejecutarSQL("SELECT * FROM evaluaciones.alumnos WHERE idAlumno='"+id+"'", false);
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

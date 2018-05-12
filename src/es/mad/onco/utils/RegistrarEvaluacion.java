package es.mad.onco.utils;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrarEvaluacion
 */
@WebServlet("/RegistrarEvaluacion")
public class RegistrarEvaluacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarEvaluacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */ 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// '&descripcion='+descripcion+"&numObjetivos="+objetivos+"&cuestionario="+cuestionario
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String numObjetivos=request.getParameter("numObjetivos");
		String cuestionario=request.getParameter("cuestionario"); 
		String descripcion=request.getParameter("descripcion");
		Configuracion cfg=new Configuracion();
		System.out.println("INSERT INTO evaluaciones.evaluaciones VALUES ('"+id+"','"+nombre+"',"+numObjetivos+",'"+descripcion+"','"+cuestionario+"','Activada')");
		cfg.ejecutarSQL("INSERT INTO evaluaciones.evaluaciones VALUES ('"+id+"','"+nombre+"',"+numObjetivos+",'"+descripcion+"','"+cuestionario+"','Activada')",true);
		ResultSet rs=cfg.ejecutarSQL("SELECT * FROM evaluaciones.evaluaciones WHERE idEvaluacion='"+id+"'", false);
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

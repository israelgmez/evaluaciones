package es.mad.onco.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class GetListadoAlumnos
 */
@WebServlet("/GetListadoAlumnos")
public class GetListadoAlumnos extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private Configuracion config=null;

    public GetListadoAlumnos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		config=new Configuracion();
		String resultadoTemp="";
		ResultSet rs=config.ejecutarSQL("SELECT IdAlumno,Nombre,Apellidos,Curso,evaluaciones,autoevaluaciones FROM evaluaciones.alumnos",false);

		try {
			if (rs!=null)
			{
				out.append("{\"alumnos\":[");
			while (rs.next())
			{ 
				resultadoTemp+="{\"idAlumno\":\""+rs.getString("IdAlumno")+"'\",\"Nombre\":\""+rs.getString("Nombre")+"\",\"Apellidos\":\""+rs.getString("Apellidos")+"\",\"Curso\":\""+rs.getString("Curso")+"\",\"evaluaciones\":"+rs.getString("evaluaciones")+",\"autoevaluaciones\":"+rs.getString("autoevaluaciones")+"},";
			}
			if (resultadoTemp.endsWith(",")) resultadoTemp=resultadoTemp.substring(0,resultadoTemp.length()-1);
			
			out.append(resultadoTemp);
			out.append("]}");
			}
			else
			{
				
				 
			}  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.append("Error al obtener el listado de alumnos");
		}

	} 
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

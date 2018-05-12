package es.mad.onco.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetPerfilId
 */
@WebServlet("/GetPerfilId")
public class GetPerfilId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private Configuracion config=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPerfilId() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		config=new Configuracion();
		String resultadoTemp="";
		
		String id=request.getParameter("iden");
		
		/*Tenemos que hacer dos comprobaciones, primero si es tutor, para generar la parte de json correspondiente a tutor
		 * y luego si es alumno para descargar la parte correspondiente
		 * permitiremos que puedan existir las dos opciones simultáneamente, sobre todo para que un tutor pueda
		 * acceder como alumno para comprobar que todo está funcionando*/
		out.append("{\"perfil\":{\"tutor\":{");

		
		/*Parte tutor*/
		
		
		try {
			ResultSet rs=config.ejecutarSQL("SELECT idTutor,Nombre,Apellidos,CorreoE,alumnos FROM evaluaciones.tutores WHERE idTutor='"+id+"'",false);
			if (rs.next())
			{
				out.append("\"idtutor\":\""+id+"\",");
				out.append("\"nombre\":\""+rs.getString("Nombre")+"\",");
				out.append("\"apellidos\":\""+rs.getString("Apellidos")+"\",");
				out.append("\"email\":\""+rs.getString("CorreoE")+"\",");
				if (rs.getString("alumnos")!=null)
					out.append("\"alumnos\":"+rs.getString("alumnos")+","); 
				else
					out.append("\"alumnos\":{},");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
		/*Parte alumno*/ 
		out.append("},\"alumno\":{");
		try {
			ResultSet rs=config.ejecutarSQL("SELECT idAlumno,Nombre,Apellidos,Curso,evaluaciones,autoevaluaciones FROM evaluaciones.alumnos WHERE idAlumno='"+id+"'",false);
			if (rs.next())
			{
				out.append("\"idalumno\":\""+id+"\",");
				out.append("\"nombre\":\""+rs.getString("Nombre")+"\",");
				out.append("\"apellidos\":\""+rs.getString("Apellidos")+"\",");
				out.append("\"curso\":\""+rs.getString("Curso")+"\",");
				if (rs.getString("evaluaciones")!=null)
					out.append("\"evaluaciones\":"+rs.getString("evaluaciones")+","); 
				else
					out.append("\"evaluaciones\":{},");
				if (rs.getString("autoevaluaciones")!=null)
					out.append("\"autoevaluaciones\":"+rs.getString("autoevaluaciones")); 
				else
					out.append("\"autoevaluaciones\":{}");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
		/*Fin perfil*/
		out.append("}}}"); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

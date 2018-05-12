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
 * Servlet implementation class GetListadoEvaluadores
 */
@WebServlet("/GetListadoEvaluadores")
public class GetListadoEvaluadores extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private Configuracion config=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListadoEvaluadores() {
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
		String listaTemp="";
		ResultSet rs=config.ejecutarSQL("SELECT idTutor,Nombre,Apellidos,CorreoE,alumnos FROM evaluaciones.tutores",false);
		 
		try {
			out.append("{\"tutores\":[");
			if (rs!=null)
			{
			while (rs.next())
			{ 
				listaTemp+="{\"idTutor\":\""+rs.getString("idTutor")+"\",\"Nombre\":\""+rs.getString("Nombre")+"\",\"Apellidos\":\""+rs.getString("Apellidos")+"\",\"correo\":\""+rs.getString("CorreoE")+"\",\"alumnos\":"+rs.getString("alumnos")+"},";
				
			}
			if (listaTemp.endsWith(",")) listaTemp=listaTemp.substring(0,listaTemp.length()-1);
			out.append(listaTemp);

			} 
			else
			{
				out.append("");
			}
			out.append("]}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.append("error");
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

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
 * Servlet implementation class GetListadoEvaluaciones
 */
@WebServlet("/GetListadoEvaluaciones")
public class GetListadoEvaluaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private Configuracion config=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListadoEvaluaciones() {
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
		ResultSet rs=config.ejecutarSQL("SELECT idEvaluacion,Nombre,Descripcion,Cuestionario,Hitos,Estado FROM evaluaciones.evaluaciones",false);
		 
		try {
			out.append("{\"evaluaciones\":[");
			if (rs!=null)
			{
			while (rs.next())
			{ 
				listaTemp+="{\"id\":\""+rs.getString("idEvaluacion")+"\",\"numPreguntas\":"+rs.getString("Hitos")+",\"Nombre\":\""+rs.getString("Nombre")+"\",\"Descripcion\":\""+rs.getString("Descripcion")+"\",\"Estado\":\""+rs.getString("Estado")+"\",\"Cuestionario\":"+rs.getString("cuestionario")+"},";
				
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

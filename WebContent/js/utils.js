var jsoneval;
var jsonalumnos;
var jsontutores;

function getAjax (url,success)
{
    var xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
    xhr.open('GET', url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState>3 && xhr.status==200) { success(xhr.responseText); }
    };
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');
    xhr.send();
    return xhr;
}
function postAjax(url, data, success) {
    var params = typeof data == 'string' ? data : Object.keys(data).map(
            function(k){ return encodeURIComponent(k) + '=' + encodeURIComponent(data[k]) }
        ).join('&');

    var xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
    xhr.open('POST', url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState>3 && xhr.status==200) { success(xhr.responseText); }
    };
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');
    xhr.send(params);
    return xhr;
}
function nuevaEvaluacion(){
	var formulario=document.getElementById("formularioEvaluacion");
	if (!evaluacionAbierta)
		{
		formulario.style.display="block";
		var idEval=document.getElementById("idEvaluacion");
		idEval.value=Date.now();
		idEval.disabled=true;
		}
	else
		{
		registrarEvaluacion(generarJsonEvaluacion());
		}
}
function formatearListadoEval()
{ 
	var evaluaciones=jsoneval.Evaluaciones;
	var listado="<ul id='listadoEvaluaciones'>";
	var eval;
	for (var i=0;i<jsoneval.evaluaciones.length;i++)
		{
		eval=jsoneval.evaluaciones[i];
		listado+="<li><span onClick=\"toggleCuestionario("+i+");\">"+eval.Nombre+": "+eval.Descripcion+"</span> ("+eval.Estado+")<input type=\"button\" value=\"Desactivar/Activar\" onClick=\"toggle("+i+");\">";
		listado+="<div id=\"cuestionario-"+i+"\" style=\"display:none\">";
		listado+="Contenido de la Evaluación:<br>";
		var preguntas=jsoneval.evaluaciones[i].Cuestionario;

		for (var o=0;o<preguntas.Evaluacion.numPreguntas;o++)
			{
			listado+=preguntas.Evaluacion.objetivos[o].nombre+"<br>";
			listado+=preguntas.Evaluacion.objetivos[o].descripcion+"<br>";
			}
		/*Evaluacion.objetivos.Nombre,descripcion*/
		listado+="</div>";
		listado+="</li>";
		}
	listado+="</ul>";
	return listado;
}
function toggleCuestionario(pos)
{
	var cuestionario=document.getElementById("cuestionario-"+pos);
	if (cuestionario.style.display=="none")
		cuestionario.style.display="block";
	else
			cuestionario.style.display="none"
}
function toggle(posicion)
{
	if (jsoneval.evaluaciones[posicion].Estado=='Desactivada')
		jsoneval.evaluaciones[posicion].Estado='Activada';
	else
		jsoneval.evaluaciones[posicion].Estado='Desactivada';
	document.getElementById("listadoEvaluaciones").innerHTML = formatearListadoEval(jsoneval);
}
function formatoCheckAlumnos()
{
	var resultado="";
	resultado="<ul>";
	var eval;
	for (var i=0;i<jsonalumnos.alumnos.length;i++)
		{
		eval=jsonalumnos.alumnos[i];
		console.log(eval);
		resultado+="<li>"+eval.Nombre+" "+eval.Apellidos+" <input type=\"checkbox\" id=\"\"> <br>";
		}
	resultado+="</ul><br>"; 

	return resultado;
}
function formatoCheckEvaluaciones()
{
	var resultado="";
	resultado="<ul>";
	var eval;
var disabled="";
	for (var i=0;i<jsoneval.evaluaciones.length;i++)
		{
		eval=jsoneval.evaluaciones[i];
if (eval.Estado=="Desactivada")
	disabled="disabled";
else
	disabled="";
		resultado+="<li>"+eval.Nombre+"("+eval.Estado+") <input type=\"checkbox\" id=\"chkeval"+i+"\" "+disabled+">Evaluación <input type=\"checkbox\" id=\"chkautoeval"+i+"\" "+disabled+">Autoevaluación<br>";
		}
	resultado+="</ul><br>"; 

	return resultado;
}
function checkAlumno(posicion)
{
	var div=document.getElementById("asignacionAlumno-"+posicion);
	if (div.style.display=="none")
		div.style.display="block";
	else
		div.style.display="none";
}
function checkEvals(posicion)
{
	var div=document.getElementById("evalAlumno-"+posicion);
	if (div.style.display=="none")
		div.style.display="block";
	else
		div.style.display="none";
}
function formatearListadoAlumnos()
{ 
	var listado="<ul id='listadoAlumnos'>";
	var eval;
	for (var i=0;i<jsonalumnos.alumnos.length;i++)
		{
		eval=jsonalumnos.alumnos[i];
		listado+="<li>"+eval.Nombre+" "+eval.Apellidos+" <a href=\"javascript:checkEvals("+i+");\">Gestionar evaluaciones</a>";
		listado+="<div id=\"evalAlumno-"+i+"\" style=\"display:none\">";
		listado+=formatoCheckEvaluaciones();
		listado+="<input type=\"button\" value=\"Registrar asignaciones\" onClick=\"asignarEvaluaciones("+i+")\">";
		listado+="</div>";
		listado+="</li>";
		}
	listado+="</ul>";
	return listado;
}
function formatearListadoTutores()
{ 
	console.log(jsontutores);
	var tutores=jsontutores.tutores;
	var listado="<ul id='listadoTutores'>";
	var eval;
	for (var i=0;i<jsontutores.tutores.length;i++)
		{
		eval=jsontutores.tutores[i];
		listado+="<li>"+eval.Nombre+" "+eval.Apellidos+" <a href=\"javascript:checkAlumno("+i+");\">Gestionar alumnos</a>";
		listado+="<div id=\"asignacionAlumno-"+i+"\" style=\"display:none\">";
		listado+=formatoCheckAlumnos();
		listado+="<input type=\"button\" value=\"Registrar asignaciones\" onClick=\"asignarAlumnos("+i+")\">";
		listado+="</div>";
		listado+="</li>";
		}
	listado+="</ul>";
	return listado;
} 
function nuevoEvaluador()
{
var div=document.getElementById("formularioEvaluacion");
div.style.visibility="visible";
div.style.display="block";
}  
var evaluacionAbierta=false;
function cargarListadoEvaluaciones()
{
	var resultado="";
	var xhttp=new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		  if (this.readyState == 4 && this.status == 200) {
			  jsoneval=JSON.parse(this.responseText);
		  }
		};
		xhttp.open("GET", "GetListadoEvaluaciones", true);
		xhttp.send();
} 
function cargarCadenaInicio()
{
	console.log("cargar cadena inicio"); 
	var resultado="";
	var xhttp=new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		  if (this.readyState == 4 && this.status == 200) {
			  document.getElementById("descripcion").value=this.responseText;
		  }
		};
		xhttp.open("GET", "InicioSesion", true);
		xhttp.send();
}
function cargarListadoAlumnos()
{
	var resultado="";
	var xhttp=new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		  if (this.readyState == 4 && this.status == 200) {
			  jsonalumnos= JSON.parse(this.responseText);
			  document.getElementById("jsonAlumnos").value=this.responseText;
		  }
		};
		xhttp.open("GET", "GetListadoAlumnos", true);
		xhttp.send();
} 
function cargarListadoTutores()
{
	var xhttp=new XMLHttpRequest();
	var resultado="";
	xhttp.onreadystatechange = function() {
		  if (this.readyState == 4 && this.status == 200) {
			  jsontutores= JSON.parse(this.responseText);
		  }
		};
		xhttp.open("GET", "GetListadoEvaluadores", true);
		xhttp.send();
		return resultado;
}

function generarEvaluacion()
{
	var divCuestionario=document.getElementById("cuestionarioEvaluacion");
	var boton=document.getElementById("btnGenerarEvaluacion");
	boton.disabled=true;
	var nombreEval=document.getElementById("nombreEvaluacion");
	var total=document.getElementById("numObjetivos");
	if (total=="")
		{
		alert("Debe indicar número de objetivos para esta evaluación");
		return;
		}
	var nomObjetivo;
	var descObjetivo;
	total.disabled=true;


	nombreEval.disabled=true;
	evaluacionAbierta=true;
	for (var i=0;i<total.value;i++)
		{
		divCuestionario.innerHTML+="Nombre del objetivo: <input type=\"text\" id=\"nomObjetivo-"+i+"\"><br>";
		divCuestionario.innerHTML+="Descripción del objetivo: <textarea id=\"descObjetivo-"+i+"\"></textarea><br>";
		}
	divCuestionario.style.display="block";
}
function generarJsonEvaluacion()
{
	var idEval=document.getElementById("idEvaluacion");
	var total=document.getElementById("numObjetivos");
	var resultado="{\"Evaluacion\":{";
	resultado+="\"nombre\":\""+idEval.value+"\",\"numPreguntas\":\""+total.value+"\",\"objetivos\":[";
	for (var i=0;i<total.value;i++)
	{
		resultado+="{\"nombre\":\""+document.getElementById("nomObjetivo-"+i).value+"\",\"descripcion\":\""+document.getElementById("descObjetivo-"+i).value.replace(/\"/g,"\\\"")+"\"}";
		if (i<(total.value-1)) resultado+=",";
	}
	resultado+="]}}";
	return resultado;
} 
function registrarInicio()
{
	var descripcion=document.getElementById("descripcion").value;
	
	postAjax('InicioSesion', 'json='+descripcion, function(data){ if (data=='success') alert("Inicio de sesión registrado correctamente.");});
window.refresh();
}
function registrarEvaluacion(cuestionario)
{
	var id=document.getElementById("idEvaluacion").value;
	var nombre=document.getElementById("nombreEvaluacion").value;
	var descripcion=document.getElementById("descripcion").value;
	var objetivos=document.getElementById("numObjetivos").value;
	postAjax('RegistrarEvaluacion', 'nombre='+nombre+'&descripcion='+descripcion+"&id="+id+"&numObjetivos="+objetivos+"&cuestionario="+cuestionario, function(data){ if (data=='success') alert("Evaluación registrada correctamente.");});
window.refresh();
}
 
function nuevoTutor()
{
var div=document.getElementById("formularioEvaluador");
div.style.display="block";
}

function registrarAlumno()
{
	var id=document.getElementById("idAlumno").value;
	var nombre=document.getElementById("nombre").value;
	var apellido=document.getElementById("apellidos").value;
	var curso=document.getElementById("curso").value;
	postAjax('RegistrarAlumno', 'nombre='+nombre+'&apellidos='+apellido+"&id="+id+"&curso="+curso, function(data){ if (data=='success') alert("Alumno registrado correctamente.");});
}

function nuevoAlumno()
{
var div=document.getElementById("formularioAlumno");
var dive=document.getElementById("evaluaciones");
div.style.visibility="visible";
div.style.display="block";
dive.innerHTML=formatoCheckEvaluaciones();
}
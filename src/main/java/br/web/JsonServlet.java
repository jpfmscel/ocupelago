package br.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.dao.AlertaDAO;
import br.dao.AvaliacaoDAO;
import br.dao.EsporteDAO;
import br.dao.EventoDAO;
import br.dao.LocalDAO;
import br.dao.NoticiaDAO;
import br.dao.ProjetoDAO;
import br.dao.UsuarioDAO;
import br.entidades.Alerta;
import br.entidades.Avaliacao;
import br.entidades.Usuario;
import br.enumeradores.EnumWebMethods;
import br.managedBeans.ListFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/jsonServlet")
public class JsonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	private UsuarioDAO usuarioDao;
	private AlertaDAO alertaDao;
	private LocalDAO localDao;
	private ProjetoDAO projetoDao;
	private NoticiaDAO noticiaDao;
	private EsporteDAO esporteDao;
	private AvaliacaoDAO avaliacaoDao;
	private EventoDAO eventoDao;

	private Logger log = Logger.getGlobal();
	
	public JsonServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer method = Integer.valueOf(request.getParameter("method"));
		if (method == null) {
			response.setCharacterEncoding("ISO-8859-1");
			response.getWriter().write("Par�metros insuficientes.");
			return;
		}

		ListFactory lf = (ListFactory) request.getSession().getServletContext().getAttribute("listFactory");
		EnumWebMethods en = EnumWebMethods.getEnumByCod(method);
		String jsonG = null;
		String json = getRequestBody(request);
		log.log(Level.INFO, json);
		System.out.println("Host :" + request.getRemoteHost() + " - Request :" + json);
		switch (en) {
		case GET_ESPORTES:
			jsonG = gson.toJson(lf.getListaEsporte());
			break;
		case GET_ALERTAS:
			jsonG = gson.toJson(lf.getListaAlerta());
			break;
		case GET_EVENTOS:
			jsonG = gson.toJson(lf.getListaEvento());
			break;
		case GET_LOCAIS:
			jsonG = gson.toJson(lf.getListaLocal());
			break;
		case GET_NOTICIAS:
			jsonG = gson.toJson(lf.getListaNoticia());
			break;
		case GET_PROJETOS:
			jsonG = gson.toJson(lf.getListaProjeto());
			break;
		case ADD_ALERTA:
			jsonG = addAlerta(json);
			break;
		case ADD_AVALIACAO:
			jsonG = addAvaliacao(json);
			break;
		case ADD_USUARIO:
			jsonG = addUsuario(json);
			break;
		case LOGIN:
			jsonG = loginUsuario(json);
			break;
		default:
			break;
		}

		response.setContentType("application/json");
		// response.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("ISO-8859-1");
		response.getWriter().write(jsonG);
	}

	private String getRequestBody(HttpServletRequest request) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) {
			String line = "";
			while ((line = br.readLine()) != null) {
				json += line;
			}
		}
		return json;
	}

	private String addAvaliacao(String json) {
		Avaliacao a = gson.fromJson(json, Avaliacao.class);
		String retorno = checkAvaliacao(a);
		if (retorno == null) {
			a.setCriadoEm(new Date());
			a.setAtivo(true);
			getAvaliacaoDao().iniciarTransacao();
			getAvaliacaoDao().inserir(a);
			getAvaliacaoDao().comitarTransacao();
			return gson.toJson(a);
		}
		return retorno;
	}

	private String addUsuario(String json) {
		Usuario u = gson.fromJson(json, Usuario.class);
		String retorno = checkUsuario(u);
		if (retorno == null) {
			u.setDataCriado(new Date());
			u.setAtivo(true);
			u.setSenha(u.getSenha());
			getUsuarioDao().iniciarTransacao();
			getUsuarioDao().inserir(u);
			getUsuarioDao().comitarTransacao();
			return gson.toJson(u);
		}
		return retorno;
	}

	private String checkUsuario(Usuario u) {
		if (u.getNome() == null || u.getNome().isEmpty()) {
			return "Nome deve ser preenchido!";
		} else if (u.getEmail() == null || u.getEmail().isEmpty()) {
			return "E-mail deve ser preenchida!";
		} else if (u.getSenha() == null || u.getSenha().isEmpty()) {
			return "Local deve ser informado!";
		}
		return null;
	}

	private String checkAvaliacao(Avaliacao a) {
		if (a.getTitulo().isEmpty() || a.getTitulo() == null) {
			return "T�tulo deve ser preenchido!";
		} else if (a.getComentario().isEmpty() || a.getComentario() == null) {
			return "Coment�rio deve ser preenchida!";
		} else if (a.getLocal() == null || a.getLocal().getId() == 0) {
			return "Local deve ser informado!";
		} else if (a.getNota() == null) {
			return "Nota deve ser informada!";
		}
		return null;
	}

	private String loginUsuario(String json) {
		Usuario u = gson.fromJson(json, Usuario.class);
		Usuario us = getUsuarioDao().logarUsuario(u.getEmail(), u.getSenha());
		return gson.toJson(us);
	}

	private String addAlerta(String json) {
		Alerta a = gson.fromJson(json, Alerta.class);

		String retorno = checkAlerta(a);

		if (retorno == null) {
			a.setDataCriado(new Date());
			a.setAtivo(true);
			getAlertaDao().iniciarTransacao();
			getAlertaDao().inserir(a);
			getAlertaDao().comitarTransacao();
			return gson.toJson(a);
		}

		return retorno;
	}

	private String checkAlerta(Alerta a) {
		if (a.getTitulo().isEmpty() || a.getTitulo() == null) {
			return "T�tulo deve ser preenchido!";
		} else if (a.getDescricao().isEmpty() || a.getDescricao() == null) {
			return "Descri��o deve ser preenchida!";
		} else if (a.getLatitude() == 0) {
			return "Localiza��o deve ser informada!";
		} else if (a.getLongitude() == 0) {
			return "Localiza��o deve ser informada!";
		}
		return null;
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public UsuarioDAO getUsuarioDao() {
		if (usuarioDao == null) {
			usuarioDao = new UsuarioDAO();
		}
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public AlertaDAO getAlertaDao() {
		if (alertaDao == null) {
			alertaDao = new AlertaDAO();
		}
		return alertaDao;
	}

	public void setAlertaDao(AlertaDAO alertaDao) {
		this.alertaDao = alertaDao;
	}

	public LocalDAO getLocalDao() {
		if (localDao == null) {
			localDao = new LocalDAO();
		}
		return localDao;
	}

	public void setLocalDao(LocalDAO localDao) {
		this.localDao = localDao;
	}

	public ProjetoDAO getProjetoDao() {
		if (projetoDao == null) {
			projetoDao = new ProjetoDAO();
		}
		return projetoDao;
	}

	public void setProjetoDao(ProjetoDAO projetoDao) {
		this.projetoDao = projetoDao;
	}

	public NoticiaDAO getNoticiaDao() {
		if (noticiaDao == null) {
			noticiaDao = new NoticiaDAO();
		}
		return noticiaDao;
	}

	public void setNoticiaDao(NoticiaDAO noticiaDao) {
		this.noticiaDao = noticiaDao;
	}

	public EsporteDAO getEsporteDao() {
		if (esporteDao == null) {
			esporteDao = new EsporteDAO();
		}
		return esporteDao;
	}

	public void setEsporteDao(EsporteDAO esporteDao) {
		this.esporteDao = esporteDao;
	}

	public AvaliacaoDAO getAvaliacaoDao() {
		if (avaliacaoDao == null) {
			avaliacaoDao = new AvaliacaoDAO();
		}
		return avaliacaoDao;
	}

	public void setAvaliacaoDao(AvaliacaoDAO avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

	public EventoDAO getEventoDao() {
		if (eventoDao == null) {
			eventoDao = new EventoDAO();
		}
		return eventoDao;
	}

	public void setEventoDao(EventoDAO eventoDao) {
		this.eventoDao = eventoDao;
	}

}

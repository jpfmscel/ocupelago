package br.managedBeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.dao.AlertaDAO;
import br.dao.BaseDao;
import br.dao.EsporteDAO;
import br.dao.EventoDAO;
import br.dao.LocalDAO;
import br.dao.NoticiaDAO;
import br.dao.ProjetoDAO;
import br.entidades.Alerta;
import br.entidades.Esporte;
import br.entidades.Evento;
import br.entidades.Local;
import br.entidades.Noticia;
import br.entidades.Projeto;
import br.util.Paginator;

@ApplicationScoped
@ManagedBean(name = "listFactory")
public class ListFactory {

	private static List<Alerta> listaAlerta = new ArrayList<>();
	private static List<Local> listaLocal = new ArrayList<>();
	private static List<Projeto> listaProjeto = new ArrayList<>();
	private static List<Noticia> listaNoticia = new ArrayList<>();
	private static List<Esporte> listaEsporte = new ArrayList<>();
	private static List<Evento> listaEvento = new ArrayList<>();

	private static Date dataAlerta = new Date();
	private static Date dataLocal = new Date();
	private static Date dataProjeto = new Date();
	private static Date dataNoticia = new Date();
	private static Date dataEsporte = new Date();
	private static Date dataEvento = new Date();

	private static Paginator pagLocal;
	private static Paginator pagProjeto;
	private static Paginator pagNoticia;
	private static Paginator pagEsporte;
	private static Paginator pagEvento;
	private static Paginator pagAlerta;

	public boolean isSamePeriod(Date d1) {
		boolean retorno = false;

		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT -03:00"), new Locale("pt-BR"));
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("GMT -03:00"), new Locale("pt-BR"));
		// c2.add(Calendar.MINUTE, -5);

		retorno = c2.before(c1);

		return retorno;
	}

	public boolean isSameMinute(Date d1) {
		boolean retorno = false;

		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT -03:00"), new Locale("pt-BR"));
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("GMT -03:00"), new Locale("pt-BR"));
		c2.add(Calendar.MINUTE, -1);

		retorno = c2.before(c1);

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public <T> void atualizarLista(BaseDao<T> b, Date d) {
		if (b instanceof EsporteDAO) {
			setListaEsporte((List<Esporte>) b.findAll());
		} else if (b instanceof LocalDAO) {
			setListaLocal((List<Local>) b.findAll());
		} else if (b instanceof NoticiaDAO) {
			setListaNoticia((List<Noticia>) b.findAll());
		} else if (b instanceof ProjetoDAO) {
			setListaProjeto((List<Projeto>) b.findAll());
		} else if (b instanceof AlertaDAO) {
			setListaAlerta((List<Alerta>) ((AlertaDAO) b).getListaInicial());
		} else if (b instanceof EventoDAO) {
			setListaEvento((List<Evento>) b.findAll());
		}
		d = new Date();
	}

	public List<Alerta> getListaAlerta() {
		// if (!(isSameMinute(dataAlerta) && !listaAlerta.isEmpty())) {
		atualizarLista(new AlertaDAO(), dataAlerta);
		// }
		return listaAlerta;
	}

	public void setListaAlerta(List<Alerta> listaAlerta) {
		ListFactory.listaAlerta = listaAlerta;
	}

	public List<Local> getListaLocal() {
		if (!(isSamePeriod(dataLocal) && !listaLocal.isEmpty())) {
			atualizarLista(new LocalDAO(), dataLocal);
		}
		return listaLocal;
	}

	public void setListaLocal(List<Local> listaLocal) {
		ListFactory.listaLocal = listaLocal;
	}

	public List<Projeto> getListaProjeto() {
		if (!(isSamePeriod(dataProjeto) && !listaProjeto.isEmpty())) {
			atualizarLista(new ProjetoDAO(), dataProjeto);
		}
		return listaProjeto;
	}

	public void setListaProjeto(List<Projeto> listaProjeto) {
		ListFactory.listaProjeto = listaProjeto;
	}

	public List<Noticia> getListaNoticia() {
		if (!(isSamePeriod(dataNoticia) && !listaNoticia.isEmpty())) {
			atualizarLista(new NoticiaDAO(), dataNoticia);
		}
		return listaNoticia;
	}

	public void setListaNoticia(List<Noticia> listaNoticia) {
		ListFactory.listaNoticia = listaNoticia;
	}

	public List<Esporte> getListaEsporte() {
		if (!(isSamePeriod(dataEsporte) && !listaEsporte.isEmpty())) {
			atualizarLista(new EsporteDAO(), dataEsporte);
		}
		return listaEsporte;
	}

	public void setListaEsporte(List<Esporte> listaEsporte) {
		ListFactory.listaEsporte = listaEsporte;
	}

	public List<Evento> getListaEvento() {
		if (!(isSamePeriod(dataEvento) && !listaEvento.isEmpty())) {
			atualizarLista(new EventoDAO(), dataEvento);
		}
		return listaEvento;
	}

	public void setListaEvento(List<Evento> listaEvento) {
		ListFactory.listaEvento = listaEvento;
	}

	public Paginator getPagLocal() {
		if (pagLocal == null) {
			pagLocal = new Paginator(listaLocal);
		}
		return pagLocal;
	}

	public static void setPagLocal(Paginator pagLocal) {
		ListFactory.pagLocal = pagLocal;
	}

	public Paginator getPagProjeto() {
		if (pagProjeto == null) {
			pagProjeto = new Paginator(listaProjeto);
		}
		return pagProjeto;
	}

	public static void setPagProjeto(Paginator pagProjeto) {
		ListFactory.pagProjeto = pagProjeto;
	}

	public Paginator getPagNoticia() {
		if (pagNoticia == null) {
			pagNoticia = new Paginator(listaNoticia);
		}
		return pagNoticia;
	}

	public void setPagNoticia(Paginator pagNoticia) {
		ListFactory.pagNoticia = pagNoticia;
	}

	public Paginator getPagEsporte() {
		if (pagEsporte == null) {
			pagEsporte = new Paginator(listaEsporte);
		}
		return pagEsporte;
	}

	public static void setPagEsporte(Paginator pagEsporte) {
		ListFactory.pagEsporte = pagEsporte;
	}

	public Paginator getPagEvento() {
		if (pagEvento == null) {
			pagEvento = new Paginator(listaEvento);
		}
		return pagEvento;
	}

	public static void setPagEvento(Paginator pagEvento) {
		ListFactory.pagEvento = pagEvento;
	}

	public Paginator getPagAlerta() {
		if (pagAlerta == null) {
			pagAlerta = new Paginator(listaAlerta);
		}
		return pagAlerta;
	}

	public static void setPagAlerta(Paginator pagAlerta) {
		ListFactory.pagAlerta = pagAlerta;
	}

}

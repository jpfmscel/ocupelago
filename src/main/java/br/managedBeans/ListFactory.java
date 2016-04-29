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

	private List<Alerta> listaAlerta = new ArrayList<>();
	private List<Local> listaLocal = new ArrayList<>();
	private List<Projeto> listaProjeto = new ArrayList<>();
	private List<Noticia> listaNoticia = new ArrayList<>();
	private List<Esporte> listaEsporte = new ArrayList<>();
	private List<Evento> listaEvento = new ArrayList<>();

	private Date dataAlerta = new Date();
	private Date dataLocal = new Date();
	private Date dataProjeto = new Date();
	private Date dataNoticia = new Date();
	private Date dataEsporte = new Date();
	private Date dataEvento = new Date();

	private Paginator pagLocal;
	private Paginator pagProjeto;
	private Paginator pagNoticia;
	private Paginator pagEsporte;
	private Paginator pagEvento;
	private Paginator pagAlerta;

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
		if (!(isSameMinute(dataAlerta) && !listaAlerta.isEmpty())) {
			atualizarLista(new AlertaDAO(), dataAlerta);
			for (Alerta a : listaAlerta) {
				a.getImagensREST();
			}
		}
		return listaAlerta;
	}

	public void setListaAlerta(List<Alerta> listaAlerta) {
		this.listaAlerta = listaAlerta;
	}

	public List<Local> getListaLocal() {
		if (!(isSamePeriod(dataLocal) && !listaLocal.isEmpty())) {
			atualizarLista(new LocalDAO(), dataLocal);
			for (Local l : listaLocal) {
				l.getImagensREST();
			}
		}
		return listaLocal;
	}

	public void setListaLocal(List<Local> listaLocal) {
		this.listaLocal = listaLocal;
	}

	public List<Projeto> getListaProjeto() {
		if (!(isSamePeriod(dataProjeto) && !listaProjeto.isEmpty())) {
			atualizarLista(new ProjetoDAO(), dataProjeto);
			for (Projeto p : listaProjeto) {
				p.getImagensREST();
			}
		}
		return listaProjeto;
	}

	public void setListaProjeto(List<Projeto> listaProjeto) {
		this.listaProjeto = listaProjeto;
	}

	public List<Noticia> getListaNoticia() {
		if (!(isSamePeriod(dataNoticia) && !listaNoticia.isEmpty())) {
			atualizarLista(new NoticiaDAO(), dataNoticia);
			for (Noticia noticia : listaNoticia) {
				noticia.getImagensREST();
			}
		}
		return listaNoticia;
	}

	public void setListaNoticia(List<Noticia> listaNoticia) {
		this.listaNoticia = listaNoticia;
	}

	public List<Esporte> getListaEsporte() {
		if (!(isSamePeriod(dataEsporte) && !listaEsporte.isEmpty())) {
			atualizarLista(new EsporteDAO(), dataEsporte);
			for (Esporte e : listaEsporte) {
				e.getImagensREST();
			}
		}
		return listaEsporte;
	}

	public void setListaEsporte(List<Esporte> listaEsporte) {
		this.listaEsporte = listaEsporte;
	}

	public List<Evento> getListaEvento() {
		if (!(isSamePeriod(dataEvento) && !listaEvento.isEmpty())) {
			atualizarLista(new EventoDAO(), dataEvento);
			for (Evento e : listaEvento) {
				e.getImagensREST();
			}
		}
		return listaEvento;
	}

	public void setListaEvento(List<Evento> listaEvento) {
		this.listaEvento = listaEvento;
	}

	public Paginator getPagLocal() {
		if (pagLocal == null) {
			pagLocal = new Paginator(listaLocal);
		}
		return pagLocal;
	}

	public void setPagLocal(Paginator pagLocal) {
		this.pagLocal = pagLocal;
	}

	public Paginator getPagProjeto() {
		if (pagProjeto == null) {
			pagProjeto = new Paginator(listaProjeto);
		}
		return pagProjeto;
	}

	public void setPagProjeto(Paginator pagProjeto) {
		this.pagProjeto = pagProjeto;
	}

	public Paginator getPagNoticia() {
		if (pagNoticia == null) {
			pagNoticia = new Paginator(listaNoticia);
		}
		return pagNoticia;
	}

	public void setPagNoticia(Paginator pagNoticia) {
		this.pagNoticia = pagNoticia;
	}

	public Paginator getPagEsporte() {
		if (pagEsporte == null) {
			pagEsporte = new Paginator(listaEsporte);
		}
		return pagEsporte;
	}

	public void setPagEsporte(Paginator pagEsporte) {
		this.pagEsporte = pagEsporte;
	}

	public Paginator getPagEvento() {
		if (pagEvento == null) {
			pagEvento = new Paginator(listaEvento);
		}
		return pagEvento;
	}

	public void setPagEvento(Paginator pagEvento) {
		this.pagEvento = pagEvento;
	}

	public Paginator getPagAlerta() {
		if (pagAlerta == null) {
			pagAlerta = new Paginator(listaAlerta);
		}
		return pagAlerta;
	}

	public void setPagAlerta(Paginator pagAlerta) {
		this.pagAlerta = pagAlerta;
	}

}

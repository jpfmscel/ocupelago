package br.managedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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

	@PostConstruct
	public void init() {
		setListaEsporte((List<Esporte>) new EsporteDAO().findAll());
		setListaLocal((List<Local>) new LocalDAO().findAll());
		setListaNoticia((List<Noticia>) new NoticiaDAO().findAll());
		setListaProjeto((List<Projeto>) new ProjetoDAO().findAll());
		setListaAlerta((List<Alerta>) new AlertaDAO().getListaInicial());
		setListaEvento((List<Evento>) new EventoDAO().findAll());

	}

	public boolean isSamePeriod(Date d1) {
		Date d2 = new Date();
		long seconds = (d2.getTime() - d1.getTime()) / 1000;

		return seconds <= 300;
	}

	public boolean isSameMinute(Date d1) {
		Date d2 = new Date();
		long seconds = (d2.getTime() - d1.getTime()) / 1000;

		return seconds <= 60;
	}

	@SuppressWarnings("unchecked")
	public <T> void atualizarLista(BaseDao<T> b, Date d) {
		if (b instanceof EsporteDAO) {
			if (!isSamePeriod(dataEsporte)) {
				setListaEsporte((List<Esporte>) b.findAll());
				dataEsporte = new Date();
			}
		} else if (b instanceof LocalDAO) {
			if (!isSamePeriod(dataLocal)) {
				setListaLocal((List<Local>) b.findAll());
				dataLocal = new Date();
			}
		} else if (b instanceof NoticiaDAO) {
			if (!isSamePeriod(dataNoticia)) {
				setListaNoticia((List<Noticia>) b.findAll());
				dataNoticia = new Date();
			}
		} else if (b instanceof ProjetoDAO) {
			if (!isSamePeriod(dataProjeto)) {
				setListaProjeto((List<Projeto>) b.findAll());
				dataProjeto = new Date();
			}
		} else if (b instanceof AlertaDAO) {
			if (!isSameMinute(dataAlerta)) {
				setListaAlerta((List<Alerta>) ((AlertaDAO) b).getListaInicial());
				dataAlerta = new Date();
			}
		} else if (b instanceof EventoDAO) {
			if (!isSamePeriod(dataEvento)) {
				setListaEvento((List<Evento>) b.findAll());
				dataEvento = new Date();
			}
		}

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

}

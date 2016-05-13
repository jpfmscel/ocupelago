package br.managedBeans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import br.dao.AlertaDAO;
import br.dao.EsporteDAO;
import br.dao.EventoDAO;
import br.dao.LocalDAO;
import br.dao.NoticiaDAO;
import br.dao.ProjetoDAO;

@ManagedBean
@ApplicationScoped
public class ManagedBeanGenerico {

	@ManagedProperty(value = "#{listFactory}")
	private ListFactory listFactory;

	public void atualizarEsportes() {
		listFactory.setListaEsporte(new EsporteDAO().findAll());
	}

	public void atualizarAlertas() {
		listFactory.setListaAlerta(new AlertaDAO().findAll());
	}

	public void atualizarEventos() {
		listFactory.setListaEvento(new EventoDAO().findAll());
	}

	public void atualizarProjetos() {
		listFactory.setListaProjeto(new ProjetoDAO().findAll());
	}

	public void atualizarNoticias() {
		listFactory.setListaNoticia(new NoticiaDAO().findAll());
	}

	public void atualizarLocais() {
		listFactory.setListaLocal(new LocalDAO().findAll());
	}
	
	public ListFactory getListFactory() {
		return listFactory;
	}

	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}

}

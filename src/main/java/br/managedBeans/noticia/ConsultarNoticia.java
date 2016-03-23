package br.managedBeans.noticia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.dao.NoticiaDAO;
import br.entidades.Noticia;

@ViewScoped
@ManagedBean(name = "consultarNoticia")
public class ConsultarNoticia implements Serializable{

	private static final long serialVersionUID = -2333684779244788471L;
	private List<Noticia> noticias;
	private Noticia noticiaSelected;
	private NoticiaDAO noticiaDAO;

	@PostConstruct
	public void atualizarNoticias() {
		setNoticias(null);
		setNoticiaSelected(null);
		getNoticias().addAll(getNoticiaDAO().findAll());
	}

	public List<Noticia> getNoticias() {
		if (noticias == null) {
			noticias = new ArrayList<Noticia>();
		}
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public Noticia getNoticiaSelected() {
		if (noticiaSelected == null) {
			noticiaSelected = new Noticia();
		}
		return noticiaSelected;
	}

	public void setNoticiaSelected(Noticia noticiaSelected) {
		this.noticiaSelected = noticiaSelected;
	}

	public NoticiaDAO getNoticiaDAO() {
		if (noticiaDAO == null) {
			noticiaDAO = new NoticiaDAO();
		}
		return noticiaDAO;
	}

	public void setNoticiaDAO(NoticiaDAO noticiaDAO) {
		this.noticiaDAO = noticiaDAO;
	}
}

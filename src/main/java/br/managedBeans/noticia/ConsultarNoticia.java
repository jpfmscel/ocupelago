package br.managedBeans.noticia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.dao.NoticiaDAO;
import br.entidades.Imagem;
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

	public String editarNoticia(Noticia e) {
		setNoticiaSelected(e);
		return "editarNoticia.xhtml";
	}

	public String excluirNoticia(Noticia e) {
		try {
			setNoticiaSelected(e);
			getNoticiaSelected().setAtivo(false);
			updateNoticia();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Noticia excluída com sucesso!", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a noticia : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setNoticiaSelected(null);
		return "consultaNoticia.xhtml";
	}

	public String updateNoticia() {
		try {
			getNoticiaDAO().iniciarTransacao();
			getNoticiaDAO().update(getNoticiaSelected());
			getNoticiaDAO().comitarTransacao();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Noticia atualizada com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a noticia : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setNoticiaSelected(null);
		return "consultaNoticia.xhtml";
	}
	
	public void removerImagem(Imagem i) {
		if (getNoticiaSelected().getImagens().contains(i)) {
			getNoticiaSelected().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setNoticia(getNoticiaSelected());
		i.setDataCriado(new Date());
		getNoticiaSelected().getImagens().add(i);
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

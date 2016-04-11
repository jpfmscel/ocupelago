package br.managedBeans.noticia;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.dao.NoticiaDAO;
import br.entidades.Imagem;
import br.entidades.Noticia;

@SessionScoped
@ManagedBean(name = "consultarNoticia")
public class ConsultarNoticia implements Serializable {

	private static final long serialVersionUID = -2333684779244788471L;
	private Noticia noticiaSelected;
	private NoticiaDAO noticiaDAO;

	@PostConstruct
	public void atualizarNoticias() {
		setNoticiaSelected(null);
	}

	public String editarNoticia(Noticia e) {
		setNoticiaSelected(e);
		return "editarNoticia.xhtml";
	}

	public String excluirNoticia(Noticia e) {
		try {
			setNoticiaSelected(e);
			getNoticiaSelected().setAtivo(false);
			update();
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
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Noticia atualizada com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a noticia : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setNoticiaSelected(null);
		return "consultaNoticia.xhtml";
	}

	private void update() {
		getNoticiaDAO().iniciarTransacao();
		getNoticiaDAO().update(getNoticiaSelected());
		getNoticiaDAO().comitarTransacao();
	}

	public void filtrarURLYoutube() {
		String urlFinal = getNoticiaSelected().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getNoticiaSelected().setVideoURL(urlFinal);
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
		i.setDataCriado(new Date());
		getNoticiaSelected().getImagens().add(i);
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

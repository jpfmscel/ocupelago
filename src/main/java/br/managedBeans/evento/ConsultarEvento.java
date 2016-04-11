package br.managedBeans.evento;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.dao.EventoDAO;
import br.entidades.Evento;
import br.entidades.Imagem;

@SessionScoped
@ManagedBean(name = "consultarEvento")
public class ConsultarEvento implements Serializable {

	private static final long serialVersionUID = -2333684779244788471L;
	private Evento eventoSelected;
	private EventoDAO eventoDAO;

	@PostConstruct
	public void atualizarEventos() {
		setEventoSelected(null);
	}

	public String editarEvento(Evento e) {
		setEventoSelected(e);
		return "editarEvento.xhtml";
	}

	public String excluirEvento(Evento e) {
		try {
			setEventoSelected(e);
			getEventoSelected().setAtivo(false);
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento excluída com sucesso!", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a evento : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setEventoSelected(null);
		return "consultaEvento.xhtml";
	}

	public String updateEvento() {
		try {
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento atualizada com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a evento : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setEventoSelected(null);
		return "consultaEvento.xhtml";
	}

	private void update() {
		getEventoDAO().iniciarTransacao();
		getEventoDAO().update(getEventoSelected());
		getEventoDAO().comitarTransacao();
	}

	public void removerImagem(Imagem i) {
		if (getEventoSelected().getImagens().contains(i)) {
			getEventoSelected().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getEventoSelected().getImagens().add(i);
	}

	public void filtrarURLYoutube() {
		String urlFinal = getEventoSelected().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getEventoSelected().setVideoURL(urlFinal);
	}

	public Evento getEventoSelected() {
		if (eventoSelected == null) {
			eventoSelected = new Evento();
		}
		return eventoSelected;
	}

	public void setEventoSelected(Evento eventoSelected) {
		this.eventoSelected = eventoSelected;
	}

	public EventoDAO getEventoDAO() {
		if (eventoDAO == null) {
			eventoDAO = new EventoDAO();
		}
		return eventoDAO;
	}

	public void setEventoDAO(EventoDAO eventoDAO) {
		this.eventoDAO = eventoDAO;
	}
}

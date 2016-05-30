package br.managedBeans.evento;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.dao.EventoDAO;
import br.entidades.Evento;
import br.entidades.Imagem;
import br.managedBeans.LoginBean;
import br.managedBeans.ManagedBeanGenerico;
import br.util.Util;

@SessionScoped
@ManagedBean(name = "consultarEvento")
public class ConsultarEvento extends ManagedBeanGenerico implements Serializable {

	private static final long serialVersionUID = -2333684779244788471L;
	private Evento eventoSelected;
	private EventoDAO eventoDAO;

	private Logger log = Logger.getGlobal();

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void iniciarEventos() {
		atualizarEventos();
		setEventoSelected(null);
	}

	public String detalharEvento(Evento e) {
		setEventoSelected(e);
		return "detalheEvento.xhtml";
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
			log.log(Level.INFO, "Evento " + e.toString() + " excluído com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento excluído com sucesso!", null));
		} catch (Exception ex) {
			log.log(Level.INFO, "Evento " + e.toString() + " com erro!");
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir o evento : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setEventoSelected(null);
		atualizarEventos();
		return "consultaEvento.xhtml";
	}

	public String updateEvento() {
		try {
			if (getEventoDAO().buscarPorNome(getEventoSelected().getNome()).size() > 1) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Evento já existe!", null));
				return null;
			} else {
				if (!Util.isDataOk(getEventoSelected().getDataInicio(), getEventoSelected().getDataFim())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data fim não pode ser anterior à data inicial! ", null));
					return null;
				}
				update();
				log.log(Level.INFO, "Evento " + getEventoSelected().toString() + " atualizado com sucesso!");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento atualizado com sucesso!", null));
			}
		} catch (Exception e) {
			log.log(Level.INFO, "Evento " + e.toString() + " com erro!");
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao editar o evento : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setEventoSelected(null);
		atualizarEventos();
		return "consultaEvento.xhtml";
	}

	private void update() {
		log.log(Level.INFO, "Usuário " + getLoginBean().getUsuarioLogado().getEmail());
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

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
}

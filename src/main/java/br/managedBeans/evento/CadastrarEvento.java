package br.managedBeans.evento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.dao.EventoDAO;
import br.entidades.Evento;
import br.entidades.Imagem;
import br.entidades.Local;
import br.managedBeans.ListFactory;
import br.managedBeans.LoginBean;
import br.util.Util;

@ViewScoped
@ManagedBean(name = "cadastrarEvento")
public class CadastrarEvento implements Serializable {

	private static final long serialVersionUID = -4742047936382758532L;

	private Evento evento;
	private EventoDAO eventoDAO;

	private Logger log = Logger.getGlobal();

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	@ManagedProperty(value = "#{listFactory}")
	private ListFactory listFactory;

	public String adicionarEvento() {
		try {
			if (!getEventoDAO().buscarPorNome(getEvento().getNome()).isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Evento já existe!", null));
				return null;
			} else {
				if (!Util.isDataOk(getEvento().getDataInicio(), getEvento().getDataFim())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data fim não pode ser anterior à data inicial! ", null));
					return null;
				}
				fixURL();
				getEventoDAO().iniciarTransacao();
				getEventoDAO().update(getEvento());
				getEventoDAO().comitarTransacao();
				log.log(Level.INFO, "Usuário " + getLoginBean().getUsuarioLogado().getEmail());
				log.log(Level.INFO, "Evento " + getEvento().toString() + " cadastrado com sucesso!");
				setEvento(null);
				getListFactory().atualizarLista(new EventoDAO(), new Date());
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o evento : " + e.getCause(), null));
			log.log(Level.SEVERE, "Evento " + getEvento().toString() + " com erro!");
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento cadastrado com sucesso!", null));
		return "consultaEvento.xhtml";
	}

	private void fixURL() {
		getEvento().setURL_facebook(Util.fixExternalURL(getEvento().getURL_facebook()));
		getEvento().setURL_twitter(Util.fixExternalURL(getEvento().getURL_twitter()));
		getEvento().setURL_site(Util.fixExternalURL(getEvento().getURL_site()));
		getEvento().setURL_youtube(Util.fixExternalURL(getEvento().getURL_youtube()));
	}

	public byte[] getBytesFromFile(UploadedFile f) {
		return f.getContents();
	}

	public void filtrarURLYoutube() {
		String urlFinal = getEvento().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getEvento().setVideoURL(urlFinal);
	}

	public void removerImagem(Imagem i) {
		if (getEvento().getImagens().contains(i)) {
			getEvento().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getEvento().getImagens().add(i);
	}

	public List<Local> completeText(String query) {
		List<Local> results = new ArrayList<Local>();
		for (Local local : getListFactory().getListaLocal()) {
			if (local.getNome().toUpperCase().contains(query.toUpperCase())) {
				results.add(local);
			}
		}

		return results;
	}

	public void onItemSelect(SelectEvent event) {
		getEvento().setLocal((Local) event.getObject());
	}

	public Evento getEvento() {
		if (evento == null) {
			evento = new Evento();
		}
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
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

	public ListFactory getListFactory() {
		return listFactory;
	}

	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}

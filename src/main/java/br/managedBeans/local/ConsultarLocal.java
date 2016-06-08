package br.managedBeans.local;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.dao.LocalDAO;
import br.dao.LocalEsporteDAO;
import br.entidades.Esporte;
import br.entidades.Imagem;
import br.entidades.Local;
import br.entidades.LocalEsporte;
import br.managedBeans.LoginBean;
import br.managedBeans.ManagedBeanGenerico;

@SessionScoped
@ManagedBean(name = "consultarLocal")
public class ConsultarLocal extends ManagedBeanGenerico implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Local> locais;
	private Local localSelected;
	private LocalDAO localDAO;
	private MapModel mapModel;
	private LocalEsporteDAO localEsporteDAO;

	private Logger log = Logger.getGlobal();

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void iniciarLocais() {
		setLocais(null);
		setLocalSelected(null);
		getLocais().addAll(getLocalDAO().findAll());
	}

	public String detalharLocal(Local e) {
		setLocalSelected(e);
		getLocalSelected().setEsportes(getLocalEsporteDAO().getEsportesByLocal(getLocalSelected().getId()));
		return "detalheLocal.xhtml";
	}

	public String editarLocal(Local e) {
		setLocalSelected(e);
		getLocalSelected().setEsportes(getLocalEsporteDAO().getEsportesByLocal(getLocalSelected().getId()));
		return "editarLocal.xhtml";
	}

	public String excluirLocal(Local e) {
		try {
			setLocalSelected(e);
			getLocalSelected().setAtivo(false);
			update();
			log.log(Level.INFO, "Local " + e.toString() + " excluído com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Local excluído com sucesso!", null));
		} catch (Exception ex) {
			log.log(Level.INFO, "Local " + e.toString() + " com erro!");
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir o local : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setLocalSelected(null);
		atualizarLocais();
		return "consultaLocal.xhtml";
	}

	public String updateLocal() {
		try {
			if (getLocalDAO().buscarPorNome(getLocalSelected().getNome()).size() > 1) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Local já existe!", null));
				return null;
			} else {
				update();

				getLocalEsporteDAO().iniciarTransacao();
				for (LocalEsporte e : getLocalEsporteDAO().getLocalEsportes(getLocalSelected().getId())) {
					getLocalEsporteDAO().remover(e);
				}
				for (Esporte esporte : getLocalSelected().getEsportes()) {
					getLocalEsporteDAO().inserir(new LocalEsporte(esporte, getLocalSelected()));
				}
				getLocalEsporteDAO().comitarTransacao();

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Local atualizado com sucesso!", null));
				log.log(Level.INFO, "Local " + getLocalSelected().toString() + " atualizado com sucesso!");
			}
		} catch (Exception e) {
			log.log(Level.INFO, "Local " + getLocalSelected().toString() + " com erro!");
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao editar o local : " + e.getMessage(), null));
			return null;
		}
		setLocalSelected(null);
		atualizarLocais();
		return "consultaLocal.xhtml";
	}

	private void update() {
		log.log(Level.INFO, "Usuário " + getLoginBean().getUsuarioLogado().getEmail());
		getLocalDAO().iniciarTransacao();
		getLocalDAO().update(getLocalSelected());
		getLocalDAO().comitarTransacao();
	}

	public void filtrarURLYoutube() {
		String urlFinal = getLocalSelected().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getLocalSelected().setVideoURL(urlFinal);
	}

	public void removerImagem(Imagem i) {
		if (getLocalSelected().getImagens().contains(i)) {
			getLocalSelected().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getLocalSelected().getImagens().add(i);
	}

	public List<Local> getLocais() {
		if (locais == null) {
			locais = new ArrayList<Local>();
		}
		return locais;
	}

	public void setLocais(List<Local> locals) {
		this.locais = locals;
	}

	public Local getLocalSelected() {
		if (localSelected == null) {
			localSelected = new Local();
		}
		return localSelected;
	}

	public void setLocalSelected(Local localSelected) {
		this.localSelected = localSelected;
	}

	public LocalDAO getLocalDAO() {
		if (localDAO == null) {
			localDAO = new LocalDAO();
		}
		return localDAO;
	}

	public void setLocalDAO(LocalDAO localDAO) {
		this.localDAO = localDAO;
	}

	public MapModel getMapModel() {
		LatLng latlng = new LatLng(getLocalSelected().getLatitude(), getLocalSelected().getLongitude());
		Marker marker = new Marker(latlng);
		setMapModel(new DefaultMapModel());
		mapModel.addOverlay(marker);
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public LocalEsporteDAO getLocalEsporteDAO() {
		if (localEsporteDAO == null) {
			localEsporteDAO = new LocalEsporteDAO();
		}
		return localEsporteDAO;
	}

	public void setLocalEsporteDAO(LocalEsporteDAO localEsporteDAO) {
		this.localEsporteDAO = localEsporteDAO;
	}
}

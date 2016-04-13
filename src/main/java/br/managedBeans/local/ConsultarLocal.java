package br.managedBeans.local;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.dao.LocalDAO;
import br.entidades.Imagem;
import br.entidades.Local;

@SessionScoped
@ManagedBean(name = "consultarLocal")
public class ConsultarLocal {

	private List<Local> locais;
	private Local localSelected;
	private LocalDAO localDAO;
	private MapModel mapModel;

	@PostConstruct
	public void atualizarLocais() {
		setLocais(null);
		setLocalSelected(null);
		getLocais().addAll(getLocalDAO().findAll());
	}

	public String detalharLocal(Local e) {
		setLocalSelected(e);
		return "detalheLocal.xhtml";
	}
	
	public String editarLocal(Local e) {
		setLocalSelected(e);
		return "editarLocal.xhtml";
	}

	public String excluirLocal(Local e) {
		try {
			setLocalSelected(e);
			getLocalSelected().setAtivo(false);
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Local excluído com sucesso!", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o local : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setLocalSelected(null);
		return "consultaLocal.xhtml";
	}

	public String updateLocal() {
		try {
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Local atualizado com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o local : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setLocalSelected(null);
		return "consultaLocal.xhtml";
	}

	private void update() {
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
}

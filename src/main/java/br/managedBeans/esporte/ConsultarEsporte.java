package br.managedBeans.esporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.dao.EsporteDAO;
import br.entidades.Esporte;
import br.entidades.Imagem;
import br.util.Util;

@SessionScoped
@ManagedBean(name = "consultarEsporte")
public class ConsultarEsporte implements Serializable {

	private static final long serialVersionUID = 5362883764686822609L;
	private List<Esporte> esportes;
	private Esporte esporteSelected;
	private EsporteDAO esporteDAO;
	private String tipoEsporte;

	@PostConstruct
	public void atualizarEsporte() {
		setEsportes(null);
		setEsporteSelected(null);
		getEsportes().addAll(getEsporteDAO().findAll());
	}

	public String detalharEsporte(Esporte e) {
		setEsporteSelected(e);
		atualizaTipoEsporte();
		return "detalheEsporte.xhtml";
	}

	public String editarEsporte(Esporte e) {
		setEsporteSelected(e);
		atualizaTipoEsporte();
		return "editarEsporte.xhtml";
	}

	public String excluirEsporte(Esporte e) {
		try {
			setEsporteSelected(e);
			getEsporteSelected().setAtivo(false);
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Esporte excluído com sucesso!", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir o esporte : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setEsporteSelected(null);
		return "consultaEsporte.xhtml";
	}

	public String updateEsporte() {
		try {
			fixURL();
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Esporte atualizado com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar o esporte : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setEsporteSelected(null);
		return "consultaEsporte.xhtml";
	}

	private void update() {
		getEsporteDAO().iniciarTransacao();
		getEsporteDAO().update(getEsporteSelected());
		getEsporteDAO().comitarTransacao();
	}

	private void atualizaTipoEsporte() {
		if (getEsporteSelected().isAereo()) {
			setTipoEsporte("aereo");
		} else if (getEsporteSelected().isAquatico()) {
			setTipoEsporte("aquatico");
		} else if (getEsporteSelected().isTerrestre()) {
			setTipoEsporte("terrestre");
		}
	}

	private void fixURL() {
		getEsporteSelected().setURL_facebook(Util.fixExternalURL(getEsporteSelected().getURL_facebook()));
		getEsporteSelected().setURL_twitter(Util.fixExternalURL(getEsporteSelected().getURL_twitter()));
		getEsporteSelected().setURL_site(Util.fixExternalURL(getEsporteSelected().getURL_site()));
		getEsporteSelected().setURL_youtube(Util.fixExternalURL(getEsporteSelected().getURL_youtube()));
	}

	public void filtrarURLYoutube() {
		String urlFinal = getEsporteSelected().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getEsporteSelected().setVideoURL(urlFinal);
	}

	public void removerImagem(Imagem i) {
		if (getEsporteSelected().getImagens().contains(i)) {
			getEsporteSelected().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getEsporteSelected().getImagens().add(i);
	}

	public List<Esporte> getEsportes() {
		if (esportes == null) {
			esportes = new ArrayList<Esporte>();
		}
		return esportes;
	}

	public void setEsportes(List<Esporte> esportes) {
		this.esportes = esportes;
	}

	public Esporte getEsporteSelected() {
		if (esporteSelected == null) {
			esporteSelected = new Esporte();
		}
		return esporteSelected;
	}

	public void setEsporteSelected(Esporte esporteSelected) {
		this.esporteSelected = esporteSelected;
	}

	public EsporteDAO getEsporteDAO() {
		if (esporteDAO == null) {
			esporteDAO = new EsporteDAO();
		}
		return esporteDAO;
	}

	public void setEsporteDAO(EsporteDAO esporteDAO) {
		this.esporteDAO = esporteDAO;
	}

	public String getTipoEsporte() {
		if (tipoEsporte == null) {
			tipoEsporte = "aereo";
		}
		return tipoEsporte;
	}

	public void setTipoEsporte(String tipoEsporte) {
		this.tipoEsporte = tipoEsporte;
	}

}

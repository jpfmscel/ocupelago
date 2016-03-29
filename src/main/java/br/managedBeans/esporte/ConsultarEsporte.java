package br.managedBeans.esporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.dao.EsporteDAO;
import br.entidades.Esporte;

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

	public String editarEsporte(Esporte e) {
		setEsporteSelected(e);
		atualizaTipoEsporte();
		return "editarEsporte.xhtml";
	}

	public String excluirEsporte(Esporte e) {
		try {
			setEsporteSelected(e);
			getEsporteSelected().setAtivo(false);
			updateEsporte();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Esporte excluído com sucesso!", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o esporte : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setEsporteSelected(null);
		return "consultaEsporte.xhtml";
	}

	public String updateEsporte() {
		try {
			getEsporteDAO().iniciarTransacao();
			getEsporteDAO().update(getEsporteSelected());
			getEsporteDAO().comitarTransacao();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Esporte atualizado com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o esporte : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setEsporteSelected(null);
		return "consultaEsporte.xhtml";
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

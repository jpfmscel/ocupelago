package br.managedBeans.esporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.dao.EsporteDAO;
import br.entidades.Esporte;

@ViewScoped
@ManagedBean(name = "consultarEsporte")
public class ConsultarEsporte implements Serializable{

	private static final long serialVersionUID = 5362883764686822609L;
	private List<Esporte> esportes;
	private Esporte esporteSelected;
	private EsporteDAO esporteDAO;

	@PostConstruct
	public void atualizarEsporte() {
		setEsportes(null);
		setEsporteSelected(null);
		getEsportes().addAll(getEsporteDAO().findAll());
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

}

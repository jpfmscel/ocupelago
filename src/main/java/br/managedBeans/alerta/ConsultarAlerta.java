package br.managedBeans.alerta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.dao.AlertaDAO;
import br.entidades.Alerta;

@ViewScoped
@ManagedBean(name = "consultarAlerta")
public class ConsultarAlerta implements Serializable{

	private static final long serialVersionUID = -2236749237134308778L;
	private List<Alerta> alertas;
	private Alerta alertaSelected;
	private AlertaDAO alertaDAO;

	@PostConstruct
	public void atualizarAlertas() {
		setAlertas(null);
		setAlertaSelected(null);
		getAlertas().addAll(getAlertaDAO().findAll());
	}

	public List<Alerta> getAlertas() {
		if (alertas == null) {
			alertas = new ArrayList<Alerta>();
		}
		return alertas;
	}

	public void setAlertas(List<Alerta> alertas) {
		this.alertas = alertas;
	}

	public Alerta getAlertaSelected() {
		if (alertaSelected == null) {
			alertaSelected = new Alerta();
		}
		return alertaSelected;
	}

	public void setAlertaSelected(Alerta alertaSelected) {
		this.alertaSelected = alertaSelected;
	}

	public AlertaDAO getAlertaDAO() {
		if (alertaDAO == null) {
			alertaDAO = new AlertaDAO();
		}
		return alertaDAO;
	}

	public void setAlertaDAO(AlertaDAO alertaDAO) {
		this.alertaDAO = alertaDAO;
	}
}

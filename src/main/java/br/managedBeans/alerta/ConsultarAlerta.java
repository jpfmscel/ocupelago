package br.managedBeans.alerta;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.dao.AlertaDAO;
import br.entidades.Alerta;

@ViewScoped
@ManagedBean(name = "consultarAlerta")
public class ConsultarAlerta implements Serializable{

	private static final long serialVersionUID = -2236749237134308778L;
	private Alerta alertaSelected;
	private AlertaDAO alertaDAO;

	@PostConstruct
	public void atualizarAlertas() {
		setAlertaSelected(null);
	}

	public String editarAlerta(Alerta e) {
		setAlertaSelected(e);
		return "editarAlerta.xhtml";
	}

	public String excluirAlerta(Alerta e) {
		try {
			setAlertaSelected(e);
			getAlertaSelected().setAtivo(false);
			updateAlerta();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta excluída com sucesso!", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a alerta : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setAlertaSelected(null);
		return "consultaAlerta.xhtml";
	}

	public String updateAlerta() {
		try {
			getAlertaDAO().iniciarTransacao();
			getAlertaDAO().update(getAlertaSelected());
			getAlertaDAO().comitarTransacao();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta atualizada com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a alerta : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setAlertaSelected(null);
		return "consultaAlerta.xhtml";
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

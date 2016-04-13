package br.managedBeans.alerta;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.dao.AlertaDAO;
import br.entidades.Alerta;
import br.managedBeans.ListFactory;

@ViewScoped
@ManagedBean(name = "consultarAlerta")
public class ConsultarAlerta implements Serializable{

	private static final long serialVersionUID = -2236749237134308778L;
	private Alerta alertaSelected;
	private AlertaDAO alertaDAO;

	@ManagedProperty(value="#{mapBean}")
	private CadastrarAlerta cadastrarAlerta;
	
	@ManagedProperty(value = "#{listFactory}")
	private ListFactory listFactory;
	
	@PostConstruct
	public void atualizarAlertas() {
		setAlertaSelected(null);
	}

	public String excluirAlerta(Alerta e) {
		try {
			setAlertaSelected(e);
			getAlertaSelected().setAtivo(false);
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta excluída com sucesso!", null));
			getCadastrarAlerta().setAlerta(null);
			getCadastrarAlerta().init();
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir a alerta : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setAlertaSelected(null);
		return "consultarAlerta.xhtml";
	}

	public String updateAlerta() {
		try {
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta atualizada com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a alerta : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setAlertaSelected(null);
		return "consultaAlerta.xhtml";
	}

	private void update() {
		getAlertaDAO().iniciarTransacao();
		getAlertaDAO().update(getAlertaSelected());
		getAlertaDAO().comitarTransacao();
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

	public CadastrarAlerta getCadastrarAlerta() {
		return cadastrarAlerta;
	}

	public void setCadastrarAlerta(CadastrarAlerta cadastrarAlerta) {
		this.cadastrarAlerta = cadastrarAlerta;
	}

	public ListFactory getListFactory() {
		return listFactory;
	}

	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}
}

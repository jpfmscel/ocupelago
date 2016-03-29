package br.managedBeans.local;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.dao.LocalDAO;
import br.entidades.Local;

@ViewScoped
@ManagedBean(name = "consultarLocal")
public class ConsultarLocal {

	private List<Local> locais;
	private Local localSelected;
	private LocalDAO localDAO;

	@PostConstruct
	public void atualizarLocals() {
		setLocais(null);
		setLocalSelected(null);
		getLocais().addAll(getLocalDAO().findAll());
	}

	public String editarLocal(Local e) {
		setLocalSelected(e);
		return "editarLocal.xhtml";
	}

	public String excluirLocal(Local e) {
		try {
			setLocalSelected(e);
			getLocalSelected().setAtivo(false);
			updateLocal();
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
			getLocalDAO().iniciarTransacao();
			getLocalDAO().update(getLocalSelected());
			getLocalDAO().comitarTransacao();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Local atualizado com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o local : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setLocalSelected(null);
		return "consultaLocal.xhtml";
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
}

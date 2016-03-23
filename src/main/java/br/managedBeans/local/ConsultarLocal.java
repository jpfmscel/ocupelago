package br.managedBeans.local;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

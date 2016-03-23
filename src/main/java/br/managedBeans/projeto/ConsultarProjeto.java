package br.managedBeans.projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.dao.ProjetoDAO;
import br.entidades.Projeto;

@ViewScoped
@ManagedBean(name = "consultarProjeto")
public class ConsultarProjeto implements Serializable{

	private static final long serialVersionUID = -1803143075277938111L;
	private List<Projeto> projetos;
	private Projeto projetoSelected;
	private ProjetoDAO projetoDAO;

	@PostConstruct
	public void atualizarProjetos() {
		setProjetos(null);
		setProjetoSelected(null);
		getProjetos().addAll(getProjetoDAO().findAll());
	}

	public List<Projeto> getProjetos() {
		if (projetos == null) {
			projetos = new ArrayList<Projeto>();
		}
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public Projeto getProjetoSelected() {
		if (projetoSelected == null) {
			projetoSelected = new Projeto();
		}
		return projetoSelected;
	}

	public void setProjetoSelected(Projeto projetoSelected) {
		this.projetoSelected = projetoSelected;
	}

	public ProjetoDAO getProjetoDAO() {
		if (projetoDAO == null) {
			projetoDAO = new ProjetoDAO();
		}
		return projetoDAO;
	}

	public void setProjetoDAO(ProjetoDAO projetoDAO) {
		this.projetoDAO = projetoDAO;
	}
}

package br.managedBeans.projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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

	public String editarProjeto(Projeto e) {
		setProjetoSelected(e);
		return "editarProjeto.xhtml";
	}

	public String excluirProjeto(Projeto e) {
		try {
			setProjetoSelected(e);
			getProjetoSelected().setAtivo(false);
			updateProjeto();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Projeto excluído com sucesso!", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o projeto : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setProjetoSelected(null);
		return "consultaProjeto.xhtml";
	}

	public String updateProjeto() {
		try {
			getProjetoDAO().iniciarTransacao();
			getProjetoDAO().update(getProjetoSelected());
			getProjetoDAO().comitarTransacao();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Projeto atualizado com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o projeto : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setProjetoSelected(null);
		return "consultaProjeto.xhtml";
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

package br.managedBeans.projeto;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.dao.ProjetoDAO;
import br.entidades.Imagem;
import br.entidades.Projeto;

@SessionScoped
@ManagedBean(name = "consultarProjeto")
public class ConsultarProjeto implements Serializable {

	private static final long serialVersionUID = -1803143075277938111L;
	private Projeto projetoSelected;
	private ProjetoDAO projetoDAO;

	@PostConstruct
	public void atualizarProjetos() {
		setProjetoSelected(null);
	}

	public String editarProjeto(Projeto e) {
		setProjetoSelected(e);
		return "editarProjeto.xhtml";
	}

	public String excluirProjeto(Projeto e) {
		try {
			setProjetoSelected(e);
			getProjetoSelected().setAtivo(false);
			update();
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
			update();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Projeto atualizado com sucesso!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o projeto : " + e.getCause().getMessage(), e.getCause().getMessage()));
			return null;
		}
		setProjetoSelected(null);
		return "consultaProjeto.xhtml";
	}

	private void update() {
		getProjetoDAO().iniciarTransacao();
		getProjetoDAO().update(getProjetoSelected());
		getProjetoDAO().comitarTransacao();
	}

	public void filtrarURLYoutube() {
		String urlFinal = getProjetoSelected().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getProjetoSelected().setVideoURL(urlFinal);
	}

	public void removerImagem(Imagem i) {
		if (getProjetoSelected().getImagens().contains(i)) {
			getProjetoSelected().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getProjetoSelected().getImagens().add(i);
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

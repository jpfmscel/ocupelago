package br.managedBeans.projeto;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.dao.ProjetoDAO;
import br.entidades.Imagem;
import br.entidades.Projeto;
import br.managedBeans.ListFactory;
import br.util.Util;

@ViewScoped
@ManagedBean(name = "cadastrarProjeto")
public class CadastrarProjeto implements Serializable {

	private static final long serialVersionUID = -1121239889065920261L;

	private UploadedFile foto;
	private Projeto projeto;
	private ProjetoDAO projetoDAO;

	@ManagedProperty(value = "#{listFactory}")
	private ListFactory listFactory;

	public String adicionarProjeto() {
		try {
			fixURL();
			getProjetoDAO().iniciarTransacao();
			getProjetoDAO().inserir(getProjeto());
			getProjetoDAO().comitarTransacao();
			setProjeto(null);
			getListFactory().atualizarLista(new ProjetoDAO(), new Date());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o projeto : " + e.getCause(), null));
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Projeto cadastrado com sucesso!", null));

		return "consultaProjeto.xhtml";
	}

	private void fixURL() {
		getProjeto().setURL_facebook(Util.fixExternalURL(getProjeto().getURL_facebook()));
		getProjeto().setURL_twitter(Util.fixExternalURL(getProjeto().getURL_twitter()));
		getProjeto().setURL_site(Util.fixExternalURL(getProjeto().getURL_site()));
		getProjeto().setURL_youtube(Util.fixExternalURL(getProjeto().getURL_youtube()));
	}
	
	public void filtrarURLYoutube() {
		String urlFinal = getProjeto().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getProjeto().setVideoURL(urlFinal);
	}

	public void removerImagem(Imagem i) {
		if (getProjeto().getImagens().contains(i)) {
			getProjeto().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getProjeto().getImagens().add(i);
	}

	public byte[] getBytesFromFile(UploadedFile f) {
		return f.getContents();
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}

	public Projeto getProjeto() {
		if (projeto == null) {
			projeto = new Projeto();
		}
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
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

	public ListFactory getListFactory() {
		return listFactory;
	}

	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}

}

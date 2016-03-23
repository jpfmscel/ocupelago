package br.managedBeans.projeto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.dao.ProjetoDAO;
import br.entidades.Projeto;
import br.util.Util;

@ViewScoped
@ManagedBean(name = "cadastrarProjeto")
public class CadastrarProjeto implements Serializable {

	private static final long serialVersionUID = -1121239889065920261L;

	private UploadedFile foto;
	private Projeto projeto;
	private ProjetoDAO projetoDAO;

	public String adicionarProjeto() {
		try {
			atualizaFilePath();
			getProjetoDAO().iniciarTransacao();
			getProjetoDAO().inserir(getProjeto());
			getProjetoDAO().comitarTransacao();
			gravarFotoDisco(getProjeto().getFilePath());
			setProjeto(null);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao inserir o projeto : "
									+ e.getCause().getMessage(), e.getCause()
									.getMessage()));
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Projeto cadastrado com sucesso!", null));

		return "consultaProjeto.xhtml";
	}

	public void filtrarURLYoutube() {
		String urlFinal = getProjeto().getVideoURL().replace("watch?", "")
				.replace("v=", "v/");
		getProjeto().setVideoURL(urlFinal);
	}

	public void removerImagem(ActionEvent actionEvent) {
		setFoto(null);
		getProjeto().setFoto(null);
	}

	public void handleFileUpload(FileUploadEvent event) {
		setFoto(event.getFile());
		getProjeto().setFoto(getBytesFromFile(event.getFile()));
	}

	private void atualizaFilePath() {
		if (getFoto() != null) {
			getProjeto().setFoto(getBytesFromFile(getFoto()));
			String filepath = Util.getFilePath() + ""
					+ getFoto().getFileName();
			getProjeto().setFilePath(filepath);
		}
	}

	private void gravarFotoDisco(String filepath) throws IOException {
		if (getFoto() != null) {
			FileOutputStream fos = null;
			fos = new FileOutputStream(filepath);
			fos.write(getProjeto().getFoto());
			fos.close();
		}
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

}

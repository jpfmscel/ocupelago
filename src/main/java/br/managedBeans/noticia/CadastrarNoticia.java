package br.managedBeans.noticia;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.dao.NoticiaDAO;
import br.entidades.Noticia;
import br.util.Util;

@ViewScoped
@ManagedBean(name = "cadastrarNoticia")
public class CadastrarNoticia implements Serializable {

	private static final long serialVersionUID = -4742047936382758532L;

	private Noticia noticia;
	private NoticiaDAO noticiaDAO;
	private UploadedFile foto;

	public String adicionarNoticia() {
		try {
			atualizaFilePath();
			getNoticia().setCriadoEm(getNovaDataByLocale());
			getNoticiaDAO().iniciarTransacao();
			getNoticiaDAO().inserir(getNoticia());
			getNoticiaDAO().comitarTransacao();
			gravarFotoDisco(getNoticia().getFilePath());
			setNoticia(null);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao inserir a notícia : "
									+ e.getCause().getMessage(), e.getCause()
									.getMessage()));
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Notícia cadastrada com sucesso!", null));

		return "consultaNoticia.xhtml";
	}

	private Date getNovaDataByLocale() {
		return Calendar.getInstance(
				new Locale(System.getProperty("user.language.format"), System
						.getProperty("user.country.format"))).getTime();
	}

	private void atualizaFilePath() {
		if (getFoto() != null) {
			getNoticia().setFoto(getBytesFromFile(getFoto()));
			String filepath = Util.getFilePath() + ""
					+ getFoto().getFileName();
			getNoticia().setFilePath(filepath);
		}
	}

	public byte[] getBytesFromFile(UploadedFile f) {
		return f.getContents();
	}

	public void filtrarURLYoutube() {
		String urlFinal = getNoticia().getVideoURL().replace("watch?", "")
				.replace("v=", "v/");
		getNoticia().setVideoURL(urlFinal);
	}

	public void removerImagem(ActionEvent actionEvent) {
		setFoto(null);
		getNoticia().setFoto(null);
	}

	public void handleFileUpload(FileUploadEvent event) {
		setFoto(event.getFile());
		getNoticia().setFoto(getBytesFromFile(event.getFile()));
	}

	private void gravarFotoDisco(String filepath) throws IOException {
		if (getFoto() != null) {
			FileOutputStream fos = null;
			fos = new FileOutputStream(filepath);
			fos.write(getNoticia().getFoto());
			fos.close();
		}
	}

	public Noticia getNoticia() {
		if (noticia == null) {
			noticia = new Noticia();
		}
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public NoticiaDAO getNoticiaDAO() {
		if (noticiaDAO == null) {
			noticiaDAO = new NoticiaDAO();
		}
		return noticiaDAO;
	}

	public void setNoticiaDAO(NoticiaDAO noticiaDAO) {
		this.noticiaDAO = noticiaDAO;
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}

}

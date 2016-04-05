package br.managedBeans.noticia;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.dao.NoticiaDAO;
import br.entidades.Imagem;
import br.entidades.Noticia;

@ViewScoped
@ManagedBean(name = "cadastrarNoticia")
public class CadastrarNoticia implements Serializable {

	private static final long serialVersionUID = -4742047936382758532L;

	private Noticia noticia;
	private NoticiaDAO noticiaDAO;

	public String adicionarNoticia() {
		try {
			getNoticia().setCriadoEm(getNovaDataByLocale());
			getNoticiaDAO().iniciarTransacao();
			getNoticiaDAO().inserir(getNoticia());
			getNoticiaDAO().comitarTransacao();
			setNoticia(null);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a notícia : " + e.getCause().getMessage(), e.getCause().getMessage()));
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Notícia cadastrada com sucesso!", null));
		return "consultaNoticia.xhtml";
	}

	private Date getNovaDataByLocale() {
		return Calendar.getInstance(new Locale(System.getProperty("user.language.format"), System.getProperty("user.country.format"))).getTime();
	}

	public byte[] getBytesFromFile(UploadedFile f) {
		return f.getContents();
	}

	public void filtrarURLYoutube() {
		String urlFinal = getNoticia().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getNoticia().setVideoURL(urlFinal);
	}

	public void removerImagem(Imagem i) {
		if (getNoticia().getImagens().contains(i)) {
			getNoticia().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setNoticia(getNoticia());
		i.setDataCriado(new Date());
		getNoticia().getImagens().add(i);
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

}

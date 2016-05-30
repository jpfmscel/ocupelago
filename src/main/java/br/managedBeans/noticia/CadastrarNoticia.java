package br.managedBeans.noticia;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.dao.NoticiaDAO;
import br.entidades.Imagem;
import br.entidades.Noticia;
import br.managedBeans.ListFactory;
import br.managedBeans.LoginBean;
import br.managedBeans.ManagedBeanGenerico;
import br.util.Util;

@ViewScoped
@ManagedBean(name = "cadastrarNoticia")
public class CadastrarNoticia extends ManagedBeanGenerico implements Serializable {

	private static final long serialVersionUID = -4742047936382758532L;

	private Noticia noticia;
	private NoticiaDAO noticiaDAO;

	private Logger log = Logger.getGlobal();

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public String adicionarNoticia() {
		try {
			if (!getNoticiaDAO().buscarPorNome(getNoticia().getTitulo()).isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Noticia j� existe!", null));
				return null;
			} else {
				getNoticia().setCriadoEm(new Date());
				fixURL();
				getNoticiaDAO().iniciarTransacao();
				getNoticiaDAO().inserir(getNoticia());
				getNoticiaDAO().comitarTransacao();
				log.log(Level.INFO, "Usu�rio " + getLoginBean().getUsuarioLogado().getEmail());
				log.log(Level.INFO, "Not�cia " + getNoticia().toString() + " cadastrada com sucesso!");
				setNoticia(null);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a not�cia : " + e.getCause(), null));
			log.log(Level.SEVERE, "Not�cia " + getNoticia().toString() + " com erro!");
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Not�cia cadastrada com sucesso!", null));
		atualizarNoticias();
		return "consultaNoticia.xhtml";
	}

	private void fixURL() {
		getNoticia().setURL_facebook(Util.fixExternalURL(getNoticia().getURL_facebook()));
		getNoticia().setURL_twitter(Util.fixExternalURL(getNoticia().getURL_twitter()));
		getNoticia().setURL_site(Util.fixExternalURL(getNoticia().getURL_site()));
		getNoticia().setURL_youtube(Util.fixExternalURL(getNoticia().getURL_youtube()));
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

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}

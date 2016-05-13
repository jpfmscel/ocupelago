package br.managedBeans.noticia;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.dao.NoticiaDAO;
import br.entidades.Imagem;
import br.entidades.Noticia;
import br.managedBeans.LoginBean;
import br.managedBeans.ManagedBeanGenerico;

@SessionScoped
@ManagedBean(name = "consultarNoticia")
public class ConsultarNoticia extends ManagedBeanGenerico implements Serializable {

	private static final long serialVersionUID = -2333684779244788471L;
	private Noticia noticiaSelected;
	private NoticiaDAO noticiaDAO;

	private Logger log = Logger.getGlobal();

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void iniciarNoticias() {
		setNoticiaSelected(null);
		atualizarNoticias();
	}

	public String detalharNoticia(Noticia e) {
		setNoticiaSelected(e);
		return "detalheNoticia.xhtml";
	}

	public String editarNoticia(Noticia e) {
		setNoticiaSelected(e);
		return "editarNoticia.xhtml";
	}

	public String excluirNoticia(Noticia e) {
		try {
			setNoticiaSelected(e);
			getNoticiaSelected().setAtivo(false);
			update();
			log.log(Level.INFO, "Noticia " + e.toString() + " excluída com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Noticia excluído com sucesso!", null));
		} catch (Exception ex) {
			ex.printStackTrace();
			log.log(Level.INFO, "Noticia " + e.toString() + " com erro!");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a notícia : " + ex.getCause().getMessage(), null));
			return null;
		}
		setNoticiaSelected(null);
		atualizarNoticias();
		return "consultaNoticia.xhtml";
	}

	public String updateNoticia() {
		try {
			if (!getNoticiaDAO().buscarPorNome(getNoticiaSelected().getTitulo()).isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Noticia já existe!", null));
				return null;
			} else {
				update();
				log.log(Level.INFO, "Noticia " + getNoticiaSelected().toString() + " excluída com sucesso!");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Noticia atualizada com sucesso!", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.INFO, "Noticia " + getNoticiaSelected().toString() + " com erro!");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir a notícia : " + e.getCause().getMessage(), null));
			return null;
		}
		setNoticiaSelected(null);
		atualizarNoticias();
		return "consultaNoticia.xhtml";
	}

	private void update() {
		log.log(Level.INFO, "Usuário " + getLoginBean().getUsuarioLogado().getEmail());
		getNoticiaDAO().iniciarTransacao();
		getNoticiaDAO().update(getNoticiaSelected());
		getNoticiaDAO().comitarTransacao();
	}

	public void filtrarURLYoutube() {
		String urlFinal = getNoticiaSelected().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getNoticiaSelected().setVideoURL(urlFinal);
	}

	public void removerImagem(Imagem i) {
		if (getNoticiaSelected().getImagens().contains(i)) {
			getNoticiaSelected().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getNoticiaSelected().getImagens().add(i);
	}

	public Noticia getNoticiaSelected() {
		if (noticiaSelected == null) {
			noticiaSelected = new Noticia();
		}
		return noticiaSelected;
	}

	public void setNoticiaSelected(Noticia noticiaSelected) {
		this.noticiaSelected = noticiaSelected;
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

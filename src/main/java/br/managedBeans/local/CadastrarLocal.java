package br.managedBeans.local;

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
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.dao.LocalDAO;
import br.entidades.Imagem;
import br.entidades.Local;
import br.managedBeans.LoginBean;
import br.managedBeans.ManagedBeanGenerico;
import br.util.Util;

@ViewScoped
@ManagedBean(name = "cadastrarLocal")
public class CadastrarLocal extends ManagedBeanGenerico implements Serializable {

	private static final long serialVersionUID = 734069129117081739L;

	private Local local;
	private LocalDAO localDAO;
	private MapModel mapModel;
	private boolean cnpjSet = false;

	private Logger log = Logger.getGlobal();

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public String adicionarLocal() {
		try {
			if (!getLocalDAO().buscarPorNome(getLocal().getNome()).isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Local já existe!", null));
				return null;
			} else {
				fixURL();
				getLocalDAO().iniciarTransacao();
				getLocalDAO().inserir(getLocal());
				getLocalDAO().comitarTransacao();
				log.log(Level.INFO, "Usuário " + getLoginBean().getUsuarioLogado().getEmail());
				log.log(Level.INFO, "Local " + getLocal().toString() + " cadastrado com sucesso!");
				setLocal(null);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o local!" + e.getCause(), null));
			log.log(Level.SEVERE, "Local " + getLocal().toString() + " com erro!");
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Local cadastrado com sucesso!", null));
		atualizarLocais();
		return "consultaLocal.xhtml";
	}

	private void fixURL() {
		getLocal().setURL_facebook(Util.fixExternalURL(getLocal().getURL_facebook()));
		getLocal().setURL_twitter(Util.fixExternalURL(getLocal().getURL_twitter()));
		getLocal().setURL_site(Util.fixExternalURL(getLocal().getURL_site()));
	}

	public void onPointSelect(PointSelectEvent event) {
		LatLng latlng = event.getLatLng();
		getLocal().setLatitude(latlng.getLat());
		getLocal().setLongitude(latlng.getLng());
		Marker marker = new Marker(latlng);
		setMapModel(null);
		getMapModel().addOverlay(marker);
	}

	public void filtrarURLYoutube() {
		String urlFinal = getLocal().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getLocal().setVideoURL(urlFinal);
	}

	public byte[] getBytesFromFile(UploadedFile f) {
		return f.getContents();
	}

	public void removerImagem(Imagem i) {
		if (getLocal().getImagens().contains(i)) {
			getLocal().getImagens().remove(i);
		}
	}

	public void removerLogo() {
		local.setLogo(null);
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getLocal().getImagens().add(i);
	}

	public void handleLogoUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getLocal().setLogo(i);
	}

	public Local getLocal() {
		if (local == null) {
			local = new Local();
		}
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
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

	public MapModel getMapModel() {
		if (mapModel == null) {
			mapModel = new DefaultMapModel();
		}
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public boolean isCnpjSet() {
		return cnpjSet;
	}

	public void setCnpjSet(boolean cnpjSet) {
		this.cnpjSet = cnpjSet;
	}

}

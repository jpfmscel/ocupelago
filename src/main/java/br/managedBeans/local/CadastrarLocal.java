package br.managedBeans.local;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.dao.LocalDAO;
import br.entidades.Local;

@ViewScoped
@ManagedBean(name = "cadastrarLocal")
public class CadastrarLocal implements Serializable {

	private static final long serialVersionUID = 734069129117081739L;

	private UploadedFile foto;
	private Local local;
	private LocalDAO localDAO;
	private MapModel mapModel;

	public String adicionarLocal() {
		try {
			atualizaFilePath();
			getLocal().setFoto(getBytesFromFile(getFoto()));
			getLocalDAO().iniciarTransacao();
			getLocalDAO().inserir(getLocal());
			getLocalDAO().comitarTransacao();
			gravarFotoDisco(getLocal().getFilePath());
			setLocal(null);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao inserir o local : "
									+ e.getCause().getMessage(), e.getCause()
									.getMessage()));
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage("messages",
				new FacesMessage("Local cadastrado com sucesso!"));

		return "cadastroLocal.xhtml";
	}

	public void onPointSelect(PointSelectEvent event) {
		LatLng latlng = event.getLatLng();
		getLocal().setLatitude(latlng.getLat());
		getLocal().setLongitude(latlng.getLng());
		Marker marker = new Marker(latlng);
		setMapModel(null);
		getMapModel().addOverlay(marker);
	}

	private void atualizaFilePath() {
		if (getFoto() != null) {
			getLocal().setFoto(getBytesFromFile(getFoto()));
			String filepath = "/Users/jpfms/imagensOcupeLago/"
					+ getFoto().getFileName();
			getLocal().setFilePath(filepath);
		}
	}

	private void gravarFotoDisco(String filepath) throws IOException {
		FileOutputStream fos = null;
		fos = new FileOutputStream(filepath);
		fos.write(getLocal().getFoto());
		fos.close();
	}

	public void filtrarURLYoutube() {
		String urlFinal = getLocal().getVideoURL().replace("watch?", "")
				.replace("v=", "v/");
		getLocal().setVideoURL(urlFinal);
	}

	public byte[] getBytesFromFile(UploadedFile f) {
		return f.getContents();
	}

	public void removerImagem(ActionEvent actionEvent) {
		setFoto(null);
		getLocal().setFoto(null);
	}

	public void handleFileUpload(FileUploadEvent event) {
		setFoto(event.getFile());
		getLocal().setFoto(getBytesFromFile(event.getFile()));
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile foto) {
		this.foto = foto;
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

}

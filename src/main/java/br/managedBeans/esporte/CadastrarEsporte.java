package br.managedBeans.esporte;

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

import br.dao.EsporteDAO;
import br.entidades.Esporte;
import br.util.Util;

@ViewScoped
@ManagedBean(name = "cadastrarEsporte")
public class CadastrarEsporte implements Serializable {

	private static final long serialVersionUID = 734069129117081739L;

	private UploadedFile foto;
	private Esporte esporte;
	private EsporteDAO esporteDAO;
	private String tipoEsporte;

	public String adicionarEsporte() {
		try {
			atualizaTipoEsporte();
			atualizaFilePath();
			getEsporteDAO().iniciarTransacao();
			getEsporteDAO().inserir(getEsporte());
			getEsporteDAO().comitarTransacao();
			gravarFotoDisco(getEsporte().getFilePath());
			setEsporte(null);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o esporte : " + e.getCause().getMessage(), e.getCause().getMessage()));
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Esporte cadastrado com sucesso!", null));

		return "consultaEsporte.xhtml";
	}

	private void atualizaFilePath() {
		if (getFoto() != null) {
			getEsporte().setFoto(getBytesFromFile(getFoto()));
			String filepath = Util.getFilePath() + "" + getFoto().getFileName();
			getEsporte().setFilePath(filepath);
		}
	}

	private void gravarFotoDisco(String filepath) throws IOException {
		if (getFoto() != null) {
			FileOutputStream fos = null;
			fos = new FileOutputStream(filepath);
			fos.write(getEsporte().getFoto());
			fos.close();
		}
	}

	private void atualizaTipoEsporte() {
		getEsporte().setAereo(false);
		getEsporte().setTerrestre(false);
		getEsporte().setAquatico(false);

		if (tipoEsporte.equalsIgnoreCase("aereo")) {
			getEsporte().setAereo(true);
		} else if (tipoEsporte.equalsIgnoreCase("terrestre")) {
			getEsporte().setTerrestre(true);
		} else if (tipoEsporte.equalsIgnoreCase("aquatico")) {
			getEsporte().setAquatico(true);
		}
	}

	public void filtrarURLYoutube() {
		String urlFinal = getEsporte().getVideoURL().replace("watch?", "").replace("v=", "v/");
		getEsporte().setVideoURL(urlFinal);
	}

	public void removerImagem(ActionEvent actionEvent) {
		setFoto(null);
		getEsporte().setFoto(null);
	}

	public void handleFileUpload(FileUploadEvent event) {
		setFoto(event.getFile());
		getEsporte().setFoto(getBytesFromFile(event.getFile()));
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

	public Esporte getEsporte() {
		if (esporte == null) {
			esporte = new Esporte();
		}
		return esporte;
	}

	public void setEsporte(Esporte esporte) {
		this.esporte = esporte;
	}

	public EsporteDAO getEsporteDAO() {
		if (esporteDAO == null) {
			esporteDAO = new EsporteDAO();
		}
		return esporteDAO;
	}

	public void setEsporteDAO(EsporteDAO esporteDAO) {
		this.esporteDAO = esporteDAO;
	}

	public String getTipoEsporte() {
		if (tipoEsporte == null) {
			tipoEsporte = "aereo";

		}
		return tipoEsporte;
	}

	public void setTipoEsporte(String tipoEsporte) {
		this.tipoEsporte = tipoEsporte;
	}

}

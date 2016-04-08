package br.managedBeans.esporte;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.dao.EsporteDAO;
import br.entidades.Esporte;
import br.entidades.Imagem;
import br.managedBeans.ListFactory;

@ViewScoped
@ManagedBean(name = "cadastrarEsporte")
public class CadastrarEsporte implements Serializable {

	private static final long serialVersionUID = 734069129117081739L;

	private Esporte esporte;
	private EsporteDAO esporteDAO;
	private String tipoEsporte;

	@ManagedProperty(value = "#{listFactory}")
	private ListFactory listFactory;
	
	public String adicionarEsporte() {
		try {
			atualizaTipoEsporte();
			getEsporteDAO().iniciarTransacao();
			getEsporteDAO().inserir(getEsporte());
			getEsporteDAO().comitarTransacao();
			setEsporte(null);
			getListFactory().atualizarLista(new EsporteDAO(), new Date());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o esporte : " + e.getCause(), null));
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Esporte cadastrado com sucesso!", null));

		return "consultaEsporte.xhtml";
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

	public void removerImagem(Imagem i) {
		if (getEsporte().getImagens().contains(i)) {
			getEsporte().getImagens().remove(i);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setEsporte(getEsporte());
		i.setDataCriado(new Date());
		getEsporte().getImagens().add(i);
	}

	public byte[] getBytesFromFile(UploadedFile f) {
		return f.getContents();
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

	public ListFactory getListFactory() {
		return listFactory;
	}

	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}

}

package br.managedBeans.local;

import java.io.Serializable;
import java.util.Date;

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
import br.managedBeans.ListFactory;

@ViewScoped
@ManagedBean(name = "cadastrarLocal")
public class CadastrarLocal implements Serializable {

	private static final long serialVersionUID = 734069129117081739L;

	private Local local;
	private LocalDAO localDAO;
	private MapModel mapModel;
	
	@ManagedProperty(value = "#{listFactory}")
	private ListFactory listFactory;

	public String adicionarLocal() {
		try {
			getLocalDAO().iniciarTransacao();
			getLocalDAO().inserir(getLocal());
			getLocalDAO().comitarTransacao();
			setLocal(null);
			getListFactory().atualizarLista(new LocalDAO(), new Date());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir o local!" + e.getCause(), null));
			e.printStackTrace();
			return null;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Local cadastrado com sucesso!", null));

		return "consultaLocal.xhtml";
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

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setLocal(getLocal());
		i.setDataCriado(new Date());
		getLocal().getImagens().add(i);
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

	public ListFactory getListFactory() {
		return listFactory;
	}

	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}

}

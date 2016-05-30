package br.managedBeans.alerta;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.dao.AlertaDAO;
import br.entidades.Alerta;
import br.entidades.Imagem;
import br.managedBeans.LoginBean;
import br.managedBeans.ManagedBeanGenerico;

@ManagedBean(name = "cadastrarAlerta")
@ViewScoped
public class CadastrarAlerta extends ManagedBeanGenerico implements Serializable {

	private static final long serialVersionUID = 1L;

	// static
	private MapModel emptyModel;

	private Alerta alerta;
	private AlertaDAO alertaDAO;
	private Marker marker;

	private Logger log = Logger.getGlobal();

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public String adicionarAlerta() {
		try {
			if(getAlerta().getLatitude() == 0 || getAlerta().getLongitude() == 0){
				
			}
			getAlerta().setDataCriado(new Date());
			getAlertaDAO().iniciarTransacao();
			getAlertaDAO().inserir(getAlerta());
			getAlertaDAO().comitarTransacao();
			log.log(Level.INFO, "Usuário " + getLoginBean().getUsuarioLogado().getEmail());
			log.log(Level.INFO, "Alerta " + getAlerta().toString() + " cadastrada com sucesso!");
			setAlerta(null);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Alerta " + getAlerta().toString() + " com erro!");
			e.printStackTrace();
			return null;
		}
		atualizarAlertas();
		return "consultaAlerta.xhtml";
	}

	public void handleFileUpload(FileUploadEvent event) {
		Imagem i = new Imagem();
		i.setData(event.getFile().getContents());
		i.setNomeArquivo(event.getFile().getFileName());
		i.setDataCriado(new Date());
		getAlerta().getImagens().add(i);
	}

	public void onPointSelect(PointSelectEvent event) {
		LatLng latlng = event.getLatLng();
		getAlerta().setLatitude(latlng.getLat());
		getAlerta().setLongitude(latlng.getLng());
		Marker marker = new Marker(latlng);
		setEmptyModel(null);
		getEmptyModel().addOverlay(marker);
	}

	public Marker getMarker() {
		return marker;
	}

	public MapModel getEmptyModel() {
		if (emptyModel == null) {
			setEmptyModel(new DefaultMapModel());
		}
		return emptyModel;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public Alerta getAlerta() {
		if (alerta == null) {
			alerta = new Alerta();
		}
		return alerta;
	}

	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public AlertaDAO getAlertaDAO() {
		if (alertaDAO == null) {
			alertaDAO = new AlertaDAO();
		}
		return alertaDAO;
	}

	public void setAlertaDAO(AlertaDAO alertaDAO) {
		this.alertaDAO = alertaDAO;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}
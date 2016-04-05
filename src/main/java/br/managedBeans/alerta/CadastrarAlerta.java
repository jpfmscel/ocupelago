package br.managedBeans.alerta;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.dao.AlertaDAO;
import br.entidades.Alerta;

@ManagedBean(name = "mapBean")
@ViewScoped
public class CadastrarAlerta implements Serializable {

	private static final long serialVersionUID = 1L;

	private MapModel emptyModel;
	private Marker marker;

	private String title;
	private double lat;
	private double lng;

	@ManagedProperty(value = "#{consultarAlerta}")
	private ConsultarAlerta consultarAlerta;

	private Alerta alerta;
	private AlertaDAO alertaDAO;

	@PostConstruct
	public void init() {
		if (getEmptyModel() == null) {
			setEmptyModel(new DefaultMapModel());

			for (Alerta a : getAlertaDAO().getListaInicial()) {
				LatLng coord = new LatLng(a.getLatitude(), a.getLongitude());
				getEmptyModel().addOverlay(new Marker(coord, a.getTitulo()));
			}
		}
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		setMarker((Marker) event.getOverlay());
	}

	public Marker getMarker() {
		return marker;
	}

	public MapModel getEmptyModel() {
		return emptyModel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void addMarker() {
		Marker marker = new Marker(new LatLng(lat, lng), title);
		getEmptyModel().addOverlay(marker);
		AlertaDAO aDAO = new AlertaDAO();
		Alerta a = new Alerta();

		a.setLatitude(lat);
		a.setLongitude(lng);
		// a.setDescricao(getDescricao());
		a.setTitulo(getTitle());
		a.setDataCriado(new Date());

		aDAO.iniciarTransacao();
		aDAO.inserir(a);
		aDAO.comitarTransacao();

		getConsultarAlerta().atualizarAlertas();
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

	public ConsultarAlerta getConsultarAlerta() {
		if (consultarAlerta == null) {
			consultarAlerta = new ConsultarAlerta();
		}
		return consultarAlerta;
	}

	public void setConsultarAlerta(ConsultarAlerta consultarAlerta) {
		this.consultarAlerta = consultarAlerta;
	}

}
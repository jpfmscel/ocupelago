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
import br.managedBeans.ListFactory;

@ManagedBean(name = "mapBean")
@ViewScoped
public class CadastrarAlerta implements Serializable {

	private static final long serialVersionUID = 1L;

	private static MapModel emptyModel;
	private Marker marker;

	private String title;
	private double lat;
	private double lng;

	private Alerta alerta;
	private AlertaDAO alertaDAO;

	@ManagedProperty(value = "#{listFactory}")
	private ListFactory listFactory;

	@PostConstruct
	public void init() {
		setEmptyModel(new DefaultMapModel());
		for (Alerta a : getAlertaDAO().getListaInicial()) {
			LatLng coord = new LatLng(a.getLatitude(), a.getLongitude());
			Marker overlay = new Marker(coord, a.getTitulo());
			emptyModel.addOverlay(overlay);
			a.setMarker(overlay);
		}
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
		getListFactory().atualizarLista(new AlertaDAO(), new Date());
		setTitle("");
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		setMarker((Marker) event.getOverlay());
	}

	public Marker getMarker() {
		return marker;
	}

	public MapModel getEmptyModel() {
		if(emptyModel==null){
			setEmptyModel(new DefaultMapModel());
		}
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
		CadastrarAlerta.emptyModel = emptyModel;
	}

	public ListFactory getListFactory() {
		return listFactory;
	}

	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}

}
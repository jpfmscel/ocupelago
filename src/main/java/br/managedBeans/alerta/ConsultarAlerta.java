package br.managedBeans.alerta;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.dao.AlertaDAO;
import br.entidades.Alerta;
import br.managedBeans.LoginBean;
import br.managedBeans.ManagedBeanGenerico;

@ViewScoped
@ManagedBean(name = "consultarAlerta")
public class ConsultarAlerta extends ManagedBeanGenerico implements Serializable {

	private static final long serialVersionUID = -2236749237134308778L;
	private Alerta alertaSelected;
	private AlertaDAO alertaDAO;

	private MapModel mapModel;
	private Marker marker;

	private Logger log = Logger.getGlobal();

	private final String URL_amarelo = "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png";

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void iniciarAlertas() {
		setAlertaSelected(null);
		atualizarAlertas();
		setMapModel(new DefaultMapModel());
		for (Alerta a : getListFactory().getListaAlerta()) {
			LatLng coord = new LatLng(a.getLatitude(), a.getLongitude());
			Marker overlay = new Marker(coord, a.getTitulo(), null, getURLPino(a));
			getMapModel().addOverlay(overlay);
			a.setMarker(overlay);
		}
	}

	private String getURLPino(Alerta a) {
		if (a.getCategoria().equalsIgnoreCase("animal")) {
			return "http://restfuljp-3.tud6baviqt.us-west-2.elasticbeanstalk.com//images/shark_small_32.png";
		} else if (a.getCategoria().equalsIgnoreCase("criminal")) {
			return "http://restfuljp-3.tud6baviqt.us-west-2.elasticbeanstalk.com//images/handcuffs_small.png";
		} else if (a.getCategoria().equalsIgnoreCase("ambiental")) {
			return "http://restfuljp-3.tud6baviqt.us-west-2.elasticbeanstalk.com//images/trash_small_32.png";
		} else if (a.getCategoria().equalsIgnoreCase("seguranca")) {
			return "http://restfuljp-3.tud6baviqt.us-west-2.elasticbeanstalk.com//images/seguranca_small.png";
		} else if (a.getCategoria().equalsIgnoreCase("incendio")) {
			return "http://restfuljp-3.tud6baviqt.us-west-2.elasticbeanstalk.com//images/fire_small.png";
		}
		return null;
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		setMarker((Marker) event.getOverlay());
	}

	public String excluirAlerta(Alerta e) {
		try {
			setAlertaSelected(e);
			getAlertaSelected().setAtivo(false);
			update();
			log.log(Level.INFO, "Alerta " + e.toString() + " excluída com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta excluída com sucesso!", null));
			atualizarAlertas();
		} catch (Exception ex) {
			log.log(Level.INFO, "Alerta " + e.toString() + " com erro!");
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir a alerta : " + ex.getCause().getMessage(), ex.getCause().getMessage()));
			return null;
		}
		setAlertaSelected(null);
		atualizarAlertas();
		return "consultaAlerta.xhtml";
	}

	private void update() {
		log.log(Level.INFO, "Usuário " + getLoginBean().getUsuarioLogado().getEmail());
		getAlertaDAO().iniciarTransacao();
		getAlertaDAO().update(getAlertaSelected());
		getAlertaDAO().comitarTransacao();
	}

	public Alerta getAlertaSelected() {
		if (alertaSelected == null) {
			alertaSelected = new Alerta();
		}
		return alertaSelected;
	}

	public void setAlertaSelected(Alerta alertaSelected) {
		this.alertaSelected = alertaSelected;
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

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}
}

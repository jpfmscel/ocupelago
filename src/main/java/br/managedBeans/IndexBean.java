package br.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.dao.LocalDAO;
import br.entidades.Esporte;
import br.entidades.Evento;
import br.entidades.Local;
import br.entidades.Noticia;
import br.entidades.Projeto;

@SessionScoped
@ManagedBean(name = "indexBean")
public class IndexBean {

	private Noticia noticiaSel;
	private Evento eventoSel;
	private Esporte esporteSel;
	private Projeto projetoSel;
	private Local localSel;

	private List<Local> restaurantes;
	private List<Local> clubes;

	private LocalDAO localDAO;

	@PostConstruct
	public void init() {
		getRestaurantes().addAll(getLocalDAO().buscarPorCategoria("Restaurante"));
		getClubes().addAll(getLocalDAO().buscarPorCategoria("Clube"));
	}

	public String detalhar(Object o) {
		String redirect = null;
		if (o instanceof Noticia) {
			setNoticiaSel((Noticia) o);
			redirect = "post";
		} else if (o instanceof Evento) {
			setEventoSel((Evento) o);
			redirect = "evento";
		} else if (o instanceof Esporte) {
			setEsporteSel((Esporte) o);
			redirect = "esporte";
		} else if (o instanceof Projeto) {
			setProjetoSel((Projeto) o);
			redirect = "projeto";
		} else if (o instanceof Local) {
			setLocalSel((Local) o);
			redirect = "local";
		}
		return redirect;
	}

	public String navegarVcNoLago() {
		atualizarListaLocais();
		return "vcnolago";
	}

	public void atualizarListaLocais() {
		setClubes(null);
		setRestaurantes(null);
		getClubes().addAll(getLocalDAO().buscarPorCategoria("Clube"));
		getRestaurantes().addAll(getLocalDAO().buscarPorCategoria("Clube"));
	}

	public Noticia getNoticiaSel() {
		return noticiaSel;
	}

	public void setNoticiaSel(Noticia noticiaSel) {
		this.noticiaSel = noticiaSel;
	}

	public Evento getEventoSel() {
		if (eventoSel == null) {
			eventoSel = new Evento();
		}
		return eventoSel;
	}

	public void setEventoSel(Evento eventoSel) {
		this.eventoSel = eventoSel;
	}

	public Esporte getEsporteSel() {
		if (esporteSel == null) {
			esporteSel = new Esporte();
		}
		return esporteSel;
	}

	public void setEsporteSel(Esporte esporteSel) {
		this.esporteSel = esporteSel;
	}

	public Projeto getProjetoSel() {
		if (projetoSel == null) {
			projetoSel = new Projeto();
		}
		return projetoSel;
	}

	public void setProjetoSel(Projeto projetoSel) {
		this.projetoSel = projetoSel;
	}

	public Local getLocalSel() {
		if (localSel == null) {
			localSel = new Local();
		}
		return localSel;
	}

	public void setLocalSel(Local localSel) {
		this.localSel = localSel;
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

	public List<Local> getClubes() {
		if (clubes == null) {
			clubes = new ArrayList<>();
		}
		return clubes;
	}

	public void setClubes(List<Local> clubes) {
		this.clubes = clubes;
	}

	public List<Local> getRestaurantes() {
		if (restaurantes == null) {
			restaurantes = new ArrayList<>();
		}
		return restaurantes;
	}

	public void setRestaurantes(List<Local> restaurantes) {
		this.restaurantes = restaurantes;
	}

}

package br.managedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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

	public String detalhar(Object o) {
		String redirect = null;
		if (o instanceof Noticia) {
			setNoticiaSel((Noticia) o);
			redirect = "post";
		} else if (o instanceof Evento) {
			setEventoSel((Evento) o);
			redirect = "paginaEvento";
		} else if (o instanceof Esporte) {
			setEsporteSel((Esporte) o);
			redirect = "paginaEsporte";
		} else if (o instanceof Projeto) {
			setProjetoSel((Projeto) o);
			redirect = "paginaProjeto";
		} else if (o instanceof Local) {
			setLocalSel((Local) o);
			redirect = "paginaLocal";
		}
		return redirect;
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

}

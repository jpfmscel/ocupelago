package br.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import br.entidades.Esporte;
import br.entidades.Evento;
import br.entidades.EventoEsporte;

public class EventoEsporteDAO extends BaseDao<EventoEsporte> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<EventoEsporte> getClasse() {
		return EventoEsporte.class;
	}

	@SuppressWarnings("unchecked")
	public List<Evento> getEventosByEsporte(int idEsporte) {
		EventoDAO eveD = new EventoDAO();
		List<Evento> lista = new ArrayList<>();

		Query q = gerarQueryPorEsporte(idEsporte);
		List<EventoEsporte> lista2 = ((List<EventoEsporte>) q.getResultList());

		for (EventoEsporte eventoEsporte : lista2) {
			lista.add(eveD.buscarPorId(eventoEsporte.getId_evento()));
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Esporte> getEsportesByEvento(int idEvento) {
		EsporteDAO eD = new EsporteDAO();
		List<Esporte> lista = new ArrayList<>();

		Query q = gerarQueryPorEvento(idEvento);
		List<EventoEsporte> lista2 = ((List<EventoEsporte>) q.getResultList());

		for (EventoEsporte eventoEsporte : lista2) {
			lista.add(eD.buscarPorId(eventoEsporte.getId_esporte()));
		}

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<EventoEsporte> getEventoEsportes(int idEvento) {
		Query q = gerarQueryPorEventoEsporte(idEvento);
		return (List<EventoEsporte>) q.getResultList();
	}

	private Query gerarQueryPorEsporte(int idEsporte) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where id_esporte ='" + idEsporte + "'");
		return getEntityManager().createQuery(sb.toString());
	}

	private Query gerarQueryPorEvento(int idEvento) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where id_evento ='" + idEvento + "'");
		return getEntityManager().createQuery(sb.toString());
	}

	private Query gerarQueryPorEventoEsporte(int idEvento) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where id_evento ='" + idEvento + "'");
		return getEntityManager().createQuery(sb.toString());
	}

}

package br.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import br.entidades.Evento;
import br.entidades.Local;

public class EventoDAO extends BaseDao<Evento> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Evento> getClasse() {
		return Evento.class;
	}

	@SuppressWarnings("unchecked")
	public List<Local> buscarPorData(Date data) {
		Query q = gerarQueryData(data);
		return ((List<Local>) q.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public List<Evento> getEventosByLocal(int idLocal) {
		EventoDAO eveD = new EventoDAO();
		List<Evento> lista = new ArrayList<>();

		Query q = gerarQueryPorLocal(idLocal);
		List<Evento> lista2 = ((List<Evento>) q.getResultList());

		for (Evento evento : lista2) {
			lista.add(eveD.buscarPorId(evento.getId()));
		}
		return lista;
	}
	
	// TODO query por local
	private Query gerarQueryPorLocal(int idLocal) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where id_esporte ='" + idLocal + "'");
		return getEntityManager().createQuery(sb.toString());
	}

	public Query gerarQueryData(Date data) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0 and dataInicio >'" + new SimpleDateFormat("yyy-MM-dd").format(new Date()) + " 00:00:00.0'");
		return getEntityManager().createQuery(sb.toString());
	}

}

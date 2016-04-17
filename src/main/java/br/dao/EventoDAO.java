package br.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import br.entidades.Evento;
import br.entidades.Local;

public class EventoDAO extends BaseDao<Evento>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Evento> getClasse() {
		return Evento.class;
	}

	public Local buscarPorNome(String nome) {
		Query q = gerarQueryNome(nome);
		return (Local) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Local> buscarPorData(Date data) {
		Query q = gerarQueryData(data);
		return ((List<Local>) q.getResultList());
	}

	//TODO query por local
	
	public Query gerarQueryData(Date data) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0 and dataInicio >'" + new SimpleDateFormat("yyy-MM-dd").format(new Date()) + " 00:00:00.0'");
		return getEntityManager().createQuery(sb.toString());
	}

	private Query gerarQueryNome(String nome) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0 and nome ='" + nome + "'");
		return getEntityManager().createQuery(sb.toString());
	}

	
}

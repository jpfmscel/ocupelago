package br.dao;

import java.util.List;

import javax.persistence.Query;

import br.entidades.Avaliacao;

public class AvaliacaoDAO extends BaseDao<Avaliacao> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Avaliacao> getClasse() {
		return Avaliacao.class;
	}

	@SuppressWarnings("unchecked")
	public List<Avaliacao> buscarPorLocal(int localId) {
		Query q = gerarQueryLocal(localId);
		return ((List<Avaliacao>) q.getResultList());
	}

	public Query gerarQueryLocal(int localId) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0 and local.id = " + localId);
		return getEntityManager().createQuery(sb.toString());
	}
}

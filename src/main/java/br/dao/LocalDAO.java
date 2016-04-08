package br.dao;

import javax.persistence.Query;

import br.entidades.Local;

public class LocalDAO extends BaseDao<Local> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Local> getClasse() {
		return Local.class;
	}

	public Local buscarPorNome(String nome) {
		Query q = gerarQueryNome(nome);
		return (Local) q.getSingleResult();
	}

	private Query gerarQueryNome(String nome) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0 and nome ='" + nome + "'");
		return getEntityManager().createQuery(sb.toString());
	}
}

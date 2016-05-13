package br.dao;

import java.util.List;

import javax.persistence.Query;

import br.entidades.Local;

public class LocalDAO extends BaseDao<Local> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Local> getClasse() {
		return Local.class;
	}

	@SuppressWarnings("unchecked")
	public List<Local> buscarPorCategoria(String cat) {
		Query q = gerarQueryPorCategoria(cat);
		return ((List<Local>) q.getResultList());
	}

	public Query gerarQueryPorCategoria(String cat) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0 and categoria ='" + cat + "'");
		return getEntityManager().createQuery(sb.toString());
	}

}

package br.dao;

import java.util.List;

import javax.persistence.Query;

import br.entidades.Local;
import br.entidades.LocalEsporte;

public class LocalEsporteDAO extends BaseDao<LocalEsporte>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<LocalEsporte> getClasse() {
		return LocalEsporte.class;
	}

	@SuppressWarnings("unchecked")
	public List<LocalEsporte> buscarPorCategoria(String cat) {
		Query q = gerarQueryPorCategoria(cat);
		return ((List<LocalEsporte>) q.getResultList());
	}

	public Query gerarQueryPorCategoria(String cat) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0 and categoria ='" + cat + "'");
		return getEntityManager().createQuery(sb.toString());
	}
}

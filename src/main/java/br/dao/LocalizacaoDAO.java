package br.dao;

import java.io.Serializable;

import javax.persistence.NoResultException;

import br.entidades.Localizacao;
import br.entidades.Usuario;

public class LocalizacaoDAO extends BaseDao<Localizacao> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Localizacao> getClasse() {
		return Localizacao.class;
	}

	public Localizacao getLast(Usuario u) {
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + Localizacao.class.getSimpleName() + " x where 1=1");
		sb.append(" order by timestamp desc");
		Localizacao loc = null;
		try {
			loc = (Localizacao) getEntityManager().createQuery(sb.toString()).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return loc;
	}

//	public List<Localizacao> getPorPeriodo(Usuario u, Date inicio, Date fim) {
//		List<Localizacao> lista = new ArrayList<Localizacao>();
//		StringBuffer sb = new StringBuffer();
//		sb.append("Select x from " + Localizacao.class.getSimpleName() + " x where 1=1");
//		sb.append(" and usuario=" + u.getId());
//		sb.append(" and timestamp between '" + Util.getTimestampFromDate(inicio));
//		sb.append("' and '" + Util.getTimestampFromDate(fim));
//		sb.append("' order by timestamp asc");
//
//		try {
//			lista.addAll(getEntityManager().createQuery(sb.toString()).getResultList());
//		} catch (NoResultException e) {
//			e.printStackTrace();
//		}
//
//		return lista;
//	}
//
//	public Localizacao getLastPorMedicao(Usuario u, Date dataMedicao) {
//		List<Localizacao> lista = new ArrayList<Localizacao>();
//		StringBuffer sb = new StringBuffer();
//		sb.append("Select x from " + Localizacao.class.getSimpleName() + " x where 1=1");
//		sb.append(" and usuario=" + u.getId());
//		sb.append(" and timestamp <= '" + Util.getTimestampFromDate(dataMedicao));
//		sb.append("' order by timestamp desc");
//
//		try {
//			return (Localizacao) getEntityManager().createQuery(sb.toString()).setMaxResults(1).getSingleResult();
//		} catch (NoResultException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}

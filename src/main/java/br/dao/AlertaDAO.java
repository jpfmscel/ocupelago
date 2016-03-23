package br.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import br.entidades.Alerta;

public class AlertaDAO extends BaseDao<Alerta> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Alerta> getClasse() {
		return Alerta.class;
	}

	@SuppressWarnings("unchecked")
	public List<Alerta> getListaInicial() {
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + Alerta.class.getSimpleName() +" x");
		sb.append(" where 1=1");
		sb.append(" and latitude between -15.8880217 and -15.6880217");
		sb.append(" and longitude between -47.9390782 and -47.7390782");

		// lat -15.8880217 and -15.6880217
		// lng -47.7390782 and -47.9390782

		List<Alerta> alertas = new ArrayList<>();

		try {
			alertas.addAll((List<Alerta>) getEntityManager().createQuery(
					sb.toString()).getResultList());
		} catch (NoResultException e) {
			System.out.println("Nenhum alerta encontrado.");
		}
		return alertas;
	}

}

package br.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import br.entidades.Esporte;
import br.entidades.Local;
import br.entidades.LocalEsporte;

public class LocalEsporteDAO extends BaseDao<LocalEsporte> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<LocalEsporte> getClasse() {
		return LocalEsporte.class;
	}

	@SuppressWarnings("unchecked")
	public List<Local> getLocaisByEsporte(int idEsporte) {
		LocalDAO locD = new LocalDAO();
		List<Local> lista = new ArrayList<>();

		Query q = gerarQueryPorEsporte(idEsporte);
		List<LocalEsporte> lista2 = ((List<LocalEsporte>) q.getResultList());

		for (LocalEsporte localEsporte : lista2) {
			lista.add(locD.buscarPorId(localEsporte.getId_local()));
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Esporte> getEsportesByLocal(int idLocal) {
		EsporteDAO eD = new EsporteDAO();
		List<Esporte> lista = new ArrayList<>();

		Query q = gerarQueryPorLocal(idLocal);
		List<LocalEsporte> lista2 = ((List<LocalEsporte>) q.getResultList());

		for (LocalEsporte localEsporte : lista2) {
			lista.add(eD.buscarPorId(localEsporte.getId_esporte()));
		}

		return lista;
	}

	private Query gerarQueryPorEsporte(int idEsporte) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where id_esporte ='" + idEsporte + "'");
		return getEntityManager().createQuery(sb.toString());
	}

	private Query gerarQueryPorLocal(int idLocal) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where id_local ='" + idLocal + "'");
		return getEntityManager().createQuery(sb.toString());
	}

	public Query gerarQueryPorCategoria(String cat) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0 and categoria ='" + cat + "'");
		return getEntityManager().createQuery(sb.toString());
	}
}

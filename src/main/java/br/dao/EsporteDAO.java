package br.dao;

import br.entidades.Esporte;

public class EsporteDAO extends BaseDao<Esporte> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Esporte> getClasse() {
		return Esporte.class;
	}

}

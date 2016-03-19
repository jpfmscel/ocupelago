package br.dao;

import br.entidades.Local;

public class LocalDAO extends BaseDao<Local>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Local> getClasse() {
		return Local.class;
	}

}

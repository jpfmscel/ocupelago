package br.dao;

import br.entidades.Projeto;

public class ProjetoDAO extends BaseDao<Projeto>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Projeto> getClasse() {
		return Projeto.class;
	}

}

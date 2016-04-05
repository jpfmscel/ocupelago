package br.dao;

import br.entidades.Avaliacao;

public class AvaliacaoDAO extends BaseDao<Avaliacao>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Avaliacao> getClasse() {
		return Avaliacao.class;
	}

}

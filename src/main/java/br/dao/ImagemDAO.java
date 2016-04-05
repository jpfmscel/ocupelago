package br.dao;

import br.entidades.Imagem;

public class ImagemDAO extends BaseDao<Imagem>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Imagem> getClasse() {
		return Imagem.class;
	}

}

package br.dao;

import br.entidades.Noticia;

public class NoticiaDAO extends BaseDao<Noticia>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Noticia> getClasse() {
		return Noticia.class;
	}

}

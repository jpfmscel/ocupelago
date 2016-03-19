package br.dao;

import br.entidades.Evento;

public class EventoDAO extends BaseDao<Evento>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Evento> getClasse() {
		return Evento.class;
	}

}

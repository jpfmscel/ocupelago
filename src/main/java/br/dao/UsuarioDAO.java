package br.dao;

import java.io.Serializable;

import javax.persistence.NoResultException;

import br.entidades.Usuario;

public class UsuarioDAO extends BaseDao<Usuario> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Usuario> getClasse() {
		return Usuario.class;
	}

	public Usuario logarUsuario(String login, String senha) {
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + Usuario.class.getSimpleName() + " x where 1=1");
		sb.append(" and senha = '"+senha+"' and login ='"+login+"'");
		Usuario u = null;
		try{
			 u = (Usuario) getEntityManager().createQuery(sb.toString()).getSingleResult();
		}catch(NoResultException e){}
		return u;
	}
	
	public Usuario buscarUsuario(String login, String email) {
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + Usuario.class.getSimpleName() + " x where 1=1");
		sb.append(" and (email = '"+email+"' or login ='"+login+"')");
		Usuario u = null;
		try{
			 u = (Usuario) getEntityManager().createQuery(sb.toString()).getSingleResult();
		}catch(NoResultException e){}
		return u;
	}
	
}

package br.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.entidades.Alerta;
import br.entidades.Avaliacao;
import br.entidades.Noticia;
import br.entidades.Projeto;

public abstract class BaseDao<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private static EntityManager entityManager;

	public T buscarPorId(int id) {
		return (T) getEntityManager().find(getClasse(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> buscarPorNome(String nome) {
		Query q = gerarQueryNome(nome);
		return ((List<T>) q.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarParteNome(String nome) {
		Query q = gerarQueryParteNome(nome);
		return ((List<T>) q.getResultList());
	}

	private Query gerarQueryParteNome(String nome) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();

		String titOuNome;

		if (getClasse().equals(Projeto.class) || getClasse().equals(Noticia.class) || getClasse().equals(Avaliacao.class) || getClasse().equals(Alerta.class)) {
			titOuNome = "titulo";
		} else {
			titOuNome = "nome";
		}
		sb.append("Select x from " + nomeClasse + " x where " + titOuNome + " like ('%" + nome + "%') and ativo <> 0");
		return getEntityManager().createQuery(sb.toString());
	}

	private Query gerarQueryNome(String nome) {
		String nomeClasse = getClasse().getSimpleName();
		StringBuffer sb = new StringBuffer();

		String titOuNome;

		if (getClasse().equals(Projeto.class) || getClasse().equals(Noticia.class) || getClasse().equals(Avaliacao.class) || getClasse().equals(Alerta.class)) {
			titOuNome = "titulo";
		} else {
			titOuNome = "nome";
		}
		sb.append("Select x from " + nomeClasse + " x where " + titOuNome + " = '" + nome + "' and ativo <> 0");
		return getEntityManager().createQuery(sb.toString());
	}

	public abstract Class<T> getClasse();

	@SuppressWarnings({ "unchecked" })
	public List<T> buscarLista(T o) {
		Query q = gerarQuery(o);
		return (List<T>) q.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public List<T> findAll() {
		Query q = gerarQuery(getClasse());
		return (List<T>) q.getResultList();
	}

	private Query gerarQuery(Class<T> classe) {
		String nomeClasse = classe.getSimpleName();
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + nomeClasse + " x where ativo <> 0");
		return getEntityManager().createQuery(sb.toString());
	}

	@SuppressWarnings("unchecked")
	public T buscarSingle(T o) {
		Query q = gerarQuery(o);
		return (T) q.getSingleResult();
	}

	public void iniciarTransacao() {
		getEntityManager().getTransaction().begin();
	}

	public void comitarTransacao() {
		getEntityManager().getTransaction().commit();
	}

	public void inserir(T obj) {
		getEntityManager().persist(obj);
	}

	public void update(T obj) {
		getEntityManager().merge(obj);
	}

	private Query gerarQuery(T obj) {

		String nomeClasse = obj.getClass().getSimpleName();
		StringBuffer sb = new StringBuffer();

		sb.append("Select x from " + nomeClasse + " x where 1=1 ");

		try {
			for (Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);

				if (f.get(obj) != null) {
					if (!f.getName().equalsIgnoreCase("serialVersionUID")) {

						if (f.getName().equalsIgnoreCase("id")) {
							if (((int) f.get(obj)) == 0) {
								sb.append("and " + f.getName() + " > " + f.get(obj));
							} else {
								sb.append("and " + f.getName() + " = " + f.get(obj));
							}
						}
					}
				}
			}
			System.out.println(sb.toString());

		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return getEntityManager().createQuery(sb.toString());
	}

	public EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = Persistence.createEntityManagerFactory("ocupelago").createEntityManager();
		}
		return entityManager;
	}

}

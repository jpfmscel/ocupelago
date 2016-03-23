package br.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class BaseDao<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	public T buscarPorId(int id) {
		return (T) getEntityManager().find(getClasse(), id);
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
		sb.append("Select x from " + nomeClasse + " x ");
		return getEntityManager().createQuery(sb.toString());
	}

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
								sb.append("and " + f.getName() + " > "
										+ f.get(obj));
							} else {
								sb.append("and " + f.getName() + " = "
										+ f.get(obj));
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
			entityManager = Persistence.createEntityManagerFactory("ocupelago")
					.createEntityManager();
		}
		return entityManager;
	}

}

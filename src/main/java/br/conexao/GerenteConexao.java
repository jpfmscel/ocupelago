package br.conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class GerenteConexao {

	private static EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	private static EntityManager entityManager;

	public static EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = getEntityManagerFactory().createEntityManager();
		}
		return entityManager;
	}

	public static void inserir(Object o) {
		getEntityManager().getTransaction().begin();
		getEntityManager().persist(o);
		getEntityManager().getTransaction().commit();
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("ocupelago");
		}
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		GerenteConexao.entityManagerFactory = entityManagerFactory;
	}

}

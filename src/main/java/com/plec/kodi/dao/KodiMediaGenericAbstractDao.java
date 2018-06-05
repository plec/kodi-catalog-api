package com.plec.kodi.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public abstract class KodiMediaGenericAbstractDao<E, K> implements KodiMediaGenericDao<E, K> {

	protected Class<E> entityClass; 

	@PersistenceContext
	EntityManager entityManager;
	
	public KodiMediaGenericAbstractDao(Class<E> entityClass) {
		this.entityClass = entityClass;
	}
	@Override
	public List<E> getMedia(int offset, int limit) {
		return this.getMedia(offset, limit, "title");
	}
	
	@Override
	public List<E> getMedia(int offset, int limit, String order) {
		final EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		final CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
		
		CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<E> from = criteriaQuery.from(entityClass);
		CriteriaQuery<E> select = criteriaQuery.select(from);
		if (order.equals("dateAdded")) {
			select.orderBy(criteriaBuilder.desc(from.get(order)));
		} else {
			select.orderBy(criteriaBuilder.asc(from.get(order)));
		}
		TypedQuery<E> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(limit);
		return typedQuery.getResultList();
	}
}

package com.plec.kodi.dao;

import java.util.ArrayList;
import java.util.List; 

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.plec.kodi.utils.Constants;


public abstract class KodiMediaGenericAbstractDao<E, K> implements KodiMediaGenericDao<E, K> {

	private static final String ENTITY_TITLE_FIELD = "title";

	private static final String ENTITY_GENRE_FIELD = "genre";

	protected Class<E> entityClass; 

	@PersistenceContext
	EntityManager entityManager;
	
	public KodiMediaGenericAbstractDao(Class<E> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	public List<E> getMedia(String genre, String title, int offset, int limit, String order) {
		final EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		final CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
		
		CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<E> from = criteriaQuery.from(entityClass);
		ParameterExpression<String> genreRestriction = null;
		ParameterExpression<String> titleRestriction = null;
		List<Predicate> predicates = new ArrayList<>();
		//genre
		if (!StringUtils.isEmpty(genre)) {
			genreRestriction = criteriaBuilder.parameter(String.class);
			predicates.add(criteriaBuilder.like(from.get(ENTITY_GENRE_FIELD), genreRestriction));
		}

		//title
		if (!StringUtils.isEmpty(title)) {
			titleRestriction = criteriaBuilder.parameter(String.class);
			predicates.add(criteriaBuilder.like(from.get(ENTITY_TITLE_FIELD), titleRestriction));
		}
		//add the restriction
		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		
		
		CriteriaQuery<E> select = criteriaQuery.select(from);
		if (order.equals(Constants.ORDER_DATE)) {
			select.orderBy(criteriaBuilder.desc(from.get(order)));
		} else {
			select.orderBy(criteriaBuilder.asc(from.get(order)));
		}
		TypedQuery<E> typedQuery = entityManager.createQuery(select);
		// genre
		if (genreRestriction != null) {
			typedQuery.setParameter(genreRestriction, genre);
		}

		//title
		if (titleRestriction != null) {
			typedQuery.setParameter(titleRestriction, title);
		}
		
		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(limit);
		return typedQuery.getResultList();
	}
	
	@Override
	public long countMedia(String genre, String title) {
		final EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		final CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
		
		ParameterExpression<String> genreRestriction = null;
		ParameterExpression<String> titleRestriction = null;
		
		List<Predicate> predicates = new ArrayList<>();
		
		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<E> from = countCriteriaQuery.from(entityClass);

		countCriteriaQuery.select(criteriaBuilder.count(from));
		//genre
		if (!StringUtils.isEmpty(genre)) {
			genreRestriction = criteriaBuilder.parameter(String.class);
			predicates.add(criteriaBuilder.like(from.get(ENTITY_GENRE_FIELD), genreRestriction));
		}
		//title
		if (!StringUtils.isEmpty(title)) {
			titleRestriction = criteriaBuilder.parameter(String.class);
			predicates.add(criteriaBuilder.like(from.get(ENTITY_TITLE_FIELD), titleRestriction));
		}		
		
		//add the restriction
		countCriteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
				
				
		TypedQuery<Long> typedQuery = entityManager.createQuery(countCriteriaQuery);
		//genre
		if (genreRestriction != null) {
			typedQuery.setParameter(genreRestriction, genre);
		}
		//title
		if (titleRestriction != null) {
			typedQuery.setParameter(titleRestriction, title);
		}
		return typedQuery.getSingleResult();
	}
}

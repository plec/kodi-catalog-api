package com.plec.kodi.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plec.kodi.domain.Genre;
import com.plec.kodi.domain.Movie;
import com.plec.kodi.entity.GenreEntity;
import com.plec.kodi.entity.MovieEntity;
import com.plec.kodi.repository.GenreRepository;
import com.plec.kodi.repository.MovieRepository;

@Service
public class KodiService {

	private static final String PATTERN_START_IMAGE = "preview=\"";
	private static final String PATTERN_END_IMAGE = "\">";

	private MovieRepository movieRepository;

	private GenreRepository genreRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	public KodiService(MovieRepository movieRepository, GenreRepository genreRepository) {
		super();
		this.movieRepository = movieRepository;
		this.genreRepository = genreRepository;
	}

	public long countMovies() {
		return movieRepository.count();
	}

	public List<Movie> getMovies() {
		return StreamSupport.stream(movieRepository.findAll().spliterator(), false).map(e -> convert(e))
				.collect(Collectors.toList());
	}

	public List<Movie> getPaginatedMovie(int offset, int limit) {
		final EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		final CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
		CriteriaQuery<MovieEntity> criteriaQuery = criteriaBuilder.createQuery(MovieEntity.class);
		Root<MovieEntity> from = criteriaQuery.from(MovieEntity.class);
		CriteriaQuery<MovieEntity> select = criteriaQuery.select(from);
		TypedQuery<MovieEntity> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(limit);
		return typedQuery.getResultList().stream().map(e -> convert(e))
				.collect(Collectors.toList());

	}

	public List<Movie> findMovieByName(String name) {
		return movieRepository.findByNameLikeCaseInsensitive("%"+name+"%").stream().map(e -> convert(e))
				.collect(Collectors.toList());
	}

	public List<Movie> findMovieByGenre(String genre) {
		return movieRepository.findByGenreLikeCaseInsensitive("%"+genre+"%").stream().map(e -> convert(e))
				.collect(Collectors.toList());
	}

	
	public List<Genre> findAllGenre() {
		return StreamSupport.stream(genreRepository.findAll().spliterator(), false).map(g -> convert(g))
				.collect(Collectors.toList());
	}
	
	private Movie convert(MovieEntity me) {
		return new Movie(me.getTitle(), me.getResume(), extractImageUrl(me.getImage()), Arrays.stream(me.getGenre().split("/")).map(s -> s.trim()).collect(Collectors.toList()), me.getOriginal_title());
	}
	private Genre convert(GenreEntity ge) {
		return new Genre(ge.getId(), ge.getValue());
	}
	
	private String extractImageUrl(String xmlImage) {
		int startFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_START_IMAGE);
		int endFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_END_IMAGE);
		int startIndex = startFirstImageUrl + PATTERN_START_IMAGE.length();
		return new String(StringUtils.substring(xmlImage, startIndex, endFirstImageUrl));
	}
	
}

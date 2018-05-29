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
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plec.kodi.domain.Genre;
import com.plec.kodi.domain.KodiMedia;
import com.plec.kodi.domain.KodiMediaType;
import com.plec.kodi.entity.GenreEntity;
import com.plec.kodi.entity.MovieEntity;
import com.plec.kodi.entity.TvShowEntity;
import com.plec.kodi.repository.GenreRepository;
import com.plec.kodi.repository.MovieRepository;
import com.plec.kodi.repository.TvShowRepository;


@Service
public class KodiService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KodiService.class);


	private static final String PATTERN_START_IMAGE_MOVIE = "preview=\"";
	private static final String PATTERN_END_IMAGE_MOVIE = "\">";

	private static final String PATTERN_START_IMAGE_TVSHOW = "<thumb aspect=\"poster\"";
	private static final String PATTERN_START_IMAGE_TVSHOW_URL = ">";
	private static final String PATTERN_END_IMAGE_TVSHOW = "</thumb>";
	
	private MovieRepository movieRepository;

	private GenreRepository genreRepository;

	private TvShowRepository tvShowRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	public KodiService(MovieRepository movieRepository, GenreRepository genreRepository, TvShowRepository tvShowRepository) {
		super();
		this.movieRepository = movieRepository;
		this.genreRepository = genreRepository;
		this.tvShowRepository = tvShowRepository;
	}

	public long countMedia(KodiMediaType mediaType) {
		switch (mediaType) {
			case MOVIE:
				return movieRepository.count();
			case TVSHOW:
				return tvShowRepository.count();
			default:
				throw new RuntimeException("MediaType " + mediaType + "is not supported");
		}
	}

	public List<KodiMedia> getMedia(KodiMediaType mediaType) {
		switch (mediaType) {
			case MOVIE:
				return StreamSupport.stream(movieRepository.findAll().spliterator(), false).map(e -> convert(e))
						.collect(Collectors.toList());
			case TVSHOW:
				return StreamSupport.stream(tvShowRepository.findAll().spliterator(), false).map(e -> convert(e))
						.collect(Collectors.toList());
			default:
				throw new RuntimeException("MediaType " + mediaType + "is not supported");
		}
	}

	public List<KodiMedia> getPaginatedMovie(int offset, int limit) {
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
	public List<KodiMedia> getPaginatedTvShow(int offset, int limit) {
		final EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		final CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
		CriteriaQuery<TvShowEntity> criteriaQuery = criteriaBuilder.createQuery(TvShowEntity.class);
		Root<TvShowEntity> from = criteriaQuery.from(TvShowEntity.class);
		CriteriaQuery<TvShowEntity> select = criteriaQuery.select(from);
		TypedQuery<TvShowEntity> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(limit);
		return typedQuery.getResultList().stream().map(e -> convert(e))
				.collect(Collectors.toList());
	}


	
	public List<KodiMedia> findKodiMediaByName(KodiMediaType kodiMediaType, String name) {
		switch (kodiMediaType) {
			case MOVIE:
				return movieRepository.findByNameLikeCaseInsensitive("%"+name+"%").stream().map(e -> convert(e))
						.collect(Collectors.toList());
			case TVSHOW:
				return tvShowRepository.findByNameLikeCaseInsensitive("%"+name+"%").stream().map(e -> convert(e))
						.collect(Collectors.toList());
			default:
				throw new RuntimeException("MediaType " + kodiMediaType + "is not supported");
		}
	}

	public List<KodiMedia> findKodiMediaByGenre(KodiMediaType kodiMediaType, String genre) {
		switch (kodiMediaType) {
			case MOVIE:
				return movieRepository.findByGenreLikeCaseInsensitive("%"+genre+"%").stream().map(e -> convert(e))
						.collect(Collectors.toList());
			case TVSHOW:
				return tvShowRepository.findByGenreLikeCaseInsensitive("%"+genre+"%").stream().map(e -> convert(e))
						.collect(Collectors.toList());
			default:
				throw new RuntimeException("MediaType " + kodiMediaType + "is not supported");
		}
	}

	
	public List<Genre> findAllGenre() {
		return StreamSupport.stream(genreRepository.findAll().spliterator(), false).map(g -> convert(g))
				.collect(Collectors.toList());
	}
	
	private KodiMedia convert(MovieEntity me) {
		return new KodiMedia(me.getTitle(), me.getResume(), extractMovieImageUrl(me.getImage()), Arrays.stream(me.getGenre().split("/")).map(s -> s.trim()).collect(Collectors.toList()), me.getOriginal_title());
	}
	private KodiMedia convert(TvShowEntity tse) {
		return new KodiMedia(tse.getTitle(), tse.getResume(), extractTvShowImageUrl(tse.getImage()), Arrays.stream(tse.getGenre().split("/")).map(s -> s.trim()).collect(Collectors.toList()), tse.getOriginal_title());
	}

	private Genre convert(GenreEntity ge) {
		return new Genre(ge.getId(), ge.getValue());
	}
	
	private String extractMovieImageUrl(String xmlImage) {
		int startFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_START_IMAGE_MOVIE);
		int endFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_END_IMAGE_MOVIE);
		int startIndex = startFirstImageUrl + PATTERN_START_IMAGE_MOVIE.length();
		return new String(StringUtils.substring(xmlImage, startIndex, endFirstImageUrl));
	}
	private String extractTvShowImageUrl(String xmlImage) {
		//find an image of type poster
		int startFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_START_IMAGE_TVSHOW);
		int startIndex = startFirstImageUrl + PATTERN_START_IMAGE_TVSHOW.length();
		String posterImageUrl = new String(StringUtils.substring(xmlImage, startIndex));
		LOGGER.debug("tvshow poster URL step 1 "+ posterImageUrl);
		
		//remove the starting string with before the URL
		int startUrl = StringUtils.indexOf(posterImageUrl, PATTERN_START_IMAGE_TVSHOW_URL);
		posterImageUrl = new String(StringUtils.substring(posterImageUrl, startUrl + PATTERN_START_IMAGE_TVSHOW_URL.length()));
		LOGGER.debug("tvshow poster URL step 2 "+ posterImageUrl);
		
		//remove end string starts with </thumb>
		int endImageUrl = StringUtils.indexOf(posterImageUrl, PATTERN_END_IMAGE_TVSHOW);
		posterImageUrl = new String (StringUtils.substring(posterImageUrl, 0, endImageUrl));
		LOGGER.debug("tvshow poster URL step 3 "+ posterImageUrl);
		return posterImageUrl;
	}
	
}

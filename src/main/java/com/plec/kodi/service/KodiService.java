package com.plec.kodi.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plec.kodi.converters.KodiMediaConverter;
import com.plec.kodi.domain.Episode;
import com.plec.kodi.domain.Genre;
import com.plec.kodi.domain.KodiMedia;
import com.plec.kodi.domain.KodiMediaType;
import com.plec.kodi.domain.Movie;
import com.plec.kodi.domain.TvShow;
import com.plec.kodi.repository.EpisodeRepository;
import com.plec.kodi.repository.GenreRepository;
import com.plec.kodi.repository.MovieRepository;
import com.plec.kodi.repository.TvShowRepository;
import com.plec.kodi.utils.Constants;

@Service
public class KodiService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KodiService.class);

	private MovieRepository movieRepository;

	private GenreRepository genreRepository;

	private TvShowRepository tvShowRepository;

	private EpisodeRepository episodeRepository;

	@Autowired
	public KodiService(MovieRepository movieRepository, GenreRepository genreRepository,
			TvShowRepository tvShowRepository, EpisodeRepository episodeRepository) {
		super();
		this.movieRepository = movieRepository;
		this.genreRepository = genreRepository;
		this.tvShowRepository = tvShowRepository;
		this.episodeRepository = episodeRepository;
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
		LOGGER.debug("get media : " + mediaType);
		switch (mediaType) {
		case MOVIE:
			return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
					.map(e -> KodiMediaConverter.convert(e)).collect(Collectors.toList());
		case TVSHOW:
			return StreamSupport.stream(tvShowRepository.findAll().spliterator(), false)
					.map(e -> KodiMediaConverter.convert(e)).collect(Collectors.toList());
		default:
			throw new RuntimeException("MediaType " + mediaType + "is not supported");
		}
	}

	public List<Movie> getPaginatedMovie(int offset, int limit, String order) {
		return movieRepository.getMedia(null, null, null, offset, limit, getOrder(order)).stream().map(e -> KodiMediaConverter.convert(e))
				.collect(Collectors.toList());
	}

	public List<TvShow> getPaginatedTvShow(int offset, int limit, String order) {
		return tvShowRepository.getMedia(null, null, null, offset, limit, getOrder(order)).stream()
				.map(e -> KodiMediaConverter.convert(e)).collect(Collectors.toList());
	}

	public List<Movie> findMovieByName(String name) {
		return movieRepository.findByNameLikeCaseInsensitive("%" + name + "%").stream()
				.map(e -> KodiMediaConverter.convert(e)).collect(Collectors.toList());
	}

	public List<TvShow> findTvShowByName(String name) {
		return tvShowRepository.findByNameLikeCaseInsensitive("%" + name + "%").stream()
				.map(e -> KodiMediaConverter.convert(e)).collect(Collectors.toList());
	}

	public List<Movie> findMovie(String genre, String title, String format, int offset, int limit, String order) {
		String currentGenre = null;
		String currentTitle = null;
		String currentFormat = null;
		if (!StringUtils.isEmpty(genre)) {
			currentGenre = "%"+genre+"%";
		}
		if (!StringUtils.isEmpty(title)) {
			currentTitle = "%"+title+"%";
		}
		if (!StringUtils.isEmpty(format)) {
			currentFormat = "%"+format;
		}
		return movieRepository.getMedia(currentGenre, currentTitle, currentFormat, offset, limit, order).stream()
				.map(e -> KodiMediaConverter.convert(e)).collect(Collectors.toList());
	}
	
	
	public List<TvShow> findTvShow(String genre, String title, int offset, int limit, String order) {
		String currentGenre = null;
		String currentTitle = null;
		if (!StringUtils.isEmpty(genre)) {
			currentGenre = "%"+genre+"%";
		}
		if (!StringUtils.isEmpty(title)) {
			currentTitle = "%"+title+"%";
		}
		return tvShowRepository.getMedia(currentGenre, currentTitle, null, offset, limit, order).stream()
				.map(e -> KodiMediaConverter.convert(e)).collect(Collectors.toList());
	}

	public List<TvShow> findTvShowByGenre(String genre) {
		return tvShowRepository.findByGenreLikeCaseInsensitive("%" + genre + "%").stream()
				.map(e -> KodiMediaConverter.convert(e)).collect(Collectors.toList());
	}

	public TvShow getTvShowById(long id) {
		return KodiMediaConverter.convert(tvShowRepository.findById(id).get());
	}

	public Movie getMovieById(long id) {
		return KodiMediaConverter.convert(movieRepository.findById(id).get());
	}

	public List<Genre> findAllGenre() {
		return StreamSupport.stream(genreRepository.findAll().spliterator(), false)
				.map(g -> KodiMediaConverter.convert(g)).collect(Collectors.toList());
	}

	public List<Episode> findEpisodesFromTvShow(long idShow) {
		return episodeRepository.findByTvShow(idShow).stream().map(e -> KodiMediaConverter.convert(e))
				.collect(Collectors.toList());
	}

	public long countMedia(KodiMediaType mediaType, String genre, String title, String format) {
		String currentGenre = null;
		String currentTitle = null;
		String currentFormat = null;
		if (!StringUtils.isEmpty(genre)) {
			currentGenre = "%"+genre+"%";
		}
		if (!StringUtils.isEmpty(title)) {
			currentTitle = "%"+title+"%";
		}
		if (!StringUtils.isEmpty(format)) {
			currentFormat = "%"+format;
		}

		switch (mediaType) {
		case MOVIE:
			return movieRepository.countMedia(currentGenre, currentTitle, currentFormat);
		case TVSHOW:
			return tvShowRepository.countMedia(currentGenre, currentTitle, null);
		default:
			throw new RuntimeException("MediaType " + mediaType + "is not supported");
		}
	}

	private String getOrder(String order) {
		String orderBy = Constants.ORDER_TITLE;
		if (order.equalsIgnoreCase(Constants.ORDER_DATE)) {
			orderBy = Constants.ORDER_DATE;
		}
		return orderBy;
	}

}

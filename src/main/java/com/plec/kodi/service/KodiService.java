package com.plec.kodi.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plec.kodi.converters.KodiMediaConverter;
import com.plec.kodi.domain.Genre;
import com.plec.kodi.domain.KodiMedia;
import com.plec.kodi.domain.KodiMediaType;
import com.plec.kodi.repository.GenreRepository;
import com.plec.kodi.repository.MovieRepository;
import com.plec.kodi.repository.TvShowRepository;


@Service
public class KodiService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KodiService.class);

	
	private MovieRepository movieRepository;

	private GenreRepository genreRepository;

	private TvShowRepository tvShowRepository;


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
		LOGGER.debug("get media : " + mediaType);
		switch (mediaType) {
			case MOVIE:
				return StreamSupport.stream(movieRepository.findAll().spliterator(), false).map(e -> KodiMediaConverter.convert(e))
						.collect(Collectors.toList());
			case TVSHOW:
				return StreamSupport.stream(tvShowRepository.findAll().spliterator(), false).map(e -> KodiMediaConverter.convert(e))
						.collect(Collectors.toList());
			default:
				throw new RuntimeException("MediaType " + mediaType + "is not supported");
		}
	}
	
	public List<KodiMedia> getPaginatedMovie(int offset, int limit) {
		return movieRepository.getMedia(offset, limit).stream().map(e -> KodiMediaConverter.convert(e))
				.collect(Collectors.toList());
	}
	
	public List<KodiMedia> getPaginatedTvShow(int offset, int limit) {
		return tvShowRepository.getMedia(offset, limit).stream().map(e -> KodiMediaConverter.convert(e))
				.collect(Collectors.toList());
	}


	
	public List<KodiMedia> findKodiMediaByName(KodiMediaType kodiMediaType, String name) {
		switch (kodiMediaType) {
			case MOVIE:
				return movieRepository.findByNameLikeCaseInsensitive("%"+name+"%").stream().map(e -> KodiMediaConverter.convert(e))
						.collect(Collectors.toList());
			case TVSHOW:
				return tvShowRepository.findByNameLikeCaseInsensitive("%"+name+"%").stream().map(e -> KodiMediaConverter.convert(e))
						.collect(Collectors.toList());
			default:
				throw new RuntimeException("MediaType " + kodiMediaType + "is not supported");
		}
	}

	public List<KodiMedia> findKodiMediaByGenre(KodiMediaType kodiMediaType, String genre) {
		switch (kodiMediaType) {
			case MOVIE:
				return movieRepository.findByGenreLikeCaseInsensitive("%"+genre+"%").stream().map(e -> KodiMediaConverter.convert(e))
						.collect(Collectors.toList());
			case TVSHOW:
				return tvShowRepository.findByGenreLikeCaseInsensitive("%"+genre+"%").stream().map(e -> KodiMediaConverter.convert(e))
						.collect(Collectors.toList());
			default:
				throw new RuntimeException("MediaType " + kodiMediaType + "is not supported");
		}
	}

	
	public List<Genre> findAllGenre() {
		return StreamSupport.stream(genreRepository.findAll().spliterator(), false).map(g -> KodiMediaConverter.convert(g))
				.collect(Collectors.toList());
	}	
}

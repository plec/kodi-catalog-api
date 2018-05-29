package com.plec.kodi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.plec.kodi.domain.KodiMedia;
import com.plec.kodi.domain.KodiMediaType;
import com.plec.kodi.service.KodiService;

@RestController
@RequestMapping("/api")
@CrossOrigin(methods=RequestMethod.GET)
public class KodiTvShowController {
	
	private static final int MAX_LIMIT = 50;
	private static final int DEFAULT_LIMIT = 10;
	
	private KodiService kodiService;
	
	
	@Autowired
	public KodiTvShowController(KodiService kodiService) {
		super();
		this.kodiService = kodiService;
	}




	@GetMapping("/tvshows")
	public List<KodiMedia> getMovies() {

		return kodiService.getMedia(KodiMediaType.TVSHOW);
	}

	@GetMapping("/tvshows/count")
	public long countMovies() {
		return kodiService.countMedia(KodiMediaType.TVSHOW);
	}

	@GetMapping("/tvshows/{offset}/{limit}")
	public List<KodiMedia> getTvShowsPaginated(@PathVariable int offset, @PathVariable int limit) {
		if (limit > MAX_LIMIT) {
			limit = DEFAULT_LIMIT;
		}
		if (offset > kodiService.countMedia(KodiMediaType.TVSHOW)) {
			return new ArrayList<>();
		}

		return kodiService.getPaginatedTvShow(offset, limit);
	}

	@GetMapping("/tvshows/title/{movieName}")
	public List<KodiMedia> getMoviesByName(@PathVariable String movieName) {
		return kodiService.findKodiMediaByName(KodiMediaType.TVSHOW, movieName);
	}

	@GetMapping("/tvshows/genre/{genreName}")
	public List<KodiMedia> getMoviesByGenre(@PathVariable String genreName) {
		return kodiService.findKodiMediaByGenre(KodiMediaType.TVSHOW, genreName);
	}
}

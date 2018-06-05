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

import com.plec.kodi.domain.Episode;
import com.plec.kodi.domain.KodiMedia;
import com.plec.kodi.domain.KodiMediaType;
import com.plec.kodi.domain.TvShow;
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
	public List<TvShow> getTvShowsPaginated(@PathVariable int offset, @PathVariable int limit) {
		return getTvShowsPaginated(offset, limit, "title");
	}
	
	@GetMapping("/tvshows/{offset}/{limit}/{order}")
	public List<TvShow> getTvShowsPaginated(@PathVariable int offset, @PathVariable int limit, @PathVariable String order) {
		if (limit > MAX_LIMIT) {
			limit = DEFAULT_LIMIT;
		}
		if (offset > kodiService.countMedia(KodiMediaType.TVSHOW)) {
			return new ArrayList<>();
		}

		return kodiService.getPaginatedTvShow(offset, limit, order);
	}

	@GetMapping("/tvshows/title/{movieName}")
	public List<TvShow> getTvShowsByName(@PathVariable String movieName) {
		return kodiService.findTvShowByName(movieName);
	}

	@GetMapping("/tvshows/genre/{genreName}")
	public List<TvShow> getTvShowsByGenre(@PathVariable String genreName) {
		return kodiService.findTvShowByGenre(genreName);
	}

	@GetMapping("/tvshows/{id}/episodes/")
	public List<Episode> getEpisodes(@PathVariable String id) {
		return kodiService.findEpisodesFromTvShow(Long.parseLong(id));
	}

}

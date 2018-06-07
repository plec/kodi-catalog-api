package com.plec.kodi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.plec.kodi.domain.TvShowDetails;
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
	@GetMapping("/tvshows/count/genre/{genreName}")
	public long countMoviesByGenre(@PathVariable String genreName) {
		return kodiService.countMedia(KodiMediaType.TVSHOW, genreName, null);
	}
	@GetMapping("/tvshows/count/title/{titleName}")
	public long countMoviesByTitle(@PathVariable String titleName) {
		return kodiService.countMedia(KodiMediaType.TVSHOW, null, titleName);
	}
	@GetMapping("/tvshows/count/genre/{genreName}/title/{titleName}")
	public long countMoviesByGenreOrTitle(@PathVariable String genreName, @PathVariable String titleName) {
		return kodiService.countMedia(KodiMediaType.TVSHOW, genreName, titleName);
	}


	@GetMapping("/tvshows/{offset}/{limit}")
	public List<TvShow> getTvShowsPaginated(@PathVariable int offset, @PathVariable int limit) {
		return getTvShowsPaginated(offset, limit, "title");
	}

	@GetMapping("/tvshows/{id}")
	public TvShowDetails getTvShowsById(@PathVariable String id) {
		TvShowDetails details = new TvShowDetails(kodiService.getTvShowById(Long.parseLong(id)));
		Map<String, List<Episode>> episodesSaison = new HashMap<>();
		List<Episode> episodesList = kodiService.findEpisodesFromTvShow(Long.parseLong(id));
		for (Episode e : episodesList) {
			if (episodesSaison.get(e.getSeason()) == null) {
				episodesSaison.put(e.getSeason(), new ArrayList<>());
			}
			episodesSaison.get(e.getSeason()).add(e);			
		}
		details.setSeasons(episodesSaison.keySet());
		details.setEpisodes(episodesSaison);
		details.setTotalEpisodes(episodesList.size());
		return details;
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
	
	@GetMapping("/tvshows/genre/{genreName}/{offset}/{limit}/{order}")
	public List<TvShow> getTvshowByGenre(@PathVariable String genreName, @PathVariable int offset, @PathVariable int limit, @PathVariable String order) {
		if (limit > MAX_LIMIT) {
			limit = DEFAULT_LIMIT;
		}
		return kodiService.findTvShow(genreName, null, offset, limit, order);
	}
	
	@GetMapping("/tvshows/title/{titleName}/{offset}/{limit}/{order}")
	public List<TvShow> getTvshowByTitle(@PathVariable String titleName, @PathVariable int offset, @PathVariable int limit, @PathVariable String order) {
		if (limit > MAX_LIMIT) {
			limit = DEFAULT_LIMIT;
		}
		return kodiService.findTvShow(null, titleName, offset, limit, order);
	}
	
	@GetMapping("/tvshows/genre/{genreName}/title/{titleName}/{offset}/{limit}/{order}")
	public List<TvShow> getTvshowByGenreAndTitle(@PathVariable String genreName, @PathVariable String titleName, @PathVariable int offset, @PathVariable int limit, @PathVariable String order) {
		if (limit > MAX_LIMIT) {
			limit = DEFAULT_LIMIT;
		}
		return kodiService.findTvShow(genreName, titleName, offset, limit, order);
	}

}

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

import com.plec.kodi.domain.Genre;
import com.plec.kodi.domain.KodiMedia;
import com.plec.kodi.domain.KodiMediaType;
import com.plec.kodi.domain.Movie;
import com.plec.kodi.service.KodiService;

@RestController
@RequestMapping("/api")
@CrossOrigin(methods = RequestMethod.GET)
public class KodiMovieController {

	private static final int MAX_LIMIT = 50;
	private static final int DEFAULT_LIMIT = 10;

	private KodiService kodiService;

	@Autowired
	public KodiMovieController(KodiService kodiService) {
		super();
		this.kodiService = kodiService;
	}

	@GetMapping("/movies")
	public List<KodiMedia> getMovies() {

		return kodiService.getMedia(KodiMediaType.MOVIE);
	}

	@GetMapping("/movies/count")
	public long countMovies() {
		return kodiService.countMedia(KodiMediaType.MOVIE);
	}

	@GetMapping("/movies/{offset}/{limit}")
	public List<Movie> getMoviesPaginated(@PathVariable int offset, @PathVariable int limit) {
		
		return getMoviesPaginated(offset, limit, "title");
	}
	
	@GetMapping("/movies/{offset}/{limit}/{order}")
	public List<Movie> getMoviesPaginated(@PathVariable int offset, @PathVariable int limit, @PathVariable String order) {
		if (limit > MAX_LIMIT) {
			limit = DEFAULT_LIMIT;
		}
		if (offset > kodiService.countMedia(KodiMediaType.MOVIE)) {
			return new ArrayList<>();
		}

		return kodiService.getPaginatedMovie(offset, limit, order);
	}

	@GetMapping("/movie/{id}")
	public Movie getMoviesById(@PathVariable String id) {
		return kodiService.getMovieById(Long.parseLong(id));
	}

	@GetMapping("/movies/title/{movieName}")
	public List<Movie> getMoviesByName(@PathVariable String movieName) {
		return kodiService.findMovieByName(movieName);
	}

	@GetMapping("/movies/genre/{genreName}")
	public List<Movie> getMoviesByGenre(@PathVariable String genreName) {
		return kodiService.findMovieByGenre(genreName);
	}

	@GetMapping("/genres")
	public List<Genre> getMoviesGenres() {
		return kodiService.findAllGenre();
	}

}

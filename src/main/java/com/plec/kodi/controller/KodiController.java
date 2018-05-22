package com.plec.kodi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plec.kodi.domain.Genre;
import com.plec.kodi.domain.Movie;
import com.plec.kodi.service.KodiService;

@RestController
@RequestMapping("/api")
public class KodiController {
	
	private static final int MAX_LIMIT = 50;
	private static final int DEFAULT_LIMIT = 10;
	
	private KodiService kodiService;
	
	
	@Autowired
	public KodiController(KodiService kodiService) {
		super();
		this.kodiService = kodiService;
	}



	@GetMapping("/movies")
	public List<Movie> getMovies() {

		return kodiService.getMovies();
	}
	
	@GetMapping("/movies/{offset}/{limit}")
	public List<Movie> getMoviesPaginated(@PathVariable int offset, @PathVariable int limit) {
		if (limit > MAX_LIMIT) {
			limit = DEFAULT_LIMIT;
		}
		if (offset > kodiService.countMovies()) {
			return new ArrayList<>();
		}
		
		return kodiService.getPaginatedMovie(offset, limit);
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

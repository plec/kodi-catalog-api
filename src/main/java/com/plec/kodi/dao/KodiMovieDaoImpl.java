package com.plec.kodi.dao;

import com.plec.kodi.entity.MovieEntity;

public class KodiMovieDaoImpl extends KodiMediaGenericAbstractDao<MovieEntity, Long> implements KodiMovieDao<MovieEntity, Long>  {
	public KodiMovieDaoImpl() {
		super(MovieEntity.class);
	}
}

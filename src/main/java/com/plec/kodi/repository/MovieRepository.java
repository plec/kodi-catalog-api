package com.plec.kodi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.plec.kodi.dao.KodiMovieDao;
import com.plec.kodi.entity.MovieEntity;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity,Long>, KodiMovieDao<MovieEntity,Long> {

	@Query("select me from MovieEntity me where lower(me.title) like lower(:movieName)")
	public List<MovieEntity> findByNameLikeCaseInsensitive(@Param("movieName") String movieName);

	@Query("select me from MovieEntity me where lower(me.genre) like lower(:genreName)")
	public List<MovieEntity> findByGenreLikeCaseInsensitive(@Param("genreName") String genreName);
	
	
}

package com.plec.kodi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.plec.kodi.dao.KodiTvShowDao;
import com.plec.kodi.entity.TvShowEntity;

public interface TvShowRepository extends CrudRepository<TvShowEntity,Long>, KodiTvShowDao<TvShowEntity,Long>{
	@Query("select tse from TvShowEntity tse where lower(tse.title) like lower(:showName)")
	public List<TvShowEntity> findByNameLikeCaseInsensitive(@Param("showName") String showName);

	@Query("select tse from TvShowEntity tse where lower(tse.genre) like lower(:genreName)")
	public List<TvShowEntity> findByGenreLikeCaseInsensitive(@Param("genreName") String genreName);
}

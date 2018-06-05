package com.plec.kodi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.plec.kodi.entity.EpisodeEntity;

@Repository
public interface EpisodeRepository extends CrudRepository<EpisodeEntity, Long>{
	@Query("select ee from EpisodeEntity ee where ee.idShow=:idShow order by ee.idEpisode asc")
	public List<EpisodeEntity> findByTvShow(@Param("idShow") long idShow);
}

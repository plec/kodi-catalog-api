package com.plec.kodi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.plec.kodi.entity.GenreEntity;

@Repository
public interface GenreRepository extends CrudRepository<GenreEntity,Long> {

}

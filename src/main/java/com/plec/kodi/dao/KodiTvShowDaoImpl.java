package com.plec.kodi.dao;

import com.plec.kodi.entity.TvShowEntity;

public class KodiTvShowDaoImpl extends KodiMediaGenericAbstractDao<TvShowEntity, Long> implements KodiTvShowDao<TvShowEntity, Long>  {
	public KodiTvShowDaoImpl() {
		super(TvShowEntity.class);
	}
}
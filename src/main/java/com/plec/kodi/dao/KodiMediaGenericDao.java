package com.plec.kodi.dao;

import java.util.List;

public interface KodiMediaGenericDao<E, K> {
	
	public List<E> getMedia(int offset, int limit, String order);
	public List<E> getMedia(int offset, int limit);

}

package com.plec.kodi.dao;

import java.util.List;

public interface KodiMediaGenericDao<E, K> {
	
	public List<E> getMedia(String genre, String titre, String format, int offset, int limit, String order);
	public long countMedia(String genre, String title, String format);

}

package com.plec.kodi.domain;

import java.util.List;

public class TvShow extends KodiMedia {
	
	private long totalSeasons;
	
	public TvShow(long id, String titre, String resume, String image, List<String> tags, String titre_original, String strPath, String dateAdded, long totalSeasons) {
		super(id, titre, resume, image, tags, titre_original, strPath, dateAdded);
		this.totalSeasons = totalSeasons;
	}

	public long getTotalSeasons() {
		return totalSeasons;
	}

	public void setTotalSeasons(long totalSeasons) {
		this.totalSeasons = totalSeasons;
	}
}

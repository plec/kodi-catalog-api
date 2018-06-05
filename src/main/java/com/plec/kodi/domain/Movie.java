package com.plec.kodi.domain;

import java.util.List;

public class Movie extends KodiMedia {
	
	private String strFileName;
	
	public Movie(long id, String titre, String resume, String image, List<String> tags, String titre_original, String strPath, String dateAdded, String strFileName) {
		super(id, titre, resume, image, tags, titre_original, strPath, dateAdded);
		this.strFileName = strFileName;
	}

	public String getStrFileName() {
		return strFileName;
	}

	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}
}

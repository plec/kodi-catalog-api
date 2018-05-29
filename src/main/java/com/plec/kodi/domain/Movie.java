package com.plec.kodi.domain;

import java.util.List;

public class Movie extends KodiMedia {
	public Movie(String titre, String resume, String image, List<String> tags, String titre_original) {
		super(titre, resume, image, tags, titre_original);
	}
}

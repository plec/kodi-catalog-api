package com.plec.kodi.domain;

import java.util.List;

public class Movie {

	public String titre;
	public String resume;
	public String image;
	public List<String> tags;
	public String titre_original;

	public Movie(String titre, String resume, String image, List<String> tags, String titre_original) {
		super();
		this.titre = titre;
		this.resume = resume;
		this.image = image;
		this.tags = tags;
		this.titre_original = titre_original;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitre_original() {
		return titre_original;
	}
	public void setTitre_original(String titre_original) {
		this.titre_original = titre_original;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}

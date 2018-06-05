package com.plec.kodi.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KodiMedia {

	private long id;
	private String titre;
	private String resume;
	private String image;
	private List<String> tags;
	private String titre_original;
	private String strPath;
	private LocalDateTime dateAdded;


	public KodiMedia(long id, String titre, String resume, String image, List<String> tags, String titre_original, String strPath, String dateAdded) {
		super();
		this.id = id;
		this.titre = titre;
		this.resume = resume;
		this.image = image;
		this.tags = tags;
		this.titre_original = titre_original;
		this.strPath = strPath;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateAdded, formatter);
		this.dateAdded = dateTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getStrPath() {
		return strPath;
	}
	public void setStrPath(String strPath) {
		this.strPath = strPath;
	}
	public LocalDateTime getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(LocalDateTime dateAdded) {
		this.dateAdded = dateAdded;
	}


	

}

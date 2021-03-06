package com.plec.kodi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="TvShowEntity")
@Table(name="tvshow_view")
public class TvShowEntity {

	@Id
	@Column(name="idShow")
	private long idShow;

	@Column(name="c00")
	private String title;
	
	@Column(name="c01")
	private String resume;

	@Column(name="c06")
	private String image;

	@Column(name="c08")
	private String genre;

	@Column(name="c09")
	private String original_title;

	@Column(name="strPath")
	private String strPath;

	@Column(name="dateAdded")
	private String dateAdded;

	@Column(name="totalSeasons")
	private long totalSeasons;

	public long getIdShow() {
		return idShow;
	}

	public void setIdShow(long idShow) {
		this.idShow = idShow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}
	
	 public String toString(){
	        return idShow + " | " + title;
	    }

	public String getStrPath() {
		return strPath;
	}

	public void setStrPath(String strPath) {
		this.strPath = strPath;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public long getTotalSeasons() {
		return totalSeasons;
	}

	public void setTotalSeasons(long totalSeasons) {
		this.totalSeasons = totalSeasons;
	}
}

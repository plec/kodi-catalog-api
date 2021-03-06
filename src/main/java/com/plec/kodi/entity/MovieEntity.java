package com.plec.kodi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="MovieEntity")
@Table(name="movie_view")
public class MovieEntity {

	@Id
	@Column(name="idMovie")
	private long idMovie;

	@Column(name="idFile")
	private long idFile;

	@Column(name="c00")
	private String title;
	
	@Column(name="c01")
	private String resume;

	@Column(name="c08")
	private String image;

	@Column(name="c14")
	private String genre;

	@Column(name="c16")
	private String original_title;
	
	@Column(name="strPath")
	private String strPath;

	@Column(name="strFileName")
	private String strFileName;

	@Column(name="dateAdded")
	private String dateAdded;

	public long getIdFile() {
		return idFile;
	}

	public void setIdFile(long idFile) {
		this.idFile = idFile;
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
	        return idFile+" | " + title;
	    }

	public long getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(long idMovie) {
		this.idMovie = idMovie;
	}

	public String getStrPath() {
		return strPath;
	}

	public void setStrPath(String strPath) {
		this.strPath = strPath;
	}

	public String getStrFileName() {
		return strFileName;
	}

	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

}

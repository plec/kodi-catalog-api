package com.plec.kodi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="EpisodeEntity")
@Table(name="episode_view")
public class EpisodeEntity {
	@Id
	@Column(name="idEpisode")
	private long idEpisode;

	@Column(name="idShow")
	private long idShow;
	
	@Column(name="c00")
	private String title;

	@Column(name="c01")
	private String resume;

	@Column(name="strPath")
	private String strPath;

	@Column(name="strFileName")
	private String strFileName;

	@Column(name="c12")
	private String season;

	@Column(name="c13")
	private String episode;

	@Column(name="dateAdded")
	private String dateAdded;

	public long getIdEpisode() {
		return idEpisode;
	}

	public void setIdEpisode(long idEpisode) {
		this.idEpisode = idEpisode;
	}

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

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String episode) {
		this.episode = episode;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

}


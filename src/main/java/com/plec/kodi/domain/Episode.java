package com.plec.kodi.domain;



public class Episode {

	private long idEpisode;
	private long idShow;
	private String title;
	private String resume;
	private String strPath;
	private String strFileName;
	private String season;
	private String episode;
	private String dateAdded;
	
	public Episode(long idEpisode, long idShow, String title, String resume, String strPath, String strFileName,
			String season, String episode, String dateAdded) {
		super();
		this.idEpisode = idEpisode;
		this.idShow = idShow;
		this.title = title;
		this.resume = resume;
		this.strPath = strPath;
		this.strFileName = strFileName;
		this.season = season;
		this.episode = episode;
		this.dateAdded = dateAdded;
	}
	
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

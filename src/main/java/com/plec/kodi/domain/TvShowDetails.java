package com.plec.kodi.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TvShowDetails extends TvShow {
	

	private Map<String, List<Episode>> episodes;
	
	private Set<String> seasons;
	
	private int totalEpisodes;
	
	public TvShowDetails(long id, String titre, String resume, String image, List<String> tags, String titre_original, String strPath, String dateAdded, long totalSeasons) {
		super(id, titre, resume, image, tags, titre_original, strPath, dateAdded,totalSeasons);
	}

	public TvShowDetails(TvShow tvShow) {
		super(tvShow.getId(), tvShow.getTitre(), tvShow.getResume(), tvShow.getImage(), tvShow.getTags(), tvShow.getTitre_original(), tvShow.getStrPath(), tvShow.getDateAdded(),tvShow.getTotalSeasons());
	}

	public Map<String, List<Episode>> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Map<String, List<Episode>> episodes) {
		this.episodes = episodes;
	}

	public Set<String> getSeasons() {
		return seasons;
	}

	public void setSeasons(Set<String> seasons) {
		this.seasons = seasons;
	}

	public int getTotalEpisodes() {
		return totalEpisodes;
	}

	public void setTotalEpisodes(int totalEpisodes) {
		this.totalEpisodes = totalEpisodes;
	}




}

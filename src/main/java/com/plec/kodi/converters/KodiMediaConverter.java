package com.plec.kodi.converters;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plec.kodi.domain.Episode;
import com.plec.kodi.domain.Genre;
import com.plec.kodi.domain.Movie;
import com.plec.kodi.domain.TvShow;
import com.plec.kodi.entity.EpisodeEntity;
import com.plec.kodi.entity.GenreEntity;
import com.plec.kodi.entity.MovieEntity;
import com.plec.kodi.entity.TvShowEntity;

public class KodiMediaConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(KodiMediaConverter.class);


	private static final String PATTERN_START_XML_IMAGE = "<thumb aspect=\"poster\"";
	private static final String PATTERN_START_XML_IMAGE_URL = ">";
	private static final String PATTERN_END_XML_IMAGE = "</thumb>";
	
	
	public static Movie convert(MovieEntity me) {
		String pathFile = StringUtils.remove(me.getStrPath(),"nfs://192.168.1.47/volume1/video");
		return new Movie(
				me.getIdMovie(),
				me.getTitle(),
				me.getResume(),
				extractImageUrl(me.getImage()),
				Arrays.stream(me.getGenre().split("/")).map(s -> s.trim()).collect(Collectors.toList()),
				me.getOriginal_title(),
				/*me.getStrPath()*/ pathFile + me.getStrFileName(),
				me.getDateAdded(),
				/*me.getStrFileName()*/"");
	}
	public static TvShow convert(TvShowEntity tse) {
		return new TvShow(
				tse.getIdShow(),
				tse.getTitle(),
				tse.getResume(),
				extractImageUrl(tse.getImage()),
				Arrays.stream(tse.getGenre().split("/")).map(s -> s.trim()).collect(Collectors.toList()),
				tse.getOriginal_title(),
				/*tse.getStrPath()*/"",
				tse.getDateAdded(),
				tse.getTotalSeasons());
	}
	public static Episode convert(EpisodeEntity ee) {
		return new Episode(ee.getIdEpisode(), ee.getIdShow(), ee.getTitle(), ee.getResume(), /*ee.getStrPath()*/"", /*ee.getStrFileName()*/"",
				ee.getSeason(), ee.getEpisode(), ee.getDateAdded());
	}
	public static Genre convert(GenreEntity ge) {
		return new Genre(ge.getId(), ge.getValue());
	}
	

	private static String extractImageUrl(String xmlImage) {
		//find an image of type poster
		int startFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_START_XML_IMAGE);
		int startIndex = startFirstImageUrl + PATTERN_START_XML_IMAGE.length();
		String posterImageUrl = new String(StringUtils.substring(xmlImage, startIndex));
		LOGGER.debug("tvshow poster URL step 1 "+ posterImageUrl);
		
		//remove the starting string with before the URL
		int startUrl = StringUtils.indexOf(posterImageUrl, PATTERN_START_XML_IMAGE_URL);
		posterImageUrl = new String(StringUtils.substring(posterImageUrl, startUrl + PATTERN_START_XML_IMAGE_URL.length()));
		LOGGER.debug("tvshow poster URL step 2 "+ posterImageUrl);
		
		//remove end string starts with </thumb>
		int endImageUrl = StringUtils.indexOf(posterImageUrl, PATTERN_END_XML_IMAGE);
		posterImageUrl = new String (StringUtils.substring(posterImageUrl, 0, endImageUrl));
		LOGGER.debug("tvshow poster URL step 3 "+ posterImageUrl);
		
		//check if image starts with https
		if (!StringUtils.startsWith(posterImageUrl, "https://")) {
			posterImageUrl = StringUtils.replace(posterImageUrl, "http://", "https://");
		}
		return posterImageUrl;
	}
}

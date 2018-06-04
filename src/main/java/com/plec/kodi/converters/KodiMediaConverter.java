package com.plec.kodi.converters;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plec.kodi.domain.Genre;
import com.plec.kodi.domain.KodiMedia;
import com.plec.kodi.entity.GenreEntity;
import com.plec.kodi.entity.MovieEntity;
import com.plec.kodi.entity.TvShowEntity;

public class KodiMediaConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(KodiMediaConverter.class);


	private static final String PATTERN_START_IMAGE_MOVIE = "preview=\"";
	private static final String PATTERN_END_IMAGE_MOVIE = "\">";

	private static final String PATTERN_START_IMAGE_TVSHOW = "<thumb aspect=\"poster\"";
	private static final String PATTERN_START_IMAGE_TVSHOW_URL = ">";
	private static final String PATTERN_END_IMAGE_TVSHOW = "</thumb>";
	
	
	public static KodiMedia convert(MovieEntity me) {
		return new KodiMedia(me.getTitle(), me.getResume(), extractMovieImageUrl(me.getImage()), Arrays.stream(me.getGenre().split("/")).map(s -> s.trim()).collect(Collectors.toList()), me.getOriginal_title());
	}
	public static KodiMedia convert(TvShowEntity tse) {
		return new KodiMedia(tse.getTitle(), tse.getResume(), extractTvShowImageUrl(tse.getImage()), Arrays.stream(tse.getGenre().split("/")).map(s -> s.trim()).collect(Collectors.toList()), tse.getOriginal_title());
	}
	public static Genre convert(GenreEntity ge) {
		return new Genre(ge.getId(), ge.getValue());
	}
	
	private static String extractMovieImageUrl(String xmlImage) {
		int startFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_START_IMAGE_MOVIE);
		int endFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_END_IMAGE_MOVIE);
		int startIndex = startFirstImageUrl + PATTERN_START_IMAGE_MOVIE.length();
		String imageUrl = new String(StringUtils.substring(xmlImage, startIndex, endFirstImageUrl));
		if (!StringUtils.startsWith(imageUrl, "https://")) {
			imageUrl = StringUtils.replace(imageUrl, "http://", "https://");
		}
		return imageUrl;
	}
	private static String extractTvShowImageUrl(String xmlImage) {
		//find an image of type poster
		int startFirstImageUrl = StringUtils.indexOf(xmlImage, PATTERN_START_IMAGE_TVSHOW);
		int startIndex = startFirstImageUrl + PATTERN_START_IMAGE_TVSHOW.length();
		String posterImageUrl = new String(StringUtils.substring(xmlImage, startIndex));
		LOGGER.debug("tvshow poster URL step 1 "+ posterImageUrl);
		
		//remove the starting string with before the URL
		int startUrl = StringUtils.indexOf(posterImageUrl, PATTERN_START_IMAGE_TVSHOW_URL);
		posterImageUrl = new String(StringUtils.substring(posterImageUrl, startUrl + PATTERN_START_IMAGE_TVSHOW_URL.length()));
		LOGGER.debug("tvshow poster URL step 2 "+ posterImageUrl);
		
		//remove end string starts with </thumb>
		int endImageUrl = StringUtils.indexOf(posterImageUrl, PATTERN_END_IMAGE_TVSHOW);
		posterImageUrl = new String (StringUtils.substring(posterImageUrl, 0, endImageUrl));
		LOGGER.debug("tvshow poster URL step 3 "+ posterImageUrl);
		
		//check if image starts with https
		if (!StringUtils.startsWith(posterImageUrl, "https://")) {
			posterImageUrl = StringUtils.replace(posterImageUrl, "http://", "https://");
		}
		return posterImageUrl;
	}
}

package com.plec.kodi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="GenreEntity")
@Table(name="genre")
public class GenreEntity {
	@Id
	@Column(name="genre_id")
	private int id;

	@Column(name="name")
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}

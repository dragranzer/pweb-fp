package com.finalwebpro.ourfilm.bean;

/**
 * User.java
 * This is a model class represents a User entity
 * @author Ramesh Fadatare
 *
 */
public class Comment {
	protected int id;
	protected String name_film;
	protected String distributor_film;
    protected String comment_film;
    protected String date_comment;
	
	public Comment() {
	}
	
	public Comment(String name_film, String distributor_film, String comment_film, String date_comment) {
		super();
		this.name_film = name_film;
		this.distributor_film = distributor_film;
		this.comment_film = comment_film;
	}

	public Comment(int id, String name_film, String distributor_film, String comment_film, String date_comment) {
		super();
		this.id = id;
		this.name_film = name_film;
		this.distributor_film = distributor_film;
		this.comment_film = comment_film;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getname_film() {
		return name_film;
	}
	public void setname_film(String name_film) {
		this.name_film = name_film;
	}
	public String getdistributor_film() {
		return distributor_film;
	}
	public void setdistributor_film(String distributor_film) {
		this.distributor_film = distributor_film;
	}
	public String getcomment_film() {
		return comment_film;
	}
	public void setcomment_film(String comment_film) {
		this.comment_film = comment_film;
    }
    public String getdate_comment() {
		return date_comment;
	}
	public void setdate_comment(String date_comment) {
		this.date_comment = date_comment;
	}
}

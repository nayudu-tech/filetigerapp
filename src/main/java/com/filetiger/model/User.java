package com.filetiger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "benutzer")
public class User {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String username;
	@Column(name = "passwort")
	private String password;
	@Column(name = "gruppe")
	private Short group;
	@Column(name = "aktiv")
	private Short active;
	@Column(name = "Vorname")
	private String firstname;
	@Column(name = "Nachname")
	private String lastname;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Short getGroup() {
		return group;
	}
	public void setGroup(Short group) {
		this.group = group;
	}
	public Short getActive() {
		return active;
	}
	public void setActive(Short active) {
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}

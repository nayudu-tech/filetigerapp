package com.filetiger.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dateien_1")
public class Files implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 19864935714277374L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Lob
	@Column(name = "datei")
	private byte[] file;
	@Column(name = "size")
	private Integer size;
	@Column(name = "endung")
	private String ending;
	@Column(name = "DocID", nullable=false)
	private Integer documentId;
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "docid", referencedColumnName = "id", insertable =  false, updatable = false)
	private Documents document;
	
	public Files(Integer id, String name, byte[] file, Integer size, String ending, Integer documentId,
			Documents document) {
		super();
		this.id = id;
		this.name = name;
		this.file = file;
		this.size = size;
		this.ending = ending;
		this.documentId = documentId;
		this.document = document;
	}
	public Files() {}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getEnding() {
		return ending;
	}
	public void setEnding(String ending) {
		this.ending = ending;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	
	public Documents getDocument() {
		return document;
	}
	public void setDocument(Documents document) {
		this.document = document;
	}
}

package com.filetiger.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocToCategoryIdentity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "dokid")
	private Integer documentId;
	@Column(name = "dokversion")
	private Integer docVersion;
	@Column(name = "katid")
	private Integer categoryId;
	
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public Integer getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(Integer docVersion) {
		this.docVersion = docVersion;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
}

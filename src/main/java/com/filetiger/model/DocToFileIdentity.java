package com.filetiger.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocToFileIdentity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5734815321798239501L;

	@Column(name = "dokid")
	private Integer documentId;
	@Column(name = "dokversion")
	private Integer docVersion;
	
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
}

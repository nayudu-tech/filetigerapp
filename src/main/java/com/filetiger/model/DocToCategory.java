package com.filetiger.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dok_zu_kat")
public class DocToCategory {

	@EmbeddedId
	private DocToCategoryIdentity docToCategoryIdentity;
	
	public DocToCategoryIdentity getDocToCategoryIdentity() {
		return docToCategoryIdentity;
	}
	public void setDocToCategoryIdentity(DocToCategoryIdentity docToCategoryIdentity) {
		this.docToCategoryIdentity = docToCategoryIdentity;
	}
}

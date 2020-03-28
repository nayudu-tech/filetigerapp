package com.filetiger.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dok_zu_datei")
public class DocToFile {

	@EmbeddedId
	private DocToFileIdentity docToFileIdentity;
	@Column(name = "tabid")
	private Integer tabId;
	@Column(name = "dateiid")
	private Integer fileId;
	
	public DocToFileIdentity getDocToFileIdentity() {
		return docToFileIdentity;
	}
	public void setDocToFileIdentity(DocToFileIdentity docToFileIdentity) {
		this.docToFileIdentity = docToFileIdentity;
	}
	public Integer getTabId() {
		return tabId;
	}
	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
}

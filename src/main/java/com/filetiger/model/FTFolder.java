package com.filetiger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ordner")
public class FTFolder {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String folderName;
	@Column(name = "Aktenschrank")
	private Integer fileCabinetId;
	@Column(name = "abgeschlossen")
	private Short completed;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public Integer getFileCabinetId() {
		return fileCabinetId;
	}
	public void setFileCabinetId(Integer fileCabinetId) {
		this.fileCabinetId = fileCabinetId;
	}
	public Short getCompleted() {
		return completed;
	}
	public void setCompleted(Short completed) {
		this.completed = completed;
	}
}

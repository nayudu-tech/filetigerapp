package com.filetiger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gruppen")
public class Group {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "addkat")
	private Short addCategory;
	@Column(name = "editkat")
	private Short editCategory;
	@Column(name = "delkat")
	private Short delCategory;
	@Column(name = "addDokArt")
	private Short addDocumentArt;
	@Column(name = "editDokArt")
	private Short editDocumentArt;
	@Column(name = "delDokArt")
	private Short delDocumentArt;
	@Column(name = "addOrdner")
	private Short addFolder;
	@Column(name = "editOrdner")
	private Short editFolder;
	@Column(name = "delOrdner")
	private Short delFolder;
	@Column(name = "addDok")
	private Short addDocument;
	@Column(name = "editDok")
	private Short editDocument;
	@Column(name = "delDok")
	private Short delDocument;
	@Column(name = "editSettings")
	private Short editSettings;
	@Column(name = "editUser")
	private Short editUser;
	
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
	public Short getAddCategory() {
		return addCategory;
	}
	public void setAddCategory(Short addCategory) {
		this.addCategory = addCategory;
	}
	public Short getEditCategory() {
		return editCategory;
	}
	public void setEditCategory(Short editCategory) {
		this.editCategory = editCategory;
	}
	public Short getDelCategory() {
		return delCategory;
	}
	public void setDelCategory(Short delCategory) {
		this.delCategory = delCategory;
	}
	public Short getAddDocumentArt() {
		return addDocumentArt;
	}
	public void setAddDocumentArt(Short addDocumentArt) {
		this.addDocumentArt = addDocumentArt;
	}
	public Short getEditDocumentArt() {
		return editDocumentArt;
	}
	public void setEditDocumentArt(Short editDocumentArt) {
		this.editDocumentArt = editDocumentArt;
	}
	public Short getDelDocumentArt() {
		return delDocumentArt;
	}
	public void setDelDocumentArt(Short delDocumentArt) {
		this.delDocumentArt = delDocumentArt;
	}
	public Short getAddFolder() {
		return addFolder;
	}
	public void setAddFolder(Short addFolder) {
		this.addFolder = addFolder;
	}
	public Short getEditFolder() {
		return editFolder;
	}
	public void setEditFolder(Short editFolder) {
		this.editFolder = editFolder;
	}
	public Short getDelFolder() {
		return delFolder;
	}
	public void setDelFolder(Short delFolder) {
		this.delFolder = delFolder;
	}
	public Short getAddDocument() {
		return addDocument;
	}
	public void setAddDocument(Short addDocument) {
		this.addDocument = addDocument;
	}
	public Short getEditDocument() {
		return editDocument;
	}
	public void setEditDocument(Short editDocument) {
		this.editDocument = editDocument;
	}
	public Short getDelDocument() {
		return delDocument;
	}
	public void setDelDocument(Short delDocument) {
		this.delDocument = delDocument;
	}
	public Short getEditSettings() {
		return editSettings;
	}
	public void setEditSettings(Short editSettings) {
		this.editSettings = editSettings;
	}
	public Short getEditUser() {
		return editUser;
	}
	public void setEditUser(Short editUser) {
		this.editUser = editUser;
	}
}

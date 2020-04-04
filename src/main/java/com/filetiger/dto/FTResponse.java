package com.filetiger.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.filetiger.model.Categories;
import com.filetiger.model.DocumentTypes;
import com.filetiger.model.Documents;
import com.filetiger.model.FTFolder;
import com.filetiger.model.FillingCabinet;
import com.filetiger.model.Group;
import com.filetiger.model.User;

@SuppressWarnings("all")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FTResponse implements Serializable{

	private static final long serialVersionUID = -3809379397710170813L;
	
	private User user;
	private List<User> users;
	private DocumentTypes documentType;
	private List<DocumentTypes> documentTypes;
	private String message;
	private String status;
	private Integer statuscode;
	private String jwtToken;
	private Categories category;
	private List<Categories> categories;
	private FillingCabinet fillingCabinet;
	private List<FillingCabinet> fillingCabinets;
	private List<Documents> documents;
	private Documents document;
	private String email;
	private List<Group> groups;
	private Group group;
	private List<FTFolder> ftFolders;
	private FTFolder ftFolder;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(Integer statuscode) {
		this.statuscode = statuscode;
	}
	public List<DocumentTypes> getDocumentTypes() {
		return documentTypes;
	}
	public void setDocumentTypes(List<DocumentTypes> documentTypes) {
		this.documentTypes = documentTypes;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public List<Categories> getCategories() {
		return categories;
	}
	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}
	public List<FillingCabinet> getFillingCabinets() {
		return fillingCabinets;
	}
	public void setFillingCabinets(List<FillingCabinet> fillingCabinets) {
		this.fillingCabinets = fillingCabinets;
	}
	public List<Documents> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Documents> documents) {
		this.documents = documents;
	}
	public DocumentTypes getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentTypes documentType) {
		this.documentType = documentType;
	}
	public Categories getCategory() {
		return category;
	}
	public void setCategory(Categories category) {
		this.category = category;
	}
	public FillingCabinet getFillingCabinet() {
		return fillingCabinet;
	}
	public void setFillingCabinet(FillingCabinet fillingCabinet) {
		this.fillingCabinet = fillingCabinet;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Documents getDocument() {
		return document;
	}
	public void setDocument(Documents document) {
		this.document = document;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<FTFolder> getFtFolders() {
		return ftFolders;
	}
	public void setFtFolders(List<FTFolder> ftFolders) {
		this.ftFolders = ftFolders;
	}
	public FTFolder getFtFolder() {
		return ftFolder;
	}
	public void setFtFolder(FTFolder ftFolder) {
		this.ftFolder = ftFolder;
	}
}

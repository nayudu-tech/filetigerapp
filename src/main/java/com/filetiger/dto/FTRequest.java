package com.filetiger.dto;

import java.util.List;

public class FTRequest {

	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String newPassword;
	private String group;
	private String activeStatus;
	private String docName;
	private String docNumber;
	private String docDate;
	private String docType;
	private String docType2;
	private String note;
	private String docGrossAmount;
	private String docNetAmount;
	private String docVersion;
	private String deleteFlag;
	private String folder;
	private List<Files> files;
	private String folderName;
	private String parentFolder;
	private String folderPosition;
	private String openFolderFlag;
	private String activeFolderFlag;
	private String moduleName;
	private String operationType;
	private String id;
	private String documentTypeName;
	private String cabinetName;
	private String cabinetLocation;
	private GroupRequest groupRequest;
	private FolderRequest folderRequest;
	
	public static class FolderRequest{
		private String folderName;
		private String fileCabinetId;
		
		public String getFolderName() {
			return folderName;
		}
		public void setFolderName(String folderName) {
			this.folderName = folderName;
		}
		public String getFileCabinetId() {
			return fileCabinetId;
		}
		public void setFileCabinetId(String fileCabinetId) {
			this.fileCabinetId = fileCabinetId;
		}
	}
	
	public static class GroupRequest{
		private String groupName;
		private String addCategory;
		private String editCategory;
		private String delCategory;
		private String addDocumentArt;
		private String editDocumentArt;
		private String delDocumentArt;
		private String addFolder;
		private String editFolder;
		private String delFolder;
		private String addDocument;
		private String editDocument;
		private String delDocument;
		private String editSettings;
		private String editUser;
		
		public String getGroupName() {
			return groupName;
		}
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		public String getAddCategory() {
			return addCategory;
		}
		public void setAddCategory(String addCategory) {
			this.addCategory = addCategory;
		}
		public String getEditCategory() {
			return editCategory;
		}
		public void setEditCategory(String editCategory) {
			this.editCategory = editCategory;
		}
		public String getDelCategory() {
			return delCategory;
		}
		public void setDelCategory(String delCategory) {
			this.delCategory = delCategory;
		}
		public String getAddDocumentArt() {
			return addDocumentArt;
		}
		public void setAddDocumentArt(String addDocumentArt) {
			this.addDocumentArt = addDocumentArt;
		}
		public String getEditDocumentArt() {
			return editDocumentArt;
		}
		public void setEditDocumentArt(String editDocumentArt) {
			this.editDocumentArt = editDocumentArt;
		}
		public String getDelDocumentArt() {
			return delDocumentArt;
		}
		public void setDelDocumentArt(String delDocumentArt) {
			this.delDocumentArt = delDocumentArt;
		}
		public String getAddFolder() {
			return addFolder;
		}
		public void setAddFolder(String addFolder) {
			this.addFolder = addFolder;
		}
		public String getEditFolder() {
			return editFolder;
		}
		public void setEditFolder(String editFolder) {
			this.editFolder = editFolder;
		}
		public String getDelFolder() {
			return delFolder;
		}
		public void setDelFolder(String delFolder) {
			this.delFolder = delFolder;
		}
		public String getAddDocument() {
			return addDocument;
		}
		public void setAddDocument(String addDocument) {
			this.addDocument = addDocument;
		}
		public String getEditDocument() {
			return editDocument;
		}
		public void setEditDocument(String editDocument) {
			this.editDocument = editDocument;
		}
		public String getDelDocument() {
			return delDocument;
		}
		public void setDelDocument(String delDocument) {
			this.delDocument = delDocument;
		}
		public String getEditSettings() {
			return editSettings;
		}
		public void setEditSettings(String editSettings) {
			this.editSettings = editSettings;
		}
		public String getEditUser() {
			return editUser;
		}
		public void setEditUser(String editUser) {
			this.editUser = editUser;
		}
	}
	
	public static class Files{
		private String fileId;
		private String fileName;
		private String fileFormat;
		private String fileData;
		private String categoryIds;
		private String fileOperationType;
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFileFormat() {
			return fileFormat;
		}
		public void setFileFormat(String fileFormat) {
			this.fileFormat = fileFormat;
		}
		public String getFileData() {
			return fileData;
		}
		public void setFileData(String fileData) {
			this.fileData = fileData;
		}
		public String getCategoryIds() {
			return categoryIds;
		}
		public void setCategoryIds(String categoryIds) {
			this.categoryIds = categoryIds;
		}
		public String getFileOperationType() {
			return fileOperationType;
		}
		public void setFileOperationType(String fileOperationType) {
			this.fileOperationType = fileOperationType;
		}
		public String getFileId() {
			return fileId;
		}
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getDocDate() {
		return docDate;
	}
	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDocType2() {
		return docType2;
	}
	public void setDocType2(String docType2) {
		this.docType2 = docType2;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getDocGrossAmount() {
		return docGrossAmount;
	}
	public void setDocGrossAmount(String docGrossAmount) {
		this.docGrossAmount = docGrossAmount;
	}
	public String getDocNetAmount() {
		return docNetAmount;
	}
	public void setDocNetAmount(String docNetAmount) {
		this.docNetAmount = docNetAmount;
	}
	public List<Files> getFiles() {
		return files;
	}
	public void setFiles(List<Files> files) {
		this.files = files;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getParentFolder() {
		return parentFolder;
	}
	public void setParentFolder(String parentFolder) {
		this.parentFolder = parentFolder;
	}
	public String getFolderPosition() {
		return folderPosition;
	}
	public void setFolderPosition(String folderPosition) {
		this.folderPosition = folderPosition;
	}
	public String getOpenFolderFlag() {
		return openFolderFlag;
	}
	public void setOpenFolderFlag(String openFolderFlag) {
		this.openFolderFlag = openFolderFlag;
	}
	public String getActiveFolderFlag() {
		return activeFolderFlag;
	}
	public void setActiveFolderFlag(String activeFolderFlag) {
		this.activeFolderFlag = activeFolderFlag;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getDocumentTypeName() {
		return documentTypeName;
	}
	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCabinetName() {
		return cabinetName;
	}
	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	public String getCabinetLocation() {
		return cabinetLocation;
	}
	public void setCabinetLocation(String cabinetLocation) {
		this.cabinetLocation = cabinetLocation;
	}
	public GroupRequest getGroupRequest() {
		return groupRequest;
	}
	public void setGroupRequest(GroupRequest groupRequest) {
		this.groupRequest = groupRequest;
	}
	public FolderRequest getFolderRequest() {
		return folderRequest;
	}
	public void setFolderRequest(FolderRequest folderRequest) {
		this.folderRequest = folderRequest;
	}
}

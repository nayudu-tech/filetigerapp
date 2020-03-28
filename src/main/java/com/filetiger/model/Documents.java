package com.filetiger.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "dokumente")
public class Documents implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3021854722938342445L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String docName;
	@Column(name = "version")
	private Integer docVersion;
	@JsonFormat(pattern = "dd.MMM.yyyy")
	@Column(name = "datum")
	private Date docDate;
	@Column(name = "belegid")
	private String docNumber;
	@Column(name = "dokumentart")
	private Integer docType;
	@Column(name = "dokumentart2")
	private Integer docType2;
	@Column(name = "ordner")
	private Integer folder;
	@Column(name = "netto")
	private Double docNetAmount;
	@Column(name = "brutto")
	private Double docGrossAmount;
	@Column(name = "mwst")
	private Double mwst;
	@Column(name = "notiz")
	private String note;
	@Column(name = "benutzer")
	private Integer user;
	@JsonFormat(pattern = "dd.MMM.yyyy HH:mm:ss")
	@Column(name = "erfasst")
	private Timestamp detectedDate;
	@Column(name = "initial_benutzer")
	private Integer initialUser;
	@JsonFormat(pattern = "dd.MMM.yyyy HH:mm:ss")
	@Column(name = "initial_erfasst")
	private Timestamp initialDetectedDate;
	@Column(name = "deleted")
	private Integer deleteFlag;
	@Transient
	private String categoryIds;
	@OneToMany(targetEntity=Files.class, mappedBy = "document", fetch = FetchType.LAZY)
	private List<Files> files;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDocName() {
		return docName;
	}
	public Documents(Integer id, String docName, Integer docVersion, Date docDate, String docNumber, Integer docType,
			Integer docType2, Integer folder, Double docNetAmount, Double docGrossAmount, Double mwst, String note,
			Integer user, Timestamp detectedDate, Integer initialUser, Timestamp initialDetectedDate,
			Integer deleteFlag, String categoryIds, List<Files> files) {
		super();
		this.id = id;
		this.docName = docName;
		this.docVersion = docVersion;
		this.docDate = docDate;
		this.docNumber = docNumber;
		this.docType = docType;
		this.docType2 = docType2;
		this.folder = folder;
		this.docNetAmount = docNetAmount;
		this.docGrossAmount = docGrossAmount;
		this.mwst = mwst;
		this.note = note;
		this.user = user;
		this.detectedDate = detectedDate;
		this.initialUser = initialUser;
		this.initialDetectedDate = initialDetectedDate;
		this.deleteFlag = deleteFlag;
		this.categoryIds = categoryIds;
		this.files = files;
	}
	public Documents() {}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public Integer getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(Integer docVersion) {
		this.docVersion = docVersion;
	}
	public Date getDocDate() {
		return docDate;
	}
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	public Integer getFolder() {
		return folder;
	}
	public void setFolder(Integer folder) {
		this.folder = folder;
	}
	public Double getMwst() {
		return mwst;
	}
	public void setMwst(Double mwst) {
		this.mwst = mwst;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public Timestamp getDetectedDate() {
		return detectedDate;
	}
	public void setDetectedDate(Timestamp detectedDate) {
		this.detectedDate = detectedDate;
	}
	public Integer getInitialUser() {
		return initialUser;
	}
	public void setInitialUser(Integer initialUser) {
		this.initialUser = initialUser;
	}
	public Timestamp getInitialDetectedDate() {
		return initialDetectedDate;
	}
	public void setInitialDetectedDate(Timestamp initialDetectedDate) {
		this.initialDetectedDate = initialDetectedDate;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public Integer getDocType() {
		return docType;
	}
	public void setDocType(Integer docType) {
		this.docType = docType;
	}
	public Integer getDocType2() {
		return docType2;
	}
	public void setDocType2(Integer docType2) {
		this.docType2 = docType2;
	}
	public Double getDocNetAmount() {
		return docNetAmount;
	}
	public void setDocNetAmount(Double docNetAmount) {
		this.docNetAmount = docNetAmount;
	}
	public Double getDocGrossAmount() {
		return docGrossAmount;
	}
	public void setDocGrossAmount(Double docGrossAmount) {
		this.docGrossAmount = docGrossAmount;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	
	public List<Files> getFiles() {
		return files;
	}
	public void setFiles(List<Files> files) {
		this.files = files;
	}
}

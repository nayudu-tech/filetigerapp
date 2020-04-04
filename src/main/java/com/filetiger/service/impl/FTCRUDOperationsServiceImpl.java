package com.filetiger.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.filetiger.dto.FTRequest;
import com.filetiger.dto.FTRequest.Files;
import com.filetiger.dto.FTResponse;
import com.filetiger.model.Categories;
import com.filetiger.model.DocToCategory;
import com.filetiger.model.DocToCategoryIdentity;
import com.filetiger.model.DocToFile;
import com.filetiger.model.DocToFileIdentity;
import com.filetiger.model.DocumentTypes;
import com.filetiger.model.Documents;
import com.filetiger.model.FTFolder;
import com.filetiger.model.FillingCabinet;
import com.filetiger.model.Group;
import com.filetiger.model.User;
import com.filetiger.repository.CategoriesRepository;
import com.filetiger.repository.DocToCategoryRepository;
import com.filetiger.repository.DocToFileRepository;
import com.filetiger.repository.DocumentTypesRepository;
import com.filetiger.repository.DocumentsRepository;
import com.filetiger.repository.FTFolderRepository;
import com.filetiger.repository.FilesRepository;
import com.filetiger.repository.FillingCabinetRepository;
import com.filetiger.repository.GroupRepository;
import com.filetiger.repository.UserRepository;
import com.filetiger.service.FTCRUDOperationsService;
import com.filetiger.util.Utility;

@Service
public class FTCRUDOperationsServiceImpl implements FTCRUDOperationsService{

	@Autowired
	private DocumentsRepository documentsRepository;
	@Autowired
	private FilesRepository filesRepository;
	@Autowired
	private DocToCategoryRepository docToCategoryRepository;
	@Autowired
	private DocToFileRepository docToFileRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DocumentTypesRepository documentTypesRepository;
	@Autowired
	private CategoriesRepository categoriesRepository;
	@Autowired
	private FillingCabinetRepository fillingCabinetRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private FTFolderRepository ftFolderRepository;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	private static final Logger logger = LoggerFactory.getLogger(FTCRUDOperationsServiceImpl.class);
	
	@Override
	public FTResponse crudOperationsImpl(FTRequest ftRequest) {
		logger.info("method fetchDocumentsImpl starts");
		FTResponse ftResponse = new FTResponse();
		String serviceName = "";
		String operationType = "";
		
		try {
			
			if(ftRequest.getOperationType() != null && !ftRequest.getOperationType().isEmpty())
				operationType = ftRequest.getOperationType();
			if(ftRequest.getModuleName() != null && !ftRequest.getModuleName().isEmpty())
				serviceName = ftRequest.getModuleName();
			
			switch (serviceName) {
			case "Documents":
				ftResponse = documentsCRUDOperations(ftRequest, operationType, ftResponse);
				break;
			case "Categories":
				ftResponse = categoriesCRUDOperations(ftRequest, operationType, ftResponse);
				break;
			case "DocumentTypes":
				ftResponse = docTypesCRUDOperations(ftRequest, operationType, ftResponse);
				break;
			case "Cabinets":
				ftResponse = cabinetsCRUDOperations(ftRequest, operationType, ftResponse);
				break;
			case "Group":
				ftResponse = groupsCRUDOperations(ftRequest, operationType, ftResponse);
				break;
			case "User":
				ftResponse = usersCRUDOperations(ftRequest, operationType, ftResponse);
				break;
			case "Folder":
				ftResponse = folderCRUDOperations(ftRequest, operationType, ftResponse);
				break;
			default:
				logger.debug("Invalid module name");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg2"));
			}
			
		}catch(Exception e) {
			logger.error("technical error message :: Error :: "+e.getMessage());
			return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("technical.error.msg"));
		}
		return ftResponse;
	}
	
	private FTResponse folderCRUDOperations(FTRequest ftRequest, String operationType, FTResponse ftResponse) {
		logger.info("method folderCRUDOperations starts");
		if(!operationType.equals("")) {
			if(operationType.equals("FindAll")) {
				List<FTFolder> ftFolders = ftFolderRepository.findAll();
				if(!ftFolders.isEmpty()) {
					logger.info("Successfully fetched all users");
					ftResponse.setFtFolders(ftFolders);
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to fetch users");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
				}
			}else if(operationType.equals("Save")) {
				FTFolder ftFolder = settingFolder(ftRequest, new FTFolder());
				if(ftFolder != null) {
					logger.info("Folder saved successfully ");
					return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("folder.success.msg"));
				}else {
					logger.debug("Failed to store Folder, please try again ");
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("folder.failed.msg"));
				}
			}else if(operationType.equals("Update")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					FTFolder ftFolderById = ftFolderRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(ftFolderById != null) {
						FTFolder updateFTFolder = settingFolder(ftRequest, ftFolderById);
						if(updateFTFolder != null) {
							logger.info("Folder updated successfully ");
							return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("folder.success.msg"));
						}else {
							logger.debug("Failed to store folder, please try again ");
							return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("folder.failed.msg"));
						}
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Folder id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("FindById")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					FTFolder ftFolderById = ftFolderRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(ftFolderById != null) {
						ftResponse.setFtFolder(ftFolderById);
						logger.info("Successfully loaded folder by id");
						return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Folder id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("Delete")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					ftFolderRepository.delete(Integer.parseInt(ftRequest.getId()));
					logger.info("Successfully Deleted Folder");
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to delete folder");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else {
				logger.debug("Invalid Operation Type");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg1"));
			}
		}else {
			logger.debug("Operation Type is mandatory");
			return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg"));
		}
	}


	private FTResponse usersCRUDOperations(FTRequest ftRequest, String operationType, FTResponse ftResponse) {
		logger.info("method usersCRUDOperations starts");
		if(!operationType.equals("")) {
			if(operationType.equals("FindAll")) {
				List<User> users = userRepository.findAll();
				if(!users.isEmpty()) {
					logger.info("Successfully fetched all users");
					ftResponse.setUsers(users);
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to fetch users");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
				}
			}else if(operationType.equals("Save")) {
				User user = settingUser(ftRequest, new User());
				if(user != null) {
					logger.info("User saved successfully ");
					return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("user.success.msg"));
				}else {
					logger.debug("Failed to store group, please try again ");
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("user.failed.msg"));
				}
			}else if(operationType.equals("Update")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					User userById = userRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(userById != null) {
						User updateUser = settingUser(ftRequest, userById);
						if(updateUser != null) {
							logger.info("User updated successfully ");
							return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("user.success.msg"));
						}else {
							logger.debug("Failed to store user, please try again ");
							return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("user.failed.msg"));
						}
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Group id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("FindById")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					User user = userRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(user != null) {
						ftResponse.setUser(user);
						logger.info("Successfully loaded user by id");
						return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("User id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("Delete")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					userRepository.delete(Integer.parseInt(ftRequest.getId()));
					logger.info("Successfully Deleted User");
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to delete user");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else {
				logger.debug("Invalid Operation Type");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg1"));
			}
		}else {
			logger.debug("Operation Type is mandatory");
			return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg"));
		}
	}

	

	private FTResponse documentsCRUDOperations(FTRequest ftRequest, String operationType, FTResponse ftResponse) throws SerialException, ParseException, SQLException {
		logger.info("method documentsCRUDOperations starts");
		if(!operationType.equals("")) {
			if(operationType.equals("FindAll")) {
				List<Documents> allDocuments = documentsRepository.findAll();
				List<Documents> finalDocuments = new ArrayList<Documents>();
				if(allDocuments != null && !allDocuments.isEmpty()) {
					for (Documents documents : allDocuments) {
						List<DocToCategory> docToCategories = docToCategoryRepository.docTypeCategories(documents.getId());
						String categoryIds = "";
						for (DocToCategory docToCategory : docToCategories) {
							categoryIds = categoryIds + docToCategory.getDocToCategoryIdentity().getDocumentId() + ",";
						}
						documents.setCategoryIds(categoryIds.substring(0, categoryIds.length() - 1));
						finalDocuments.add(documents);
					}
					ftResponse.setDocuments(finalDocuments);
					logger.info("Fetch document successfully ");
					return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Faild to store document, please try again ");
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.failed.msg"));
				}
			}else if(operationType.equals("Save")) {
				Documents document = setDocuments(ftRequest, new Documents());
				if(document != null)
					return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.success.msg"));
				logger.debug("Faild to store document, please try again ");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.failed.msg"));
			}else if(operationType.equals("Update")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					Documents documentById = documentsRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(documentById != null) {
						Documents updateDocument = setDocuments(ftRequest, documentById);
						if(updateDocument != null) {
							logger.info("Document updated successfully ");
							return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.success.msg"));
						}else {
							logger.debug("Failed to store document, please try again ");
							return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.failed.msg"));
						}
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Document id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("FindById")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					Documents document = documentsRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(document == null)
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					List<DocToCategory> docToCategories = docToCategoryRepository.docTypeCategories(document.getId());
					String categoryIds = "";
					for (DocToCategory docToCategory : docToCategories) {
						categoryIds = categoryIds + docToCategory.getDocToCategoryIdentity().getDocumentId() + ",";
					}
					document.setCategoryIds(categoryIds.substring(0, categoryIds.length() - 1));
					ftResponse.setDocument(document);
					logger.info("Fetch document successfully ");
					return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Document id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("Delete")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					documentsRepository.delete(Integer.parseInt(ftRequest.getId()));
					logger.info("Successfully Deleted Group");
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to delete group");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else {
				logger.debug("Invalid Operation Type");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg1"));
			}
		}else {
			logger.debug("Operation Type is mandatory");
			return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg"));
		}
	}
	
	private FTResponse groupsCRUDOperations(FTRequest ftRequest, String operationType, FTResponse ftResponse) {
		logger.info("method groupsCRUDOperations starts");
		if(!operationType.equals("")) {
			if(operationType.equals("FindAll")) {
				List<Group> groupList = groupRepository.findAll();
				if(!groupList.isEmpty()) {
					logger.info("Successfully fetched all groups");
					ftResponse.setGroups(groupList);
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to fetch groups");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
				}
			}else if(operationType.equals("Save")) {
				Group group = settingGroup(ftRequest, new Group());
				if(group != null) {
					logger.info("Group saved successfully ");
					return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("group.success.msg"));
				}else {
					logger.debug("Failed to store group, please try again ");
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("group.failed.msg"));
				}
			}else if(operationType.equals("FindById")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					Group group = groupRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(group != null) {
						ftResponse.setGroup(group);
						logger.info("Successfully loaded group by id");
						return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Group id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("Update")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					Group groupById = groupRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(groupById != null) {
						Group updateGroup = settingGroup(ftRequest, groupById);
						if(updateGroup != null) {
							logger.info("Group updated successfully ");
							return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("group.success.msg"));
						}else {
							logger.debug("Failed to store group, please try again ");
							return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("group.failed.msg"));
						}
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Group id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("Delete")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					groupRepository.delete(Integer.parseInt(ftRequest.getId()));
					logger.info("Successfully Deleted Group");
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to delete group");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else {
				logger.debug("Invalid Operation Type");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg1"));
			}
			
		}else {
			logger.debug("Operation Type is mandatory");
			return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg"));
		}
	}
	
	private FTResponse docTypesCRUDOperations(FTRequest ftRequest, String operationType, FTResponse ftResponse) {
		logger.info("method docTypesCRUDOperations starts");
		if(!operationType.equals("")) {
			if(operationType.equals("FindAll")) {
				List<DocumentTypes> documentTypesList = documentTypesRepository.findAll();
				if(!documentTypesList.isEmpty()) {
					logger.info("Successfully fetched document types");
					ftResponse.setDocumentTypes(documentTypesList);
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to fetch document types");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
				}
			}else if(operationType.equals("Save")) {
				if(ftRequest.getDocumentTypeName() != null && !ftRequest.getDocumentTypeName().isEmpty()) {
					DocumentTypes documentType = new DocumentTypes();
					if(ftRequest.getDocumentTypeName() != null && !ftRequest.getDocumentTypeName().isEmpty())
						documentType.setDocumentType(ftRequest.getDocumentTypeName());
					documentTypesRepository.save(documentType);
					logger.info("Successfully Saved document type");
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to store document type");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg1"));
				}
			}else if(operationType.equals("Update")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					DocumentTypes documentType = documentTypesRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(documentType == null)
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					if(ftRequest.getDocumentTypeName() != null && !ftRequest.getDocumentTypeName().isEmpty())
						documentType.setDocumentType(ftRequest.getDocumentTypeName());
					DocumentTypes updatedDocumentType = documentTypesRepository.save(documentType);
					if(updatedDocumentType != null) {
						logger.info("Document Type updated successfully ");
						return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
					}else {
						logger.debug("Failed to store document type, please try again ");
						return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.type.failed.msg1"));
					}
				}else {
					logger.debug("Document type id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("FindById")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					DocumentTypes documentTypeById = documentTypesRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(documentTypeById != null) {
						ftResponse.setDocumentType(documentTypeById);
						logger.info("Successfully loaded document type by id");
						return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Document type id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("Delete")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					documentTypesRepository.delete(Integer.parseInt(ftRequest.getId()));
					logger.info("Successfully Deleted document types");
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to delete document types");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else {
				logger.debug("Invalid Operation Type");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg1"));
			}
		}else {
			logger.debug("Operation Type is mandatory");
			return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg"));
		}
	}
	
	private FTResponse categoriesCRUDOperations(FTRequest ftRequest, String operationType, FTResponse ftResponse) {
		logger.info("method categoriesCRUDOperations starts");
		if(!operationType.equals("")) {
			if(operationType.equals("FindAll")) {
				List<Categories> categoriesList = categoriesRepository.findAll();
				if(!categoriesList.isEmpty()) {
					logger.info("Successfully fetched categories");
					ftResponse.setCategories(categoriesList);
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to fetch categories");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
				}
			}else if(operationType.equals("Save")) {
				if(ftRequest != null) {
					Categories savedCategory = settingCategory(ftRequest, new Categories());
					if(savedCategory != null) {
						logger.info("Category saved successfully ");
						return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("category.success.msg"));
					}else {
						logger.debug("Failed to store category, please try again ");
						return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("category.failed.msg"));
					}
				}else {
					logger.debug("Not a valid request, please try again ");
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("invalid.reques.msg"));
				}
			}else if(operationType.equals("Update")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					Categories categoriesById = categoriesRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(categoriesById != null) {
						Categories updateCategory = settingCategory(ftRequest, categoriesById);
						if(updateCategory != null) {
							logger.info("Category updated successfully ");
							return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("category.success.msg"));
						}else {
							logger.debug("Failed to store category, please try again ");
							return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.type.failed.msg1"));
						}
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Category id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("FindById")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					Categories categoriesById = categoriesRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(categoriesById != null) {
						ftResponse.setCategory(categoriesById);
						logger.info("Successfully load document type");
						return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Document type id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("Delete")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					categoriesRepository.delete(Integer.parseInt(ftRequest.getId()));
					docToCategoryRepository.deleteAllCategoriesAttachedToCategory(Integer.parseInt(ftRequest.getId()));
					logger.info("Successfully Deleted document types");
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to delete document types");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else {
				logger.debug("Invalid Operation Type");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg1"));
			}
		}else {
			logger.debug("Operation Type is mandatory");
			return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg"));
		}
	}
	
	private FTResponse cabinetsCRUDOperations(FTRequest ftRequest, String operationType, FTResponse ftResponse) {
		logger.info("method cabinetsCRUDOperations starts");
		if(!operationType.equals("")) {
			if(operationType.equals("FindAll")) {
				List<FillingCabinet> fillingCabinets = fillingCabinetRepository.findAll();
				if(!fillingCabinets.isEmpty()) {
					logger.info("Successfully fetched categories");
					ftResponse.setFillingCabinets(fillingCabinets);
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to fetch categories");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
				}
			}else if(operationType.equals("Save")) {
				if(ftRequest != null) {
					FillingCabinet savedCabinet = settingCabinet(ftRequest, new FillingCabinet());
					if(savedCabinet != null) {
						logger.info("Cabinet saved successfully ");
						return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("cabinet.success.msg"));
					}else {
						logger.debug("Failed to store cabinet, please try again ");
						return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.type.failed.msg1"));
					}
				}else {
					logger.debug("Not a valid request, please try again ");
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("invalid.reques.msg"));
				}
			}else if(operationType.equals("Update")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					FillingCabinet fillingCabinetById = fillingCabinetRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(fillingCabinetById != null) {
						FillingCabinet updateCabinet = settingCabinet(ftRequest, fillingCabinetById);
						if(updateCabinet != null) {
							logger.info("Cabinet updated successfully ");
							return Utility.getInstance().successResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("cabinet.success.msg"));
						}else {
							logger.debug("Failed to store category, please try again ");
							return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("document.type.failed.msg1"));
						}
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Cabinet id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("FindById")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					FillingCabinet fillingCabinetById = fillingCabinetRepository.findOne(Integer.parseInt(ftRequest.getId()));
					if(fillingCabinetById != null) {
						ftResponse.setFillingCabinet(fillingCabinetById);
						logger.info("Successfully load cabinets ");
						return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
					}else {
						logger.debug("Failed to fetch data by id");
						return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("document.type.failed.msg"));
					}
				}else {
					logger.debug("Cabinet id is mandatory");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else if(operationType.equals("Delete")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty()) {
					fillingCabinetRepository.delete(Integer.parseInt(ftRequest.getId()));
					logger.info("Successfully Deleted cabinet");
					return Utility.getInstance().successResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("transaction.success.msg"));
				}else {
					logger.debug("Failed to delete cabinet");
					return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, Utility.getInstance().readProperty("id.failed.msg"));
				}
			}else {
				logger.debug("Invalid Operation Type");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg1"));
			}
		}else {
			logger.debug("Operation Type is mandatory");
			return Utility.getInstance().failureResponse(ftRequest, ftResponse, Utility.getInstance().readProperty("crud.operations.failed.msg"));
		}
	}
	
	private Documents setDocuments(FTRequest ftRequest, Documents document) throws ParseException, SerialException, SQLException {
		Documents savedDocument = null;
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yyyy");
		User user = userRepository.findByUsername(ftRequest.getEmail());
		if(user != null) {
			
			if(ftRequest.getDocName() != null && !ftRequest.getDocName().isEmpty())
				document.setDocName(ftRequest.getDocName());
			if(ftRequest.getDocNumber() != null && !ftRequest.getDocNumber().isEmpty())
				document.setDocNumber(ftRequest.getDocNumber());
			if(ftRequest.getDocType() != null && !ftRequest.getDocType().isEmpty())
				document.setDocType(Integer.parseInt(ftRequest.getDocType()));
			if(ftRequest.getDocType2() != null && !ftRequest.getDocType2().isEmpty())
				document.setDocType2(Integer.parseInt(ftRequest.getDocType2()));
			if(ftRequest.getFolder() != null && !ftRequest.getFolder().isEmpty())
				document.setFolder(Integer.parseInt(ftRequest.getFolder()));
			if(ftRequest.getNote() != null && !ftRequest.getNote().isEmpty())
				document.setNote(ftRequest.getNote());
			if(ftRequest.getDocGrossAmount() != null && !ftRequest.getDocGrossAmount().isEmpty())
				document.setDocGrossAmount(Double.parseDouble(ftRequest.getDocGrossAmount()));
			if(ftRequest.getDocNetAmount() != null && !ftRequest.getDocNetAmount().isEmpty())
				document.setDocNetAmount(Double.parseDouble(ftRequest.getDocNetAmount()));
			if(ftRequest.getDocDate() != null && !ftRequest.getDocDate().isEmpty())
				document.setDocDate(simpleDateFormat.parse(ftRequest.getDocDate()));
			if(ftRequest.getDeleteFlag() != null && !ftRequest.getDeleteFlag().isEmpty())
				document.setDeleteFlag(Integer.parseInt(ftRequest.getDeleteFlag()));
				
			document.setDetectedDate(new Timestamp(date.getTime()));
			document.setInitialDetectedDate(new Timestamp(date.getTime()));
			document.setUser(Integer.parseInt(user.getGroup().toString()));
			document.setInitialUser(Integer.parseInt(user.getGroup().toString()));
			
			if((ftRequest.getOperationType() != null && !ftRequest.getOperationType().isEmpty()) && ftRequest.getOperationType().equalsIgnoreCase("Update")) {
				if(ftRequest.getId() != null && !ftRequest.getId().isEmpty())
					document.setId(Integer.parseInt(ftRequest.getId()));
				document.setDocVersion(document.getDocVersion() + 1);
			}else {
				document.setDocVersion(1);
			}
			
			savedDocument = documentsRepository.save(document);
			if(savedDocument != null) {
				if(!ftRequest.getFiles().isEmpty()) {
					for (Files file : ftRequest.getFiles()) {
						if((file.getFileOperationType() != null && !file.getFileOperationType().isEmpty()) && file.getFileOperationType().equalsIgnoreCase("Save")) {
							com.filetiger.model.Files storeFile = new com.filetiger.model.Files();
							storeFile.setName(file.getFileName() + "." + file.getFileFormat());
							storeFile.setEnding(file.getFileFormat());
							byte[] fileData = file.getFileData().getBytes();
							storeFile.setFile(fileData);
							storeFile.setSize(file.getFileData().length());
							storeFile.setDocumentId(savedDocument.getId());
							
							com.filetiger.model.Files savedFile = filesRepository.save(storeFile);
							if(savedFile != null) {
								String categoryIds[] = file.getCategoryIds().split(",");
								DocToFile docToFile = new DocToFile();
								docToFile.setDocToFileIdentity(new DocToFileIdentity());
								docToFile.getDocToFileIdentity().setDocumentId(savedDocument.getId());
								docToFile.getDocToFileIdentity().setDocVersion(savedDocument.getDocVersion());
								docToFile.setTabId(1);
								docToFile.setFileId(savedFile.getId());
								
								docToFileRepository.save(docToFile);
								
								for(int i=0; i<categoryIds.length; i++) {
									DocToCategory docToCategory = new DocToCategory();
									docToCategory.setDocToCategoryIdentity(new DocToCategoryIdentity());
									docToCategory.getDocToCategoryIdentity().setDocumentId(savedDocument.getId());
									docToCategory.getDocToCategoryIdentity().setDocVersion(savedDocument.getDocVersion());
									docToCategory.getDocToCategoryIdentity().setCategoryId(Integer.parseInt(categoryIds[i]));
									
									docToCategoryRepository.save(docToCategory);
								}
							}
						}else if(ftRequest.getOperationType().equalsIgnoreCase("Delete")) {
							if(file.getFileId() != null && !file.getFileId().isEmpty()) {
								filesRepository.delete(Integer.parseInt(file.getFileId()));
								logger.info("Successfully Deleted File");
							}else {
								logger.debug("Failed to delete File");
							}
						}
					}
				}
			}
		}
		return savedDocument;
	}
	
	private Categories settingCategory(FTRequest ftRequest, Categories category) {
		Categories savedCategory = null;
		if(ftRequest.getFolderName() != null && !ftRequest.getFolderName().isEmpty())
			category.setName(ftRequest.getFolderName());
		if(ftRequest.getParentFolder() != null && !ftRequest.getParentFolder().isEmpty())
			category.setParent(Integer.parseInt(ftRequest.getParentFolder()));
		if(ftRequest.getFolderPosition() != null && !ftRequest.getFolderPosition().isEmpty())
			category.setPosition(Integer.parseInt(ftRequest.getFolderPosition()));
		if(ftRequest.getOpenFolderFlag() != null && !ftRequest.getOpenFolderFlag().isEmpty())
			category.setOpen(Integer.parseInt(ftRequest.getOpenFolderFlag()));
		if(ftRequest.getActiveFolderFlag() != null && !ftRequest.getActiveFolderFlag().isEmpty())
			category.setActive(Integer.parseInt(ftRequest.getActiveFolderFlag()));
		if((ftRequest.getOperationType() != null && !ftRequest.getOperationType().isEmpty()) && ftRequest.getOperationType().equalsIgnoreCase("Update")) {
			if(ftRequest.getId() != null && !ftRequest.getId().isEmpty())
				category.setId(Integer.parseInt(ftRequest.getId()));
		}
		savedCategory = categoriesRepository.save(category);
		
		return savedCategory;
	}
	
	private FillingCabinet settingCabinet(FTRequest ftRequest, FillingCabinet fillingCabinet) {
		if(ftRequest.getCabinetName() != null && !ftRequest.getCabinetName().isEmpty())
			fillingCabinet.setName(ftRequest.getCabinetName());
		if(ftRequest.getCabinetLocation() != null && !ftRequest.getCabinetLocation().isEmpty())
			fillingCabinet.setPlace(ftRequest.getCabinetLocation());
		if((ftRequest.getOperationType() != null && !ftRequest.getOperationType().isEmpty()) && ftRequest.getOperationType().equalsIgnoreCase("Update")) {
			if(ftRequest.getId() != null && !ftRequest.getId().isEmpty())
				fillingCabinet.setId(Integer.parseInt(ftRequest.getId()));
		}
		
		FillingCabinet savedCabinet = fillingCabinetRepository.save(fillingCabinet);
		
		return savedCabinet;
	}
	
	private Group settingGroup(FTRequest ftRequest, Group group) {
		if(ftRequest.getGroupRequest().getGroupName() != null && !ftRequest.getGroupRequest().getGroupName().isEmpty())
			group.setName(ftRequest.getGroupRequest().getGroupName());
		if(ftRequest.getGroupRequest().getAddCategory() != null && !ftRequest.getGroupRequest().getAddCategory().isEmpty())
			group.setAddCategory(Short.parseShort(ftRequest.getGroupRequest().getAddCategory()));
		if(ftRequest.getGroupRequest().getEditCategory() != null && !ftRequest.getGroupRequest().getEditCategory().isEmpty())
			group.setEditCategory(Short.parseShort(ftRequest.getGroupRequest().getEditCategory()));
		if(ftRequest.getGroupRequest().getDelCategory() != null && !ftRequest.getGroupRequest().getDelCategory().isEmpty())
			group.setDelCategory(Short.parseShort(ftRequest.getGroupRequest().getDelCategory()));
		if(ftRequest.getGroupRequest().getAddDocumentArt() != null && !ftRequest.getGroupRequest().getAddDocumentArt().isEmpty())
			group.setAddDocumentArt(Short.parseShort(ftRequest.getGroupRequest().getAddDocumentArt()));
		if(ftRequest.getGroupRequest().getEditDocumentArt() != null && !ftRequest.getGroupRequest().getEditDocumentArt().isEmpty())
			group.setEditDocumentArt(Short.parseShort(ftRequest.getGroupRequest().getEditDocumentArt()));
		if(ftRequest.getGroupRequest().getDelDocumentArt() != null && !ftRequest.getGroupRequest().getDelDocumentArt().isEmpty())
			group.setDelDocumentArt(Short.parseShort(ftRequest.getGroupRequest().getDelDocumentArt()));
		if(ftRequest.getGroupRequest().getAddFolder() != null && !ftRequest.getGroupRequest().getAddFolder().isEmpty())
			group.setAddFolder(Short.parseShort(ftRequest.getGroupRequest().getAddFolder()));
		if(ftRequest.getGroupRequest().getEditFolder() != null && !ftRequest.getGroupRequest().getEditFolder().isEmpty())
			group.setEditFolder(Short.parseShort(ftRequest.getGroupRequest().getEditFolder()));
		if(ftRequest.getGroupRequest().getDelFolder() != null && !ftRequest.getGroupRequest().getDelFolder().isEmpty())
			group.setDelFolder(Short.parseShort(ftRequest.getGroupRequest().getDelFolder()));
		if(ftRequest.getGroupRequest().getAddDocument() != null && !ftRequest.getGroupRequest().getAddDocument().isEmpty())
			group.setAddDocument(Short.parseShort(ftRequest.getGroupRequest().getAddDocument()));
		if(ftRequest.getGroupRequest().getEditDocument() != null && !ftRequest.getGroupRequest().getEditDocument().isEmpty())
			group.setEditDocument(Short.parseShort(ftRequest.getGroupRequest().getEditDocument()));
		if(ftRequest.getGroupRequest().getDelDocument() != null && !ftRequest.getGroupRequest().getDelDocument().isEmpty())
			group.setDelDocument(Short.parseShort(ftRequest.getGroupRequest().getDelDocument()));
		if(ftRequest.getGroupRequest().getEditSettings() != null && !ftRequest.getGroupRequest().getEditSettings().isEmpty())
			group.setEditSettings(Short.parseShort(ftRequest.getGroupRequest().getEditSettings()));
		if(ftRequest.getGroupRequest().getEditUser() != null && !ftRequest.getGroupRequest().getEditUser().isEmpty())
			group.setEditUser(Short.parseShort(ftRequest.getGroupRequest().getEditUser()));
		if((ftRequest.getOperationType() != null && !ftRequest.getOperationType().isEmpty()) && ftRequest.getOperationType().equalsIgnoreCase("Update")) {
			if(ftRequest.getId() != null && !ftRequest.getId().isEmpty())
				group.setId(Integer.parseInt(ftRequest.getId()));
		}
		
		Group saveGroup = groupRepository.save(group);
		
		return saveGroup;
	}
	
	private User settingUser(FTRequest ftRequest, User user) {
		if(ftRequest.getEmail() != null && !ftRequest.getEmail().isEmpty())
			user.setUsername(ftRequest.getEmail());
		if(ftRequest.getPassword() != null && !ftRequest.getPassword().isEmpty())
			user.setPassword(bcryptEncoder.encode(ftRequest.getPassword()));
		if(ftRequest.getFirstname() != null && !ftRequest.getFirstname().isEmpty())
			user.setFirstname(ftRequest.getFirstname());
		if(ftRequest.getLastname() != null && !ftRequest.getLastname().isEmpty())
			user.setLastname(ftRequest.getLastname());
		if((ftRequest.getOperationType() != null && !ftRequest.getOperationType().isEmpty()) && ftRequest.getOperationType().equalsIgnoreCase("Update")) {
			if(ftRequest.getGroup() != null && !ftRequest.getGroup().isEmpty())
				user.setGroup(Short.parseShort(ftRequest.getGroup()));
			if(ftRequest.getActiveStatus() != null && !ftRequest.getActiveStatus().isEmpty())
			user.setActive(Short.parseShort(ftRequest.getActiveStatus()));
		}else {
			user.setGroup(Short.parseShort("1"));
			user.setActive(Short.parseShort("1"));
		}
		
		User savedUser = userRepository.save(user);
		return savedUser;
	}
	

	private FTFolder settingFolder(FTRequest ftRequest, FTFolder ftFolder) {
		if(ftRequest.getFolderRequest().getFolderName() != null && !ftRequest.getFolderRequest().getFolderName().isEmpty())
			ftFolder.setFolderName(ftRequest.getFolderRequest().getFolderName());
		if(ftRequest.getFolderRequest().getFileCabinetId() != null)
			ftFolder.setFileCabinetId(Integer.parseInt(ftRequest.getFolderRequest().getFileCabinetId()));
		ftFolder.setCompleted(Short.parseShort("0"));
		
		if((ftRequest.getOperationType() != null && !ftRequest.getOperationType().isEmpty()) && ftRequest.getOperationType().equalsIgnoreCase("Update")) {
			if(ftRequest.getId() != null && !ftRequest.getId().isEmpty())
				ftFolder.setId(Integer.parseInt(ftRequest.getId()));
		}
		
		FTFolder savedFolder = ftFolderRepository.save(ftFolder);
		
		return savedFolder;
	}
}

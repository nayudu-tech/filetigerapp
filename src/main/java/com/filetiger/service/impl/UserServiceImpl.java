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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.filetiger.dto.FTRequest;
import com.filetiger.dto.FTRequest.Files;
import com.filetiger.dto.FTResponse;
import com.filetiger.jwt.config.JwtTokenUtil;
import com.filetiger.model.Categories;
import com.filetiger.model.DocToCategory;
import com.filetiger.model.DocToCategoryIdentity;
import com.filetiger.model.DocToFile;
import com.filetiger.model.DocToFileIdentity;
import com.filetiger.model.DocumentTypes;
import com.filetiger.model.Documents;
import com.filetiger.model.FillingCabinet;
import com.filetiger.model.User;
import com.filetiger.repository.CategoriesRepository;
import com.filetiger.repository.DocToCategoryRepository;
import com.filetiger.repository.DocToFileRepository;
import com.filetiger.repository.DocumentTypesRepository;
import com.filetiger.repository.DocumentsRepository;
import com.filetiger.repository.FilesRepository;
import com.filetiger.repository.FillingCabinetRepository;
import com.filetiger.repository.UserRepository;
import com.filetiger.service.UserService;
import com.filetiger.util.Utility;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DocumentsRepository documentsRepository;
	@Autowired
	private FilesRepository filesRepository;
	@Autowired
	private CategoriesRepository categoriesRepository;
	@Autowired
	private DocumentTypesRepository documentTypesRepository;
	@Autowired
	private DocToCategoryRepository docToCategoryRepository;
	@Autowired
	private FillingCabinetRepository fillingCabinetRepository;
	@Autowired
	private DocToFileRepository docToFileRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public FTResponse registerUserImpl(FTRequest ftRequest) {
		logger.info("method registerUserImpl starts");
		Short group = 1;
		Short activeStatus = 1;
		FTResponse ftResponse = new FTResponse();
		
		try {
			if(ftRequest.getEmail() != null && !ftRequest.getEmail().isEmpty()) {
				if(userRepository.findByUsername(ftRequest.getEmail()) == null) {
					User newUser = new User();
					newUser.setUsername(ftRequest.getEmail());
					if(ftRequest.getPassword() != null && !ftRequest.getPassword().isEmpty())
						newUser.setPassword(bcryptEncoder.encode(ftRequest.getPassword()));
					newUser.setFirstname(ftRequest.getFirstname());
					newUser.setLastname(ftRequest.getLastname());
					newUser.setGroup(group);
					newUser.setActive(activeStatus);
					User savedUser = userRepository.save(newUser);
					if(savedUser != null) {
						logger.info("Registration successful with email :: "+ftRequest.getEmail());
						ftResponse.setEmail(ftRequest.getEmail());
						return Utility.getInstance().successResponse(ftRequest, ftResponse, registrationSuccessMsg);
					}else {
						logger.debug("Registration failed with email :: "+ftRequest.getEmail());
						return Utility.getInstance().failureResponse(ftRequest, ftResponse, registrationFailedMsg);
					}
				}else {
					logger.debug("Already register user :: "+ftRequest.getEmail());
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, registrationFailedMsg1);
				}
			}else {
				logger.debug("Username is mandatory ");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, loginFailedMsg3);
			}
			
		}catch(Exception e) {
			logger.error("technical error message ::"+e.getMessage());
			return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, technicalErroMsg);
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public FTResponse createAuthenticationTokenImpl(FTRequest authenticationRequest) {
		logger.info("method createAuthenticationToken starts");
		FTResponse ftResponse = new FTResponse();
		try {
			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
			final UserDetails userDetails = loadUserByUsername(authenticationRequest.getEmail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			if(token != null) {
				logger.info("user successfully loggedin :: token is :: "+token);
				ftResponse.setJwtToken(token);
				ftResponse.setEmail(authenticationRequest.getEmail());
				return Utility.getInstance().successResponse(new FTRequest(), ftResponse, loginSuccessMsg);
			}else {
				logger.debug("user login failed");
				return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, loginFailedMsg);
			}
			
		}catch(Exception e) {
			logger.error("technical error message ::"+e.getMessage());
			if(e.getMessage().equalsIgnoreCase("INVALID_CREDENTIALS")) {
				logger.error("invalid credentials");
				return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, loginFailedMsg1);
			}else if(e.getMessage().equalsIgnoreCase("USER_DISABLED")){
				logger.error("user disabled");
				return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, loginFailedMsg2);
			}else {
				logger.error("other exception");
				return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, technicalErroMsg);
			}
		}
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@Override
	public FTResponse fetchDocumentTypesImpl() {
		logger.info("method fetchDocumentTypesImpl starts");
		FTResponse ftResponse = new FTResponse();
		
		try {
			
			List<DocumentTypes> documentTypesList = documentTypesRepository.findAll();
			
			if(!documentTypesList.isEmpty()) {
				logger.info("Successfully fetched document types");
				ftResponse.setDocumentTypes(documentTypesList);
				return Utility.getInstance().successResponse(new FTRequest(), ftResponse, txnSuccessMsg);
			}else {
				logger.debug("Failed to fetch document types");
				return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, documentTypeFailedMsg);
			}
			
			
		}catch(Exception e) {
			logger.error("technical error message ::"+e.getMessage());
			return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, technicalErroMsg);
		}
	}
	
	@Override
	public FTResponse changePasswordImpl(FTRequest ftRequest) {
		logger.info("method changePasswordImpl starts"); 
		FTResponse ftResponse = new FTResponse();
		
		try {
		
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User userExist = userRepository.findUser(ftRequest.getEmail(), user.getPassword());
			
			if(userExist != null) {
				userExist.setPassword(bcryptEncoder.encode(ftRequest.getNewPassword()));
				User updatedUser = userRepository.save(userExist);
				if(updatedUser != null) {
					logger.info("Password changed successfully :: "+ftRequest.getEmail());
					return Utility.getInstance().successResponse(ftRequest, ftResponse, registrationFailedMsg3);
				}else {
					logger.debug("Password changed failed :: "+ftRequest.getEmail());
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, registrationFailedMsg4);
				}
			}else {
				logger.debug("Not a valid request ");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, invalidRequestMsg);
			}
		}catch(Exception e) {
			logger.error("technical error message ::"+e.getMessage());
			return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, technicalErroMsg);
		}
	}
	
	@Override
	public FTResponse saveDocumentImpl(FTRequest ftRequest) {
		logger.info("method newOrEditDocumentImpl starts");
		FTResponse ftResponse = new FTResponse();
		
		try {
			if(ftRequest != null) {
				Documents document = setDocuments(ftRequest);
				if(document != null) {
					logger.info("Document saved successfully ");
					return Utility.getInstance().successResponse(ftRequest, ftResponse, documentSuccessMsg);
				}else {
					logger.debug("Faild to store document, please try again ");
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, documentFailedMsg);
				}
			}else {
				logger.debug("Not a valid request, please try again ");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, invalidRequestMsg);
			}
		}catch(Exception e) {
			logger.error("technical error message ::"+e.getMessage());
			return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, technicalErroMsg + e.getCause() + e.getStackTrace());
		}
	}
	
	@Override
	public FTResponse saveCategoryImpl(FTRequest ftRequest) {
		logger.info("method saveCategoryImpl starts");
		FTResponse ftResponse = new FTResponse();
		
		try {
			
			if(ftRequest != null) {
				Categories savedCategory = settingCategory(ftRequest);
				if(savedCategory != null) {
					logger.info("Category saved successfully ");
					return Utility.getInstance().successResponse(ftRequest, ftResponse, categorySuccessMsg);
				}else {
					logger.debug("Failed to store category, please try again ");
					return Utility.getInstance().failureResponse(ftRequest, ftResponse, categoryFailedMsg);
				}
			}else {
				logger.debug("Not a valid request, please try again ");
				return Utility.getInstance().failureResponse(ftRequest, ftResponse, invalidRequestMsg);
			}
			
		}catch(Exception e) {
			logger.error("technical error message ::"+e.getMessage());
			return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, technicalErroMsg);
		}
	}
	
	@Override
	public FTResponse fetchCategoriesImpl() {
		logger.info("method fetchCategoriesImpl starts");
		FTResponse ftResponse = new FTResponse();
		
		try {
			List<Categories> categoriesList = categoriesRepository.findAll();
			if(!categoriesList.isEmpty()) {
				logger.info("Successfully fetched categories");
				ftResponse.setCategories(categoriesList);
				return Utility.getInstance().successResponse(new FTRequest(), ftResponse, txnSuccessMsg);
			}else {
				logger.debug("Failed to fetch categories");
				return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, documentTypeFailedMsg);
			}
		}catch(Exception e) {
			logger.error("technical error message :: Error :: "+e.getMessage());
			return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, technicalErroMsg);
		}
	}
	
	@Override
	public FTResponse fetchCabinetsImpl() {
		logger.info("method fetchCabinetsImpl starts");
		FTResponse ftResponse = new FTResponse();
		
		try {
			List<FillingCabinet> fillingCabinetList = fillingCabinetRepository.findAll();
			if(!fillingCabinetList.isEmpty()) {
				logger.info("Successfully fetched cabinets");
				ftResponse.setFillingCabinets(fillingCabinetList);
				return Utility.getInstance().successResponse(new FTRequest(), ftResponse, txnSuccessMsg);
			}else {
				logger.debug("Failed to fetch cabinets");
				return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, documentTypeFailedMsg);
			}
		}catch(Exception e) {
			logger.error("technical error message :: Error :: "+e.getMessage());
			return Utility.getInstance().failureResponse(new FTRequest(), ftResponse, technicalErroMsg);
		}
	}
	
	private Documents setDocuments(FTRequest ftRequest) throws ParseException, SerialException, SQLException {
		Documents savedDocument = null;
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yyyy");
		User user = userRepository.findByUsername(ftRequest.getEmail());
		if(user != null) {
			
			Documents document = new Documents();
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
			document.setDocVersion(1);
			
			savedDocument = documentsRepository.save(document);
			if(savedDocument != null) {
				if(!ftRequest.getFiles().isEmpty()) {
					for (Files file : ftRequest.getFiles()) {
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
					}
				}
			}
		}
		return savedDocument;
	}
	
	private Categories settingCategory(FTRequest ftRequest) {
		Categories savedCategory = null;
		Categories category = new Categories();
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
		
		savedCategory = categoriesRepository.save(category);
		
		return savedCategory;
	}
	
	@Value("${technical.error.msg}")
	private String technicalErroMsg;
	
	@Value("${registration.success.msg}")
	private String registrationSuccessMsg;
	
	@Value("${registration.failed.msg}")
	private String registrationFailedMsg;
	    
	@Value("${login.success.msg}")
	private String loginSuccessMsg;
	
	@Value("${login.failed.msg}")
	private String loginFailedMsg;
	
	@Value("${transaction.success.msg}")
	private String txnSuccessMsg;
	
	@Value("${document.type.failed.msg}")
	private String documentTypeFailedMsg;
	
	@Value("${login.failed.msg1}")
	private String loginFailedMsg1;
	
	@Value("${login.failed.msg2}")
	private String loginFailedMsg2;
	
	@Value("${login.failed.msg3}")
	private String loginFailedMsg3;
	
	@Value("${registration.failed.msg1}")
	private String registrationFailedMsg1;
	
	@Value("${registration.failed.msg2}")
	private String registrationFailedMsg2;
	
	@Value("${registration.failed.msg3}")
	private String registrationFailedMsg3;
	
	@Value("${registration.failed.msg4}")
	private String registrationFailedMsg4;
	
	@Value("${document.success.msg}")
	private String documentSuccessMsg;
	
	@Value("${document.failed.msg}")
	private String documentFailedMsg;

	@Value("${invalid.reques.msg}")
	private String invalidRequestMsg;
	
	@Value("${category.success.msg}")
	private String categorySuccessMsg;
	
	@Value("${category.failed.msg}")
	private String categoryFailedMsg;
}

package com.filetiger.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.filetiger.dto.FTRequest;
import com.filetiger.dto.FTResponse;
import com.filetiger.service.FTCRUDOperationsService;
import com.filetiger.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileTigerAPIs {

	@Autowired
	private UserService userService;
	@Autowired
	private FTCRUDOperationsService crudOperationsService;
	
	//register file tiger user
	@PostMapping(value = "/register")
	public FTResponse registerUser(@Valid @RequestBody FTRequest ftRequest) {
		return userService.registerUserImpl(ftRequest);
	}
	//authenticate user using email and password, returns jwt token
	@PostMapping(value = "/authenticate")
	public FTResponse createAuthenticationToken(@Valid @RequestBody FTRequest authenticationRequest) {
		return userService.createAuthenticationTokenImpl(authenticationRequest);
	}
	//fetch document types
	@GetMapping(value = "/fetchDocumentTypes")
	public FTResponse fetchDocumentTypes() {
		return userService.fetchDocumentTypesImpl();
	}
	//password change
	@PostMapping(value = "/changePassword")
	public FTResponse changePassword(@Valid @RequestBody FTRequest ftRequest) {
		return userService.changePasswordImpl(ftRequest);
	}
	//Save document
	@PostMapping(value = "/saveDocument")
	public FTResponse saveDocument(@Valid @RequestBody FTRequest ftRequest) {
		return userService.saveDocumentImpl(ftRequest);
	}
	//Save category
	@PostMapping(value = "/saveCategory")
	public FTResponse saveCategory(@Valid @RequestBody FTRequest ftRequest) {
		return userService.saveCategoryImpl(ftRequest);
	}
	//Fetch all categories(Folder hierarchy)
	@GetMapping(value = "/fetchCategories")
	public FTResponse fetchCategories() {
		return userService.fetchCategoriesImpl();
	}
	//Fetch all categories(Folder hierarchy)
	@GetMapping(value = "/fetchCabinets")
	public FTResponse fetchCabinets() {
		return userService.fetchCabinetsImpl();
	}
	//All CRUD operations
	@PostMapping(value = "/crudOperations")
	public FTResponse crudOperations(@Valid @RequestBody FTRequest ftRequest) {
		return crudOperationsService.crudOperationsImpl(ftRequest);
	}
}

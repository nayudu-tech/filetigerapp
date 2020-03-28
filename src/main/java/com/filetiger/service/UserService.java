package com.filetiger.service;

import com.filetiger.dto.FTRequest;
import com.filetiger.dto.FTResponse;

public interface UserService {

	FTResponse registerUserImpl(FTRequest ftRequest);
	FTResponse createAuthenticationTokenImpl(FTRequest authenticationRequest);
	FTResponse fetchDocumentTypesImpl();
	FTResponse changePasswordImpl(FTRequest ftRequest);
	FTResponse saveDocumentImpl(FTRequest ftRequest);
	FTResponse saveCategoryImpl(FTRequest ftRequest);
	FTResponse fetchCategoriesImpl();
	FTResponse fetchCabinetsImpl();
}

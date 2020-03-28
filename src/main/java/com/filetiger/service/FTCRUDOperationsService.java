package com.filetiger.service;

import com.filetiger.dto.FTRequest;
import com.filetiger.dto.FTResponse;

public interface FTCRUDOperationsService {

	FTResponse crudOperationsImpl(FTRequest ftRequest);
}

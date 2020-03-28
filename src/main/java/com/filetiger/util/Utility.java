package com.filetiger.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.filetiger.dto.FTRequest;
import com.filetiger.dto.FTResponse;

public class Utility {
	
	private static final Utility utility = new Utility();
	private Utility() {}
	public static Utility getInstance() {
		return utility;
	}

	public FTResponse successResponse(FTRequest request, FTResponse ftResponse, String message) {
		ftResponse.setStatus("success");
		ftResponse.setStatuscode(200);
		ftResponse.setMessage(message);
		
		return ftResponse;
	}
	
	public FTResponse failureResponse(FTRequest request, FTResponse ftResponse, String message) {
		ftResponse.setStatus("failure");
		ftResponse.setStatuscode(300);
		ftResponse.setMessage(message);
		
		return ftResponse;
	}
	
	public String readProperty(String key) {
		String value = "";
		
		try (InputStream input = Utility.class.getClassLoader().getResourceAsStream("appmessages.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value
            value = prop.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return value;
	}
}

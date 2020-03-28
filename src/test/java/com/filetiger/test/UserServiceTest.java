package com.filetiger.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.filetiger.dto.FTRequest;
import com.filetiger.dto.FTResponse;
import com.filetiger.model.User;
import com.filetiger.repository.UserRepository;
import com.filetiger.service.impl.UserServiceImpl;
import com.filetiger.util.Utility;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class UserServiceTest 
    extends TestCase
{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	User newUser = new User();
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Value("${registration.success.msg}")
	private String registrationSuccessMsg;
	FTResponse ftResponse = new FTResponse();
	
	Short group = 1;
	Short activeStatus = 1;
	 
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */        
    public FTResponse registerUserImpl( FTRequest ftRequest )
    {
       // super( testName );                                     
    	if(userRepository.findByUsername(ftRequest.getEmail()) == null) {
                                                                                     
        newUser.setUsername(ftRequest.getEmail());
		newUser.setPassword(bcryptEncoder.encode(ftRequest.getPassword()));
		newUser.setFirstname(ftRequest.getFirstname());
		newUser.setLastname(ftRequest.getLastname());
		newUser.setGroup(group);
		newUser.setActive(activeStatus);
		User savedUser = userRepository.save(newUser);
		if(savedUser != null) {
			logger.info("Registration successful with email :: "+ftRequest.getEmail());
			return Utility.getInstance().successResponse(ftRequest, ftResponse, registrationSuccessMsg    );
		}
    	}return null;
    }    

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() 
    {
        return new TestSuite( UserServiceTest.class);
    }

    /**                                    
     * Rigourous Test :-)
     */
	
	  public void testApp() { assertTrue( true ); }
	 
}

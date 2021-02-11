package com.example.contact.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.contact.bean.ContactBean;
import com.example.contact.exception.InvalidInputException;
import com.example.contact.service.ContactService;
import com.example.contact.validator.EmailValidation;

@RestController
public class ContactController {

	@Autowired
	ContactService contactService;
	
	private static final Logger logger = LoggerFactory.getLogger(ContactController.class); 
	
	@GetMapping(value="/v1/contacts", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getContactList(){
		logger.info("Inside getContactList start");
		List<ContactBean> contactList = contactService.getContactList();
		logger.info("Inside getContactList end");
		return new ResponseEntity(contactList, HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/createcontact", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactBean> addContactDetails (@RequestBody ContactBean contactBean)
	{
		logger.info("Inside addContactDetails start");
		ResponseEntity<ContactBean> response = null;
		ContactBean contactDetails = null;
		try {
			if(contactBean == null) {
				throw new InvalidInputException("Contact Details not Found");
			}
			if(null != contactBean.getEmail()) {
				boolean validateEmail = EmailValidation.isValidEmail(contactBean.getEmail());
				if(!validateEmail) {
					throw new InvalidInputException("Email is invalid");
				}
			}
			contactDetails = contactService.createContact(contactBean);
			response = new ResponseEntity<ContactBean>(contactDetails, HttpStatus.OK);
			logger.info("Inside addContactDetails end");
		}
		catch(InvalidInputException e) {
			logger.error("Invalid input:", e.getMessage());
			response = new ResponseEntity<ContactBean>(contactDetails, HttpStatus.BAD_REQUEST);
		}
	    return response;
	}
	
	@PutMapping(value="/v1/updatecontact/{contactId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactBean> updateContactDetails(@PathVariable("contactId") String contactId, @RequestBody ContactBean contactBean) {
		logger.info("Inside updateContactDetails start");
		ContactBean contactDetails = contactService.updateContactDetails(contactId, contactBean);
		logger.info("Inside updateContactDetails end");
		return new ResponseEntity<ContactBean>(contactDetails, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/v1/deletecontact/{contactId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteContactDetails(@PathVariable("contactId") String contactId){
		logger.info("Inside deleteContactDetails start");
		Integer Id = contactService.deleteContactDetails(contactId);
		logger.info("Inside deleteContactDetails end");
		return new ResponseEntity<Integer>(Id, HttpStatus.OK);
	}

}

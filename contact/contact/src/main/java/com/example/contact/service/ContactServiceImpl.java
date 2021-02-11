package com.example.contact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contact.Repository.ContactRepository;
import com.example.contact.bean.ContactBean;
import com.example.contact.controller.ContactController;
import com.example.contact.exception.ContactNotFoundException;
import com.example.contact.exception.InvalidInputException;
import com.example.contact.model.Contact;
import com.example.contact.validator.EmailValidation;
import com.example.contact.validator.MobileValidation;

@Service
public class ContactServiceImpl implements ContactService{
	
	private static final String ACTIVE = "Active";
	private static final String INACTIVE = "Inactive";

	@Autowired
	ContactRepository contactRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

	@Override
	public List<ContactBean> getContactList() {
		logger.info("Inside ContactService.getContactList");
		List<ContactBean> contactBeanList = null;
		List<Contact> contactList = contactRepository.findActiveContacts();
		if(!contactList.isEmpty()) {
			contactBeanList = new ArrayList<>();
			for (Contact contact : contactList) {
				ContactBean contactBean = new ContactBean();
				contactBean.setFirstName(contact.getFirstName());
				contactBean.setLastName(contact.getLastName());
				contactBean.setEmail(null != contact.getEmail() ? contact.getEmail() : null);
				contactBean.setMobileNo(contact.getMobileNo());
				contactBean.setStatus(contact.getStatus());
				contactBeanList.add(contactBean);
			}
		}
		else {
			logger.error("Contact Details not found");
			throw new ContactNotFoundException("Contact Details not found");
		}
		return contactBeanList;
	}

	@Override
	public ContactBean createContact(ContactBean contactBean) {
		logger.info("Inside ContactService.createContact");
		Contact contact = new Contact();
		contact.setFirstName(contactBean.getFirstName());
		contact.setLastName(contactBean.getLastName());
		if(null != contactBean.getEmail()) {
			boolean validateEmail = EmailValidation.isValidEmail(contactBean.getEmail());
			if(!validateEmail) {
				throw new InvalidInputException("Email is invalid");
			}
			contact.setEmail(contactBean.getEmail());
		}
		boolean validateMobileNo = MobileValidation.isValidMobileNo(contactBean.getMobileNo());
		if(!validateMobileNo) {
			throw new InvalidInputException("MobileNo is invalid");
		}
		contact.setMobileNo(contactBean.getMobileNo());
		contact.setStatus(ACTIVE);
		contactRepository.save(contact);
		logger.info("Contact created successfully");
		return contactBean;
	}

	@Override
	public ContactBean updateContactDetails(String contactId, ContactBean contactBean) {
		logger.info("Inside ContactService.updateContactDetails");
		Optional<Contact> contact = contactRepository.findByIdAndActive(Integer.parseInt(contactId), ACTIVE);
		if(contact.isPresent()) {
			Contact contact2 = contact.get();
			contact2.setFirstName(contactBean.getFirstName());
			contact2.setLastName(contactBean.getLastName());
			contact2.setEmail(null != contactBean.getEmail() ? contactBean.getEmail() : null);
			contact2.setMobileNo(contactBean.getMobileNo());
			contact2.setStatus(ACTIVE);
			contactRepository.save(contact2);
		}
		else {
			logger.error("Contact Details not found for contactId" + contactId);
			throw new ContactNotFoundException("ContactDetails not found");
		}
		return contactBean;
	}

	@Override
	public Integer deleteContactDetails(String contactId) {
		logger.info("Inside ContactService.deleteContactDetails");
		Optional<Contact> contact = contactRepository.findByIdAndActive(Integer.parseInt(contactId), ACTIVE);
		if(contact.isPresent()) {
			Contact contact2 = contact.get();
			contact2.setStatus(INACTIVE);
			contactRepository.save(contact2);
			return contact2.getContactKey();
		}
		else {
			logger.error("Contact Details not found for contactId" + contactId);
			throw new ContactNotFoundException("ContactDetails not found");
		}
	}


}

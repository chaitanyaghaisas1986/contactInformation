package com.example.contact.service;

import java.util.List;

import com.example.contact.bean.ContactBean;

public interface ContactService {

	List<ContactBean> getContactList();

	ContactBean createContact(ContactBean contactBean);

	ContactBean updateContactDetails(String contactId, ContactBean contactBean);

	Integer deleteContactDetails(String contactId);

}

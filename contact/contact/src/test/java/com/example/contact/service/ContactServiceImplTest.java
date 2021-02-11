package com.example.contact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.contact.Repository.ContactRepository;
import com.example.contact.bean.ContactBean;
import com.example.contact.exception.ContactNotFoundException;
import com.example.contact.model.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ContactServiceImplTest {

	@Mock
	ContactRepository contactRepository;
	
	@InjectMocks
	ContactServiceImpl contactServiceImpl;
	
	@Before
	public void setUp() {
		contactRepository = Mockito.mock(ContactRepository.class);
		contactServiceImpl = Mockito.mock(ContactServiceImpl.class);
	}
	
	@Test
	public void getContactListTest() {
		List<Contact> contactList = new ArrayList<Contact>();
		Contact contact = createContact();
		contactList.add(contact);
		Mockito.when(contactRepository.findActiveContacts()).thenReturn(contactList);
		contactServiceImpl.getContactList();
	}
	
	@Test(expected = ContactNotFoundException.class)
	public void getContactListTestException() {
		List<Contact> contactList = new ArrayList<Contact>();
		Mockito.when(contactRepository.findActiveContacts()).thenReturn(contactList);
		contactServiceImpl.getContactList();
	}
	
	@Test
	public void updateContactDetailsTest() {
		String contactId = "2";
		ContactBean contactBean = createContactBean();
		Contact contact = createContact();
		Optional<Contact> contact2 = Optional.of(contact);
		Mockito.when(contactRepository.findByIdAndActive(Integer.parseInt(contactId), "Active")).thenReturn(contact2);
		contactServiceImpl.updateContactDetails(contactId, contactBean);
	}
	
	@Test(expected = ContactNotFoundException.class)
	public void updateContactDetailsTestException() {
		String contactId = "2";
		ContactBean contactBean = createContactBean();
		Optional<Contact> contact2 = Optional.empty();
		Mockito.when(contactRepository.findByIdAndActive(Integer.parseInt(contactId), "Active")).thenReturn(contact2);
		contactServiceImpl.updateContactDetails(contactId, contactBean);
	}
	
	
	@Test
	public void deleteContactDetailsTest() {
		String contactId = "2";
		Contact contact = createContact();
		Optional<Contact> contact2 = Optional.of(contact);
		Mockito.when(contactRepository.findByIdAndActive(Integer.parseInt(contactId), "Active")).thenReturn(contact2);
		contactServiceImpl.deleteContactDetails(contactId);
	}
	
	@Test(expected = ContactNotFoundException.class)
	public void deleteContactDetailsTestException() {
		String contactId = "2";
		Optional<Contact> contact2 = Optional.empty();
		Mockito.when(contactRepository.findByIdAndActive(Integer.parseInt(contactId), "Active")).thenReturn(contact2);
		contactServiceImpl.deleteContactDetails(contactId);
	}
	
	@Test
	public void createContactTest() {
		ContactBean contactBean = createContactBean();
		contactServiceImpl.createContact(contactBean);
	}

	private ContactBean createContactBean() {
		ContactBean contactBean = new ContactBean();
		contactBean.setFirstName("Test");
		contactBean.setLastName("Test");
		contactBean.setEmail("abc@gmail.com");
		contactBean.setMobileNo("9900000001");
		contactBean.setStatus("Active");
		return contactBean;
	}

	private Contact createContact() {
		Contact contact = new Contact();
		contact.setContactKey(1);
		contact.setFirstName("Test");
		contact.setLastName("Test");
		contact.setEmail("abc@gmail.com");
		contact.setMobileNo("9900000001");
		contact.setStatus("Active");
		return contact;
	}
}

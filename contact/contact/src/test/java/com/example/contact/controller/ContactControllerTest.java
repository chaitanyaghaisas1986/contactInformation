package com.example.contact.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.contact.bean.ContactBean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ContactControllerTest {

	@InjectMocks
	private ContactController contactController;
	
	@Before
	public void setUp() {
		contactController = Mockito.mock(ContactController.class);
	}

	@Test
	public void getContactListTest() throws Exception {
		List<ContactBean> contactBeanList = new ArrayList<ContactBean>();
		ContactBean contactBean = createContactBean();
		contactBeanList.add(contactBean);
		Mockito.when(contactController.getContactList()).thenReturn(new ResponseEntity(contactBeanList,HttpStatus.OK));
	}
	
	@Test
	public void addContactDetailsTest() {
		ContactBean contactBean = createContactBean();
		Mockito.when(contactController.addContactDetails(contactBean)).thenReturn(new ResponseEntity<ContactBean>(contactBean, HttpStatus.OK));
	}
	
	@Test
	public void updateContactDetailsTest() {
		String contactId = "2";
		ContactBean contactBean = createContactBean();
		Mockito.when(contactController.updateContactDetails(contactId, contactBean)).thenReturn(new ResponseEntity<ContactBean>(contactBean, HttpStatus.OK));
	}
	
	@Test
	public void deleteContactDetailsTest() {
		String contactId = "2";
		Mockito.when(contactController.deleteContactDetails(contactId)).thenReturn(new ResponseEntity<Integer>(Integer.parseInt(contactId), HttpStatus.OK));
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
}

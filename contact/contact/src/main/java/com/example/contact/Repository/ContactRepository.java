package com.example.contact.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.contact.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{

	@Query("from Contact contact where contact.status='Active'")
	List<Contact> findActiveContacts();

	@Query("from Contact contact where contact.contactKey=:contactId and contact.status=:active")
	Optional<Contact> findByIdAndActive(Integer contactId, String active);

}

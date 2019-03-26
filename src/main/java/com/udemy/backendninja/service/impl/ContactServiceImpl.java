package com.udemy.backendninja.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.converter.ContactConverter;
import com.udemy.backendninja.entity.Contact;
import com.udemy.backendninja.model.ContactModel;
import com.udemy.backendninja.repository.ContactRepository;
import com.udemy.backendninja.service.ContactService;

@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService{

	@Autowired
	@Qualifier("contactRepository")
	private ContactRepository contactRepository;
	
	@Autowired
	@Qualifier("contactConverter")
	private ContactConverter contactConverter;
	
	@Override
	public ContactModel addContact(ContactModel contactModel) {
		Contact contact = contactRepository.save(contactConverter.modelToEntity(contactModel));
		return contactConverter.entityToModel(contact);
	}

	@Override
	public List<ContactModel> listAllContacts() {
		List<Contact> contacts = contactRepository.findAll();
		List<ContactModel> contactsModel = new ArrayList<>();
		for(Contact contact : contacts) {
			contactsModel.add(contactConverter.entityToModel(contact));
		}
		return contactsModel;
	}

	@Override
	public ContactModel findContactById(int id) {
		return contactConverter.entityToModel(contactRepository.findById(id));
	}

	@Override
	public void removeContact(int id) {
		Contact contact = contactConverter.modelToEntity(findContactById(id));
		if (contact!=null) {
			contactRepository.delete(contact);
		}
	}

}

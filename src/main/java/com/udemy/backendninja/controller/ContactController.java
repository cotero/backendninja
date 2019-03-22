package com.udemy.backendninja.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.udemy.backendninja.constants.ViewConstants;
import com.udemy.backendninja.model.ContactModel;
import com.udemy.backendninja.service.ContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {

	private static final Log LOG = LogFactory.getLog(ContactController.class);

	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactService;
	
	@GetMapping("/cancel")
	public String cancel() {
		return ViewConstants.CONTACTS_VIEW;
	}

	@GetMapping("/contactform")
	public String redirectContactForm(Model model) {
		model.addAttribute("contactModel", new ContactModel());
		return ViewConstants.CONTACT_FORM_VIEW;
	}

	@PostMapping("/addcontact")
	public ModelAndView addContact(@ModelAttribute(name = "contactModel") ContactModel contactModel,
									Model model) {
		LOG.info("METHOD addContact() -- PARAMS: " + contactModel);
		ModelAndView mav = new ModelAndView(ViewConstants.CONTACTS_VIEW);
		//mav.addObject("contactModel", contactModel);
		mav.addObject("result", 1);
		contactService.addContact(contactModel);
		
		return mav;
	}

}

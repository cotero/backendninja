package com.udemy.backendninja.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.udemy.backendninja.constants.ViewConstants;
import com.udemy.backendninja.model.UserCredential;

@Controller
public class LoginController {
	
	private static final Log LOG = LogFactory.getLog(LoginController.class);

	
	@GetMapping("/login")
	public ModelAndView showLoginForm(@RequestParam(name="error", required=false) String error,
										@RequestParam(name="logout", required=false) String logout) {
		LOG.info("METHOD: showLoginForm()");
		ModelAndView mav = new ModelAndView(ViewConstants.LOGIN_VIEW);
		mav.addObject("error", error);
		mav.addObject("logout", logout);
		mav.addObject("userCredential", new UserCredential());
		LOG.info("Returning to login view");
		return mav;
	}

	@GetMapping({"/loginsuccess", "/"})
	public String loginCheck() {
		LOG.info("METHOD: loginCheck() -- ");
		
		return "redirect:/contacts/showcontacts";
				
	}
}

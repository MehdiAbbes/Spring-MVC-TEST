package com.sfr.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sfr.demo.model.Form;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping("init.html")
	public String init(Model model){
	    model.addAttribute("ourForm", new Form());
	    return "form";
	}
	
	@RequestMapping(value = "submitForm.html", method = RequestMethod.POST)
	public String handleFormSubmit(@ModelAttribute("ourForm") Form form, BindingResult bindingResult) {
	    System.out.println(form.getAge());
	    return "redirect:www.sfr.fr";
	}
	
}

package com.sfr.demo.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sfr.demo.model.Form;
import com.sfr.demo.validator.FormValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    
    @Autowired
    private FormValidator formValidator;
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public String init(Model model) {
        model.addAttribute("ourForm", new Form());
        return "form";
    }
    
    @InitBinder
    public void initDateBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        CustomDateEditor customDateFormat;
        try {
            customDateFormat = new CustomDateEditor(dateFormat, false);
            binder.registerCustomEditor(Date.class, customDateFormat);
        } catch (IllegalArgumentException e) {
            logger.error("{}", e);
        }
        
    }
    
//    @InitBinder("form")
//    public void initFormBinder(final WebDataBinder binder) {
//        binder.setValidator(formValidator);
//    }
    
    
    
    @RequestMapping(value = "submitForm", method = RequestMethod.POST)
    public ModelAndView handleFormSubmit(@ModelAttribute("ourForm") Form form, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("redirect:http://www.sfr.fr");
        formValidator.validate(form, bindingResult);
        mav.addObject("ourForm", form);
        if(bindingResult.hasErrors()){
            mav.setViewName("form");
        }
        return mav;
    }
    
}

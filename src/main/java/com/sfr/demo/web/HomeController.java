package com.sfr.demo.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sfr.demo.excpetion.ErrorDetail;
import com.sfr.demo.excpetion.FunctionalException;
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
    public String init(final Model model) {
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
    
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<ErrorDetail> handleFunctionalException(final FunctionalException fe) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<ErrorDetail>(fe.getErrorDetail(), headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    
    @RequestMapping(value = "submitForm", method = RequestMethod.POST)
    public ModelAndView handleFormSubmit(final Model model, @ModelAttribute("ourForm") final Form form, final BindingResult bindingResult, final HttpServletRequest request) throws FunctionalException {
        ModelAndView mav = new ModelAndView("redirect:http://www.sfr.fr");
        this.formValidator.validate(form, bindingResult);
        mav.addObject("ourForm", form);
        
        //validating form
        if (bindingResult.hasErrors()) {
            mav.setViewName("form");
        }else if(Integer.parseInt(form.getAge()) >= 100){
            throw new FunctionalException(new ErrorDetail("age", "GrandPas shouldn't use internet"));
        }
        
        //if valid form
        model.addAttribute("name", form.getName());
        request.setAttribute("age", form.getAge());
        return mav;
    }
    
}

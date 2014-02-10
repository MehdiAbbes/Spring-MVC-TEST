package com.sfr.demo.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sfr.demo.model.Form;

@Component
public class FormValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return FormValidator.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object object, Errors errors) {
        Form form = (Form) object;
        if(StringUtils.isEmpty(form.getName())){
            errors.rejectValue("name", "Come on man ! you need to put some name");
        }
        if(!NumberUtils.isDigits(form.getAge())){
            errors.rejectValue("age", "WTF! Age must be a set of valid digits!");
        }
        if(form.getDate() == null){
            errors.rejectValue("date", "Yo ! the date should respect this format : dd-MM-yyyy capice");
        }
        
    }
    
}

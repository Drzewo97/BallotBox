package com.drzewo97.ballotbox.constraint.fieldmatch;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;

/**
 * Validator for fieldmatch annotation
 * @see FieldMatch
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {

            BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
            final Object firstObj = wrapper.getPropertyDescriptor(firstFieldName);
            final Object secondObj = wrapper.getPropertyDescriptor(secondFieldName);

            String one = (String)((PropertyDescriptor) firstObj).getReadMethod().invoke(value);
            String two = (String)((PropertyDescriptor) secondObj).getReadMethod().invoke(value);
            valid = one.equals(two);

            // ¯\_(ツ)_/¯ equals doesn't work
            //valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
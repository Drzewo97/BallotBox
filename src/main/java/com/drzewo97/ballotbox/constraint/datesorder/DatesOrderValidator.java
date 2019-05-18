package com.drzewo97.ballotbox.constraint.datesorder;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;

/**
 * Validator for DatesOrder annotation
 * @see DatesOrder
 */
public class DatesOrderValidator implements ConstraintValidator<DatesOrder, Object> {

    private String beforeFieldName;
    private String afterFieldName;
    private String message;

    @Override
    public void initialize(final DatesOrder constraintAnnotation) {
        beforeFieldName = constraintAnnotation.before();
        afterFieldName = constraintAnnotation.after();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = false;
        try
        {
            BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
            final Object beforeObj = wrapper.getPropertyDescriptor(beforeFieldName);
            final Object afterObj = wrapper.getPropertyDescriptor(afterFieldName);

            LocalDateTime before = (LocalDateTime)((PropertyDescriptor) beforeObj).getReadMethod().invoke(value);
            LocalDateTime after = (LocalDateTime)((PropertyDescriptor) afterObj).getReadMethod().invoke(value);

            valid = before.isBefore(after);
        }
        catch (final Exception ignore)
        {
            // ignore
            // TODO: wrong cast
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(beforeFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
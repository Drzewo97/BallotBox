package com.drzewo97.ballotbox.core.constraint.collectionsize;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.util.Collection;

/**
 * Validator for CollectionSize annotation
 * @see CollectionSize
 */
public class CollectionSizeValidator implements ConstraintValidator<CollectionSize, Object> {

    private String sizeFieldName;
    private String collectionFieldName;
    private String message;

    @Override
    public void initialize(final CollectionSize constraintAnnotation) {
        sizeFieldName = constraintAnnotation.size();
        collectionFieldName = constraintAnnotation.collection();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = false;
        try
        {
            BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
            final Object sizeObj = wrapper.getPropertyDescriptor(sizeFieldName);
            final Object collectionObj = wrapper.getPropertyDescriptor(collectionFieldName);

            Integer size = (Integer)((PropertyDescriptor) sizeObj).getReadMethod().invoke(value);
            Collection<?> collection = (Collection<?>)((PropertyDescriptor) collectionObj).getReadMethod().invoke(value);

            valid = size <= collection.size();
        }
        catch (final Exception ignore)
        {
            // ignore
            //TODO: wrong cast
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(collectionFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
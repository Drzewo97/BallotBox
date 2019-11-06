package com.drzewo97.ballotbox.core.constraint.collectionsize;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation that provides validation of size of collection - value from field size has to be <= size of the collection
 * Provide fields names as parameters
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CollectionSizeValidator.class)
public @interface CollectionSize {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     *
     * @return name of the Integer field that's value is used to compare to size of collection
     */
    String size();

    /**
     *
     * @return name of the collection field that's value is used to compare to size field
     */
    String collection();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @interface List
    {
        CollectionSize[] value();
    }
}

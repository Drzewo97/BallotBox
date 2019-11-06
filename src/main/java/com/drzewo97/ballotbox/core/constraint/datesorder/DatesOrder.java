package com.drzewo97.ballotbox.core.constraint.datesorder;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation that provides validation on two LocalDateTime fields - it's validated when before is chronologically before after
 * Provide fields names as parameters
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DatesOrderValidator.class)
public @interface DatesOrder
{
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     *
     * @return name of the field that has to be before 'after' field
     */
    String before();

    /**
     *
     * @return name of the field that has to be after 'before' field
     */
    String after();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @interface List
    {
        DatesOrder[] value();
    }
}
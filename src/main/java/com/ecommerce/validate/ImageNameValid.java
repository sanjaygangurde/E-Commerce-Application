package com.ecommerce.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    // error message
    String message() default "Invalid image name";

    // represent the group of constraint
    Class<?>[] groups() default {};

    // define the type of validation
    Class<? extends Payload>[] payload() default {};


}

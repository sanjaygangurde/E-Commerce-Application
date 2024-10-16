package com.ecommerce.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {

    private static final Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        logger.info("Validating ImageName: {}", value);

        if (value.isBlank()) {
            return false;
        } else
            return true;
    }
}

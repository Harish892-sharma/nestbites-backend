package homies.com.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator
        implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phone,
                           ConstraintValidatorContext context) {

        if (phone == null || phone.isBlank()) {
            return false;
        }

        phone = phone.trim();

        // Indian mobile number validation
        return phone.matches("^[6-9]\\d{9}$");
    }
}
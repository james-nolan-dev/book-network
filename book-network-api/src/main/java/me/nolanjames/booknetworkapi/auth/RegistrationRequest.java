package me.nolanjames.booknetworkapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import me.nolanjames.booknetworkapi.shared.ValidationMessages;

public record RegistrationRequest(
        @NotEmpty(message = ValidationMessages.FIRST_NAME_MUST_NOT_BE_BLANK)
        @NotBlank(message = ValidationMessages.FIRST_NAME_MUST_NOT_BE_BLANK)
        String firstname,
        @NotEmpty(message = ValidationMessages.LAST_NAME_MUST_NOT_BE_BLANK)
        @NotBlank(message = ValidationMessages.LAST_NAME_MUST_NOT_BE_BLANK)
        String lastname,
        @Email(message = ValidationMessages.EMAIL_FORMAT)
        @NotEmpty(message = ValidationMessages.EMAIL_MUST_NOT_BE_BLANK)
        @NotBlank(message = ValidationMessages.EMAIL_MUST_NOT_BE_BLANK)
        String email,
        @NotEmpty(message = ValidationMessages.PASSWORD_MUST_NOT_BE_BLANK)
        @NotBlank(message = ValidationMessages.PASSWORD_MUST_NOT_BE_BLANK)
        @Size(min = 8, message = ValidationMessages.PASSWORD_MIN_CHARS)
        String password
) {
}

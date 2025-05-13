package com.predators.security.model;

import lombok.Builder;

@Builder
public record SignInRequest(
//        @NotBlank(message = "Email must not be empty")
//        @Email(message = "Email is not correct")
        String email,

//        @NotBlank(message = "Password must not be empty")
//        @Size(min = 2, max = 50, message = "Length of the password should be between 2 and 50 symbols")
        String password) {
}

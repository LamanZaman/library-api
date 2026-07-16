package com.devjoint.library_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;
    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    private LocalDate registrationDate;
}

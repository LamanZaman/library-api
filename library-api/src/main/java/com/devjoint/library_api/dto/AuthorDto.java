package com.devjoint.library_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private Long id;
    @NotBlank(message = "Full name cannot be empty")
    @Size(min = 2, max = 70, message = "Full name must be between 2 and 70 characters")
    private String fullName;
    @NotBlank(message = "Nationality cannot be empty")
    private String nationality;
}

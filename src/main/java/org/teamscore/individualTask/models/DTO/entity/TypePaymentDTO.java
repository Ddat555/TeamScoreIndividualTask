package org.teamscore.individualTask.models.DTO.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypePaymentDTO {
    @NotNull
    private Long id;
    @NotBlank
    @Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
    private String name;
}

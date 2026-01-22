package org.teamscore.individualTask.models.DTO.entity.createDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDTO {
    @NotBlank
    @Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
    private String name;
    @NotBlank
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Цвет должен быть в HEX формате")
    private String color;
    @Size(max = 500, message = "Описание не должно превышать 500 символов")
    private String description;

}

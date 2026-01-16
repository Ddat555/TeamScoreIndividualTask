package org.teamscore.individualTask.models.DTO.entity.createDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDTO {
    private String name;
    private String color;
    private String description;

}

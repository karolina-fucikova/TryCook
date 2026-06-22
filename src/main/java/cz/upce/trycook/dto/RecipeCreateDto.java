package cz.upce.trycook.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import cz.upce.trycook.validation.ValidIngredients;

@Data
public class RecipeCreateDto {

    @NotBlank(message = "Název receptu nesmí být prázdný")
    private String title;

    private String description;

    @ValidIngredients
    private String ingredients;

    @Min(value = 1, message = "Doba přípravy musí být alespoň 1 minuta")
    private int timeToCookMinutes;

    @NotNull(message = "ID autora je povinné")
    private Long authorId;
}
package cz.upce.trycook.dto;

import lombok.Data;

@Data
public class RecipeResponseDto {
    private Long id;
    private String title;
    private String description;
    private String ingredients;
    private int timeToCookMinutes;
    private String authorUsername;
}
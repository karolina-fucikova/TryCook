package cz.upce.trycook.controller;

import cz.upce.trycook.dto.RecipeCreateDto;
import cz.upce.trycook.dto.RecipeResponseDto;
import cz.upce.trycook.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
@Tag(name = "Recepty", description = "API pro správu a vyhledávání kuchařských receptů")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "Vytvoření nového receptu", description = "Uloží nový recept do databáze. Vyžaduje přihlášení.")
    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(@Valid @RequestBody RecipeCreateDto dto) {
        RecipeResponseDto createdRecipe = recipeService.createRecipe(dto);
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    @Operation(summary = "Úprava receptu", description = "Aktualizuje existující recept podle zadaného ID. Vyžaduje přihlášení.")
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> updateRecipe(
            @PathVariable Long id,
            @Valid @RequestBody RecipeCreateDto dto) {

        RecipeResponseDto updatedRecipe = recipeService.updateRecipe(id, dto);
        return ResponseEntity.ok(updatedRecipe);
    }


    @Operation(summary = "Získání všech receptů", description = "Vrací stránkovaný seznam receptů s možností vyhledávání podle názvu.")
    @GetMapping
    public ResponseEntity<Page<RecipeResponseDto>> getRecipes(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<RecipeResponseDto> recipes = recipeService.searchRecipes(keyword, pageable);

        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "Smazání receptu", description = "Tento endpoint může volat pouze uživatel s rolí ADMIN.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
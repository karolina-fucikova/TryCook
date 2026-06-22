package cz.upce.trycook.service;

import cz.upce.trycook.dto.RecipeCreateDto;
import cz.upce.trycook.dto.RecipeResponseDto;
import cz.upce.trycook.entity.Recipe;
import cz.upce.trycook.entity.User;
import cz.upce.trycook.repository.RecipeRepository;
import cz.upce.trycook.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public RecipeResponseDto createRecipe(RecipeCreateDto dto) {
        log.info("Zpracovávám požadavek na vytvoření nového receptu: {}", dto.getTitle());

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> {
                    log.warn("Pokus o vytvoření receptu s neexistujícím autorem (ID: {})", dto.getAuthorId());
                    return new RuntimeException("Uživatel nebyl nalezen.");
                });

        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setIngredients(dto.getIngredients());
        recipe.setTimeToCookMinutes(dto.getTimeToCookMinutes());
        recipe.setAuthor(author);

        Recipe savedRecipe = recipeRepository.save(recipe);

        log.info("Recept '{}' byl úspěšně uložen do databáze s ID: {}", savedRecipe.getTitle(), savedRecipe.getId());

        return mapToDto(savedRecipe);
    }

    public RecipeResponseDto updateRecipe(Long id, RecipeCreateDto dto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pokus o úpravu neexistujícího receptu s ID: {}", id);
                    return new RuntimeException("Recept s ID " + id + " neexistuje.");
                });

        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setIngredients(dto.getIngredients());
        recipe.setTimeToCookMinutes(dto.getTimeToCookMinutes());

        Recipe updatedRecipe = recipeRepository.save(recipe);
        log.info("Recept s ID {} byl úspěšně upraven.", id);

        return mapToDto(updatedRecipe);
    }

    public List<RecipeResponseDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Page<RecipeResponseDto> searchRecipes(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty()) {
            return recipeRepository.findAll(pageable).map(this::mapToDto);
        }
        return recipeRepository.findByTitleContainingIgnoreCase(keyword, pageable).map(this::mapToDto);
    }

    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            log.warn("Pokus o smazání neexistujícího receptu s ID: {}", id);
            throw new RuntimeException("Recept s ID " + id + " neexistuje.");
        }
        recipeRepository.deleteById(id);
        log.info("Recept s ID {} byl úspěšně smazán.", id);
    }


    private RecipeResponseDto mapToDto(Recipe recipe) {
        RecipeResponseDto dto = new RecipeResponseDto();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setIngredients(recipe.getIngredients());
        dto.setTimeToCookMinutes(recipe.getTimeToCookMinutes());
        dto.setAuthorUsername(recipe.getAuthor().getUsername());
        return dto;
    }
}
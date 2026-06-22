package cz.upce.trycook.service;

import cz.upce.trycook.dto.RecipeCreateDto;
import cz.upce.trycook.dto.RecipeResponseDto;
import cz.upce.trycook.entity.Recipe;
import cz.upce.trycook.entity.User;
import cz.upce.trycook.repository.RecipeRepository;
import cz.upce.trycook.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void createRecipe_Success_ReturnsDto() {
        // 1. PŘÍPRAVA
        RecipeCreateDto dto = new RecipeCreateDto();
        dto.setTitle("Testovací recept");
        dto.setDescription("Zkouška testů");
        dto.setIngredients("Voda");
        dto.setTimeToCookMinutes(5);
        dto.setAuthorId(1L);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("test_user");

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(10L);
        savedRecipe.setTitle("Testovací recept");
        savedRecipe.setAuthor(mockUser);


        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

        // 2. AKCE
        RecipeResponseDto result = recipeService.createRecipe(dto);

        // 3. KONTROLA
        assertNotNull(result);
        assertEquals("Testovací recept", result.getTitle());
        assertEquals("test_user", result.getAuthorUsername());

        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void createRecipe_UserNotFound_ThrowsException() {
        // 1. PŘÍPRAVA
        RecipeCreateDto dto = new RecipeCreateDto();
        dto.setAuthorId(99L); // ID, které neexistuje

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // 2. & 3. AKCE A KONTROLA
        Exception exception = assertThrows(RuntimeException.class, () -> {
            recipeService.createRecipe(dto);
        });

        assertEquals("Uživatel nebyl nalezen.", exception.getMessage());

        verify(recipeRepository, never()).save(any());
    }
}
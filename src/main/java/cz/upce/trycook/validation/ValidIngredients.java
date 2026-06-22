package cz.upce.trycook.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IngredientsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIngredients {
    String message() default "Recept musí obsahovat alespoň dvě ingredience oddělené čárkou.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

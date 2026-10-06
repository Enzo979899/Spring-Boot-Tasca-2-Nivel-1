package cat.itacademy.s04.t02.n01.fruitapih2.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record FruitRequest(@NotBlank String name, @Positive double weightInKilos) {

}

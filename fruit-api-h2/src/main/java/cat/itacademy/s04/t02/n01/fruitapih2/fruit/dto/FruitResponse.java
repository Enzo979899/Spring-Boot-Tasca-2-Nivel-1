package cat.itacademy.s04.t02.n01.fruitapih2.fruit.dto;

import cat.itacademy.s04.t02.n01.fruitapih2.fruit.model.Fruit;

public record FruitResponse(Long id, String name, double weightInKilos) {

    public static FruitResponse from(Fruit fruit) {
        return new FruitResponse(fruit.getId(), fruit.getName(), fruit.getWeightInKilos());
    }

}

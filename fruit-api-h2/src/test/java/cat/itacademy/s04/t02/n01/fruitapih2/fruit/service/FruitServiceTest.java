package cat.itacademy.s04.t02.n01.fruitapih2.fruit.service;

import cat.itacademy.s04.t02.n01.fruitapih2.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.fruit.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitService fruitService;

    @Test
    void shouldCreateFruit() {
        Fruit fruit = new Fruit(null, "Manzana", 1.8);
        Fruit savedFruit = new Fruit(1L, "Uva", 2.4);

        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedFruit);

        Fruit result = fruitService.create(fruit);

        assertEquals(1L, result.getId());
        assertEquals("Uva", result.getName());
        assertEquals(2.4, result.getWeightInKilos());
    }
}
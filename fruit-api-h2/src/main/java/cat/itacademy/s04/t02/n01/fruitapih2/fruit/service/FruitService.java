package cat.itacademy.s04.t02.n01.fruitapih2.fruit.service;

import cat.itacademy.s04.t02.n01.fruitapih2.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitService {
    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public Fruit create(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    public List<Fruit> findAll() {
        return fruitRepository.findAll();
    }
}

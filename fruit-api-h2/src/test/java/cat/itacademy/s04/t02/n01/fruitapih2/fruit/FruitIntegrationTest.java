package cat.itacademy.s04.t02.n01.fruitapih2.fruit;

import cat.itacademy.s04.t02.n01.fruitapih2.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.fruit.repository.FruitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FruitIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FruitRepository fruitRepository;

    @BeforeEach
    void setUp() {
        fruitRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveFruit() throws Exception {
        mockMvc.perform(post("/fruits").contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                  "name": "Manzana",
                                  "weightInKilos": 1.2
                                }
                                """)).andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Manzana"))
                .andExpect(jsonPath("$.weightInKilos").value(1.2));

        mockMvc.perform(get("/fruits")).andExpect(status().isOk()).andExpect(jsonPath("$.length()")
                        .value(1)).andExpect(jsonPath("$[0].name").value("Manzana"))
                .andExpect(jsonPath("$[0].weightInKilos").value(1.2));
    }

    @Test
    void shouldUpdateFruit() throws Exception {
        Fruit savedFruit = fruitRepository.save(new Fruit(null, "Pera", 2.0));

        mockMvc.perform(put("/fruits/{id}", savedFruit.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Melon",
                              "weightInKilos": 3.0
                            }
                            """)).andExpect(status().isOk()).andExpect(jsonPath("$.id")
                        .value(savedFruit.getId())).andExpect(jsonPath("$.name").value("Melon"))
                .andExpect(jsonPath("$.weightInKilos").value(3.0));
    }

    @Test
    void shouldDeleteFruit() throws Exception {
        Fruit savedFruit = fruitRepository.save(new Fruit(null, "Platano", 1.8));

        mockMvc.perform(delete("/fruits/{id}", savedFruit.getId())).andExpect(status().isNoContent());

        mockMvc.perform(get("/fruits/{id}", savedFruit.getId())).andExpect(status().isNotFound());
    }

}

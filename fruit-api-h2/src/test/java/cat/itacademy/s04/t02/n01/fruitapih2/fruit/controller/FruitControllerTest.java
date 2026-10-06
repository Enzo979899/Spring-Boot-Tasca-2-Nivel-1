package cat.itacademy.s04.t02.n01.fruitapih2.fruit.controller;


import cat.itacademy.s04.t02.n01.fruitapih2.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.fruit.service.FruitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FruitController.class)
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FruitService fruitService;

    @Test
    void shouldCreateFruitAndReturnCreated() throws Exception {
        Fruit savedFruit = new Fruit(1L, "Manzana", 1.5);

        when(fruitService.create(any(Fruit.class))).thenReturn(savedFruit);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                  "name": "Manzana",
                                  "weightInKilos": 1.5
                                }
                                """)).andExpect(status().isCreated()).andExpect(jsonPath("$.id")
                .value(1)).andExpect(jsonPath("$.name").value("Manzana"))
                .andExpect(jsonPath("$.weightInKilos").value(1.5));
    }
}


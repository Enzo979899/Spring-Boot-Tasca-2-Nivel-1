package cat.itacademy.s04.t02.n01.fruitapih2.fruit.controller;


import cat.itacademy.s04.t02.n01.fruitapih2.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.fruit.service.FruitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void shouldReturnAllFruits() throws Exception {
        Fruit uva = new Fruit(1L, "Uva", 1.4);
        Fruit melon = new Fruit(2L, "Melon", 3.7);

        when(fruitService.findAll())
                .thenReturn(List.of(uva, melon));

        mockMvc.perform(get("/fruits")).andExpect(status().isOk()).andExpect(jsonPath("$.length()")
                        .value(2)).andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Uva"))
                .andExpect(jsonPath("$[0].weightInKilos").value(1.4))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Melon"))
                .andExpect(jsonPath("$[1].weightInKilos").value(3.7));
    }

    @Test
    void shouldReturnFruitById() throws Exception {
        Fruit mandarinas = new Fruit(1L, "Mandarinas", 1.5);

        when(fruitService.findById(1L)).thenReturn(mandarinas);

        mockMvc.perform(get("/fruits/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id")
                        .value(1)).andExpect(jsonPath("$.name").value("Mandarinas"))
                .andExpect(jsonPath("$.weightInKilos").value(1.5));
    }

    @Test
    void shouldReturnNotFoundWhenFruitDoesNotExist() throws Exception {
        when(fruitService.findById(888L)).thenThrow(new FruitNotFoundException(888L));

        mockMvc.perform(get("/fruits/888")).andExpect(status().isNotFound());
    }

}


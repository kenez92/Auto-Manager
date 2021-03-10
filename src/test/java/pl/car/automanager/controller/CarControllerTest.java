package pl.car.automanager.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.car.automanager.boundary.car.CarUseCase;
import pl.car.automanager.persistence.entity.Car;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CarController.class)
class CarControllerTest {

    @MockBean
    CarUseCase carUseCase;

    @Autowired
    MockMvc mvc;

    @Test
    public void shouldAddCar() throws Exception {

        JSONObject object = new JSONObject()
                .put("brand", "Opel")
                .put("model", "Astra")
                .put("vin","DSSAD556654")
                .put("engine","1450")
                .put("fuel","PETROL");

        mvc.perform(post("/car")
                .content(object.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldGetAllCars() throws Exception {
        Car fordCar = new Car();
        Car fiatCar = new Car();
        when(carUseCase.findAll()).thenReturn(List.of(fordCar, fiatCar));

        mvc.perform(get("/car"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}

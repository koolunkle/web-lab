package com.example.ecomm.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.ecomm.config.ObjectMapperConfig;
import com.example.ecomm.entity.ProductEntity;
import com.example.ecomm.exception.RestApiErrorHandler;
import com.example.ecomm.hateoas.ProductRepresentationModelAssembler;
import com.example.ecomm.mapper.ProductMapper;
import com.example.ecomm.model.Product;
import com.example.ecomm.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private static final String URI = "/api/v1/products";

    private MockMvc mockMvc;

    @Mock
    private ProductService service;

    @Mock
    private MessageSource msgSource;

    @Mock
    private ProductMapper mapper;

    private ProductController controller;
    private ProductEntity entity;
    private Product product;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapperConfig().objectMapper());

        controller = new ProductController(service, new ProductRepresentationModelAssembler(mapper));
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestApiErrorHandler(msgSource))
                .build();

        entity = new ProductEntity()
                .setId(UUID.fromString("a1b9b31d-e73c-4112-af7c-b68530f38222"))
                .setName("Name")
                .setDescription("Description")
                .setImageUrl("/images/image.jpeg")
                .setPrice(new BigDecimal(20.99).setScale(2, RoundingMode.HALF_DOWN))
                .setCount(100);

        product = new Product().id(entity.getId().toString()).name(entity.getName())
                .description(entity.getDescription()).imageUrl(entity.getImageUrl())
                .price(entity.getPrice().doubleValue()).count(entity.getCount());
    }

    @Test
    @DisplayName("returns product by given ID")
    public void getProduct() throws Exception {
        // given
        given(service.getProduct("a1b9b31d-e73c-4112-af7c-b68530f38222"))
                .willReturn(Optional.of(entity));
        given(mapper.toModel(entity)).willReturn(product);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/products/{id}", "a1b9b31d-e73c-4112-af7c-b68530f38222")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk());
        verifyJson(result);
    }

    private void verifyJson(final ResultActions result) throws Exception {
        final String BASE_PATH = "http://localhost";
        result
                .andExpect(jsonPath("id", is(entity.getId().toString())))
                .andExpect(jsonPath("name", is(entity.getName())))
                .andExpect(jsonPath("description", is(entity.getDescription())))
                .andExpect(jsonPath("imageUrl", is(entity.getImageUrl())))
                .andExpect(jsonPath("price", is(entity.getPrice().doubleValue())))
                .andExpect(jsonPath("count", is(entity.getCount())))
                .andExpect(jsonPath("links[0].rel", is("self")))
                .andExpect(jsonPath("links[0].href", is(BASE_PATH + URI + "/" + entity.getId())))
                .andExpect(jsonPath("links[1].rel", is("products")))
                .andExpect(jsonPath("links[1].href", is(BASE_PATH + URI + "?page=1&size=10{&tag,name}")));
    }
}

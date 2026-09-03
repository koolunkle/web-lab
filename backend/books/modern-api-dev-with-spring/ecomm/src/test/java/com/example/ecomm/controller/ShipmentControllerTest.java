package com.example.ecomm.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.ecomm.config.ObjectMapperConfig;
import com.example.ecomm.entity.ShipmentEntity;
import com.example.ecomm.exception.RestApiErrorHandler;
import com.example.ecomm.hateoas.ShipmentRepresentationModelAssembler;
import com.example.ecomm.mapper.ShipmentMapper;
import com.example.ecomm.model.Shipment;
import com.example.ecomm.service.ShipmentService;

import tools.jackson.databind.json.JsonMapper;

@ExtendWith(MockitoExtension.class)
public class ShipmentControllerTest {

    private static final String id = "a1b9b31d-e73c-4112-af7c-b68530f38222";

    private MockMvc mockMvc;

    @Mock
    private ShipmentService service;

    @Mock
    private ShipmentRepresentationModelAssembler assembler;

    @Mock
    private MessageSource msgSource;

    @InjectMocks
    private ShipmentController controller;

    private final ShipmentMapper shipmentMapper = Mappers.getMapper(ShipmentMapper.class);
    private ShipmentEntity entity;
    private Shipment model;

    private JacksonTester<List<Shipment>> shipmentTester;

    @BeforeEach
    public void setup() {
        JsonMapper mapper = new ObjectMapperConfig().objectMapper();
        JacksonTester.initFields(this, mapper);

        JacksonJsonHttpMessageConverter mappingConverter = new JacksonJsonHttpMessageConverter(mapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestApiErrorHandler(msgSource))
                .setMessageConverters(mappingConverter)
                .build();

        final Instant now = Instant.now();

        entity = new ShipmentEntity();
        entity.setId(UUID.fromString(id));
        entity.setCarrier("Carrier");
        entity.setEstDeliveryDate(new Timestamp(now.getEpochSecond() * 1000));

        model = shipmentMapper.toModel(entity);
    }

    @Test
    @DisplayName("returns shipments by given order ID")
    public void testGetShipmetByOrderId() throws Exception {
        // given
        given(service.getShipmentByOrderId(id)).willReturn(List.of(entity));
        given(assembler.toListModel(List.of(entity))).willReturn(List.of(model));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/v1/shipping/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(shipmentTester.write(List.of(model)).getJson());
    }
}

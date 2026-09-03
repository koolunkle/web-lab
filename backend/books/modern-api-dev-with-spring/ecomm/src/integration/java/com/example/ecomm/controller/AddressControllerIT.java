package com.example.ecomm.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.assertj.core.util.Strings;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.example.ecomm.AuthClient;
import com.example.ecomm.TestUtils;
import com.example.ecomm.model.Address;
import com.example.ecomm.model.SignedInUser;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "spring.flyway.clean-disabled=false")
@AutoConfigureTestRestTemplate
@TestPropertySource(locations = "classpath:application-it.properties")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddressControllerIT {

    private static ObjectMapper objectMapper;
    private static AuthClient authClient;
    private static SignedInUser signedInUser;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public static void init(@Autowired Flyway flyway) {
        objectMapper = TestUtils.objectMapper();

        flyway.clean();
        flyway.migrate();
    }

    @BeforeEach
    public void setup(TestInfo info) {
        if (Objects.isNull(signedInUser) || Strings.isNullOrEmpty(signedInUser.getAccessToken())
                || TestUtils.isTokenExpired(signedInUser.getAccessToken())) {
            authClient = new AuthClient(restTemplate, objectMapper);

            if (info.getTags().contains("NonAdminUser")) {
                signedInUser = authClient.login("scott", "tiger");
            } else {
                signedInUser = authClient.login("scott2", "tiger");
            }
        }
    }

    @Test
    @DisplayName("returns all addresses")
    @Order(6)
    public void getAllAddress() {
        // given
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(signedInUser.getAccessToken());

        // when
        ResponseEntity<JsonNode> addressResponseEntity = restTemplate
                .exchange("/api/v1/addresses", HttpMethod.GET, new HttpEntity<>(headers), JsonNode.class);

        // then
        assertThat(addressResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        JsonNode node = addressResponseEntity.getBody();
        List<Address> addressFromResponse = objectMapper.convertValue(node, new TypeReference<ArrayList<Address>>() {
        });

        assertThat(addressFromResponse).hasSizeGreaterThan(0);
        assertThat(addressFromResponse.get(0)).hasFieldOrProperty("links");
        assertThat(addressFromResponse.get(0)).isInstanceOf(Address.class);
    }
}

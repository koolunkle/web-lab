package com.example.ecomm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.ecomm.entity.AddressEntity;
import com.example.ecomm.exception.ResourceNotFoundException;
import com.example.ecomm.model.AddAddressReq;
import com.example.ecomm.repository.AddressRepository;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

        private final static String id = "a1b9b31d-e73c-4112-af7c-b68530f38222";
        private final static String nonExistId = "a1b9b31d-e73c-4112-af7c-b68530f38220";

        private static AddressEntity entity;
        private static AddAddressReq addAddressReq;

        @Mock
        private AddressRepository repository;

        @InjectMocks
        private AddressServiceImpl service;

        @BeforeAll
        public static void setup() {
                entity = new AddressEntity()
                                .setId(UUID.fromString(id))
                                .setNumber("111")
                                .setResidency("Residency")
                                .setStreet("Street")
                                .setCity("City")
                                .setState("State")
                                .setCountry("Country")
                                .setPincode("12345");

                addAddressReq = new AddAddressReq()
                                .id(entity.getId().toString())
                                .number(entity.getNumber())
                                .residency(entity.getResidency())
                                .street(entity.getStreet())
                                .city(entity.getCity())
                                .state(entity.getState())
                                .country(entity.getCountry())
                                .pincode(entity.getPincode());
        }

        @Test
        @DisplayName("returns an AddressEntity when private method toEntity() is called with Address model")
        public void convertModelToEntity() {
                // given
                AddressServiceImpl service = new AddressServiceImpl(repository);

                // when
                AddressEntity entity = ReflectionTestUtils.invokeMethod(service, "toEntity", addAddressReq);

                // then
                then(entity)
                                .as("Check addreses entity is returned and not null").isNotNull();

                then(entity.getNumber())
                                .as("Check house/flat no is set").isEqualTo(addAddressReq.getNumber());

                then(entity.getResidency())
                                .as("Check residency is set").isEqualTo(addAddressReq.getResidency());

                then(entity.getStreet())
                                .as("Check street is set").isEqualTo(addAddressReq.getStreet());

                then(entity.getCity())
                                .as("Check city is set").isEqualTo(addAddressReq.getCity());

                then(entity.getState())
                                .as("Check state is set").isEqualTo(addAddressReq.getState());

                then(entity.getCountry())
                                .as("Check country is set").isEqualTo(addAddressReq.getCountry());

                then(entity.getPincode())
                                .as("Check pincode is set").isEqualTo(addAddressReq.getPincode());
        }

        @Test
        @DisplayName("save a new address")
        public void createAddress() {
                // given
                given(repository.save(any())).willReturn(entity);

                // when
                Optional<AddressEntity> result = service.createAddress(addAddressReq);

                // then
                assertThat(result).isNotNull();
                assertThat(result).isNotEmpty();
                assertThat(result.get()).isEqualTo(entity);
        }

        @Test
        @DisplayName("delete address by given existing id")
        public void deleteAddressesByIdWhenExists() {
                // given
                given(repository.findById(UUID.fromString(id)))
                                .willReturn(Optional.of(entity));

                willDoNothing().given(repository).deleteById(UUID.fromString(id));

                // when
                service.deleteAddressesById(id);

                // then
                verify(repository, times(1)).findById(UUID.fromString(id));
                verify(repository, times(1)).deleteById(UUID.fromString(id));
        }

        @Test
        @DisplayName("return empty address by the given non-existing id")
        public void getAddressesByIdWhenNotExists() {
                // given
                given(repository.findById(UUID.fromString(nonExistId)))
                                .willReturn(Optional.empty());

                // when
                Optional<AddressEntity> result = service.getAddressesById(nonExistId);

                // then
                assertThat(result).isNotNull();
                assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("delete address by given non-existing id, should throw ResourceNotFoundException")
        public void deleteAddressesByNonExistId() {
                // given
                given(repository.findById(UUID.fromString(nonExistId)))
                                .willReturn(Optional.empty());

                // when
                // then
                thenThrownBy(() -> service.deleteAddressesById(nonExistId))
                                .isInstanceOf(ResourceNotFoundException.class)
                                .hasMessageContaining("No Address found with id " + nonExistId);

                verify(repository, times(1)).findById(UUID.fromString(nonExistId));
                verify(repository, times(0)).deleteById(UUID.fromString(nonExistId));
        }

        @Test
        @DisplayName("return all addresses")
        public void getAllAddress() {
                // given
                given(repository.findAll()).willReturn(List.of(entity));

                // when
                Iterable<AddressEntity> result = service.getAllAddresses();

                // then
                assertThat(result).isNotNull();
                assertThat(result).contains(entity);
        }
}

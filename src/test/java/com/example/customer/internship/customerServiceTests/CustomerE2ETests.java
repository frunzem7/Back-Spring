package com.example.customer.internship.customerServiceTests;

import com.example.customer.internship.dto.CustomerDTO;
import com.example.customer.internship.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserters;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient(timeout = "PT1M")//30 seconds
public class CustomerE2ETests {
    private String serverURL;

    @LocalServerPort
    private int port;

    private final WebTestClient webTestClient;

    @Mock
    private HttpServletRequest request;

    private final CustomerService service;

    @BeforeAll
    public void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        serverURL = String.format("%s:%s", "localhost", port);
    }

    @AfterEach
    public void clearEach() {
//        service.deleteAll();
    }

    @Test
    @DisplayName("Save a valid customer and return a valid saved dto")
    public void save_validCustomer_validSaveCustomer() {
        // arrange
        CustomerDTO createDTO = new CustomerDTO();
        createDTO.setFirstName("Alina");
        createDTO.setLastName("Vieru");
        createDTO.setAddress("str. Maria Biesu 14");
        createDTO.setNumberPhone(69785463L);

        // act
        CustomerDTO savedCustomer = webTestClient
                .post()
                .uri(serverURL + "/api/service/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        // assert
        Assertions.assertNotNull(savedCustomer);

        Assertions.assertEquals(createDTO.getFirstName(), savedCustomer.getFirstName());
        Assertions.assertEquals(createDTO.getLastName(), savedCustomer.getLastName());
        Assertions.assertEquals(createDTO.getAddress(), savedCustomer.getAddress());
        Assertions.assertEquals(createDTO.getNumberPhone(), savedCustomer.getNumberPhone());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/customer/" + savedCustomer.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Alina", "Mariana", "David"})
    @DisplayName("Update a valid customer and return a valid updated dto")
    public void updateCustomer(String firstName) {
        //arrange
        CustomerDTO createDTO = new CustomerDTO();
        createDTO.setFirstName(firstName);
        createDTO.setLastName("Rotaru");
        createDTO.setAddress("str. Maria Biesu 14");
        createDTO.setNumberPhone(69785463L);

        CustomerDTO savedCustomer = webTestClient
                .post()
                .uri(serverURL + "/api/service/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedCustomer);

        savedCustomer.setFirstName(firstName);
        savedCustomer.setLastName("Rotaru");
        savedCustomer.setAddress("str. Maria Biesu 14");
        savedCustomer.setNumberPhone(69785463L);

        // act
        CustomerDTO updatedDto = webTestClient
                .put()
                .uri(serverURL + "/api/service/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(savedCustomer))
                .exchange()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        //assert
        Assertions.assertNotNull(updatedDto);
        Assertions.assertEquals(savedCustomer, updatedDto);

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/customer/" + savedCustomer.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("Delete customer")
    public void deleteCustomerById() {
        //arrange
        CustomerDTO createDTO = new CustomerDTO();
        createDTO.setFirstName("Alina");
        createDTO.setLastName("Rotaru");
        createDTO.setAddress("str. Maria Biesu 14");
        createDTO.setNumberPhone(69785463L);

        CustomerDTO savedCustomer = webTestClient
                .post()
                .uri(serverURL + "/api/service/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedCustomer);

        //act
        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/customer/" + savedCustomer.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();

    }

    @Test
    @DisplayName("Get customer")
    public void getCustomer() {
        //arrange
        CustomerDTO createDTO = new CustomerDTO();
        createDTO.setFirstName("Alina");
        createDTO.setLastName("Rotaru");
        createDTO.setAddress("str. Maria Biesu 14");
        createDTO.setNumberPhone(69785463L);

        CustomerDTO savedCustomer = webTestClient
                .post()
                .uri(serverURL + "/api/service/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedCustomer);

        //act
        CustomerDTO gotCustomer = this.webTestClient
                .get()
                .uri(serverURL + "/api/service/customer/" + savedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        // assertions
        Assertions.assertNotNull(gotCustomer);
        Assertions.assertEquals(createDTO.getFirstName(), gotCustomer.getFirstName());
        Assertions.assertEquals(createDTO.getLastName(), gotCustomer.getLastName());
        Assertions.assertEquals(createDTO.getAddress(), gotCustomer.getAddress());
        Assertions.assertEquals(createDTO.getNumberPhone(), gotCustomer.getNumberPhone());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/customer/" + savedCustomer.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}

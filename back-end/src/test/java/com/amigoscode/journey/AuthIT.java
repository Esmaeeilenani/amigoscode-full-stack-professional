package com.amigoscode.journey;

import com.amigoscode.auth.LoginVM;
import com.amigoscode.customer.CustomerRegistration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthIT {

    @Autowired
    private WebTestClient webTestClient;

    private static final String uri = "/api/v1/auth";

    private static final String CUSTOMER_URI = "/api/v1/customers";

    @Test
    void canLogin() {
        // Given
        String name = getFaker().name().fullName();
        String email = """
                %s%s@integration.com""".formatted(name.replaceAll(" ", "_").toLowerCase()
                , UUID.randomUUID());
        int age = new Random().nextInt(1, 100);
        CustomerRegistration registration =
                new CustomerRegistration(name
                        , email,
                        "password", "MALE", age);

        LoginVM loginVM = new LoginVM(registration.email(), registration.password());

        webTestClient
                .post()
                .uri(uri+"/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(loginVM), LoginVM.class)
                .exchange()
                .expectStatus()
                .isForbidden();



        //post request
         webTestClient
                .post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registration), CustomerRegistration.class)
                .exchange()
                .expectStatus()
                .isOk();


        // When

        // Then
        webTestClient
                .post()
                .uri(uri+"/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(loginVM), LoginVM.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

    public static Faker getFaker() {
        return new Faker();
    }
}

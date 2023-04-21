package com.amigoscode.journey;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRegistration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIT {

    @Autowired
    private WebTestClient webTestClient;

    private static final String uri = "/api/v1/customers";


    @Test
    void canRegisterCustomer() {
        // create RegisterCustomer
        String name = getFaker().name().fullName();
        String email = """
                %s%s@integration.com
                """.formatted(name.replaceAll(" ", "_").toLowerCase()
                , UUID.randomUUID());
        int age = new Random().nextInt(1, 100);
        CustomerRegistration registration =
                new CustomerRegistration(name
                        , email,
                        age);

        //post request

        webTestClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registration), CustomerRegistration.class)
                .exchange()
                .expectStatus()
                .isCreated();
        //get all customer to make sure customer is present
        List<Customer> allCustomer = webTestClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        Customer expectdCustomer = new Customer(name, email, age);
        assertThat(allCustomer)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectdCustomer);


        int id = allCustomer
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::id)
                .findAny().orElse(-1);
        expectdCustomer.id(id);
        // Then

        webTestClient
                .get()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .isEqualTo(expectdCustomer);

    }

    @Test
    void deleteCustomer() {
        // create RegisterCustomer
        String name = getFaker().name().fullName();
        String email = """
                %s%s@integration.com
                """.formatted(name.replaceAll(" ", "_").toLowerCase()
                , UUID.randomUUID());
        int age = new Random().nextInt(1, 100);
        CustomerRegistration registration =
                new CustomerRegistration(name
                        , email,
                        age);


        //post request
        webTestClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registration), CustomerRegistration.class)
                .exchange()
                .expectStatus()
                .isCreated();
        //get all customer to make sure customer is present
        List<Customer> allCustomer = webTestClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();


        int id = allCustomer
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::id)
                .findAny().orElse(-1);

        webTestClient
                .delete()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();


        webTestClient
                .get()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

    }


    @Test
    void updateCustomer() {
        // create RegisterCustomer
        String name = getFaker().name().fullName();
        String email = """
                %s%s@integration.com
                """.formatted(name.replaceAll(" ", "_").toLowerCase()
                , UUID.randomUUID());
        int age = new Random().nextInt(1, 100);
        CustomerRegistration registration =
                new CustomerRegistration(name
                        , email,
                        age);


        //post request
        webTestClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registration), CustomerRegistration.class)
                .exchange()
                .expectStatus()
                .isCreated();
        //get all customer to make sure customer is present
        List<Customer> allCustomer = webTestClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();


        int id = allCustomer
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::id)
                .findAny().orElse(-1);

        Customer updated = new Customer()
                .id(id)
                .name("esmaeeil")
                .email(registration.email())
                .age(registration.age());

        webTestClient
                .put()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updated), Customer.class)
                .exchange()
                .expectStatus()
                .isOk();


        Customer expected = webTestClient
                .get()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        assertThat(expected).isEqualTo(updated);

    }

    public static Faker getFaker() {
        return new Faker();
    }
}

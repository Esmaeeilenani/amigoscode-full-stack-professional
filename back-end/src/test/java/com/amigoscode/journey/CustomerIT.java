package com.amigoscode.journey;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerDTO;
import com.amigoscode.customer.CustomerRegistration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
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
                %s%s@integration.com""".formatted(name.replaceAll(" ", "_").toLowerCase()
                , UUID.randomUUID());
        int age = new Random().nextInt(1, 100);
        CustomerRegistration registration =
                new CustomerRegistration(name
                        , email,
                        "password", "MALE", age);

        //post request
        byte[] responseBodyContent = webTestClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registration), CustomerRegistration.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .getResponseBodyContent();
        String jwtToken = new String(responseBodyContent) ;

        //get all customer to make sure customer is present
        List<CustomerDTO> allCustomer = webTestClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        CustomerDTO expectdCustomer = new CustomerDTO(null,name, email, age, "MALE");
        assertThat(allCustomer)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectdCustomer);


        int id = allCustomer
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(CustomerDTO::getId)
                .findAny().orElse(-1);
        expectdCustomer.setId(id);
        // Then


       CustomerDTO captured = webTestClient
                .get()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
               .getResponseBody();

       assertThat(captured.getId()).isEqualTo(expectdCustomer.getId());

    }

    @Test
    void deleteCustomer() {
        // create RegisterCustomer
        String name = getFaker().name().fullName();
        String email = """
                %s%s@integration.com""".formatted(name.replaceAll(" ", "_").toLowerCase()
                , UUID.randomUUID());
        int age = new Random().nextInt(1, 100);
        CustomerRegistration registration =
                new CustomerRegistration(name
                        , email,
                        "password", "MALE", age);


        //post request create customer
        webTestClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registration), CustomerRegistration.class)
                .exchange()
                .expectStatus()
                .isOk();

        // create new customer to delete the first one
        String name2 = getFaker().name().fullName();
        CustomerRegistration customerToDoDeleteAction =
                new CustomerRegistration(name2
                        , """
                %s%s@integration.com""".formatted(name2.replaceAll(" ", "_").toLowerCase()
                        , UUID.randomUUID()),
                        "password", "MALE", age);
        byte[] responseBodyContent = webTestClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerToDoDeleteAction), CustomerRegistration.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .getResponseBodyContent();
        String jwtToken = new String(responseBodyContent) ;


        //get all customer to make sure customer is present
        List<CustomerDTO> allCustomer = webTestClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();


        int id = allCustomer
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(CustomerDTO::getId)
                .findAny().orElse(-1);

        webTestClient
                .delete()
                .uri(uri + "/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();


        webTestClient
                .get()
                .uri(uri + "/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
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
                %s%s@integration.com""".formatted(name.replaceAll(" ", "_").toLowerCase()
                , UUID.randomUUID());
        int age = new Random().nextInt(1, 100);
        CustomerRegistration registration =
                new CustomerRegistration(name
                        , email,
                        "password", "MALE", age);


        //post request
        byte[] responseBodyContent = webTestClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registration), CustomerRegistration.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .getResponseBodyContent();
        String jwtToken = new String(responseBodyContent) ;



        //get all customer to make sure customer is present
        List<Customer> allCustomer = webTestClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
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

        CustomerDTO updated = new CustomerDTO()
                .setId(id)
                .setName("esmaeeil")
                .setEmail(registration.email())
                .setAge(registration.age())
                .setGender("MALE");

        webTestClient
                .put()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
                .body(Mono.just(updated), CustomerDTO.class)
                .exchange()
                .expectStatus()
                .isOk();


        CustomerDTO expected = webTestClient
                .get()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        assertThat(expected.getId()).isEqualTo(updated.getId());

    }

    public static Faker getFaker() {
        return new Faker();
    }
}

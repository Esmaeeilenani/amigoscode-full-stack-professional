package com.amigoscode.customer;

import com.amigoscode.AbstractTestcontainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

import java.util.Random;
import java.util.UUID;


@DataJpaTest
//required to connect to Test container
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainer {

    @Autowired
    private CustomerRepository underTest;

    @BeforeEach
    void setUp() {
        //clean the table before each method
        underTest.deleteAll();
    }

    @Test
    void existsByEmail() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        underTest.save(customer);

        // When
        boolean actual = underTest.existsByEmail(customer.email());
        // Then
        assertThat(actual).isTrue();

    }
}
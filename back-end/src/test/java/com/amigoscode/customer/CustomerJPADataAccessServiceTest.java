package com.amigoscode.customer;

import com.amigoscode.AbstractTestcontainer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJPADataAccessServiceTest extends AbstractTestcontainer {

    private CustomerJPADataAccessService underTest;

    @Mock
    private CustomerRepository customerRepository;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);

    }

    @AfterEach
    void afterEach() {

        try {
            autoCloseable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void selectAllCustomers() {

        // When
        underTest.selectAllCustomers();
        // Then
        Mockito.verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        // Given
        int id = 1;
        // When
        underTest.selectCustomerById(id);
        // Then
        Mockito.verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(new Random().nextInt(16, 99));


        // When
        underTest.insertCustomer(customer);

        // Then
        Mockito.verify(customerRepository)
                .save(customer);

    }

    @Test
    void existByEmail() {
        // Given
        String email = "";
        // When
        underTest.existByEmail(email);

        // Then
        Mockito.verify(customerRepository).existsByEmail(email);
    }

    @Test
    void existById() {
        // Given
        int id = 1;
        // When
        underTest.existById(id);
        // Then
        Mockito.verify(customerRepository).existsById(id);
    }

    @Test
    void deleteById() {
        // Given
        int id = 1;
        // When
        underTest.deleteById(id);
        // Then
        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(new Random().nextInt(16, 99));


        // When
        underTest.updateCustomer(customer);

        // Then
        Mockito.verify(customerRepository)
                .save(customer);

    }
}
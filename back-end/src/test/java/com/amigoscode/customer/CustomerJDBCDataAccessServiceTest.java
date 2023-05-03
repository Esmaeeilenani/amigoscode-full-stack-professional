package com.amigoscode.customer;

import com.amigoscode.AbstractTestcontainer;
import com.amigoscode.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;



class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainer {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        this.underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate()
                , customerRowMapper);
    }

    @Test
    void selectAllCustomers() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        underTest.insertCustomer(customer);

        // When
        List<Customer> customers = underTest.selectAllCustomers();

        // Then
        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.email()))
                .map(Customer::id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        // When
        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);


        // Then
        assertThat(actualCustomer)
                .isPresent()
                .hasValueSatisfying(
                        c -> assertThat(c.getId().equals(id)).isTrue()
                );
    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        // When
        underTest.insertCustomer(customer);

        // Then
        assertThat(underTest.selectAllCustomers().isEmpty()).isFalse();
    }

    @Test
    void existByEmail() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        underTest.insertCustomer(customer);

        // When
        boolean actual = underTest.existByEmail(customer.email());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existById() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.email()))
                .map(Customer::id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        // When
        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);


        // Then
        assertThat(underTest.existById(id)).isTrue();
    }

    @Test
    void deleteById() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.email()))
                .map(Customer::id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        // When
        underTest.deleteById(id);
        // Then
        assertThat(underTest.selectCustomerById(id).isEmpty()).isTrue();

    }

    @Test
    void updateCustomer() {
        // Given
        Customer customer = new Customer()
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.email()))
                .map(Customer::id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        Customer updated = new Customer()
                .id(id)
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .password("password")
                .gender("MALE")
                .age(new Random().nextInt(16, 99));
        underTest.updateCustomer(updated);
        // When
        Customer actual = underTest.selectCustomerById(id).get();

        // Then
        assertThat(actual.getName().equals(customer.name())).isFalse();
        assertThat(actual.getName().equals(updated.name())).isTrue();
    }
}
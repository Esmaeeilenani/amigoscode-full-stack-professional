package com.amigoscode.customer;

import com.amigoscode.AbstractTestcontainer;
import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest extends AbstractTestcontainer {

    private CustomerService underTest;

    @Mock
    private CustomerDao customerDao;


    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }


    @Test
    void getAllCustomers() {


        // When
        underTest.getAllCustomers();


        // Then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {
        // Given
        int id = 1;
        Customer customer = new Customer()
                .id(id)
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(new Random().nextInt(16, 99));

        when(customerDao.selectCustomerById(id))
                .thenReturn(Optional.of(customer));

        // When
        Customer actual = underTest.getCustomer(id);

        // Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void cantGetCustomer() {
        // Given
        int id = 1;

        when(customerDao.selectCustomerById(id))
                .thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                //it should be the exact message
                .hasMessage("customer with id [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        // Given
        int id = 1;
        String name = getFaker().name().fullName();
        String email = getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID();
        int age = new Random().nextInt(16, 99);

        when(customerDao.existByEmail(email)).thenReturn(false);

        // When
        CustomerRegistration registration = new CustomerRegistration(name, email, age);
        underTest.addCustomer(registration);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao)
                .insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(registration.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(registration.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(registration.age());

    }

    @Test
    void addCustomerFail() {
        // Given
        String name = getFaker().name().fullName();
        String email = getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID();
        int age = new Random().nextInt(16, 99);

        when(customerDao.existByEmail(email)).thenReturn(true);

        // When
        CustomerRegistration registration = new CustomerRegistration(name, email, age);
        assertThatThrownBy(() -> underTest.addCustomer(registration))
                .isInstanceOf(DuplicateResourceException.class)
                //it should be the exact message
                .hasMessage("email already exists");
        // Then
        verify(customerDao, never()).insertCustomer(any());

    }


    @Test
    void existCustomerByEmail() {
        // Given
        String email = getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID();


        when(customerDao.existByEmail(email))
                .thenReturn(true);

        // When
        boolean actual = underTest.existCustomerByEmail(email);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existCustomerById() {
        // Given
        int id = 1;

        when(customerDao.existById(id))
                .thenReturn(true);

        // When
        boolean actual = underTest.existCustomerById(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void deleteCustomerById() {
        // Given
        int id = 1;

        // When
        underTest.deleteCustomerById(id);
        // Then
        verify(customerDao).deleteById(id);
    }

    @Test
    void updateCustomerById() {
        // Given
        int id = 1;
        Customer customer = new Customer()
                .id(id)
                .name(getFaker().name().fullName())
                .email(getFaker().internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(new Random().nextInt(16, 99));

        when(customerDao.selectCustomerById(customer.getId()))
                .thenReturn(Optional.of(customer));

        Customer updated =
                new Customer()
                        .name("esmaeeil")
                        .email("esmaeeil@gmail.com")
                        .age(25);

        // When
        underTest.updateCustomerById(customer.getId(), updated);
        // Then
        assertThat(customer.getId()).isEqualTo(updated.id());
    }
}
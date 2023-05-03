package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    private final CustomerDao customerDao;

    private final PasswordEncoder passwordEncoder;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDTO> getAllCustomers() {
        return CustomerMapper.toDto(customerDao.selectAllCustomers());
    }

    public CustomerDTO getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .map(CustomerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public Customer findCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistration customerRegistration) {

        String email = customerRegistration.email();
        if (customerDao.existByEmail(email)) {
            throw new DuplicateResourceException("email already exists");
        }

        Customer customer = new Customer()
                .name(customerRegistration.name())
                .email(customerRegistration.email())
                .password(passwordEncoder.encode(customerRegistration.password()))
                .gender("MALE")
                .age(customerRegistration.age());

        customerDao.insertCustomer(customer);
    }

    public boolean existCustomerByEmail(String email) {
        return customerDao.existByEmail(email);
    }

    public boolean existCustomerById(Integer id) {
        return customerDao.existById(id);
    }

    public void deleteCustomerById(Integer id) {
        customerDao.deleteById(id);
    }

    public void updateCustomerById(Integer id, CustomerDTO customer) {
        Customer oldCustomer = findCustomer(id);
        if (!oldCustomer.getEmail().equals(customer.getEmail()) &&
                customerDao.existByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("email already exists");
        }
        Customer updated = CustomerMapper.toEntity(customer);
        updated.password(oldCustomer.password());

        customerDao.updateCustomer(updated);
    }

}

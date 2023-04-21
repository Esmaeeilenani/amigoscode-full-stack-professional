package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
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

    public void updateCustomerById(Integer id, Customer customer) {
        Customer oldCustomer = getCustomer(id);
        if (!oldCustomer.getEmail().equals(customer.email()) &&
                customerDao.existByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("email already exists");
        }


        customer.id(id);
        customerDao.updateCustomer(customer);
    }

}

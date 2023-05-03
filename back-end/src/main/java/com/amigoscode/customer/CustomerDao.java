package com.amigoscode.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
    void insertCustomer(Customer customer);
    boolean existByEmail(String email);

    boolean existById(Integer id);

    void deleteById(Integer id);

    void updateCustomer(Customer customer);

    Optional<Customer> selectUserByEmail(String email);

}

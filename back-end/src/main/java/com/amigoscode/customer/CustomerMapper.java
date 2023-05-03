package com.amigoscode.customer;


import java.util.List;

public class CustomerMapper {

    public static CustomerDTO toDto(Customer customer) {
        return new CustomerDTO()
                .setId(customer.getId())
                .setName(customer.getName())
                .setAge(customer.getAge())
                .setEmail(customer.getEmail())
                .setGender(customer.gender());
    }

    public static List<CustomerDTO> toDto(List<Customer> customers) {
        return customers
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    public static Customer toEntity(CustomerDTO customer) {
        return new Customer()
                .id(customer.getId())
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .gender(customer.getGender());
    }

    public static List<Customer> toEntity(List<CustomerDTO> customerDTOS) {
        return customerDTOS
                .stream()
                .map(CustomerMapper::toEntity)
                .toList();
    }


}

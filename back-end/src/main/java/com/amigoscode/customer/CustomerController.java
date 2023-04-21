package com.amigoscode.customer;


import com.amigoscode.exception.DuplicateResourceException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerRegistration customerRegistration){
        customerService.addCustomer(customerRegistration);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Integer id ,@RequestBody Customer customer){

        customerService.updateCustomerById(id, customer);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id){
        customerService.deleteCustomerById(id);

        return ResponseEntity.ok().build();
    }

}

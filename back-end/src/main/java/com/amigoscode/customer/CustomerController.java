package com.amigoscode.customer;


import com.amigoscode.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;


    public CustomerController(CustomerService customerService, JWTUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;

    }


    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("{customerId}")
    public CustomerDTO getCustomer(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegistration customerRegistration) {
        customerService.addCustomer(customerRegistration);
        String token = jwtUtil.issueToken(customerRegistration.email(), "ROLE_USER");
        return ResponseEntity
                .ok(token);


    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customer) {

        customerService.updateCustomerById(id, customer);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomerById(id);

        return ResponseEntity.ok().build();
    }

}

package com.amigoscode.customer;

import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {


    private final JdbcTemplate jdbcTemplate;

    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        String sql = """
                SELECT * from customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);

    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        String sql = """
                SELECT * from customer where id=?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO customer (name,email,age)
                values (?, ?, ?)
                """;
        jdbcTemplate.update(sql, customer.name(), customer.email(), customer.age());
    }

    @Override
    public boolean existByEmail(String email) {
        String sql = """
                SELECT * from customer where email=?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, email)
                .stream()
                .findAny()
                .isPresent();
    }

    @Override
    public boolean existById(Integer id) {
        String sql = """
                SELECT * from customer where id=?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findAny()
                .isPresent();
    }

    @Override
    public void deleteById(Integer id) {
        String sql = """
                delete from customer where id=?
                 """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateCustomer(Customer customer) {

        String sql ;

        if (customer.name() != null) {
            sql = """
                    UPDATE customer set name=? WHERE id=?
                    """;
            jdbcTemplate.update(sql, customer.name(), customer.id());
        }

        if (customer.email()!= null){
            sql = """
                    UPDATE customer set email=? WHERE id=?
                    """;
            jdbcTemplate.update(sql, customer.email(), customer.id());
        }
        if (customer.age() != null){
            sql = """
                    UPDATE customer set age=? WHERE id=?
                    """;
            jdbcTemplate.update(sql, customer.age(), customer.id());
        }


    }
}

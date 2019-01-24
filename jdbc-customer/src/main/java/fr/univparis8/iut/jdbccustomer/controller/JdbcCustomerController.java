package fr.univparis8.iut.jdbccustomer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.univparis8.iut.jdbccustomer.CustomerRowMapper;
import fr.univparis8.iut.jdbccustomer.model.Customer;

@RestController
public class JdbcCustomerController {
    @Autowired
    JdbcTemplate jdbcTemplate;
//    
//    @GetMapping("/customers")
//	public Object findAll(){
//		return Arrays.asList();
//	}
    
	@GetMapping("/customers")
	public List<Customer> findAll(){
	   	// log.info(MessageFormat.format("Querying for customer records "));

	        List<Customer> customers = jdbcTemplate.query(
	                "SELECT * FROM customers",
	                new CustomerRowMapper() );
	        return customers;
	}
	
	@GetMapping("/customersByID/{id}")
	public List<Customer> findAll(@PathVariable("id") long id){
	   	// log.info(MessageFormat.format("Querying for customer records "));
	        List<Customer> customers = jdbcTemplate.query(
	        		 "SELECT id, first_name, last_name FROM customers WHERE id = ?", new Object[] {id },
	                 new CustomerRowMapper());
	        return customers;
	}

}

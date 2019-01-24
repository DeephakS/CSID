package fr.univparis8.iut.jdbccustomer;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import fr.univparis8.iut.jdbccustomer.model.Customer;

@SpringBootApplication
public class JdbcCustomerApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JdbcCustomerApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(JdbcCustomerApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {
    	createTable();
    	insertValues();

    	List<Customer> customers= findBySurname("Josh");
    	for (Customer customer : customers) {
    		log.info(customer.toString());
    	}

    }

    public void insertValues() {
     this.insertCustomer(new Customer( "totoNomtest00000", "totoPrenom"));
     this.insertCustomer(new Customer( "totoNom1", "totoPrenom1"));
     this.insertCustomer(new Customer( "totoNom2", "totoPrenom2"));
     this.insertCustomer(new Customer( "Josh", "totoPrenom3"));
     this.insertCustomer(new Customer( "totoNom4", "totoPrenom4"));
     this.insertCustomer(new Customer( "Josh", "totoPrenom5"));
    }

    public void createTable() {
    	 log.info("Creating tables");

         jdbcTemplate.execute("DROP TABLE IF EXISTS customers");
         jdbcTemplate.execute("CREATE TABLE customers(" +
                 "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
    }
    /**
     * @param newCusto : the Customer to be added in database
     */
    public void insertCustomer(Customer newCusto) {
    	jdbcTemplate.update(
    			"INSERT INTO customers(first_name, last_name) VALUES (?,?)",
    			newCusto.getFirstName(),
    			newCusto.getLastName()
    			);
    }
    public List<Customer> findBySurname(String surname){
   	 log.info(MessageFormat.format("Querying for customer records where first_name = {0}:", surname));

        return jdbcTemplate.query(
                "SELECT * FROM customers WHERE first_name = ?", new Object[] {surname },
                new CustomerRowMapper());
   }

}
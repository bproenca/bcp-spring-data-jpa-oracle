package br.com.bcp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppJpa implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(AppJpa.class);

	@Autowired
	private CustomerRepository customerRep;

	public static void main(String[] args) {
		SpringApplication.run(AppJpa.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Clean up
		customerRep.deleteAll();

		// Split up the array of whole names into an array of first/last names
		List<String[]> splitUpNames = Arrays.asList("1 John Woo", "2 Jeff Dean", "3 Josh Bloch", "4 Josh Long").stream()
				.map(name -> name.split(" ")).collect(Collectors.toList());

		// Use stream to print out each tuple of the list
		splitUpNames.forEach(name -> customerRep.save(new Customer(Long.valueOf(name[0]), name[1], name[2])));

		log.info("Querying for customer records where first_name = 'Josh':");
		customerRep.findByFirstName("Josh").forEach(customer -> log.info(customer.toString()));
		;
	}

}

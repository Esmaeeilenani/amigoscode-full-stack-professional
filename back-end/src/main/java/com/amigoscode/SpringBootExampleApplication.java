package com.amigoscode;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SpringBootExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleApplication.class, args);
	}



//	@Bean
//	CommandLineRunner runner(CustomerRepository customerRepository){
//		return arg->{
//
//			for (int i = 0; i < 100; i++) {
//				Faker faker = new Faker();
//				String name = faker.name().firstName() + "_"+faker.name().lastName();
//				Customer customer = new Customer()
//						.name(name.replace("_"," "))
//						.email("""
//								%s@gmail.com
//								""".formatted(name).toLowerCase())
//						.age(new Random().nextInt(16,99));
//				customerRepository.save(customer);
//			}
//		};
//	}

}

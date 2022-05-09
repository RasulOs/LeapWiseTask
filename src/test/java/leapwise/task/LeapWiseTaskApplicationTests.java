package leapwise.task;

import leapwise.task.persistence.model.Expression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;

@SpringBootTest
public class LeapWiseTaskApplicationTests {

	@Test
	public void integrationTest()
	{
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ConfigurableApplicationContext context = null;

		SpringApplication app = new SpringApplication (LeapWiseTaskApplication.class);
		context = app.run ();

		Expression expression = new Expression();
		expression.setName("test expression");
		expression.setValue("(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");

		ResponseEntity<String> responseEntity = testRestTemplate.exchange("http://localhost:8081/expression", HttpMethod.POST, createHttpRequest(expression), String.class);

		Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
	}

	private <T> HttpEntity<T> createHttpRequest(T body)
	{
		HttpHeaders headers = new HttpHeaders();

		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);

		return new HttpEntity<>(body, headers);
	}
}

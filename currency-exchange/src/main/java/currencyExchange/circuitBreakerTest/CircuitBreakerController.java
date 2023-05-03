package currencyExchange.circuitBreakerTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("currency-exchange")
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	private static int counter = 0;
	
	@GetMapping("sample/api")
	//@Retry(name = "default", fallbackMethod = "hardCodedResponse")
	@CircuitBreaker(name = "circuitBreakerTest", fallbackMethod = "circuitBreakerFallBack")
	public String testSample() {
		
		counter++;
		logger.info("No. of method call: " + counter);
		String value = 
		new RestTemplate().getForEntity("http://localhost:8000/fakeUrl", String.class)
		.getBody();
		return value;
	}
	
	public String hardCodedResponse(Exception ex) {
		return "Service is currently unavailable";
	}
	
	public String circuitBreakerFallBack(Exception ex) {
		return "Circuit breaker activated";
	}
	
	
}

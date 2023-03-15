package currencyConversion;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	//localhost:8100/currency-conversion/from/EUR/to/RSD/quantity/100
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getConversion
		(@PathVariable String from, @PathVariable String to, @PathVariable double quantity) {
		
		HashMap<String,String> uriVariables = new HashMap<String,String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> response = 
				new RestTemplate().
				getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
						CurrencyConversion.class, uriVariables);
		
		CurrencyConversion cc = response.getBody();
		
		return new CurrencyConversion(from,to,cc.getConversionMultiple(), cc.getEnvironment(),
				cc.getConversionMultiple().multiply(BigDecimal.valueOf(quantity)), quantity);
	}
	
	//localhost:8100/currency-conversion?from=EUR&to=RSD&quantity=50
	@GetMapping("/currency-conversion")
	public CurrencyConversion getConversionParams
				(@RequestParam String from, @RequestParam String to, @RequestParam(defaultValue = "10") double quantity) {
		HashMap<String,String> uriVariables = new HashMap<String,String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> response = 
				new RestTemplate().
				getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
						CurrencyConversion.class, uriVariables);
		
		CurrencyConversion cc = response.getBody();
		
		return new CurrencyConversion(from,to,cc.getConversionMultiple(), cc.getEnvironment(),
				cc.getConversionMultiple().multiply(BigDecimal.valueOf(quantity)), quantity);
	}
	
	
}

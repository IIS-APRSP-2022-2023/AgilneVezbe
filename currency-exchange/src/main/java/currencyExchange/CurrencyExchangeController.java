package currencyExchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private CurrencyExchangeRepository repo;
	
	@Autowired 
	private Environment environment;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getExchange(@PathVariable String from, @PathVariable String to) {
		//return new CurrencyExchange(10000, from, to, BigDecimal.valueOf(117),"");
		String port = environment.getProperty("local.server.port");
		CurrencyExchange kurs = repo.findByFromAndToIgnoreCase(from, to);
		kurs.setEnvironment(port);
		return kurs;
	}
	
	//localhost:8000/currency-exchange/from/EUR/to/RSD
}

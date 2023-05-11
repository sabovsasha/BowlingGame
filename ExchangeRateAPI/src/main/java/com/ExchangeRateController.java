package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {

	@Autowired
	Monobank monobank;
	@Autowired
	Privatbank privatbank;
	@Autowired
	Bankgov bankgovua;

	@RequestMapping(value = "/synchExchangeRates", method = RequestMethod.GET)
	public void synchExchangeRates(@RequestParam String provider) {
		if (provider.trim().equalsIgnoreCase("monobank")) {
			monobank.synchExchangeRates();
		}
		if (provider.trim().equalsIgnoreCase("bankgovua")) {
			bankgovua.synchExchangeRates();
		}
		if (provider.trim().equalsIgnoreCase("privatbank")) {
			privatbank.synchExchangeRates();
		}
	}

	@RequestMapping(value = "/getAvgExchRatesForToday", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public Object getAvgExchRatesForToday(@RequestParam String provider) {
		Object object = null;
		if (provider.trim().equalsIgnoreCase("monobank")) {
			object = monobank.getAvgExchRatesForToday();
		}
		if (provider.trim().equalsIgnoreCase("bankgovua")) {
			object = bankgovua.getAvgExchRatesForToday();
		}
		if (provider.trim().equalsIgnoreCase("privatbank")) {
			object = privatbank.getAvgExchRatesForToday();
		}
		return object;
	}

	@RequestMapping(value = "/getAvgExchRatesForPeriod", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public Object getAvgExchRatesForPeriod(@RequestParam String provider, @RequestParam String prevDate,
			@RequestParam String nextDate) {
		Object object = null;
		if (provider.trim().equalsIgnoreCase("monobank")) {
			object = monobank.getAvgExchRatesForPeriod(prevDate, nextDate);
		}
		if (provider.trim().equalsIgnoreCase("bankgovua")) {
			object = bankgovua.getAvgExchRatesForPeriod(prevDate, nextDate);
		}
		if (provider.trim().equalsIgnoreCase("privatbank")) {
			object = privatbank.getAvgExchRatesForPeriod(prevDate, nextDate);
		}
		return object;
	}

}

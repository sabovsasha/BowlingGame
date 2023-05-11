package com;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.Common;
import response.MonobankResponse;

@Service
public class Monobank extends Common {

	private final String apiUrl = "https://api.monobank.ua/bank/currency";
	@Autowired
	MonobankRepository monobankRepo;

	public void synchExchangeRates() {
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			int responsecode = conn.getResponseCode();

			if (responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			} else {
				String inline = "";
				Scanner scanner = new Scanner(url.openStream());

				while (scanner.hasNext()) {
					inline += scanner.nextLine();
				}

				scanner.close();

				JSONArray array = new JSONArray(inline);

				for (int i = 0; i < array.length(); i++) {
					MonobankCurrency currency = new MonobankCurrency();
					JSONObject object = array.getJSONObject(i);
					if (object.getInt("currencyCodeB") == 980) { // UAH
						currency.setCc(getCurrencyCode(object.getInt("currencyCodeA")));
						currency.setBuy(object.getBigDecimal("rateBuy"));
						currency.setSell(object.getBigDecimal("rateSell"));
						currency.setRate(object.getBigDecimal("rateCross"));
						currency.setDate(new Date(object.getLong("date") * 1000));
						monobankRepo.save(currency);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MonobankResponse> getAvgExchRatesForToday() {
		Date curDate = new Date();
		Date prvDate = atStartOfDay(curDate);
		Date nxtDate = atEndOfDay(curDate);
		List<Object[]> avgRateList = monobankRepo.findAvgRatesInDateRange(prvDate, nxtDate);
		List<MonobankResponse> listResponse = new ArrayList<MonobankResponse>();
		for (Object[] avgRate : avgRateList) {
			MonobankResponse response = new MonobankResponse();
			response.setCc((String) avgRate[0]);
			response.setBuy(((BigDecimal) avgRate[1]).floatValue());
			response.setSale(((BigDecimal) avgRate[2]).floatValue());
			response.setRate(((BigDecimal) avgRate[3]).floatValue());
			listResponse.add(response);
		}
		return listResponse;
	}

	public List<MonobankResponse> getAvgExchRatesForPeriod(String prevDate, String nextDate) {
		Date prvDate = atStartOfDay(geDatefromString(prevDate, new SimpleDateFormat("dd/MM/yyyy")));
		Date nxtDate = atEndOfDay(geDatefromString(nextDate, new SimpleDateFormat("dd/MM/yyyy")));
		List<Object[]> avgRateList = monobankRepo.findAvgRatesInDateRange(prvDate, nxtDate);
		List<MonobankResponse> listResponse = new ArrayList<MonobankResponse>();
		for (Object[] avgRate : avgRateList) {
			MonobankResponse response = new MonobankResponse();
			response.setCc((String) avgRate[0]);
			response.setBuy(((BigDecimal) avgRate[1]).floatValue());
			response.setSale(((BigDecimal) avgRate[2]).floatValue());
			response.setRate(((BigDecimal) avgRate[3]).floatValue());
			listResponse.add(response);
		}
		return listResponse;
	}

}

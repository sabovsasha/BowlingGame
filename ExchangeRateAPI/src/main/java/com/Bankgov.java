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
import response.BankgovResponse;

@Service
public class Bankgov extends Common {

	private final String apiUrl = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
	@Autowired
	BankgovRepository bankgovRepo;

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
					BankgovCurrency currency = new BankgovCurrency();
					JSONObject object = array.getJSONObject(i);
					currency.setCc(object.getString("cc"));
					currency.setRate(object.getBigDecimal("rate"));
					currency.setDate(geDatefromString(object.getString("exchangedate"), new SimpleDateFormat("dd.MM.yyyy")));
					bankgovRepo.save(currency);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BankgovResponse> getAvgExchRatesForToday() {
		Date curDate = new Date();
		Date prvDate = atStartOfDay(curDate);
		Date nxtDate = atEndOfDay(curDate);
		List<Object[]> avgRateList = bankgovRepo.findAvgRatesInDateRange(prvDate, nxtDate);
		List<BankgovResponse> listResponse = new ArrayList<BankgovResponse>();
		for (Object[] avgRate : avgRateList) {
			BankgovResponse response = new BankgovResponse();
			response.setCc((String) avgRate[0]);
			response.setRate(((BigDecimal) avgRate[1]).floatValue());
			listResponse.add(response);
		}
		return listResponse;
	}

	public List<BankgovResponse> getAvgExchRatesForPeriod(String prevDate, String nextDate) {
		Date prvDate = atStartOfDay(geDatefromString(prevDate, new SimpleDateFormat("dd/MM/yyyy")));
		Date nxtDate = atEndOfDay(geDatefromString(nextDate, new SimpleDateFormat("dd/MM/yyyy")));
		List<Object[]> avgRateList = bankgovRepo.findAvgRatesInDateRange(prvDate, nxtDate);
		List<BankgovResponse> listResponse = new ArrayList<BankgovResponse>();
		for (Object[] avgRate : avgRateList) {
			BankgovResponse response = new BankgovResponse();
			response.setCc((String) avgRate[0]);
			response.setRate(((BigDecimal) avgRate[1]).floatValue());
			listResponse.add(response);
		}
		return listResponse;
	}

}

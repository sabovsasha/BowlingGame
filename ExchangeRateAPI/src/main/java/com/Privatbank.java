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
import response.PrivatbankResponse;

@Service
public class Privatbank extends Common {

	private final String apiUrl = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
	@Autowired
	PrivatbankRepository privatbankRepo;

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
					PrivatbankCurrency currency = new PrivatbankCurrency();
					JSONObject object = array.getJSONObject(i);
					currency.setCc(object.getString("ccy"));
					currency.setBuy(object.getBigDecimal("buy"));
					currency.setSale(object.getBigDecimal("sale"));
					currency.setDate(new Date());
					privatbankRepo.save(currency);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PrivatbankResponse> getAvgExchRatesForToday() {
		Date curDate = new Date();
		Date prvDate = atStartOfDay(curDate);
		Date nxtDate = atEndOfDay(curDate);
		List<Object[]> avgRateList = privatbankRepo.findAvgRatesInDateRange(prvDate, nxtDate);
		List<PrivatbankResponse> listResponse = new ArrayList<PrivatbankResponse>();
		for (Object[] avgRate : avgRateList) {
			PrivatbankResponse response = new PrivatbankResponse();
			response.setCc((String) avgRate[0]);
			response.setBuy(((BigDecimal) avgRate[1]).floatValue());
			response.setSale(((BigDecimal) avgRate[2]).floatValue());
			listResponse.add(response);
		}
		return listResponse;
	}

	public List<PrivatbankResponse> getAvgExchRatesForPeriod(String prevDate, String nextDate) {
		Date prvDate = atStartOfDay(geDatefromString(prevDate, new SimpleDateFormat("dd/MM/yyyy")));
		Date nxtDate = atEndOfDay(geDatefromString(nextDate, new SimpleDateFormat("dd/MM/yyyy")));
		List<Object[]> avgRateList = privatbankRepo.findAvgRatesInDateRange(prvDate, nxtDate);
		List<PrivatbankResponse> listResponse = new ArrayList<PrivatbankResponse>();
		for (Object[] avgRate : avgRateList) {
			PrivatbankResponse response = new PrivatbankResponse();
			response.setCc((String) avgRate[0]);
			response.setBuy(((BigDecimal) avgRate[1]).floatValue());
			response.setSale(((BigDecimal) avgRate[2]).floatValue());
			listResponse.add(response);
		}
		return listResponse;
	}

}

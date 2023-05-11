package common;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;
import java.util.Set;

public class Common {

	public static Date geDatefromString(String prevDate, DateFormat sourceFormat) {		
		Date date = null;
		try {
			date = sourceFormat.parse(prevDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	public static Date atStartOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	public static Date atEndOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return localDateTimeToDate(endOfDay);
	}

	public static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static String getCurrencyCode(int numericCode) {
	    Set<Currency> currencies = Currency.getAvailableCurrencies();
	    for (Currency currency : currencies) {
	        if (currency.getNumericCode() == numericCode) {
	            return currency.getCurrencyCode();
	        }
	    }
	    throw new IllegalArgumentException("Currency with numeric code "  + numericCode + " not found");
	}

}

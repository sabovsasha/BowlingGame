package com;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivatbankRepository extends CrudRepository<PrivatbankCurrency, Integer>{
	@Query(value="SELECT cc, AVG(buy) as buy, AVG(sale) as sale FROM privatcurrencies where date between ?1 and ?2 GROUP BY cc ORDER BY cc", nativeQuery=true)
	List<Object[]> findAvgRatesInDateRange(Date prevDate, Date nextDate);
	
}

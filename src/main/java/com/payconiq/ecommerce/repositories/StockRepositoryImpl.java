
package com.payconiq.ecommerce.repositories;

import static com.payconiq.ecommerce.util.EcommerceUtils.getCurrentDateTimeStr;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.payconiq.ecommerce.beans.Stock;

@Component("stockRepository")
public class StockRepositoryImpl implements StockRepository {

	ConcurrentHashMap<Long, Stock> stocks;

	StockRepositoryImpl() {
		stocks = new ConcurrentHashMap<>();
		String currentDateTimeStr = getCurrentDateTimeStr();
		Stock stock1 = new Stock(101l, "BNP", 201.8, currentDateTimeStr);
		stocks.put(stock1.getId(), stock1);
		Stock stock2 = new Stock(102l, "JPMC", 34.6, currentDateTimeStr);
		stocks.put(stock2.getId(), stock2);
		Stock stock3 = new Stock(104l, "BOA", 34.4, currentDateTimeStr);
		stocks.put(stock3.getId(), stock3);
		Stock stock4 = new Stock(105l, "UBS", 34.6, currentDateTimeStr);
		stocks.put(stock4.getId(), stock4);
		Stock stock5 = new Stock(109l, "ANZ", 122.6, currentDateTimeStr);
		stocks.put(stock5.getId(), stock5);

	}

	@Override
	public Stock findByName(String name) {
		Stock stock = null;
		Optional<Entry<Long, Stock>> result = this.stocks.entrySet().stream()
				.filter(e -> e.getValue().getName().equals(name)).findAny();
		if (result.isPresent()) {
			return result.get().getValue();
		}
		return stock;
	}

	@Override
	public Stock findOne(Long stockId) {
		Stock stock = this.stocks.get(stockId);
		return stock;
	}

	@Override
	public void save(Stock newStock) {
		this.stocks.put(newStock.getId(), newStock);
	}

	@Override
	public Collection<Stock> findAll() {
		Collection<Stock> values = this.stocks.values();
		return values;
	}

}
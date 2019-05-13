
package com.payconiq.ecommerce.service;

import java.util.Collection;

import com.payconiq.ecommerce.beans.Stock;

public interface StockService {

	Stock findById(Long id);

	Stock findByName(String name);

	void saveStock(Stock stock);

	void updateStock(Stock stock);

	Collection<Stock> findAllStocks();

	boolean isStockExist(Stock stock);
}

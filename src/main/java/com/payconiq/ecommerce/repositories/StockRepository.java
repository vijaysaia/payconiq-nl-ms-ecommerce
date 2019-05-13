
package com.payconiq.ecommerce.repositories;

import java.util.Collection;

import com.payconiq.ecommerce.beans.Stock;

public interface StockRepository {
	Stock findByName(String name);
	Stock findOne(Long id);
	void save(Stock user);
	Collection<Stock> findAll();
}

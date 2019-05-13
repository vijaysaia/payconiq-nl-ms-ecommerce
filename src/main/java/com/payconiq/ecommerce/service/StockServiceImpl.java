package com.payconiq.ecommerce.service;

import static com.payconiq.ecommerce.util.EcommerceUtils.generateRandomNumber;
import static com.payconiq.ecommerce.util.EcommerceUtils.getCurrentDateTimeStr;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payconiq.ecommerce.beans.Stock;
import com.payconiq.ecommerce.repositories.StockRepository;
import com.payconiq.ecommerce.util.EcommerceConstants;
import com.payconiq.ecommerce.util.EcommerceLogConstants;

import lombok.extern.slf4j.Slf4j;

@Service("stockService")
@Slf4j
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;

	public Stock findById(Long id) {

		log.debug(EcommerceLogConstants.OUT_REQ, EcommerceConstants.System.DB,
				 "findById", id);
		Stock stock = stockRepository.findOne(id);

		log.debug(EcommerceLogConstants.OUT_RES, EcommerceConstants.System.DB,
				 "findById", stock);
		return stock;
	}

	public Stock findByName(String name) {
		log.debug(EcommerceLogConstants.OUT_REQ, EcommerceConstants.System.DB,
				 "findByName", name);
		Stock stock = stockRepository.findByName(name);

		log.debug(EcommerceLogConstants.OUT_RES, EcommerceConstants.System.DB,
				 "findByName", stock);

		return stock;
	}

	public void saveStock(Stock stock) {
		log.debug(EcommerceLogConstants.OUT_REQ, EcommerceConstants.System.DB,
				 "saveStock", stock);

		if (stock.getId() == null) {
			stock.setId(generateRandomNumber());
		}
		stock.setTimestamp(getCurrentDateTimeStr());
		stockRepository.save(stock);
		log.debug(EcommerceLogConstants.OUT_RES, EcommerceConstants.System.DB,
				 "saveStock", "Success");
	}

	public void updateStock(Stock stock) {
		log.debug(EcommerceLogConstants.OUT_REQ, EcommerceConstants.System.DB,
				 "updateStock", stock);
		saveStock(stock);
		log.debug(EcommerceLogConstants.OUT_RES, EcommerceConstants.System.DB,
				 "updateStock", "Success");
	}

	public Collection<Stock> findAllStocks() {
		log.debug(EcommerceLogConstants.OUT_REQ, EcommerceConstants.System.DB,
				 "findAllStocks", StringUtils.EMPTY);
		Collection<Stock> stocks = stockRepository.findAll();
		
		log.debug(EcommerceLogConstants.OUT_RES, EcommerceConstants.System.DB,
				 "findAllStocks", stocks);
		return stocks;
	}

	public boolean isStockExist(Stock stock) {
		log.debug(EcommerceLogConstants.OUT_REQ, EcommerceConstants.System.DB,
				 "isStockExist", stock);
		boolean isStockExists = findByName(stock.getName()) != null;

		log.debug(EcommerceLogConstants.OUT_RES, EcommerceConstants.System.DB,
				 "isStockExist", Boolean.valueOf(isStockExists));
		return isStockExists;
	}

}
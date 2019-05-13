package com.payconiq.ecommerce.controller;

import static com.payconiq.ecommerce.util.EcommerceUtils.*;

import static com.payconiq.ecommerce.util.EcommerceConstants.*;
import static com.payconiq.ecommerce.util.EcommerceUtils.getCurrentDateTimeStr;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.payconiq.ecommerce.beans.ErrorDetails;
import com.payconiq.ecommerce.beans.Stock;
import com.payconiq.ecommerce.commons.PerfLog;
import com.payconiq.ecommerce.exception.EcommerceException;
import com.payconiq.ecommerce.service.StockService;
import com.payconiq.ecommerce.util.CustomErrorType;
import com.payconiq.ecommerce.util.EcommerceConstants;
import com.payconiq.ecommerce.util.EcommerceLogConstants;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class EcommerceRestApiController {

	@Autowired
	StockService stockService;

	// -------------------Retrieve All Stocks---------------------------------------------

	@PerfLog
	@RequestMapping(value = "/stocks/", method = RequestMethod.GET)
	public ResponseEntity<?> listAllStocks(@RequestHeader(value = UUID_HEADER, required = false) String uuid) {

		log.debug(EcommerceLogConstants.IN_REQ, MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME));
		
		Collection<Stock> stocks = null;
		try {
			stocks = stockService.findAllStocks();
		} catch (IllegalArgumentException iae) {
			log.error(EcommerceLogConstants.EX, "listAllStocks", "IllegalArgumentException", iae.getMessage(), iae,
					iae);
			return prepareResponse(iae, uuid);
		} catch (Exception e) {
			log.error(EcommerceLogConstants.EX, "listAllStocks", "Exception", e.getMessage(), e, e);
			return prepareResponse(e, uuid);
		}
		log.debug(EcommerceLogConstants.IN_RES, MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME),
				stocks);
		return new ResponseEntity<Collection<Stock>>(stocks,addResponseHeaders(uuid), HttpStatus.OK);

	}

	// -------------------Retrieve Single Stock------------------------------------------

	@PerfLog
	@RequestMapping(value = "/stocks/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getStock(@PathVariable("id") long id,
			@RequestHeader(value = UUID_HEADER, required = false) String uuid) {
		log.debug(EcommerceLogConstants.IN_REQ, MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME),
				id);
		Stock stock = null;
		try {
			stock = stockService.findById(id);
			if (stock == null) {
				log.error("Stock with id {} not found.", id);
				return new ResponseEntity<>(new CustomErrorType("Stock with id " + id + " not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (IllegalArgumentException iae) {
			log.error(EcommerceLogConstants.EX, "getStock", "IllegalArgumentException", iae.getMessage(), iae, iae);
			return prepareResponse(iae, uuid);
		} catch (Exception e) {
			log.error(EcommerceLogConstants.EX, "getStock", "Exception", e.getMessage(), e, e);
			return prepareResponse(e, uuid);
		}
		log.debug(EcommerceLogConstants.IN_RES, MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME),
				stock);
		return new ResponseEntity<Stock>(stock,addResponseHeaders(uuid), HttpStatus.OK);
	}

	// -------------------Create a Stock-------------------------------------------

	@PerfLog
	@RequestMapping(value = "/stocks/", method = RequestMethod.POST)
	public ResponseEntity<?> createStock(@RequestHeader(value = UUID_HEADER, required = false) String uuid,
			@RequestBody @Valid Stock stock, UriComponentsBuilder ucBuilder) {
		log.debug(EcommerceLogConstants.IN_REQ, MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME),
				stock);
		try {
			if (stockService.isStockExist(stock)) {
				log.error("Unable to create. A Stock with name {} already exist", stock.getName());
				return new ResponseEntity<>(
						new CustomErrorType(
								"Unable to create. A Stock with name " + stock.getName() + " already exist."),
						HttpStatus.CONFLICT);
			}
			stockService.saveStock(stock);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/api/stocks/{id}").buildAndExpand(stock.getId()).toUri());
			log.debug(EcommerceLogConstants.IN_RES, MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME),
					HttpStatus.CREATED);
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		} catch (IllegalArgumentException iae) {
			log.error(EcommerceLogConstants.EX, "createStock", "IllegalArgumentException", iae.getMessage(), iae, iae);
			return prepareResponse(iae, uuid);
		} catch (Exception e) {
			log.error(EcommerceLogConstants.EX, "createStock", "Exception", e.getMessage(), e, e);
			return prepareResponse(e, uuid);
		}
		

	}

	// ------------------- Update a Stock ------------------------------------------------

	
	@PerfLog
	@RequestMapping(value = "/stocks/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateStock(@PathVariable("id") long id,
			@RequestHeader(value = UUID_HEADER, required = false) String uuid, @RequestBody Stock stock) {

		log.debug(EcommerceLogConstants.IN_REQ, MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME),
				stock);
		try {

			Stock currentStock = stockService.findById(id);
			if (currentStock == null) {
				log.error("Unable to update. Stock with id {} not found.", id);
				return new ResponseEntity<>(new CustomErrorType("Unable to upate. Stock with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}
			currentStock.setName(stock.getName());
			currentStock.setCurrentPrice(stock.getCurrentPrice());
			currentStock.setTimestamp(getCurrentDateTimeStr());

			stockService.updateStock(currentStock);
			log.debug(EcommerceLogConstants.IN_RES, MDC.get(SYSTEM_NAME), MDC.get(INTERFACE_NAME),
					HttpStatus.OK);
			return new ResponseEntity<Stock>(currentStock,addResponseHeaders(uuid), HttpStatus.OK);
		} catch (IllegalArgumentException iae) {
			log.error(EcommerceLogConstants.EX, "updateStock", "IllegalArgumentException", iae.getMessage(), iae, iae);
			return prepareResponse(iae, uuid);
		} catch (Exception e) {
			log.error(EcommerceLogConstants.EX, "updateStock", "Exception", e.getMessage(), e, e);
			return prepareResponse(e, uuid);
		}

	}

	private ResponseEntity<?> prepareResponse(Throwable t, String uuid) {
		HttpStatus httpStatus = null;

		Set<ErrorDetails> errorDetails = null;
		if (t instanceof IllegalArgumentException) {
			httpStatus = HttpStatus.BAD_REQUEST;
			errorDetails = new HashSet<>();
			errorDetails.add(new ErrorDetails(null, null, null, t.getMessage()));
		} else if (t instanceof EcommerceException) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			EcommerceException pe = (EcommerceException) t;
			errorDetails = pe.getErrors();
		} else if (t instanceof Throwable) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			errorDetails = new HashSet<>();
			errorDetails.add(new ErrorDetails(null, null, null, t.getMessage()));
		}
		HttpHeaders respHeaders = addResponseHeaders(uuid);
		return new ResponseEntity<>(errorDetails, respHeaders, httpStatus);

	}

}
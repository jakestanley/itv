package uk.co.jakestanley.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jakestanley.data.Offer;
import uk.co.jakestanley.data.Sku;
import uk.co.jakestanley.exceptions.BadOfferException;
import uk.co.jakestanley.exceptions.EntityExistsException;
import uk.co.jakestanley.exceptions.NotFoundException;

@Service("offerService")
public class OfferService {

	@Autowired
	StockService stockService;
	
	/**
	 * Maps SKU name strings to offers
	 */
	private Map<String, Offer> offers = new HashMap<>();
	
	public Offer addOffer(Offer offer) 
			throws EntityExistsException, NotFoundException, BadOfferException {
		
		String skuName = offer.getSku();
		
		// check that an offer doesn't already exist for the provided SKU
		if(offers.containsKey(offer)){
			throw new EntityExistsException(
					"An offer exists for SKU \"" + skuName + "\""); 
		}
		
		// check that the provided offer SKU exists
		if(!stockService.exists(skuName)){
			throw new NotFoundException(
					"Submitted offer contained non-existent SKU " +
					"\"" + skuName + "\"");
		}
		
		// check offer price to protect from divide by zero
		if(offer.getPrice() < 1){
			throw new BadOfferException("Submitted offer had an offer price less "
					+ "than zero. This is not allowed.");
		}
		
		// as above but for quantities
		if(offer.getQuantity() < 1){
			throw new BadOfferException("Submitted offer had a quantity less "
					+ "than zero. This is not allowed.");
		}
		
		offers.put(skuName, offer);
		
		return offer;
	}

	/**
	 * Gets the special offer for an SKU if one is available. Returns null if 
	 * one is not available.
	 * @param sku
	 * @return
	 */
	public Offer getOffer(String sku) {
		Offer offer = offers.get(sku);
		return offer;
	}
	
	/**
	 * Gets the special offer for an SKU if one is available. Returns null if 
	 * one is not available.
	 * @param sku
	 * @return
	 */
	public Offer getOffer(Sku sku) {
		return getOffer(sku.getName());
	}
	
}

package uk.co.jakestanley.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import uk.co.jakestanley.data.Sku;
import uk.co.jakestanley.exceptions.EntityExistsException;
import uk.co.jakestanley.exceptions.NotFoundException;

//import uk.co.jakestanley.services.data.Sku;

@Service("stockService")
public class StockService {

	private Map<String, Sku> skus = new HashMap<String, Sku>();

	public Sku createSku(Sku sku) throws EntityExistsException {
		
		String name = sku.getName();
		
		if (skus.containsKey(name)) {
			throw new EntityExistsException(
					"SKU \"" + name + "\" already exists");
		}

		skus.put(name, sku);
		
		return sku;
	}
	
	/**
	 * Returns an SKU or throws an exception if one does not exist with the 
	 * provided name.
	 * @param name
	 * @return
	 * @throws NotFoundException
	 */
	public Sku getSku(String name) throws NotFoundException {
		
		Sku sku = skus.get(name);
		
		if(sku == null){
			throw new NotFoundException(
					"An SKU with that name could not be found");
		}
		
		return sku;
	}

	public boolean exists(String skuName) {
		return skus.containsKey(skuName);
	}
}

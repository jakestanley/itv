package uk.co.jakestanley.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.co.jakestanley.data.Sku;
import uk.co.jakestanley.exceptions.EntityExistsException;
import uk.co.jakestanley.exceptions.NotFoundException;
import uk.co.jakestanley.services.StockService;

@RestController
@RequestMapping(value="/sku")
public class SkuController {
	
	@Autowired
	StockService stockService;
	
	/**
	 * Submit a single SKU
	 * @param sku
	 */
	@RequestMapping(method=RequestMethod.POST)
	private Sku addSku(@RequestBody Sku sku){
		try {
			stockService.createSku(sku);
		} catch (EntityExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return sku;
	}
	
//	/**
//	 * Return an SKU whose name matches the provided parameter
//	 * @param name
//	 */
//	@RequestMapping(method=RequestMethod.GET, value="/{name}")
//	private Sku getSingleSku(@PathVariable("name") String name){
//		try {
//			stockService.getSku(name);
//		} catch (NotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}	
}
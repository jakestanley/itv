package uk.co.jakestanley;

import java.util.HashSet;
import java.util.Set;

import net.minidev.json.JSONObject;
import uk.co.jakestanley.data.Offer;
import uk.co.jakestanley.data.Sku;

public final class TestDataUtils {

	/**
	 * Create set of dummy SKU objects. These are predefined and will work 
	 * with the dummy offers created by ::createDummyOffers().
	 * @return
	 */
    public static Set<Sku> createDummySkus() {
    	
        Set<Sku> dummies = new HashSet<Sku>();
        
        // add the test SKUs
        dummies.add(new Sku("A",50));
        dummies.add(new Sku("B",30));
        dummies.add(new Sku("C",20));
        dummies.add(new Sku("D",15));
        
        return dummies;
    }

    public static Set<Offer> createDummyOffers() {
        
        Set<Offer> dummies = new HashSet<Offer>();
        
        // add the test offers
        dummies.add(new Offer("A",3,130));
        dummies.add(new Offer("B",2,45));
        
        return dummies;
    }
    
    /**
     * Convert an SKU to a JSONObject for testing the Spring MVC controller
     * @param sku
     * @return
     */
    public static JSONObject toJsonObject(Sku sku) {
    	
    	JSONObject jsonSku = new JSONObject();
    	
    	jsonSku.put("name", sku.getName());
    	jsonSku.put("price", sku.getPrice());
    	
    	return jsonSku;
    }
    
    public static JSONObject toJsonObject(Offer offer) {
    	JSONObject jsonOffer = new JSONObject();
    	
    	jsonOffer.put("sku", 		offer.getSku());
    	jsonOffer.put("price", 		offer.getPrice());
    	jsonOffer.put("quantity", 	offer.getQuantity());
    	
    	return jsonOffer;
    }
}

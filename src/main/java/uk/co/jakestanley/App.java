package uk.co.jakestanley;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.NonNull;

/**
 * Checkout
 * @author jake.stanley
 */
public class App {

	private static final String DEFAULT_SKUS_PATH 	= "_skus.csv";
	private static final String DEFAULT_OFFERS_PATH = "_offers.csv";
    private static final String CSV_SEPARATOR 		= ",";

    private int cartTotal;
    private Map<String, Integer> cart;
    private Map<String, Integer> skus;
    private Map<String, Offer> offers;

    public App(String skusPath, String offersPath){       

        // initialise the cart
        cart = new HashMap<>();
        cartTotal = 0;

        try {
            // load SKUs file
			loadSkus(skusPath);
			
	        // load offers file
	        loadOffers(offersPath);
	        
	        openCheckout();
	        calculateCartTotal();
	        
	        // print the cart total
	        String formatted = getFormattedCartTotal();
	        System.out.println("Cart total: " + formatted);

		} catch (IOException e) {
			System.err.println("Error loading files: " + e);
		}

    }

    void addSku(String rowStr) {
        String[] sku = rowStr.split(CSV_SEPARATOR);
        
        // check array length matches that of an sku
        if(sku.length == 2){
        	
        	String name = sku[0];
        	
        	// return on empty string
        	if(name.isEmpty()){
        		return;
        	}
        	
        	// return if one exists already
        	if(this.skus.get(name) != null){
        		return; // could also throw an exception
        	}
        	
        	// put the sku and price in the skus map
        	try {
        		Integer price = Integer.parseInt(sku[1]);
        		
        		// put sku in the map
        		this.skus.put(name, price);
        	} catch (NumberFormatException e) {
        		System.err.println(
        				"Failed to parse numeric values in SKUs file: " + e);
        	}
        }
    }

    void addOffer(String rowStr) {
        String[] offer = rowStr.split(CSV_SEPARATOR);
        
        // check array length matches that of an offer
        if(offer.length == 3){ // Offer.class.getFields())
        	
        	String sku = offer[0];
        	
        	// return on empty string
        	if(sku.isEmpty()){
        		return;
        	}
        	
        	// return if an offer already exists
        	if(this.offers.get(sku) != null){
        		return;
        	}
        	
        	// put offer in the map if a number format exception isn't thrown 
        	try {
        		// parse values and create offer object
        		Integer quantity = Integer.parseInt(offer[1]);
        		
        		// defend against DIV/0
        		if(quantity == 0){
        			return;
        		}
        		
            	Integer price = Integer.parseInt(offer[2]);
            	Offer o = new Offer(quantity, price);
            	
            	// put offer in the map
            	this.offers.put(sku, o);
        	} catch (NumberFormatException e) {
        		System.err.println(
        				"Failed to parse numeric values in offers file:" + e);
        	}
        }
    }

    /**
     * Applies rows in a file to the provided function
     * @param path
     * @param func
     * @throws IOException
     */
    void load(@NonNull String path, @NonNull Consumer<String> func) throws IOException {
        Stream<String> rowStream = null;
        rowStream = Files.lines(Paths.get(path));
        rowStream.forEach(func);
        rowStream.close();
    }

    void loadSkus(@NonNull String path) throws IOException {
    	
        skus = new HashMap<String, Integer>();
        load(path, this::addSku);
    }

    void loadOffers(@NonNull String path) throws IOException {
    	
        offers = new HashMap<String, Offer>();
        load(path, this::addOffer);
    }

    void addItemToCart(String item) {
		// check the item is a valid SKU
	    if(skus.get(item) != null){
	    	
	    	// get the current cart quantity for selected item
	    	Integer quantity = cart.get(item);
	
	    	if(quantity == null){ // quantity = (quantity == null) ? 1 : quantity++;
	    		quantity = 1;
	    	} else {
	    		quantity++;
	    	}
	    	
	    	skus.put(item, quantity);
	    }
	}

	void openCheckout() {
		System.out.println(
				"The checkout is open. Type an SKU and press enter to add that "
				+ "item to the cart. Type \"/\" and press enter to close the "
				+ "checkout.");
		
		// open input reader
		Scanner scanner = new Scanner(System.in);
		
		// scan input
		while(scanner.hasNext()){
			String item = scanner.nextLine();
			
			// Close the checkout
			if(item.isEmpty()){
				break;
			} else {
				addItemToCart(item);
			}
		}
		
		scanner.close();
	}

	void calculateCartTotal(){
    	
    	this.cartTotal = 0;

    	// iterate through the cart and figure out the offers
    	cart.forEach((sku,cartQuantity) -> {
    		int basePrice = skus.get(sku);
    		Offer offer = offers.get(sku);
    		
    		int productTotal = 0;
    		
    		if(offer == null){ // no offer exists. just tally up using the base cost
    			productTotal = (cartQuantity * basePrice);
    		} else {
    			int offerQuantity 	= offer.getQuantity();
    			int offerPrice 		= offer.getPrice();
    			
    			// figure out how many times the offer is applied
    			int offerTotal = ((int) Math.floor(cartQuantity / offerQuantity)) * offerPrice;
    			
    			// figure out the price for remaining items
    			int nonOfferTotal = (cartQuantity % offerQuantity) * basePrice;
    			
    			// add to cart
    			productTotal = offerTotal + nonOfferTotal;
    		}
    		
    		this.cartTotal += productTotal;
    	});
    }

	private String getFormattedCartTotal() {
		NumberFormat fmt = NumberFormat.getCurrencyInstance();
		return fmt.format(cartTotal / 100);
	}

	public static void main(String[] args) {
	    System.out.println("Welcome to the checkout!");
	    new App(DEFAULT_SKUS_PATH, DEFAULT_OFFERS_PATH);
	}

}

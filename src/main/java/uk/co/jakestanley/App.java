package uk.co.jakestanley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Checkout
 * 
 * @author jake.stanley
 */
@SpringBootApplication
public class App {
//
//		try {
//			// load SKUs file
//			loadSkus(skusPath);
//
//			// load offers file
//			loadOffers(offersPath);
//
//			openCheckout();
//			calculateCartTotal();
//
//			// print the cart total
//			String formatted = getFormattedCartTotal();
//			System.out.println("Cart total: " + formatted);
//
//		} catch (IOException e) {
//			System.err.println("Error loading files: " + e);
//		}
//
//	}
//

//	/**
//	 * Applies rows in a file to the provided function
//	 * 
//	 * @param path
//	 * @param func
//	 * @throws IOException
//	 */
//	void load(@NonNull String path, @NonNull Consumer<String> func) throws IOException {
//		Stream<String> rowStream = null;
//		rowStream = Files.lines(Paths.get(path));
//		rowStream.forEach(func);
//		rowStream.close();
//	}
//
//	void loadSkus(@NonNull String path) throws IOException {
//
//		skus = new HashMap<String, Integer>();
//		load(path, this::addSku);
//	}
//
//	void loadOffers(@NonNull String path) throws IOException {
//
//		offers = new HashMap<String, Offer>();
//		load(path, this::addOffer);
//	}
//
//	void addItemToCart(String item) {
//		// check the item is a valid SKU
//		if (skus.get(item) != null) {
//
//			// get the current cart quantity for selected item
//			Integer quantity = cart.get(item);
//
//			if (quantity == null) { // quantity = (quantity == null) ? 1 :
//									// quantity++;
//				quantity = 1;
//			} else {
//				quantity++;
//			}
//
//			skus.put(item, quantity);
//		}
//	}
//
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

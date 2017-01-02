package uk.co.jakestanley.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jakestanley.data.Cart;
import uk.co.jakestanley.data.Item;
import uk.co.jakestanley.data.Offer;
import uk.co.jakestanley.data.Sku;
import uk.co.jakestanley.exceptions.NotFoundException;

@Service("cartService")
public class CartService {

	@Autowired
	private OfferService offerService;

	private Map<Long, Cart> carts = new HashMap<Long, Cart>();
	
	private Long cartIncrementor = 1L;

	public Cart createCart() {
		
		Long cartId = cartIncrementor++;
		Cart cart = new Cart(cartId);
		carts.put(cartId, cart);
		
		return cart;
	}
	
	/**
	 * Get reference to cart object
	 * @param cartId
	 * @return
	 * @throws NotFoundException 
	 */
	public Cart getCart(Long cartId) throws NotFoundException {
		
		// check that the cart exists for this ID
		if(!carts.containsKey(cartId)){
			// cart couldn't be found
			throw new NotFoundException();
		}
		
		Cart cart = carts.get(cartId);

		return cart;
	}
	
	/**
	 * Add items to a cart. If the item already exists, the quantity is added 
	 * to the existing cart item quantity
	 * @param cartId
	 * @param item
	 * @return
	 */
	public Cart addItemToCart(Long cartId, Item item) throws NotFoundException {

		Cart cart = getCart(cartId);
		Map<String, Item> items = cart.getItems();
		
		// if an item with matching SKU is in the cart, add the quantity
		String skuName = item.getSku().getName();
		if(items.containsKey(skuName)){
			items.get(skuName).addToQuantity(item.getQuantity());
		} else {
			items.put(skuName, item);
		}
		
		return cart;
	}

	/**
	 * Return cart total value after offers have been calculated
	 * @param cartId
	 * @return
	 * @throws NotFoundException
	 */
	public int getCartCheckout(Long cartId) throws NotFoundException {

		// check the cart exists
		if(!carts.containsKey(cartId)){
			throw new NotFoundException(
					"A cart with ID \"" + cartId + "\" could not be found.");
		}
		
		// get the cart
		Cart cart = carts.get(cartId);
		
		// initialise cart total value to zero
		int cartTotal = 0;
	
		// iterate through the cart and figure out the offers
		Collection<Item> items = cart.getItems().values();
		for (Iterator<Item> it = items.iterator(); it.hasNext();) {
			
			// set any variables we need
			Item item = it.next();
			Sku sku = item.getSku();
			int basePrice = sku.getPrice();
			int cartQuantity = item.getQuantity();

			// total value for this cart item
			int productTotal = 0;
			
			// get available offer (if any)
			Offer offer = offerService.getOffer(sku);

			if (offer == null) { 
				// no offer exists. use the base SKU price
				productTotal = (cartQuantity * basePrice);
			} else {
				int offerQuantity = offer.getQuantity();
				int offerPrice = offer.getPrice();

				// get the price of offer applicable items by 
				// calculating cart quantity divided by offer quantity.
				// the result may not be an integer, so round that down 
				// and multiply it by the offer price
				int offerTotal = ((int) Math.floor(cartQuantity / offerQuantity)) * offerPrice;

				// figure out the price for remaining items
				// remainder after modulo cart quantity/offer quantity 
				// multiplied by the base item cost will give the cost 
				// for the remaining quantity for this item
				int nonOfferTotal = (cartQuantity % offerQuantity) * basePrice;

				// add to cart
				productTotal = offerTotal + nonOfferTotal;
			}

			// add calculated product total to the cart total
			cartTotal += productTotal;
			
		}

		return cartTotal;
	}
}

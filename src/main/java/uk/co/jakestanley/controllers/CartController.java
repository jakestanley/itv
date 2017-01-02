package uk.co.jakestanley.controllers;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.co.jakestanley.controllers.data.CartDto;
import uk.co.jakestanley.controllers.data.ItemDto;
import uk.co.jakestanley.data.Cart;
import uk.co.jakestanley.data.Item;
import uk.co.jakestanley.data.Sku;
import uk.co.jakestanley.exceptions.NotFoundException;
import uk.co.jakestanley.services.CartService;
import uk.co.jakestanley.services.StockService;

@RestController
@RequestMapping(value="/cart")
public class CartController {

	@Autowired
	CartService cartService;
	
	@Autowired
	StockService stockService;

	@RequestMapping(value="/new", method=RequestMethod.GET)
	private CartDto createCart() {
		
		Cart cart = cartService.createCart();
		CartDto dto = mapToDto(cart);
		return dto;
	}
	
	@RequestMapping(value="/{cartId}/item", method=RequestMethod.POST)
	private ItemDto addItemToCart(	@PathVariable("cartId") Long cartId, 
									@RequestBody ItemDto item) {

		try {
			cartService.addItemToCart(cartId, mapToEntity(item));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return item;
	}
	
	/**
	 * End the checkout process and return a formatted checkout value string
	 * @param cartId
	 * @return
	 */
	@RequestMapping(value="/{cartId}/checkout")
	private String getCartCheckout(@PathVariable("cartId") Long cartId) {

		String formattedValue = "";

		try {
			int cartValue = cartService.getCartCheckout(cartId);
			formattedValue = getFormattedPrice(cartValue);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formattedValue;
	}

	private Item mapToEntity(ItemDto dto) throws NotFoundException {
		
		Item item = new Item();
		
		// get the sku object from the stock service and set it
		Sku itemSku = stockService.getSku(dto.getSku());
		item.setSku(itemSku);
		item.setQuantity(dto.getQuantity());
		
		return item;
	}

	private static CartDto mapToDto(Cart cart) {
		
		// convert cart item objects to DTOs
		Set<ItemDto> items = new HashSet<ItemDto>();
		cart.getItems().values().forEach(i -> items.add(new ItemDto(i)));
		
		CartDto dto = new CartDto(cart.getCartId(), items);

		return dto;
	}
	
	/**
	 * Convert Integer into a commonly formatted price string
	 * @param price
	 * @return
	 */
	private static String getFormattedPrice(int price) {

		NumberFormat fmt = NumberFormat.getCurrencyInstance();
		return fmt.format(price / 100);
	}
}

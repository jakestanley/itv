package uk.co.jakestanley.controllers;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private ResponseEntity<ItemDto> addItemToCart(
			@PathVariable("cartId") Long cartId, @RequestBody ItemDto item) {
		
		try {
			cartService.addItemToCart(cartId, mapToEntity(item));
		} catch (NotFoundException e) {
			e.printStackTrace(); // TODO logger
			return new ResponseEntity<ItemDto>(item, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ItemDto>(item, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{cartId}", method=RequestMethod.GET)
	private ResponseEntity<CartDto> getCart(
			@PathVariable("cartId") Long cartId) {
		
		try {
			Cart cart = cartService.getCart(cartId);
			CartDto dto = mapToDto(cart);
			return new ResponseEntity<CartDto>(dto, HttpStatus.OK);
		} catch (NotFoundException e) {
			e.printStackTrace(); // TODO logger
			return new ResponseEntity<CartDto>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * End the checkout process and return a formatted checkout value string
	 * @param cartId
	 * @return
	 */
	@RequestMapping(value="/{cartId}/checkout")
	private ResponseEntity<String> getCartCheckout(@PathVariable("cartId") Long cartId) {

		String formattedValue = "";

		try {
			int cartValue = cartService.getCartCheckout(cartId);
			formattedValue = getFormattedPrice(cartValue);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>(formattedValue, HttpStatus.OK);
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

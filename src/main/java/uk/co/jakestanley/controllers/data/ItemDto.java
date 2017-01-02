package uk.co.jakestanley.controllers.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.co.jakestanley.data.Item;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemDto {

	public ItemDto(Item i) {
		this.sku = i.getSku().getName();
		this.quantity = i.getQuantity();
	}

	private String sku;
	private Integer quantity;
	
}

package uk.co.jakestanley.controllers.data;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CartDto {
	
	private Long cartId;
	private Set<ItemDto> items = new HashSet<ItemDto>();

}

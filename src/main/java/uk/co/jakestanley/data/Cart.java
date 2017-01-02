package uk.co.jakestanley.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Cart implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2981247148999533469L;
	
	public Cart(Long i) {
		this.cartId = i;
		this.items = new HashMap<String, Item>();
	}

	private Long cartId;
	private Map<String, Item> items;
	
}

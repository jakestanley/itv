package uk.co.jakestanley.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Item implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5336077505271034640L;

	private Sku sku;
	private Integer quantity;
	
	public void addToQuantity(Integer add) {
		this.quantity += add;
	}
	
}

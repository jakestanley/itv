package uk.co.jakestanley.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Offer implements Serializable {
	
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5207257580515784820L;

	private static final int MIN_QUANTITY = 1;
	private static final int MIN_PRICE = 1;

	private String name;
	private String sku;
    private Integer quantity;
    private Integer price;

}
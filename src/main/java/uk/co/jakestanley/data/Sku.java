package uk.co.jakestanley.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Sku implements Serializable {
	
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7973502927255820212L;

	private String name;
	private Integer price; 

}

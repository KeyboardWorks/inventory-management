package keyboard.works.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsReceiptItemRequest {

	@NotNull(message = "Receipted is mandatory")
	@Positive(message = "Receipted must greather than zero")
	private BigDecimal receipted;
	
	@NotNull(message = "Price is mandatory")
	@Positive(message = "Price must greather than zero")
	private BigDecimal price;
	
	@NotNull(message = "Product To Base is mandatory")
	private String product;
	
}

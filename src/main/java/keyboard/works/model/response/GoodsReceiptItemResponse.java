package keyboard.works.model.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsReceiptItemResponse {

	private String id;
	
	private BigDecimal receipted;
	
	private BigDecimal price;
	
	private ProductResponse product;
	
}

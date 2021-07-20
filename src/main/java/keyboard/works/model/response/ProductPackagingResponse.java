package keyboard.works.model.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPackagingResponse {

	private BigDecimal quantityToBase;
	
	private UnitOfMeasureResponse unitOfMeasure;
	
}
